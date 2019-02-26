/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.MasterCategory;

/**
 *
 * @author admin
 */
public class MasterCategoryDAO extends DAOBase<MasterCategory>
        implements IDAO<MasterCategory>{
    
    public MasterCategoryDAO() {
        super(MasterCategory.class);
    }
    
}
