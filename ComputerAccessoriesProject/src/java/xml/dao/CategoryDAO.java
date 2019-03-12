/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import java.util.List;
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
    
    public List<Category> getTopCategories() {
        String queryStr = "select top(5) " +
                "C.id, C.name\n" +
                "from Category C\n" +
                "left join Product P on C.id = P.categoryId\n" +
                "left join OrderDetail OD on P.id = OD.productId and OD.deleted = 0\n" +
                "group by C.id, C.name\n" +
                "having count(P.id) > 0\n" +
                "order by sum(ISNULL(OD.quantity, 0)) desc, count(P.id) desc\n";
        
        List<Category> result = this.getAllCustom(queryStr);
        
        return result;
    }
}
