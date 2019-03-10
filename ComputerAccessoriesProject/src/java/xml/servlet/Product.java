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
import xml.service.ProductService;
import xml.utils.StringUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "Product", urlPatterns = {"/Product"})
public class Product extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = new ProductService();
        
        try {
            String categoryRawIdStr = req.getParameter("categoryRawId");
            Integer categoryRawId = StringUtils.parseStringToInt(categoryRawIdStr);
            
            if(categoryRawId == null) {
                //not support create product yet.
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                boolean updated = productService.updateNewProducts(categoryRawId);
                if(updated) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
