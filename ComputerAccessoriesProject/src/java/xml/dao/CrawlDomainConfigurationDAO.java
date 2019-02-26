/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.CrawlDomainConfiguration;

/**
 *
 * @author admin
 */
public class CrawlDomainConfigurationDAO extends DAOBase<CrawlDomainConfiguration>
        implements IDAO<CrawlDomainConfiguration>{
    
    public CrawlDomainConfigurationDAO() {
        super(CrawlDomainConfiguration.class);
    }
    
}
