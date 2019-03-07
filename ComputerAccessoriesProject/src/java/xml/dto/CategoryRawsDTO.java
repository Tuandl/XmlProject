/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "categoryRaws")
public class CategoryRawsDTO {
    @XmlElement(name = "categoryRaw")
    private List<CategoryRawDTO> categoryRaws;

    public List<CategoryRawDTO> getCategoryRaws() {
        return categoryRaws;
    }

    public void setCategoryRaws(List<CategoryRawDTO> categoryRaws) {
        this.categoryRaws = categoryRaws;
    }
}
