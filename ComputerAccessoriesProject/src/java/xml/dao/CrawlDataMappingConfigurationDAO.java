/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.CrawlDataMappingConfiguration;

/**
 *
 * @author admin
 */
public class CrawlDataMappingConfigurationDAO extends DAOBase<CrawlDataMappingConfiguration>
        implements IDAO<CrawlDataMappingConfiguration>{
    
    public CrawlDataMappingConfigurationDAO() {
        super(CrawlDataMappingConfiguration.class);
    }
    
}
