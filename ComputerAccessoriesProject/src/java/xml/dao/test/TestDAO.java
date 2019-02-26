/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import xml.dao.IDAO;
import xml.dao.UserDAO;
import xml.model.User;

/**
 *
 * @author admin
 */
public class TestDAO {
    public static void main(String[] args) {
//        testGetAll();
//        testGetById();
//        testInsertUser();
//        testUpdateUser(5);

        testInsertUser();
        System.out.println("");
        testGetAll();
        System.out.println("");
        testDeleteUser();
        System.out.println("");
        testGetAll();
    }
    
    public static void testGetAll(){
        UserDAO userDAO = new UserDAO();
        
//        List<User> users = userDAO.getAll("username = ? and password = ?", "tuandapchai 2", "123123");
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
    
    public static void testInsertUser() {
        UserDAO userDAO = new UserDAO();
        
        User user = new User();
        user.setUsername("Feated");
        user.setPassword("iegh4fah8Bok");
        user.setFullName("Võ An Văn");
        user.setIsAdmin(false);
        
        boolean isSuccess = userDAO.insert(user);
        
        System.out.println("is success = " + isSuccess);
        System.out.println("user id = " + user.getId());
    }
    
    public static void testUpdateUser() {
        UserDAO dao = new UserDAO();
        
        User user = new User();
        user.setUsername("Puladogaver1973");
        user.setPassword("FoT5ooxai");
        user.setFullName("Trương Quốc Hậu");
        user.setIsAdmin(false);
        user.setId(4);
        
        dao.update(user);
    }
    
    public static void testUpdateUser(int id){
        UserDAO dao = new UserDAO();
        
        User user = dao.getById(id);
        user.setFullName("Trịnh Thị Cẩm Giàu");
        user.setUsername("Adicullece");
        user.setPassword("ue8feighahB");
        
        dao.update(user); 
    }
    
    public static void testDeleteUser() {
        IDAO dao = new UserDAO();
        
        boolean result = dao.delete(3);
        System.out.println("result = " + result);
    }
}
