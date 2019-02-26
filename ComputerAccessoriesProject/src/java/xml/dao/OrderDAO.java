/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.Order;

/**
 *
 * @author admin
 */
public class OrderDAO extends DAOBase<Order> implements IDAO<Order>{
    
    public OrderDAO() {
        super(Order.class);
    }
    
}
