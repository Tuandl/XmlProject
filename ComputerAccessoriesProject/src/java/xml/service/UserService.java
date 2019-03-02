/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service;

import java.util.List;
import xml.dao.UserDAO;
import xml.model.User;

/**
 *
 * @author admin
 */
public class UserService {
    private final UserDAO userDao;
    
    public UserService() {
        userDao = new UserDAO();
    }
    
    public boolean checkLogin(String username, String password) {
        List<User> users = userDao.getAll("username = ? and password = ?", username, password);
        
        if(users.size() > 0) {
            return true;
        }
        return false;
    }
            
    public String register(User newUser){
        List<User> users = userDao.getAll("username = ?", newUser.getUsername());
        
        if(users.size() > 0) {
            return "Username is already existed.";
        }
        
        boolean insertedResult = userDao.insert(newUser);
        if(insertedResult == false) {
            return "Register error.";
        }
        
        return "";
    }
}
