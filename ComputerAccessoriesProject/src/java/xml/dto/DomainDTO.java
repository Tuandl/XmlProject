/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import xml.model.CrawlDomainConfiguration;

/**
 *
 * @author admin
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "domain")
public class DomainDTO {
    private int id;
    private String domainName;
    private String initUrl;
    private String pagingXPathQuery;
    
    public DomainDTO () {
    }
    
    public DomainDTO(CrawlDomainConfiguration domain) {
        id = domain.getId();
        domainName = domain.getDomainName();
        initUrl = domain.getInitUrl();
        pagingXPathQuery = domain.getPagingXPathQuery();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getInitUrl() {
        return initUrl;
    }

    public void setInitUrl(String initUrl) {
        this.initUrl = initUrl;
    }

    public String getPagingXPathQuery() {
        return pagingXPathQuery;
    }

    public void setPagingXPathQuery(String pagingXPathQuery) {
        this.pagingXPathQuery = pagingXPathQuery;
    }
    
    
}
