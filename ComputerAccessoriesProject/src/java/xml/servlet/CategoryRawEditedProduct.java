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
import xml.service.CategoryRawService;
import xml.utils.StringUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "CategoryRawEditedProduct", urlPatterns = {"/CategoryRaw/EditedProduct"})
public class CategoryRawEditedProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryRawService categoryRawService = new CategoryRawService();
        try (PrintWriter out = resp.getWriter()) {
            String categoryRawIdStr = req.getParameter("categoryRawId");
            Integer categoryRawId = StringUtils.parseStringToInt(categoryRawIdStr);
            
            if(categoryRawId == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } 
            else {
                int editedProductQuantity = categoryRawService
                        .countEditedProductInCategoryRaw(categoryRawId);
                
                out.append(editedProductQuantity + "");
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
    }
}
