/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service;

import java.util.List;
import xml.dao.CategoryRawDAO;
import xml.dao.ProductDAO;
import xml.dao.ProductDetailRawDAO;
import xml.dao.ProductRawDAO;
import xml.model.CategoryRaw;
import xml.model.Product;
import xml.model.ProductDetailRaw;
import xml.model.ProductRaw;

/**
 *
 * @author admin
 */
public class ProductService {
    
    private final ProductRawDAO productRawDao;
    private final ProductDetailRawDAO productDetailRawDao;
    private final ProductDAO productDao;
    private final CategoryRawDAO categoryRawDao;
    
    public ProductService() {
        productRawDao = new ProductRawDAO();
        productDetailRawDao = new ProductDetailRawDAO();
        productDao = new ProductDAO();
        categoryRawDao = new CategoryRawDAO();
    }
    
    public boolean updateNewProducts(int categoryRawId) {
        List<ProductRaw> newProductRaws = productRawDao.getNewProductRawsByCategoryRawId(categoryRawId);
        CategoryRaw categoryRaw = categoryRawDao.getById(categoryRawId);
        
        if(newProductRaws == null || newProductRaws.size() == 0) {
            return true;
        }
        
        for(ProductRaw productRaw : newProductRaws) {
            ProductDetailRaw productDetailRaw = 
                    productDetailRawDao.getByProductRawId(productRaw.getId());
            Product oldEntity = productDao.getProductByProductRawId(productRaw.getId());
            
            if(oldEntity == null) {
                //insert
                Product newProduct = new Product();
                newProduct.setCategoryId(categoryRaw.getCategoryId());
                newProduct.setImageUrl(productRaw.getImgUrl());
                newProduct.setName(productRaw.getName());
                newProduct.setPrice(productRaw.getPrice());
                newProduct.setProductRawId(productRaw.getId());
                if(productDetailRaw != null) {
                    newProduct.setDescription(productDetailRaw.getDescription());
                }
                
                boolean inserted = productDao.insert(newProduct);
                if(!inserted) {
                    System.out.println("ERROR!! Cannot insert new product");
                }
            }
            else {
                //update
                oldEntity.setImageUrl(productRaw.getImgUrl());
                oldEntity.setName(productRaw.getName());
                oldEntity.setPrice(productRaw.getPrice());
                if(productDetailRaw != null) {
                    oldEntity.setDescription(productDetailRaw.getDescription());
                }
                
                boolean updated = productDao.update(oldEntity);
                if(!updated) {
                    System.out.println("ERROR!! Cannot update old product");
                }
            }
            
        }
        
        return true;
    }
}
