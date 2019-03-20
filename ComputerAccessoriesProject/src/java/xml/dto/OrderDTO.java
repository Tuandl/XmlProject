/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dto;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import xml.model.Order;

/**
 *
 * @author admin
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
public class OrderDTO {
    private String orderCode;
    private int amount;
    private String phoneNo;
    private String address;
    private int customerId;
    @XmlElement(name = "orderDetail")
    private List<OrderDetailDTO> orderDetailDTOs;
    private String createdAtString;
    private String customerName;
    
    public OrderDTO() {}
    
    public OrderDTO(Order order) {
        orderCode = order.getOrderCode();
        amount = order.getAmount();
        phoneNo = order.getPhoneNo();
        address = order.getAddress();
        customerId = order.getCustomerId();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        createdAtString = format.format(order.getCreatedAt());
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetailDTO> getOrderDetailDTOs() {
        return orderDetailDTOs;
    }

    public void setOrderDetailDTOs(List<OrderDetailDTO> orderDetailDTOs) {
        this.orderDetailDTOs = orderDetailDTOs;
    }

    public String getCreatedAtString() {
        return createdAtString;
    }

    public void setCreatedAtString(String createdAtString) {
        this.createdAtString = createdAtString;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    
}
