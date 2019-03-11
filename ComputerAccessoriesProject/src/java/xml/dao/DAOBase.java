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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import xml.model.ModelBase;
import xml.utils.DBUtils;
import xml.utils.ReflectionUtils;

/**
 *
 * @author admin
 */
public abstract class DAOBase<T extends ModelBase> implements IDAO<T> {

    private final Class<T> type;

    public DAOBase(Class<T> type) {
        this.type = type;
    }

    private Class<T> getModelClass() {
        return this.type;
    }

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public List<T> getAll() {
        List<T> result = getAll(null, (Object) null);
        return result;
    }

    @Override
    public List<T> getAll(String filterQuery, Object... parameters) {
        List<T> result = new ArrayList<T>();

        try {
            //Make query string
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select ");

            Class modelClass = this.getModelClass();
            List<String> objectFields = ReflectionUtils.getAllFieldNames(modelClass);
            for (String field : objectFields) {
                queryBuilder.append(DBUtils.generateSqlName(field));
                queryBuilder.append(",");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);

            queryBuilder.append(" from ");
            queryBuilder.append(DBUtils.generateSqlName(modelClass.getSimpleName()));

            if (filterQuery != null && !filterQuery.trim().isEmpty()) {
                queryBuilder.append(" where ");
                queryBuilder.append(filterQuery);
            }

            String queryString = queryBuilder.toString();

            //Make connection
            connection = DBUtils.makeConnection();
            preparedStatement = connection.prepareCall(queryString);

            if (parameters != null) {
                for (int index = 0; index < parameters.length; index++) {
                    Object param = parameters[index];
                    //index + 1 because of preparedStatement.setParam index from 1
                    DBUtils.setParameter(preparedStatement, index + 1, param, param.getClass());
                }
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                T entity = (T) modelClass.newInstance();
                Class objectClass = entity.getClass();

                for (String fieldName : objectFields) {
                    Field field = ReflectionUtils.getFieldByName(objectClass, fieldName);

                    Object value = null;
                    try {
                        value = DBUtils.getResult(resultSet, field);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (value != null) {
                        ReflectionUtils.setFieldValue(entity, field, value);
                    }
                }
                result.add(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return result;
    }

    @Override
    public List<T> getAll(int skip, int take, Field orderBy, boolean isReverse, String filterQuery, Object... parameters) {
        List<T> result = new ArrayList<T>();

        try {
            //Make query string
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select ");

            Class modelClass = this.getModelClass();
            List<String> objectFields = ReflectionUtils.getAllFieldNames(modelClass);
            for (String field : objectFields) {
                queryBuilder.append(DBUtils.generateSqlName(field));
                queryBuilder.append(",");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);

            queryBuilder.append(" from ");
            queryBuilder.append(DBUtils.generateSqlName(modelClass.getSimpleName()));

            if (filterQuery != null && !filterQuery.trim().isEmpty()) {
                queryBuilder.append(" where ");
                queryBuilder.append(filterQuery);
            }
            
            queryBuilder.append(" order by ");
            
            if(orderBy != null) {
                queryBuilder.append(DBUtils.generateSqlName(orderBy.getName()));
                if(isReverse) {
                    queryBuilder.append(" desc ");
                }
            } else {
                queryBuilder.append(" CURRENT_TIMESTAMP ");
            }
            
            if(skip < 0 || take <= 0) {
                return result;
            }
            
            queryBuilder.append(" OFFSET ");
            queryBuilder.append(skip);
            queryBuilder.append(" rows ");
            queryBuilder.append(" fetch next ");
            queryBuilder.append(take);
            queryBuilder.append(" rows only");

            String queryString = queryBuilder.toString();

            //Make connection
            connection = DBUtils.makeConnection();
            preparedStatement = connection.prepareCall(queryString);

            if (parameters != null) {
                for (int index = 0; index < parameters.length; index++) {
                    Object param = parameters[index];
                    //index + 1 because of preparedStatement.setParam index from 1
                    DBUtils.setParameter(preparedStatement, index + 1, param, param.getClass());
                }
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                T entity = (T) modelClass.newInstance();
                Class objectClass = entity.getClass();

                for (String fieldName : objectFields) {
                    Field field = ReflectionUtils.getFieldByName(objectClass, fieldName);

                    Object value = null;
                    try {
                        value = DBUtils.getResult(resultSet, field);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (value != null) {
                        ReflectionUtils.setFieldValue(entity, field, value);
                    }
                }
                result.add(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return result;
    }
    
    @Override
    public T getById(int id) {
        T result = null;
        List<T> entities = getAll("id = ?", id);

        if (entities != null && entities.size() > 0) {
            result = entities.get(0);
        }

        return result;
    }

    @Override
    public T getSingle(String filterQuery, Object... parameters) {
        T result = null;
        List<T> entities = getAll(filterQuery, parameters);
        
        if(entities != null && entities.size() > 0) {
            result = entities.get(0);
        }
        
        return result;
    }
    
    @Override
    public boolean insert(T entity) {
        if (entity == null) {
            return false;
        }
        entity.setCreatedAt(new Date());
        entity.setDeleted(false);

        boolean isInsertSuccess = false;

        try {
            Class modelClass = this.getModelClass();

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("insert into ");
            queryBuilder.append(DBUtils.generateSqlName(modelClass.getSimpleName()));
            queryBuilder.append(" (");

            List<String> fieldNames = ReflectionUtils.getAllFieldNames(modelClass);

            for (String fieldName : fieldNames) {
                //Id is auto generated is
                if (fieldName.equals("id")) {
                    continue;
                }
                queryBuilder.append(DBUtils.generateSqlName(fieldName));
                queryBuilder.append(",");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            queryBuilder.append(") values (");

            for (int index = 0; index < fieldNames.size() - 1; index++) {
                queryBuilder.append("?,");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            queryBuilder.append(")");

            String queryString = queryBuilder.toString();

            connection = DBUtils.makeConnection();
            preparedStatement = connection.prepareStatement(queryString,
                    Statement.RETURN_GENERATED_KEYS);

            int count = 1;
            for (String fieldName : fieldNames) {
                if (fieldName.equals("id")) {
                    continue;
                }

                Field field = ReflectionUtils.getFieldByName(modelClass, fieldName);
                field.setAccessible(true);
                Object value = field.get(entity);
                DBUtils.setParameter(preparedStatement, count++, value, field.getType());
            }

            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    isInsertSuccess = true;
                    entity.setId((int) resultSet.getLong(1));
                }
            }

        } catch (Exception e) {
            isInsertSuccess = false;
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return isInsertSuccess;
    }

    @Override
    public boolean update(T entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getId() == 0) {
            return false;
        }
        entity.setUpdatedAt(new Date());

        boolean isUpdateSuccess = false;

        try {
            Class modelClass = this.getModelClass();
            List<String> fieldNames = ReflectionUtils.getAllFieldNames(modelClass);

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("update ");
            queryBuilder.append(DBUtils.generateSqlName(modelClass.getSimpleName()));
            queryBuilder.append(" set ");

            for (String fieldName : fieldNames) {
                //Id is auto generated is
                if (fieldName.equals("id")) {
                    continue;
                }

                queryBuilder.append(DBUtils.generateSqlName(fieldName));
                queryBuilder.append("=?,");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            queryBuilder.append(" where id = ?");

            String queryString = queryBuilder.toString();

            System.out.println(queryString);

            connection = DBUtils.makeConnection();
            preparedStatement = connection.prepareStatement(queryString);

            int count = 1;
            for (String fieldName : fieldNames) {
                if (fieldName.equals("id")) {
                    continue;
                }

                Field field = ReflectionUtils.getFieldByName(modelClass, fieldName);
                field.setAccessible(true);
                Object value = field.get(entity);
                DBUtils.setParameter(preparedStatement, count++, value, field.getType());
            }
            DBUtils.setParameter(preparedStatement, count++, entity.getId(), Integer.TYPE);

            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow > 0) {
                isUpdateSuccess = true;
            }

        } catch (Exception e) {
            isUpdateSuccess = false;
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return isUpdateSuccess;
    }

    @Override
    public boolean delete(String deleteQuery, Object... parameters) {
        boolean isDeleteSuccess = false;

        try {
            Class modelClass = this.getModelClass();

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("delete ");
            queryBuilder.append(DBUtils.generateSqlName(modelClass.getSimpleName()));
            
            if(deleteQuery != null && !deleteQuery.trim().isEmpty()) {
                queryBuilder.append(" where ");
                queryBuilder.append(deleteQuery);
            }
            
            String queryString = queryBuilder.toString();

            System.out.println(queryString);

            connection = DBUtils.makeConnection();
            preparedStatement = connection.prepareStatement(queryString);

            if(parameters != null) {
                for(int index = 0; index < parameters.length; index++){
                    Object param = parameters[index];
                    DBUtils.setParameter(preparedStatement, index + 1, param, param.getClass());
                }
            }

            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow > 0) {
                isDeleteSuccess = true;
            }

        } catch (Exception e) {
            isDeleteSuccess = false;
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return isDeleteSuccess;
    }

    @Override
    public boolean delete(int id) {
        boolean isDeleteSuccess = delete("id = ?", id);
        return isDeleteSuccess;
    }

    @Override
    public int count(String filterQuery, Object... parameters) {
        int result = 0;

        try {
            //Make query string
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select count(*) as 'total' from ");

            Class modelClass = this.getModelClass();
            queryBuilder.append(DBUtils.generateSqlName(modelClass.getSimpleName()));

            if (filterQuery != null && !filterQuery.trim().isEmpty()) {
                queryBuilder.append(" where ");
                queryBuilder.append(filterQuery);
            }

            String queryString = queryBuilder.toString();

            //Make connection
            connection = DBUtils.makeConnection();
            preparedStatement = connection.prepareCall(queryString);

            if (parameters != null) {
                for (int index = 0; index < parameters.length; index++) {
                    Object param = parameters[index];
                    //index + 1 because of preparedStatement.setParam index from 1
                    DBUtils.setParameter(preparedStatement, index + 1, param, param.getClass());
                }
            }

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return result;
    }
    
    protected List<T> getAllCustom(String queryString, Object... parameters) {
        List<T> result = new ArrayList<T>();

        try {
            //Make query string
            Class modelClass = this.getModelClass();
            List<String> objectFields = ReflectionUtils.getAllFieldNames(modelClass);

            //Make connection
            connection = DBUtils.makeConnection();
            preparedStatement = connection.prepareCall(queryString);

            if (parameters != null) {
                for (int index = 0; index < parameters.length; index++) {
                    Object param = parameters[index];
                    //index + 1 because of preparedStatement.setParam index from 1
                    DBUtils.setParameter(preparedStatement, index + 1, param, param.getClass());
                }
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                T entity = (T) modelClass.newInstance();
                Class objectClass = entity.getClass();

                for (String fieldName : objectFields) {
                    Field field = ReflectionUtils.getFieldByName(objectClass, fieldName);

                    Object value = null;
                    try {
                        value = DBUtils.getResult(resultSet, field);
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }

                    if (value != null) {
                        ReflectionUtils.setFieldValue(entity, field, value);
                    }
                }
                result.add(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return result;
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
