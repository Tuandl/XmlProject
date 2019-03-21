/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xml.dto.DomainConfigDTO;
import xml.dto.DomainDTO;
import xml.dto.DomainsDTO;
import xml.service.DomainService;
import xml.utils.StringUtils;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "DomainServlet", urlPatterns = {"/Domain"})
public class DomainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DomainService domainService = new DomainService();
        
        try {
            String domainIdStr = req.getParameter("id");
            if(domainIdStr == null) {
                //get all domains
                DomainsDTO dtos = domainService.getAllDomainDTOs();
                XMLUtils.marshallToOutputStream(dtos, resp.getOutputStream());
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                Integer domainId = StringUtils.parseStringToInt(domainIdStr);
                if(domainId == null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                
                //Get by id
                DomainConfigDTO dto = domainService.getDomainConfigByDomainId(domainId);
                XMLUtils.marshallToOutputStream(dto, resp.getOutputStream());
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DomainService domainService = new DomainService();
        try {
            String data = req.getParameter("data");
            if(data == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            DomainConfigDTO domainDto = (DomainConfigDTO) XMLUtils.unmarshallFromString(DomainConfigDTO.class, data);
            if(domainDto == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            boolean inserted = domainService.addDomainConfig(domainDto);
            if(inserted) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DomainService domainService = new DomainService();
        try {
            String data = StringUtils.readStringFromInputStream(req.getInputStream());
            if(data == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            DomainConfigDTO dto = (DomainConfigDTO) XMLUtils.unmarshallFromString(DomainConfigDTO.class, data);
            if(dto == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            boolean updated = domainService.updateDomainConfig(dto);
            if(updated) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DomainService domainService = new DomainService();
        try {
            String domainIdStr = req.getParameter("id");
            if(domainIdStr == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            Integer domainId = StringUtils.parseStringToInt(domainIdStr);
            if(domainId == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            boolean deleted = domainService.deleteDomain(domainId);
            if(deleted) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
