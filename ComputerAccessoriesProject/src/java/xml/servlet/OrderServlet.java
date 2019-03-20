/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xml.dto.OrderDTO;
import xml.service.OrderService;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/Order"})
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderService orderService = new OrderService();
        try (PrintWriter out = resp.getWriter()) {
            String data = req.getParameter("data");
            if(data == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            OrderDTO orderDto = (OrderDTO)XMLUtils.unmarshallFromString(OrderDTO.class, data);
            if(orderDto == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            } 
            
            boolean saved = orderService.saveOrder(orderDto);
            if(saved) {
                //return orderCode
                out.append(orderDto.getOrderCode());
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
