/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service;

import java.util.List;
import xml.dao.CategoryRawDAO;
import xml.dao.ProductDAO;
import xml.dao.ProductRawDAO;
import xml.model.CategoryRaw;
import xml.model.Product;
import xml.model.ProductRaw;

/**
 *
 * @author admin
 */
public class CategoryRawService {
    private final CategoryRawDAO categoryRawDao;
    private final ProductRawDAO productRawDao;
    private final ProductDAO productDao;
    
    public CategoryRawService() {
        categoryRawDao = new CategoryRawDAO();
        productRawDao = new ProductRawDAO();
        productDao = new ProductDAO();
    }
    
    public List<CategoryRaw> getAllCategoryRaw() {
        List<CategoryRaw> result = categoryRawDao.getAll("deleted = ?", false);
        return result;
    }
    
    /**
     New ProductRaw condition: isNew = true and not has any Product
     */
    public int countNewProductInCategoryRaw(int categoryRawId) {
        int counter = 0;
        List<ProductRaw> productRaws = productRawDao.getAll(
                "isNew = ? and deleted = ?", true, false);
        
        for(ProductRaw productRaw : productRaws) {
            if(productRaw.isIsNew()) {
                Product product = productDao.getProductByProductRawId(productRaw.getId());
                if(product == null) {
                    counter++;
                }
            }
        }
        
        return counter;
    }
    
    /**
     Edited ProductRaw condition: isNew = true and has a Product reference to
     */
    public int countEditedProductInCategoryRaw(int categoryRawId) {
        int counter = 0;
        List<ProductRaw> productRaws = productRawDao.getAll(
                "isNew = ? and deleted = ?", true, false);
        
        for(ProductRaw productRaw : productRaws) {
            if(productRaw.isIsNew()) {
                Product product = productDao.getProductByProductRawId(productRaw.getId());
                if(product != null) {
                    counter++;
                }
            }
        }
        
        return counter;
    }
    
    public int countProductRawInCategoryRaw(int categoryRawId) {
        int counter = 0;
        List<ProductRaw> productRaws = productRawDao.getAll(
                "deleted = ?", false);
        counter = productRaws.size();
        
        return counter;
    }
    
    public boolean deleteCategoryRaw(int categoryRawId) {
        //TODO: options: delte Product
        
        //delete productRaw
        List<ProductRaw> productRaws = productRawDao.getAll("categoryRawId = ?", categoryRawId);
        for(ProductRaw productRaw : productRaws) {
            productRawDao.delete(productRaw.getId());
        }
        
        //Delete categoryRaw
        boolean deleted = categoryRawDao.delete(categoryRawId);
        return deleted;
    }
}
