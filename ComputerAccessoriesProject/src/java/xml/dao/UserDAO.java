/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.User;

/**
 *
 * @author admin
 */
public class UserDAO extends DAOBase<User> implements IDAO<User>{
    
    public UserDAO() {
        super(User.class);
    }
    
}
