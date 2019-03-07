/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xml.model.ProductRaw;
import xml.service.CrawlService;
import xml.utils.StringUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "CrawlProduct", urlPatterns = {"/Crawl/Product"})
public class CrawlProduct extends HttpServlet {

    private final CrawlService crawlService;
    
    public CrawlProduct() {
        crawlService = new CrawlService();
    }
    
    /**
     * Crawl product base on categoryRawId and bind that categoryRaw to categoryId
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String categoryRawIdStr = req.getParameter("categoryRawId");
            String categoryIdStr = req.getParameter("categoryId");
            Integer categoryRawId = StringUtils.parseStringToInt(categoryRawIdStr);
            Integer categoryId = StringUtils.parseStringToInt(categoryIdStr);
            
            if(categoryRawId == null || categoryId == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                List<ProductRaw> productRaws =  crawlService.crawlProductRaw(categoryRawId, categoryId);
                resp.setStatus(HttpServletResponse.SC_OK);
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
