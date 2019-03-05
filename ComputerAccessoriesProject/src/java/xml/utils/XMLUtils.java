/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils;

import com.sun.xml.bind.StringInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 *
 * @author admin
 */
public class XMLUtils {
    public static String marshallToString(Object object) {
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(object, stringWriter);
            
            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static void marshallToWriter(Object object, Writer writer){
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, "UTF-8");
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void marshallToOutputStream(Object object, OutputStream outputStream){
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, "UTF-8");
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Document parseDomFromString(String xmlContent) {
        try {
            DocumentBuilderFactory domBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domBuilder = domBuilderFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlContent));
            Document result = domBuilder.parse(is);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static XPath createXPath() {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        
        return xpath;
    }
    
    public static String getPIForHtml(String htmlString) {
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + htmlString;
        return result;
    }
    
    public static String parseNodeToString(Node node) {
        String result = "";
        
        try {
            TransformerFactory transformFactory = TransformerFactory.newInstance();
            Transformer transformer = transformFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            
            StreamResult xmlStream = new StreamResult(new StringWriter());
            DOMSource domSource = new DOMSource(node);
            
            transformer.transform(domSource, xmlStream);
            
            result = xmlStream.getWriter().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
}
