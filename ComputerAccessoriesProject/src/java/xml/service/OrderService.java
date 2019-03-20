/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import xml.dao.OrderDAO;
import xml.dao.OrderDetailDAO;
import xml.dao.ProductDAO;
import xml.dao.UserDAO;
import xml.dto.OrderDTO;
import xml.dto.OrderDetailDTO;
import xml.model.Order;
import xml.model.OrderDetail;
import xml.model.User;

/**
 *
 * @author admin
 */
public class OrderService {
    private final OrderDAO orderDao;
    private final OrderDetailDAO orderDetailDao;
    private final UserDAO userDao;
    
    public OrderService() {
        orderDao = new OrderDAO();
        orderDetailDao = new OrderDetailDAO();
        userDao = new UserDAO();
    }
    
    public String generateOrderCode() {
        Date date = new Date();
        String strDateFormat = "yyyyMMddHHmmss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String code = dateFormat.format(date);
        return code;
    }
    
    public boolean saveOrder(OrderDTO orderDto) {
        orderDto.setOrderCode(generateOrderCode());
        Order order = new Order(orderDto);
        
        boolean inserted = orderDao.insert(order);
        if(inserted) {
            for(OrderDetailDTO orderDetailDto : orderDto.getOrderDetailDTOs()) {
                OrderDetail orderDetail = new OrderDetail(orderDetailDto);
                orderDetail.setOrderId(order.getId());
                
                inserted = orderDetailDao.insert(orderDetail);
                if(!inserted) {
                    System.out.println("ERROR! Cannot insert order detail");
                    return false;
                }
            }
        } else {
            System.out.println("ERROR! Cannot insert order");
            return false;
        }
        return true;
    }
    
    public OrderDTO getOrder(String orderCode) {
        Order order = orderDao.getOrderByOrderCode(orderCode);
        OrderDTO orderDto = new OrderDTO(order);
        User user = userDao.getById(order.getCustomerId());
        if(user != null) {
            orderDto.setCustomerName(user.getFullName());
        }
        
        List<OrderDetail> orderDetails = orderDetailDao.getByOrderId(order.getId());
        List<OrderDetailDTO> orderDetailDtos = orderDetails.stream()
                .map((x) -> new OrderDetailDTO(x))
                .collect(Collectors.toList());
        
        orderDto.setOrderDetailDTOs(orderDetailDtos);
        return orderDto;
    }
}
