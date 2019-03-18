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
import xml.utils.FileUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "ProductImageServlet", urlPatterns = {"/Product/Image"})
public class ProductImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String imageName = req.getParameter("name");
            if(!FileUtils.checkProductImageExisted(imageName)) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            String extension = FileUtils.getFileExtension(imageName);
            resp.setContentType("image/" + extension.toLowerCase());
            FileUtils.writeProductRawImageToOutputStream(imageName, resp.getOutputStream());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    
}
