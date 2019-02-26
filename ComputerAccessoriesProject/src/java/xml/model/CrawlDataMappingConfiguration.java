/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.model;

/**
 *
 * @author admin
 */
public class CrawlDataMappingConfiguration extends ModelBase{
    private int domainId;
    private int dataId;
    private String xPathQuery;

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
}
