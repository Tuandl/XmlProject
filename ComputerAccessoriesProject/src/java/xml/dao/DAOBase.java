/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import xml.model.ModelBase;
import xml.utils.DBUtils;
import xml.utils.ReflectionUtils;

/**
 *
 * @author admin
 */
public abstract class DAOBase<T extends ModelBase> implements IDAO<T>{

    private final Class<T> type;

    public DAOBase(Class<T> type) {
        this.type = type;
    }
    
    private Class<T> getModelClass(){
        return this.type;
    }
    
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public List<T> getAll() {
        List<T> result = getAll(null, null);
        return result;
    }
    
    @Override
    public List<T> getAll(String filterQuery, Object[] parameters) {
        List<T> result = new ArrayList<T>();
        
        try {
            //Make query string
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select ");
            
            Class modelClass = this.getModelClass();
            List<String> objectFields = ReflectionUtils.getAllFieldNames(modelClass);
            for(String field : objectFields){
                queryBuilder.append(DBUtils.generateSqlName(field));
                queryBuilder.append(",");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            
            queryBuilder.append(" from ");
            queryBuilder.append(DBUtils.generateSqlName(modelClass.getSimpleName()));
            
            if(filterQuery != null && !filterQuery.trim().isEmpty()) {
                queryBuilder.append(" where ");
                queryBuilder.append(filterQuery);
            }
            
            String queryString = queryBuilder.toString();
            
            //Make connection
            connection = DBUtils.makeConnection();
            preparedStatement = connection.prepareCall(queryString);
            
            if(parameters != null){
                for(int index = 0; index < parameters.length; index++) {
                    Object param = parameters[index];
                    //index + 1 because of preparedStatement.setParam index from 1
                    DBUtils.setParameter(preparedStatement, index + 1, param);
                }
            }
            
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                T entity = (T)modelClass.newInstance();
                Class objectClass = entity.getClass();
                
                for(String fieldName: objectFields){
                    Field field = ReflectionUtils.getFieldByName(objectClass, fieldName);
                    
                    Object value = null;
                    try {
                        value = DBUtils.getResult(resultSet, field);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    if(value != null){
                        ReflectionUtils.setFieldValue(entity, field, value);
                    }
                }
                result.add(entity);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        
        return result;
    }

    @Override
    public T getById(int id) {
        T result = null;
        List<T> entities = getAll("id = ?", new Object[] {id});
        
        if(entities != null && entities.size() > 0) {
            result = entities.get(0);
        }
            
        return result;
    }

    @Override
    public boolean insert(T entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(T entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(T entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}