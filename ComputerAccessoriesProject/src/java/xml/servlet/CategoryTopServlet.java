/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xml.dto.CategoriesDTO;
import xml.dto.CategoryDTO;
import xml.service.CategoryService;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "CategoryTopServlet", urlPatterns = {"/Category/Top"})
public class CategoryTopServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        try {
            CategoriesDTO dto = categoryService.getTopCategories();
            resp.setStatus(HttpServletResponse.SC_OK);
            XMLUtils.marshallToOutputStream(dto, resp.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
