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
import xml.dao.UserDAO;
import xml.service.UserService;

/**
 *
 * @author admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        try (PrintWriter out = response.getWriter()){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            boolean loginResult = userService.checkLogin(username, password);
            
            if(loginResult) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.append("Login Success");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.append("Invalid Username or Password.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
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
