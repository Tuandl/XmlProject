/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import xml.model.CategoryRaw;

/**
 *
 * @author admin
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "categoryRaw")
public class CategoryRawDTO {
    private int id;
    private String name;
    private String url;
    private DomainDTO domain;
    private CategoryDTO category;
    private boolean isCrawl;
    private int newProductRawQuantity;
    private int editedProductRawQuantity;
    private int totalProductRaw;
    
    public CategoryRawDTO() {}
    
    public CategoryRawDTO(CategoryRaw categoryRaw) {
        id = categoryRaw.getId();
        name = categoryRaw.getName();
        url = categoryRaw.getUrl();
        isCrawl = categoryRaw.getCategoryId() > 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DomainDTO getDomain() {
        return domain;
    }

    public void setDomain(DomainDTO domain) {
        this.domain = domain;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public boolean isIsCrawl() {
        return isCrawl;
    }

    public void setIsCrawl(boolean isCrawl) {
        this.isCrawl = isCrawl;
    }

    public int getNewProductRawQuantity() {
        return newProductRawQuantity;
    }

    public void setNewProductRawQuantity(int newProductRawQuantity) {
        this.newProductRawQuantity = newProductRawQuantity;
    }

    public int getEditedProductRawQuantity() {
        return editedProductRawQuantity;
    }

    public void setEditedProductRawQuantity(int editedProductRawQuantity) {
        this.editedProductRawQuantity = editedProductRawQuantity;
    }

    public int getTotalProductRaw() {
        return totalProductRaw;
    }

    public void setTotalProductRaw(int totalProductRaw) {
        this.totalProductRaw = totalProductRaw;
    }
    
    
}
