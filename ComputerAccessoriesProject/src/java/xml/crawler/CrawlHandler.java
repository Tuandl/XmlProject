/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.crawler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xml.dao.CrawlDataConfigurationDAO;
import xml.dao.CrawlDataMappingConfigurationDAO;
import xml.formater.XMLFormater;
import xml.model.CrawlDataConfiguration;
import xml.model.CrawlDataMappingConfiguration;
import xml.utils.ReflectionUtils;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
public class CrawlHandler {
    
    public static String getDataFromHtml(String urlString){
        
        StringBuilder builder = new StringBuilder();
        URL url = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String line = null;
        boolean foundBody = false;
        
        try {
            url = new URL(urlString);
            inputStream = url.openStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = bufferedReader.readLine()) != null) {
                if(foundBody){
                    builder.append(line);
                    if(line.contains("</body")) {
                        break;
                    }
                }
                else {
                    if(line.contains("<body")) {
                        foundBody = true;
                        builder.append(line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        String result = builder.toString();
        
        return result;
    }
    
    public static List<Object> extractData(int domainId, Class ObjClass, 
            String htmlFormated) {
        List<Object> results = new ArrayList<>();
        
        try{
            //Get config for class
            CrawlDataConfigurationDAO dataConfigDao = 
                    new CrawlDataConfigurationDAO();
            
            String className = ObjClass.getSimpleName();
            List<CrawlDataConfiguration> dataConfigs = 
                    dataConfigDao.getAll("className = ?", className);

            CrawlDataConfiguration blockData = null;
            for(CrawlDataConfiguration data : dataConfigs){
                if(data.getPropertyName() == null) {
                    blockData = data;
                    break;
                }
            }
            dataConfigs.remove(blockData);
            
            if(blockData == null) {
                System.out.println("Not found Block data");
                return results;
            }
            
            //Get xpath for block
            CrawlDataMappingConfigurationDAO dataMappingDAO = 
                    new CrawlDataMappingConfigurationDAO();
            CrawlDataMappingConfiguration blockMapping =
                    dataMappingDAO.getSingle("domainId = ? and dataId = ?", 
                            domainId, blockData.getId());
            
            if(blockMapping == null) {
                System.out.println("Not found Block mapping");
                return results;
            }
            
            //get xpath for prop
            List<CrawlDataMappingConfiguration> propsMapping = 
                    new ArrayList();
            
            for(CrawlDataConfiguration dataConfig : dataConfigs) {
                CrawlDataMappingConfiguration dataMapping = 
                        dataMappingDAO.getSingle("domainId = ? and dataId = ?", 
                                domainId, dataConfig.getId());
                if(dataMapping != null) {
                    propsMapping.add(dataMapping);
                }
            }
            
            if(propsMapping == null || propsMapping.size() == 0) {
                System.out.println("Not Found Prop mapping");
                return results;
            }
            
            //Xpath query for block element
            Document document = XMLUtils.parseDomFromString(htmlFormated);
            
            if(document == null) {
                System.out.println("Cannot create document");
                return results;
            }
            
            XPath xpath = XMLUtils.createXPath();
            String expression = blockMapping.getxPathQuery();
            
            NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
            
            for(int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i).cloneNode(true);
                
                Object data = ObjClass.newInstance();
                boolean validated = true;
                
                for(CrawlDataMappingConfiguration propMapping : propsMapping) {
                    //Get field of object for reflection
                    String fieldName = "";
                    for(CrawlDataConfiguration dataConfig : dataConfigs) {
                        if(dataConfig.getId() == propMapping.getDataId()) {
                            fieldName = dataConfig.getPropertyName();
                            break;
                        }
                    }
                    if(fieldName.isEmpty()) continue;
                    Field field = ReflectionUtils.getFieldByName(ObjClass, fieldName);
                    
                    //query to get value of field
                    //reason to add '.' is to query only subtree of this node
                    expression = "." + propMapping.getxPathQuery();
                    
                    Object value = null;
                    
                    if(!propMapping.isIsNodeResult()) {
                        //query for string or integer
                        value = xpath.evaluate(expression, node, XPathConstants.STRING);
                        value = ((String) value).trim();
                        
                        //Validate data
                        //validate int
                        if(field.getType().equals(int.class) || field.getType().equals(Integer.TYPE)) {
                            try {
                                String valueStr = XMLFormater.parsePrice((String) value);
                                int parsedInt = Integer.parseInt(valueStr);
                                value = parsedInt;
                            } catch (Exception e) {
                                //cannot parsed
                                validated = false;
                                break;
                            } 
                        } 
                    }
                    else {
                        //query for node 
                        NodeList nodeValues = (NodeList) xpath.evaluate(expression, node, XPathConstants.NODESET);
                        StringBuilder valueBuilder = new StringBuilder();
                        for(int index_value = 0; index_value < nodeValues.getLength(); index_value++ ){
                            Node nodeValue = nodeValues.item(index_value);
                            valueBuilder.append(XMLUtils.parseNodeToString(nodeValue));
                        }
                        
                        value = valueBuilder.toString();
                    }

                    ReflectionUtils.setFieldValue(data, field, value);
                }
                
                if(validated) {
                    results.add(data);
                }
            }
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return results;
    }
    
    public static String extractString(String xmlString, String expression) {
        String result = null;
        try {
            Document document = XMLUtils.parseDomFromString(xmlString);
            if(document == null) {
                System.out.println("Cannot create dom");
                return result;
            }
            
            XPath xpath = XMLUtils.createXPath();
            result = (String) xpath.evaluate(expression, document, XPathConstants.STRING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    
}
