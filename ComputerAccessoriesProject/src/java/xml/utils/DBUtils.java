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
import java.sql.Types;
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
        else if(field.getType().equals(Integer.TYPE) || field.getType().equals(Integer.class)) {
            result = resultSet.getInt(fieldName);
        }
        else if(field.getType().equals(Boolean.TYPE) || field.getType().equals(Boolean.class)) {
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
            int index, Object value, Class valueClass) throws SQLException{
        
        if(valueClass.equals(String.class)){
            if(value != null) {
                preparedStatement.setString(index, (String)value);
            } else {
                preparedStatement.setNull(index, Types.NVARCHAR);
            }
        } 
        else if(valueClass.equals(Integer.TYPE) || valueClass.equals(Integer.class)) {
            if(value != null){
                preparedStatement.setInt(index, (int)value);
            } else {
                preparedStatement.setNull(index, Types.INTEGER);
            }
        }
        else if(valueClass.equals(Boolean.TYPE) || valueClass.equals(Boolean.class)){
            if(value != null){
                preparedStatement.setBoolean(index, (boolean)value);
            } else {
                preparedStatement.setNull(index, Types.BOOLEAN);
            }
        }
        else if(valueClass.equals(Date.class)){
            if(value != null){
                Timestamp timestamp = new Timestamp(((Date)value).getTime());
                preparedStatement.setTimestamp(index, timestamp);
            } else {
                preparedStatement.setNull(index, Types.DATE);
            }
        } 
        else {
            System.out.println("Exception setParamete " + valueClass.getTypeName());
        }
    }
}
 