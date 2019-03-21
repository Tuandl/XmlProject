/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "domains")
public class DomainsDTO {
    @XmlElement(name = "domain")
    private List<DomainDTO> domainDtos;

    public List<DomainDTO> getDomainDtos() {
        return domainDtos;
    }

    public void setDomainDtos(List<DomainDTO> domainDtos) {
        this.domainDtos = domainDtos;
    }
    
    
}
