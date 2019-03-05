/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.CategoryRaw;

/**
 *
 * @author admin
 */
public class CategoryRawDAO extends DAOBase<CategoryRaw> 
        implements IDAO<CategoryRaw>{
    
    public CategoryRawDAO() {
        super(CategoryRaw.class);
    }
    
}
