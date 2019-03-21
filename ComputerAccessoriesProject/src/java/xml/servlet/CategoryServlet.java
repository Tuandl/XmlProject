/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xml.dto.CategoriesDTO;
import xml.dto.CategoryDTO;
import xml.model.Category;
import xml.service.CategoryService;
import xml.utils.StringUtils;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/CategoryServlet"})
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        
        try {
            String categoryIdStr = req.getParameter("id");
            int categoryId = -1;
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (Exception ex) {
                //cannot parse
            }
            if(categoryId == -1) {
                //get all
                List<Category> categories = categoryService.getAvailableCategories();

                List<CategoryDTO> categoryDTOs = new ArrayList<>();

                for(Category category : categories) {
                    CategoryDTO categoryDto = new CategoryDTO();
                    categoryDto.setName(category.getName());
                    categoryDto.setId(category.getId());
                    
                    categoryDTOs.add(categoryDto);
                }

                CategoriesDTO result = new CategoriesDTO();
                result.setCategoryDTOs(categoryDTOs);

                XMLUtils.marshallToOutputStream(result, resp.getOutputStream());
            } else {
                //get by id
                Category category = categoryService.getById(categoryId);
                CategoryDTO result = new CategoryDTO();
                result.setId(category.getId());
                result.setName(category.getName());
                
                XMLUtils.marshallToOutputStream(result, resp.getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();            
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();

        try {
            String data = req.getParameter("data");
            CategoryDTO dto = (CategoryDTO) XMLUtils.unmarshallFromString(CategoryDTO.class, data);
            if(dto == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            Category newCategory = new Category();
            newCategory.setName(dto.getName());
            
            boolean added = categoryService.addCategory(newCategory);
            if(added) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            
        } catch (Exception e) {
            e.printStackTrace();            
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        try {
            String categoryIdStr = req.getParameter("id");
            int categoryId = -1;
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (Exception ex) {
                //cannot parse
            }
            if(categoryId != -1) {
                boolean deleted = categoryService.removeCategory(categoryId);
                if(deleted) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();            
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();

        try {
            String data = StringUtils.readStringFromInputStream(req.getInputStream());
            
            CategoryDTO dto = (CategoryDTO) XMLUtils.unmarshallFromString(CategoryDTO.class, data);
            if(dto == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            Category category = new Category();
            category.setId(dto.getId());
            category.setName(dto.getName());
            
            boolean updated = categoryService.updateCategory(category);
            if(updated) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();            
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
