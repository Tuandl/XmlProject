/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import xml.model.OrderDetail;

/**
 *
 * @author admin
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderDetail")
public class OrderDetailDTO {
    private int orderId;
    private int productId;
    private int price;
    private int quantity;
    private int amount;
    private String productName;
    
    public OrderDetailDTO() {};
    
    public OrderDetailDTO(OrderDetail orderDetail) {
        orderId = orderDetail.getOrderId();
        productId = orderDetail.getProductId();
        price = orderDetail.getPrice();
        quantity = orderDetail.getQuantity();
        amount = orderDetail.getPrice() * orderDetail.getQuantity();
        productName = orderDetail.getProductName();
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
}
