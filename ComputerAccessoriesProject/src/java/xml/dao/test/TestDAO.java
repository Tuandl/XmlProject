/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import xml.dao.UserDAO;
import xml.model.User;

/**
 *
 * @author admin
 */
public class TestDAO {
    public static void main(String[] args) {
//        testGetAll();
        testGetById();
    }
    
    public static void testGetAll(){
        UserDAO userDAO = new UserDAO();
//        
//        List<User> users = userDAO.getAll("username = ? and password = ?", 
//                new Object[] {"tuandapchai 2", "123123"});
        List<User> users = userDAO.getAll();
        
        System.out.println("user length = " + users.size());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for(User user : users){
            System.out.println("id = " + user.getId());
            System.out.println("username = " + user.getUsername());
            System.out.println("password = " + user.getPassword());
            System.out.println("fullname = " + user.getFullName());
            System.out.println("created = " + dateFormat.format(user.getCreatedAt()));
            System.out.println("deleted = " + user.isDeleted());
            System.out.println("is admin = " + user.isIsAdmin());
        }
    }
    
    public static void testGetById(){
        UserDAO userDAO = new UserDAO();

        User user = userDAO.getById(2);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println("id = " + user.getId());
        System.out.println("username = " + user.getUsername());
        System.out.println("password = " + user.getPassword());
        System.out.println("fullname = " + user.getFullName());
        System.out.println("created = " + dateFormat.format(user.getCreatedAt()));
        System.out.println("deleted = " + user.isDeleted());
        System.out.println("is admin = " + user.isIsAdmin());
    }
}
