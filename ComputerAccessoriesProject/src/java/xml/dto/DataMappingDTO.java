/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import xml.model.CrawlDataMappingConfiguration;

/**
 *
 * @author admin
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "dataMapping")
public class DataMappingDTO {
    private int id;
    private int domainId;
    private int dataId;
    private String xPathQuery;
    private boolean isNodeResult;
    private DataConfigDTO data;
    
    public DataMappingDTO() {
        
    }
    
    public DataMappingDTO(CrawlDataMappingConfiguration dataMapping) {
        this.id = dataMapping.getId();
        this.domainId = dataMapping.getDomainId();
        this.dataId = dataMapping.getDataId();
        this.xPathQuery = dataMapping.getxPathQuery();
        this.isNodeResult = dataMapping.isIsNodeResult();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getxPathQuery() {
        return xPathQuery;
    }

    public void setxPathQuery(String xPathQuery) {
        this.xPathQuery = xPathQuery;
    }

    public boolean isIsNodeResult() {
        return isNodeResult;
    }

    public void setIsNodeResult(boolean isNodeResult) {
        this.isNodeResult = isNodeResult;
    }

    public DataConfigDTO getData() {
        return data;
    }

    public void setData(DataConfigDTO data) {
        this.data = data;
    }
    
    
}
