/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import java.util.List;
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
    
    public List<CrawlDataMappingConfiguration> getByDomainId(int domainId) {
        String filter = "domainId = ? and deleted = ?";
        List<CrawlDataMappingConfiguration> result = 
                getAll(filter, domainId, false);
        
        return result;
    }
}
