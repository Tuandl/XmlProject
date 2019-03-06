/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import java.util.List;
import xml.model.ProductRaw;

/**
 *
 * @author admin
 */
public class ProductRawDAO extends DAOBase<ProductRaw> 
        implements IDAO<ProductRaw>{
    
    public ProductRawDAO() {
        super(ProductRaw.class);
    }
    
    public ProductRaw getProductRawByDetailUrl(String detailUrl) {
        ProductRaw productRaw = this.getSingle("detailUrl = ?", detailUrl);
        return productRaw;
    }
    
    public List<ProductRaw> getNewProductRawsByCategoryRawId(int categoryRawId) {
        List<ProductRaw> productRaws = this.getAll(
                "categoryRawId = ? and isNew = ?", categoryRawId, true);
        return productRaws;
    }
}
