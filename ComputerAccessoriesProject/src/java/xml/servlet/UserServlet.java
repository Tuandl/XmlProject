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
import xml.model.User;
import xml.service.UserService;

/**
 *
 * @author admin
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    private final UserService userService;
    
    public UserServlet() {
        super();
        userService = new UserService();
    }
    
    //Register
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            
            User newUser = new User();
            newUser.setUsername(username);;
            newUser.setPassword(password);
            newUser.setFullName(fullName);
            
            String registerResult = userService.register(newUser);
            
            if(registerResult.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.append("Register Success");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.append(registerResult);
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
