/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.model;

import xml.dto.OrderDetailDTO;

/**
 *
 * @author admin
 */
public class OrderDetail extends ModelBase{
    private int orderId;
    private int productId;
    private int price;
    private int quantity;
    private String productName;
    
    public OrderDetail(){}
    
    public OrderDetail(OrderDetailDTO orderDetailDto) {
        this.productId = orderDetailDto.getProductId();
        this.price = orderDetailDto.getPrice();
        this.quantity = orderDetailDto.getQuantity();
        this.productName = orderDetailDto.getProductName();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
}
