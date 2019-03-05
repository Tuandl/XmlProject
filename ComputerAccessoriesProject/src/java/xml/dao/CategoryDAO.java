/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.Category;

/**
 *
 * @author admin
 */
public class CategoryDAO extends DAOBase<Category> 
        implements IDAO<Category>{
    
    public CategoryDAO() {
        super(Category.class);
    }
    
}
