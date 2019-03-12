/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import java.util.List;
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
    
    public Product getProductByProductRawId(int productRawId) {
        Product result = this.getSingle("productRawId = ?", productRawId);
        return result;
    }
    
    public boolean existedProductBoundToProductRaw(int productRawId) {
        int result = this.count("productRawId = ?", productRawId);
        return result > 0;
    }
    
    public List<Product> getTopProduct() {
        String queryString = "select top(5)\n" +
                "P.id, P.deleted, P.createdAt, P.updatedAt, P.name, P.categoryId, P.description, P.imageUrl, P.price\n" +
                "from Product P\n" +
                "left join OrderDetail OD on P.id = OD.productId and OD.deleted = 0\n" +
                "group by P.id, P.deleted, P.createdAt, P.updatedAt, P.name, P.categoryId, P.description, P.imageUrl, P.price\n" +
                "order by SUM(OD.quantity) desc";
        
        List<Product> result = getAllCustom(queryString);
        
        return result;
    }
    
    public List<Product> getTopProductByCategory(int categoryId) {
        String queryString = "select top(5)\n" +
                "P.id, P.deleted, P.createdAt, P.updatedAt, P.name, P.categoryId, P.description, P.imageUrl, P.price\n" +
                "from Product P\n" +
                "left join OrderDetail OD on P.id = OD.productId and OD.deleted = 0\n" +
                "where P.categoryId = ?\n" +
                "group by P.id, P.deleted, P.createdAt, P.updatedAt, P.name, P.categoryId, P.description, P.imageUrl, P.price\n" +
                "order by SUM(OD.quantity) desc";
        
        List<Product> result = getAllCustom(queryString, categoryId);
        
        return result;
    }
}
