/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.ProductDetailRaw;

/**
 *
 * @author admin
 */
public class ProductDetailRawDAO extends DAOBase<ProductDetailRaw> 
        implements IDAO<ProductDetailRaw>{
    
    public ProductDetailRawDAO() {
        super(ProductDetailRaw.class);
    }
    
}
