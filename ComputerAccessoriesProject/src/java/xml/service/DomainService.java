/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import xml.dao.CrawlDataConfigurationDAO;
import xml.dao.CrawlDataMappingConfigurationDAO;
import xml.dao.CrawlDomainConfigurationDAO;
import xml.dto.DataConfigDTO;
import xml.dto.DataMappingDTO;
import xml.dto.DomainConfigDTO;
import xml.dto.DomainDTO;
import xml.dto.DomainsDTO;
import xml.model.CrawlDataConfiguration;
import xml.model.CrawlDataMappingConfiguration;
import xml.model.CrawlDomainConfiguration;

/**
 *
 * @author admin
 */
public class DomainService {
    private CrawlDomainConfigurationDAO domainDao;
    private CrawlDataConfigurationDAO dataDao;
    private CrawlDataMappingConfigurationDAO dataMappingDao;
    
    public DomainService() {
        domainDao = new CrawlDomainConfigurationDAO();
        dataDao = new CrawlDataConfigurationDAO();
        dataMappingDao = new CrawlDataMappingConfigurationDAO();
    }
    
    public CrawlDomainConfiguration getDomainById(int domainId) {
        CrawlDomainConfiguration domain = domainDao.getSingle("id = ?", domainId);
        return domain;
    }
    
    public List<CrawlDomainConfiguration> getAllDomains() {
        List<CrawlDomainConfiguration> domains = domainDao.getAll("deleted = ?", false);
        return domains;
    }
    
    public DomainsDTO getAllDomainDTOs() {
        String query = "deleted = ?";
        List<CrawlDomainConfiguration> domains = domainDao.getAll(query, false);
        List<DomainDTO> domainDtos = domains.stream()
                .map((x) -> new DomainDTO(x))
                .collect(Collectors.toList());
        
        DomainsDTO domainsDto = new DomainsDTO();
        domainsDto.setDomainDtos(domainDtos);
        return domainsDto;
    }
    
    public DomainConfigDTO getDomainConfigByDomainId(int domainId) {
        DomainConfigDTO result = null;
        
        CrawlDomainConfiguration domain = domainDao.getById(domainId);
        if(domain == null) {
            //return init domain configuration
            result = getInitDomainConfigDTO();
            return result;
        }
        
        result = new DomainConfigDTO(domain);
        
        List<CrawlDataMappingConfiguration> dataMappings = 
                dataMappingDao.getByDomainId(domainId);
        
        List<DataMappingDTO> dataMappingDtos = new ArrayList<>();
        
        for(CrawlDataMappingConfiguration dataMapping : dataMappings) {
            DataMappingDTO dataMappingdto = new DataMappingDTO(dataMapping);
            
            CrawlDataConfiguration data = dataDao.getById(dataMapping.getDataId());
            DataConfigDTO dataDto = new DataConfigDTO(data);
            
            dataMappingdto.setData(dataDto);
            
            dataMappingDtos.add(dataMappingdto);
        }
        
        result.setDataMappings(dataMappingDtos);;
        
        return result;
    }
    
    /**
     * Purpose: init dataMapping for all data when config new domain
     * @return 
     */
    public DomainConfigDTO getInitDomainConfigDTO() {
        DomainConfigDTO domainDto = new DomainConfigDTO();
        
        List<CrawlDataConfiguration> datas = dataDao.getAll();
        if(datas == null) return null;
        
        List<DataMappingDTO> dataMappingDtos = new ArrayList();
        
        datas.stream().forEach((data) -> {
            DataConfigDTO dataDto = new DataConfigDTO(data);
            
            DataMappingDTO dataMappingDto = new DataMappingDTO();
            dataMappingDto.setDataId(data.getId());
            dataMappingDto.setData(dataDto);
            
            
            dataMappingDtos.add(dataMappingDto);
        });
        
        domainDto.setDataMappings(dataMappingDtos);
        return domainDto;
    }
    
    public boolean addDomainConfig(DomainConfigDTO domainDto) {
        if(!checkDataMappingDTO(domainDto)) {
            return false;
        }
        
        CrawlDomainConfiguration domain = new CrawlDomainConfiguration(domainDto);
        boolean inserted = domainDao.insert(domain);
        if(!inserted) {
            System.out.println("ERROR: cannot insert domainConfig");
            return false;
        }
        
        for(DataMappingDTO dataMappingDto : domainDto.getDataMappings()) {
            CrawlDataMappingConfiguration dataMapping = new CrawlDataMappingConfiguration(dataMappingDto);
            dataMapping.setDomainId(domain.getId());
            
            inserted = dataMappingDao.insert(dataMapping);
            if(!inserted) {
                System.out.println("ERROR: cannot insert dataMapping");
                return false;
            }
        }
        
        return true;
    }
    
    public boolean updateDomainConfig(DomainConfigDTO domainDto) {
        CrawlDomainConfiguration domain = new CrawlDomainConfiguration(domainDto);
        
        boolean updated = domainDao.update(domain);
        if(!updated) {
            System.out.println("ERROR: cannot update domain");
            return false;
        }
        
        for(DataMappingDTO dataMappingDto : domainDto.getDataMappings()) {
            CrawlDataMappingConfiguration dataMapping = new CrawlDataMappingConfiguration(dataMappingDto);
            
            updated = dataMappingDao.update(dataMapping);
            if(!updated) {
                System.out.println("ERROR: cannot update dataMapping");
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Purpose: check dataId in DataMappingDTOs
     * @param domainDto
     * @return 
     */
    private boolean checkDataMappingDTO(DomainConfigDTO domainDto) {
        if(domainDto == null) {
            return false;
        }
        if(domainDto.getDataMappings() == null) {
            return false;
        }
        
        List<CrawlDataConfiguration> datas = dataDao.getAll();
        
        if(domainDto.getDataMappings().size() != datas.size()) {
            return false;
        }
        
        for(DataMappingDTO dataMapping : domainDto.getDataMappings()) {
            boolean existData = datas.stream().anyMatch((data) -> {
                return data.getId() == dataMapping.getDataId();
            });
            
            if(!existData) {
                System.out.println("ERROR: Not found dataId (" + dataMapping.getDataId() + ") in DataMappingDTO");
                return false;
            }
        }
        
        return true;
    }
    
    public boolean deleteDomain(int domainId) {
        
        CrawlDomainConfiguration domain = domainDao.getById(domainId);
        if(domain == null) {
            return false;
        }
        
        String filter = "domainId = ?";
        boolean deleted = dataMappingDao.delete(filter, domainId);
        if(!deleted) {
            System.out.println("ERROR: Cannot delete data mapping");
            return false;
        }
        
        deleted = domainDao.delete(domainId);
        if(!deleted) {
            System.out.println("ERROR: cannot delete domain");
            return false;
        }
        
        return true;
    }
}
