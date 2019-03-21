/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.model;

import xml.dto.DataMappingDTO;

/**
 *
 * @author admin
 */
public class CrawlDataMappingConfiguration extends ModelBase{
    private int domainId;
    private int dataId;
    private String xPathQuery;
    private boolean isNodeResult;
    
    public CrawlDataMappingConfiguration() {}
    
    public CrawlDataMappingConfiguration(DataMappingDTO dto) {
        this.id = dto.getId();
        this.domainId = dto.getDomainId();
        this.dataId = dto.getDataId();
        this.xPathQuery = dto.getxPathQuery();
        this.isNodeResult = dto.isIsNodeResult();
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
    
    
}
