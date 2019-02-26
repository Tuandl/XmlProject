/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import java.util.List;
import xml.model.ModelBase;

/**
 *
 * @author admin
 */
public interface IDAO <T extends ModelBase>{
    public List<T> getAll();
    public T getById(int id);
    public boolean insert(T entity);
    public boolean update(T entity);
    public boolean delete(T entity);
    public boolean deleteById(int id);
}
