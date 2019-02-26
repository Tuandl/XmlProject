/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

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
    
    public static Object getResult(ResultSet resultSet, Field field) throws SQLException{
        Object result = null;
        String fieldName = field.getName();
        if(field.getType().equals(String.class)) {
            result = resultSet.getString(fieldName);
        } 
        else if(field.getType().equals(Integer.TYPE)) {
            result = resultSet.getInt(fieldName);
        }
        else if(field.getType().equals(Boolean.TYPE)) {
            result = resultSet.getBoolean(fieldName);
        }
        else if(field.getType().equals(Date.class)){
            Timestamp timestamp = resultSet.getTimestamp(fieldName);
            if(timestamp != null) {
                result = new Date(timestamp.getTime());
            }
        }
            
        return result;
    }
    
    public static String generateSqlName(String name){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(name);
        builder.append("]");
        
        return builder.toString();
    } 
    
    public static void setParameter(PreparedStatement preparedStatement,
            int index, Object value) throws SQLException{
        Class valueClass = value.getClass();
        
        if(valueClass.getTypeName().equals(String.class)){
            preparedStatement.setString(index, (String)value);
        } 
        else if(valueClass.getTypeName().equals(Integer.TYPE)) {
            preparedStatement.setInt(index, (int)value);
        }
        else if(valueClass.getTypeName().equals(Boolean.TYPE)){
            preparedStatement.setBoolean(index, (boolean)value);
        }
        else if(valueClass.getTypeName().equals(Date.class)){
            Timestamp timestamp = new Timestamp(((Date)value).getTime());
            preparedStatement.setTimestamp(index, timestamp);
        }
        else {
            preparedStatement.setObject(index, value);
        }
    }
}
 