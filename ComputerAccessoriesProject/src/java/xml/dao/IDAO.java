/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import java.lang.reflect.Field;
import java.util.List;
import xml.model.ModelBase;

/**
 *
 * @author admin
 */
public interface IDAO <T extends ModelBase>{
    public List<T> getAll();
    public List<T> getAll(String filterQuery, Object... parameters);
    public List<T> getAll(int skip, int take, Field orderBy, boolean isReverse, 
            String filterQuery, Object... parameters);
    public int count(String filterQuery, Object... parameters);
    public T getSingle(String filterQuery, Object... parameters);
    public T getById(int id);
    public boolean insert(T entity);
    public boolean update(T entity);
    public boolean delete(int id);
    public boolean delete(String deleteQuery, Object... parameters);
}
