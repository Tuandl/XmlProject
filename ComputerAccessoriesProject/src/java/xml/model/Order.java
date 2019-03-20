/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.model;

import xml.dto.OrderDTO;

/**
 *
 * @author admin
 */
public class Order extends ModelBase{
    private String orderCode;
    private int amount;
    private String phoneNo;
    private String address;
    private int customerId;
    
    public Order() {}

    public Order(OrderDTO orderDto) {
        this.orderCode = orderDto.getOrderCode();
        this.amount = orderDto.getAmount();
        this.phoneNo = orderDto.getPhoneNo();
        this.address = orderDto.getAddress();
        this.customerId = orderDto.getCustomerId();
    }
    
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
