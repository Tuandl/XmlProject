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
public class CrawlDomainConfiguration extends ModelBase{
    private String domainName;
    private String initUrl;
    private String pagingXPathQuery;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getPagingXPathQuery() {
        return pagingXPathQuery;
    }

    public void setPagingXPathQuery(String pagingXPathQuery) {
        this.pagingXPathQuery = pagingXPathQuery;
    }

    public String getInitUrl() {
        return initUrl;
    }

    public void setInitUrl(String initUrl) {
        this.initUrl = initUrl;
    }
    
    
    
}
