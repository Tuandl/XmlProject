/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class ReflectionUtils {
    
    public static List<String> getAllFieldNames(Class objectClass) {
        List<Field> fields = new ArrayList<>();
        Field[] fieldArray;
        
        fieldArray = objectClass.getDeclaredFields();
        fields.addAll(Arrays.asList(fieldArray));
        
        Class superClass = objectClass.getSuperclass();
        if(superClass != null) {
            fieldArray = superClass.getDeclaredFields();
            fields.addAll(Arrays.asList(fieldArray));
        }
        
        List<String> resultList = new ArrayList<>();
        
        for(Field field : fields){
            resultList.add(field.getName());
        }
        
        return resultList;
    }
    
    public static void setFieldValue(Object object, Field field, Object value){
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        
        try {
            field.set(object, value);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        
        field.setAccessible(accessible);
    }
    
    public static Field getFieldByName(Class objectClass, String fieldName){
        Field field = null;
        
        try {
            field = objectClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            //catch in case of field not found
        }
        
        if(field == null){
            try {
                field = objectClass.getSuperclass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                //if not found, return null already, so no need to handle here
            }
        }
        
        return field;
    }
}
