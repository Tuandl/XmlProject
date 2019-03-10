/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xml.dto.CategoryDTO;
import xml.dto.CategoryRawDTO;
import xml.dto.DomainDTO;
import xml.dto.CategoryRawsDTO;
import xml.model.Category;
import xml.model.CategoryRaw;
import xml.model.CrawlDomainConfiguration;
import xml.service.CategoryRawService;
import xml.service.CategoryService;
import xml.service.DomainService;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "CategoryRawServlet", urlPatterns = {"/CategoryRawServlet"})
public class CategoryRawServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryRawService categoryRawService = new CategoryRawService();
        DomainService domainService = new DomainService();
        CategoryService categoryService = new CategoryService();
        try {
            List<CategoryRaw> categoryRaws = categoryRawService.getAllCategoryRaw();
            List<CrawlDomainConfiguration> domains = 
                    domainService.getAllDomains();
            List<Category> categories = categoryService.getAvailableCategories();
            
            List<CategoryRawDTO> categoryRawDtos = new ArrayList<CategoryRawDTO>();
            for(CategoryRaw categoryRaw : categoryRaws){
                CategoryRawDTO dto = new CategoryRawDTO(categoryRaw);
                        
                CrawlDomainConfiguration domain = domains.stream()
                        .filter((x) -> x.getId() == categoryRaw.getDomainId())
                        .findAny()
                        .get();
                DomainDTO domainDto = new DomainDTO(domain);
                dto.setDomain(domainDto);

                if(categoryRaw.getCategoryId() > 0) {
                    Category category = categories.stream()
                            .filter((x) -> x.getId() == categoryRaw.getCategoryId())
                            .findAny()
                            .get();
                    CategoryDTO categoryDto = new CategoryDTO(category);
                    dto.setCategory(categoryDto);
                }
                
                categoryRawDtos.add(dto);
            }
            
            CategoryRawsDTO dto = new CategoryRawsDTO();
            dto.setCategoryRaws(categoryRawDtos);
            
            resp.setStatus(HttpServletResponse.SC_OK);
            XMLUtils.marshallToOutputStream(dto, resp.getOutputStream());
            
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryRawService categoryRawService = new CategoryRawService();
        try {
            String idStr = req.getParameter("id");
            int id = -1;
            try {
                id = Integer.parseInt(idStr);
            } catch (Exception e) {
                //cannot parse int
            }
            
            if(id == -1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } 
            else {
                boolean deleted = categoryRawService.deleteCategoryRaw(id);
                if(deleted) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
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
