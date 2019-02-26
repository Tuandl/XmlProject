/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.CrawlDataConfiguration;

/**
 *
 * @author admin
 */
public class CrawlDataConfigurationDAO extends DAOBase<CrawlDataConfiguration> 
        implements IDAO<CrawlDataConfiguration>{
    
    public CrawlDataConfigurationDAO() {
        super(CrawlDataConfiguration.class);
    }
    
}
