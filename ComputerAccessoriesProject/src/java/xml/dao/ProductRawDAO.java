/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

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
    
}
