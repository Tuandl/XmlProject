/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author admin
 */
public class DBUtils {
    
    private static final String connectionString = 
            "jdbc:sqlserver://localhost:1433; databaseName=XmlDb;";
    private static final String dbUsername = "sa";
    private static final String dbPassword = "123456789";
    
    public static Connection makeConnection(){
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager
                    .getConnection(connectionString, dbUsername, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
 