/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.Product;

/**
 *
 * @author admin
 */
public class ProductDAO extends DAOBase<Product>
        implements IDAO<Product>{
    
    public ProductDAO() {
        super(Product.class);
    }
    
}