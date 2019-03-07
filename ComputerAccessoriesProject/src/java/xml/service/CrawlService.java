/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import xml.crawler.CrawlHandler;
import xml.dao.CategoryDAO;
import xml.dao.CategoryRawDAO;
import xml.dao.CrawlDataConfigurationDAO;
import xml.dao.CrawlDataMappingConfigurationDAO;
import xml.dao.CrawlDomainConfigurationDAO;
import xml.dao.ProductDAO;
import xml.dao.ProductDetailRawDAO;
import xml.dao.ProductRawDAO;
import xml.formater.XMLFormater;
import xml.model.Category;
import xml.model.CategoryRaw;
import xml.model.CrawlDomainConfiguration;
import xml.model.ProductDetailRaw;
import xml.model.ProductRaw;
import xml.thread.CrawlProductDetailRawThread;
import xml.utils.StringUtils;

/**
 *
 * @author admin
 */
public class CrawlService {
    private final CrawlDomainConfigurationDAO domainConfigDao;
    private final CrawlDataConfigurationDAO dataConfigDao;
    private final CrawlDataMappingConfigurationDAO dataMappingDao;
    private final CategoryRawDAO categoryRawDao;
    private final ProductRawDAO productRawDao;
    private final ProductDetailRawDAO productDetailRawDao;
    private final ProductDAO productDao;
    private final CategoryDAO categoryDao;
    
    public CrawlService() {
        domainConfigDao = new CrawlDomainConfigurationDAO();
        dataConfigDao = new CrawlDataConfigurationDAO();
        dataMappingDao = new CrawlDataMappingConfigurationDAO();
        categoryRawDao = new CategoryRawDAO();
        productRawDao = new ProductRawDAO();
        productDetailRawDao = new ProductDetailRawDAO();
        productDao = new ProductDAO();
        categoryDao = new CategoryDAO();
    }
    
    public List<CategoryRaw> crawlAllCategories() {
        List<CategoryRaw> result = new ArrayList<>();
        
        List<CrawlDomainConfiguration> domains = domainConfigDao.getAll(
                "deleted = ?", false);
        
        for(CrawlDomainConfiguration domain : domains) {
            //crawl html
            String htmlStr = CrawlHandler.getDataFromHtml(domain.getInitUrl());
            htmlStr = XMLFormater.removeMiscellaneousTags(htmlStr);
            String xmlStr = XMLFormater.generateXMLWellForm(htmlStr);
            
            //Extract data
            List<CategoryRaw> rawCategories = CrawlHandler
                    .extractData(domain.getId(), CategoryRaw.class, xmlStr)
                    .stream()
                    .map((element) -> (CategoryRaw)element)
                    .collect(Collectors.toList());
            
            //Enrich data
            rawCategories.forEach((rawCategory) -> {
                rawCategory.setDomainId(domain.getId());
                //categoryId = 0 means not bound to a category yet.
                rawCategory.setCategoryId(0);
                
                String url = rawCategory.getUrl();
                url = StringUtils.concatUrl(domain.getInitUrl(), url);
                rawCategory.setUrl(url);
            });
            
            //Process data
            for(CategoryRaw categoryRaw : rawCategories) {
                if(isCategoryRawExisted(categoryRaw) == false) {
                    boolean inserted = categoryRawDao.insert(categoryRaw);
                    if(inserted) {
                        result.add(categoryRaw);
                    }
                    else {
                        System.out.println("Error!! A new category raw cannot inserted");
                    }
                }
            }
        }
        
        return result;
    }
    
    private boolean isCategoryRawExisted(CategoryRaw categoryRaw) {
        CategoryRaw entity = categoryRawDao.getSingle(
                "url = ? and deleted = ?", categoryRaw.getUrl(), false);
        
        if(entity == null) {
            return false;
        }
        return true;
    }
    
    public List<ProductRaw> crawlProductRaw(int categoryRawId, int categoryId) {
        int countNewProducts = 0;
        int countEditedProducts = 0;
        
        List<ProductRaw> newProducts = new ArrayList();
        
        CategoryRaw categoryRaw = categoryRawDao.getById(categoryRawId);
        if(categoryRaw == null) {
            return newProducts;
        }
        Category category = categoryDao.getById(categoryId);
        if(category == null) {
            return newProducts;
        }
        
        CrawlDomainConfiguration domain = domainConfigDao.getById(categoryRaw.getDomainId());
        boolean continueCrawling = true;
        String currentUrl = categoryRaw.getUrl();
        
        while(continueCrawling) {
            //crawl page
            String htmlStr = CrawlHandler.getDataFromHtml(currentUrl);
            htmlStr = XMLFormater.removeMiscellaneousTags(htmlStr);
            String xmlStr = XMLFormater.generateXMLWellForm(htmlStr);
            
            //extract data
            List<ProductRaw> productRaws = CrawlHandler
                    .extractData(domain.getId(), ProductRaw.class, xmlStr)
                    .stream()
                    .map((x) -> (ProductRaw)x)
                    .collect(Collectors.toList());

            //enrich data
            productRaws.forEach((productRaw) -> {
                productRaw.setCategoryRawId(categoryRawId);
                productRaw.setIsNew(true);
                
                String hashContent =
                        productRaw.getName() + 
                        productRaw.getPrice() + 
                        productRaw.getImgUrl();
                int hashCode = StringUtils.hashString(hashContent);
                productRaw.setHashCode(hashCode);
                
                String imgUrl = productRaw.getImgUrl();
                imgUrl = StringUtils.concatUrl(domain.getInitUrl(), imgUrl);
                productRaw.setImgUrl(imgUrl);
                
                String detailUrl = productRaw.getDetailUrl();
                detailUrl = StringUtils.concatUrl(domain.getInitUrl(), detailUrl);
                productRaw.setDetailUrl(detailUrl);
            });
            
            //Process data
            for(ProductRaw productRaw : productRaws) {
                String detailUrl = productRaw.getDetailUrl();
                ProductRaw oldEntity = productRawDao.getProductRawByDetailUrl(detailUrl);
                
                if(oldEntity == null) {
                    boolean inserted = productRawDao.insert(productRaw);
                    if(inserted) {
                        newProducts.add(productRaw);
                        countNewProducts++;
                        
                        //create thread for getting Product Detail
                        CrawlProductDetailRawThread crawlDetailThread = 
                                new CrawlProductDetailRawThread(productRaw.getId());
                        crawlDetailThread.start();
                        
                    }
                    else { 
                        System.out.println("ERROR!! Cannot insert a new product raw");
                    }
                } 
                else {
                    if(oldEntity.getHashCode() != productRaw.getHashCode()) {
                        productRaw.setId(oldEntity.getId());
                        boolean updated = productRawDao.update(productRaw);
                        if(updated) {
                            newProducts.add(productRaw);
                            countEditedProducts++;
                            
                            //create thread for gettig product detail
                            CrawlProductDetailRawThread crawlDetailThread = 
                                    new CrawlProductDetailRawThread(productRaw.getId());
                            crawlDetailThread.start();
                            
                        }
                        else {
                            System.out.println("ERROR!! Cannot update edited product raw");
                        }
                    }
                }
            }
            
            //check continue constain
            currentUrl = CrawlHandler.extractString(xmlStr, domain.getPagingXPathQuery());
            if(currentUrl == null || currentUrl.isEmpty()) {
                continueCrawling = false;
            }
        }
        
        //update categoryRaw status
        categoryRaw.setCategoryId(categoryId);
        categoryRawDao.update(categoryRaw);
        
        return newProducts;
    }
    
    public void crawlProductDetailRaw(int productRawId) {
        ProductRaw productRaw = productRawDao.getById(productRawId);
        if(productRaw == null) return;
        
        CategoryRaw categoryRaw = categoryRawDao.getById(productRaw.getCategoryRawId());
        if(categoryRaw == null) return;
        
        CrawlDomainConfiguration domain = domainConfigDao.getById(categoryRaw.getDomainId());
        if(domain == null) return;
        
        //Get Html Data
        String htmlStr = CrawlHandler.getDataFromHtml(productRaw.getDetailUrl());
        htmlStr = XMLFormater.removeMiscellaneousTags(htmlStr);
        String xmlStr = XMLFormater.generateXMLWellForm(htmlStr);
                
        //Extract data
        List<ProductDetailRaw> productDetailRaws = CrawlHandler
                .extractData(domain.getId(), ProductDetailRaw.class, xmlStr)
                .stream()
                .map((x) -> (ProductDetailRaw)x)
                .collect(Collectors.toList());
        if(productDetailRaws.size() == 0) return; 
        ProductDetailRaw productDetailRaw = productDetailRaws.get(0);
        
        //Enrich data
        productDetailRaw.setProductRawId(productRawId);
                
        //Process data
        ProductDetailRaw oldEntity = productDetailRawDao.getByProductRawId(productRawId);
        if(oldEntity == null) {
            boolean inserted = productDetailRawDao.insert(productDetailRaw);
            if(inserted == false) {
                System.out.println("ERROR!! Cannot insert a new product detail raw");
            }
        }
        else {
            if(!oldEntity.getDescription().equals(productDetailRaw.getDescription())) {
                //different
                productDetailRaw.setId(oldEntity.getId());
                boolean updated = productDetailRawDao.update(productDetailRaw);
                if(!updated) {
                    System.out.println("ERROR!! Cannot Update an edited product detail raw");
                }
            }
        }
    }
}
