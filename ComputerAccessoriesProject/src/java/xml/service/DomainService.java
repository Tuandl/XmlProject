/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service;

import xml.dao.CrawlDomainConfigurationDAO;
import xml.model.CrawlDomainConfiguration;

/**
 *
 * @author admin
 */
public class DomainService {
    private CrawlDomainConfigurationDAO domainDao;
    
    public DomainService() {
        domainDao = new CrawlDomainConfigurationDAO();
    }
    
    public CrawlDomainConfiguration getDomainById(int domainId) {
        CrawlDomainConfiguration domain = domainDao.getSingle("id = ?", domainId);
        return domain;
    }
    
    
}
