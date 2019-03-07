/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xml.dto.CategoryRawDTO;
import xml.model.CategoryRaw;
import xml.service.CrawlService;

/**
 *
 * @author admin
 */
@WebServlet(name = "CrawlCategory", urlPatterns = {"/Crawl/Category"})
public class CrawlCategory extends HttpServlet {
    
    private final CrawlService crawlService;
    
    public CrawlCategory() {
        crawlService = new CrawlService();
    }

    /**
     * Crawl category in all domain and return number of new categories
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter out = resp.getWriter()){
            List<CategoryRaw> newCategoryRaws = crawlService.crawlAllCategories();
            out.append(newCategoryRaws.size() + "");
            resp.setStatus(HttpServletResponse.SC_OK);
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
