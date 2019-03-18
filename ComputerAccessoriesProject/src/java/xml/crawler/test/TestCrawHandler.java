/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.crawler.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import xml.crawler.CrawlHandler;
import xml.formater.XMLFormater;
import xml.model.CategoryRaw;
import xml.model.ProductDetailRaw;
import xml.model.ProductRaw;
import xml.service.CrawlService;
import xml.utils.FileUtils;
import static xml.utils.FileUtils.PRODUCT_IMG_FOLDER;
import xml.utils.StringUtils;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
public class TestCrawHandler {
    public static void main(String[] args) {
//        testCrawlDalatLaptopCategories();
//        testCrawlDataNamTruongThinhLaptopCategories();

//        testCrawlDataProductDalatLaptop();
//        testCrawlDataProductNamTruongThinhLaptop();

//        testCrawlDataProductDetailDalatLaptop();
//        testCrawlDataProductDetailNamTruongThinhLaptop();

//        testCurrentPath();
        testCrawlImg();
    }
        
    public static void testCrawlDalatLaptopCategories() {
        String url = "https://dalatlaptop.com/";
        String htmlData = CrawlHandler.getDataFromHtml(url);
        htmlData = XMLFormater.removeMiscellaneousTags(htmlData);
        String test = XMLFormater.generateXMLWellForm(htmlData);
        test = XMLFormater.removeInvalidXmlChars(test);
        
        FileUtils.saveIntoFile("testdata.xml", test);
        System.out.println("done save file");
        
        List<CategoryRaw> datas = 
                CrawlHandler.extractData(1, CategoryRaw.class, test)
                    .stream()
                    .map((x) -> (CategoryRaw)x)
                    .collect(Collectors.toList());
        
        System.out.println("datas: " + datas.size());
        
        for(CategoryRaw data : datas) {
            System.out.println("name = " + data.getName() + "; url = " + data.getUrl());
        }
    }
    
    public static void testCrawlDataNamTruongThinhLaptopCategories() {
        String url = "http://namtruongthinhdalat.vn/";
        String htmlData = CrawlHandler.getDataFromHtml(url);
        htmlData = XMLFormater.removeMiscellaneousTags(htmlData);
        String test = XMLFormater.generateXMLWellForm(htmlData);
        test = XMLFormater.removeInvalidXmlChars(test);
        
        FileUtils.saveIntoFile("testdata.xml", test);
        System.out.println("done save file");
        
        List<CategoryRaw> datas = 
                CrawlHandler.extractData(1, CategoryRaw.class, test)
                    .stream()
                    .map((x) -> (CategoryRaw)x)
                    .collect(Collectors.toList());
        
        System.out.println("datas: " + datas.size());
        
        for(CategoryRaw data : datas) {
            System.out.println("name = " + data.getName() + "; url = " + data.getUrl());
        }
    }
    
    public static void testCrawlDataProductDalatLaptop() {
        String url = "https://dalatlaptop.com/product-category/linh-kien-pc/ram-may-ban/";
        String htmlData = CrawlHandler.getDataFromHtml(url);
        htmlData = XMLFormater.removeMiscellaneousTags(htmlData);
        String test = XMLFormater.generateXMLWellForm(htmlData);
        test = XMLFormater.removeInvalidXmlChars(test);
        
        FileUtils.saveIntoFile("testdata.xml", test);
        System.out.println("done save file");
        
        List<ProductRaw> datas = 
                CrawlHandler.extractData(1, ProductRaw.class, test)
                    .stream()
                    .map((x) -> (ProductRaw)x)
                    .collect(Collectors.toList());
        
        System.out.println("datas: " + datas.size());
        
        for(ProductRaw data : datas) {
            System.out.println("name = " + data.getName() + "; "
                    + "imgUrl = " + data.getImgUrl() + "; "
                    + "price = " + data.getPrice() + "; "
                    + "detailUrl = " + data.getDetailUrl());
        }
    }
    
    public static void testCrawlDataProductNamTruongThinhLaptop() {
        String url = "http://namtruongthinhdalat.vn/san-pham-chi-tiet/thiet-bi-an-ninh";
        String htmlData = CrawlHandler.getDataFromHtml(url);
        htmlData = XMLFormater.removeMiscellaneousTags(htmlData);
        String test = XMLFormater.generateXMLWellForm(htmlData);
        test = XMLFormater.removeInvalidXmlChars(test);
        
        FileUtils.saveIntoFile("testdata.xml", test);
        System.out.println("done save file");
        
        List<ProductRaw> datas = 
                CrawlHandler.extractData(2, ProductRaw.class, test)
                    .stream()
                    .map((x) -> (ProductRaw)x)
                    .collect(Collectors.toList());
        
        System.out.println("datas: " + datas.size());
        
        for(ProductRaw data : datas) {
            System.out.println("name = " + data.getName() + "; "
                    + "imgUrl = " + data.getImgUrl() + "; "
                    + "price = " + data.getPrice() + "; "
                    + "detailUrl = " + data.getDetailUrl());
        }
    }
    
    public static void testCrawlDataProductDetailDalatLaptop() {
        String url = "https://dalatlaptop.com/product/may-ban-cu-e8400main-giga-g41-ram-ddr3-2gb-hdd-80gb/";
        String htmlData = CrawlHandler.getDataFromHtml(url);
        htmlData = XMLFormater.removeMiscellaneousTags(htmlData);
        String test = XMLFormater.generateXMLWellForm(htmlData);
        test = XMLFormater.removeInvalidXmlChars(test);
        
        FileUtils.saveIntoFile("testdata.xml", test);
        System.out.println("done save file");
        
        List<ProductDetailRaw> datas = 
                CrawlHandler.extractData(1, ProductDetailRaw.class, test)
                    .stream()
                    .map((x) -> (ProductDetailRaw)x)
                    .collect(Collectors.toList());
        
        System.out.println("datas: " + datas.size());
        
        for(ProductDetailRaw data : datas) {
            System.out.println("description = " + data.getDescription());
        }
    }
    
    public static void testCrawlDataProductDetailNamTruongThinhLaptop() {
        String url = "http://namtruongthinhdalat.vn/san-pham-chi-tiet/camera-ip-megapixel-dahua-2mp-ipc-hfw4239tp-ase";
        String htmlData = CrawlHandler.getDataFromHtml(url);
        htmlData = XMLFormater.removeMiscellaneousTags(htmlData);
        String test = XMLFormater.generateXMLWellForm(htmlData);
        test = XMLFormater.removeInvalidXmlChars(test);
        
        FileUtils.saveIntoFile("testdata.xml", test);
        System.out.println("done save file");
        
        List<ProductDetailRaw> datas = 
                CrawlHandler.extractData(2, ProductDetailRaw.class, test)
                    .stream()
                    .map((x) -> (ProductDetailRaw)x)
                    .collect(Collectors.toList());
        
        System.out.println("datas: " + datas.size());
        
        for(ProductDetailRaw data : datas) {
            System.out.println("description = " + data.getDescription());
        }
    }
    
    public static void testCurrentPath() {
        String tmp = System.getProperty("user.dir");
        System.out.println(tmp);
        String imgPath = StringUtils.concatUrl(tmp, "/" + PRODUCT_IMG_FOLDER);
        System.out.println(imgPath);
    }
    
    public static void testCrawlImg() {
        String url = "https://dalatlaptop.com/uploads/18/08/dell-inspiron-5570-m5i5238w-office365-dai-dien-450x300-600x600.jpg";
        FileUtils.saveProductRawImage(url, "test");
        System.out.println("Done");
    }
}
