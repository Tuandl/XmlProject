/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service;

import java.util.List;
import java.util.stream.Collectors;
import xml.dao.CategoryDAO;
import xml.dto.CategoriesDTO;
import xml.dto.CategoryDTO;
import xml.model.Category;

/**
 *
 * @author admin
 */

public class CategoryService {
    private final CategoryDAO categoryDAO;
    
    public CategoryService() {
        categoryDAO = new CategoryDAO();
    }
    
    public List<Category> getAvailableCategories() {
        List<Category> results = categoryDAO.getAll("deleted = ?", false);
        return results;
    }
    
    public Category getById(int categoryId) {
        Category result = categoryDAO.getById(categoryId);
        return result;
    }
    
    public boolean addCategory(Category newCategory) {
        boolean result = categoryDAO.insert(newCategory);
        return result;
    }
    
    public boolean removeCategory(int categoryId) {
        boolean result = false;
        
        Category entity = categoryDAO.getById(categoryId);
        if(entity == null) {
            return result;
        }
        
        entity.setDeleted(true);
        result = categoryDAO.update(entity);
        
        return result;
    }
    
    public boolean updateCategory(Category category) {
        boolean result = categoryDAO.update(category);
        return result;
    }
    
    public CategoriesDTO getTopCategories() {
        List<Category> categories = categoryDAO.getTopCategories();
        
        List<CategoryDTO> categoriesDto = categories.stream()
                .map((x) -> new CategoryDTO(x))
                .collect(Collectors.toList());
        
        CategoriesDTO result = new CategoriesDTO();
        result.setCategoryDTOs(categoriesDto);
        return result;
    }
}
