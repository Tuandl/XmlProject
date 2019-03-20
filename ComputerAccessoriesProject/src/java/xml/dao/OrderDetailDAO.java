/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import java.util.List;
import xml.model.OrderDetail;

/**
 *
 * @author admin
 */
public class OrderDetailDAO extends DAOBase<OrderDetail>
        implements IDAO<OrderDetail>{
    
    public OrderDetailDAO() {
        super(OrderDetail.class);
    }
    
    public List<OrderDetail> getByOrderId(int orderId) {
        String filter = "orderId = ?";
        List<OrderDetail> orderDetails = getAll(filter, orderId);
        
        return orderDetails;
    }
    
}
