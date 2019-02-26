/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.SubCategory;

/**
 *
 * @author admin
 */
public class SubCategoryDAO extends DAOBase<SubCategory>
        implements IDAO<SubCategory>{
    
    public SubCategoryDAO() {
        super(SubCategory.class);
    }
    
}
