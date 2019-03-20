/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.MimeConstants;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.w3c.dom.Document;
import xml.fop.resolver.LocalResolver;

/**
 *
 * @author admin
 */
public class FopUtils {
    private static final String FOP_CONFIG_PATH = "WEB-INF\\fop.xconf";
    
    public static void transformDomToPdf(Document dom, String xslPath, OutputStream outputStream,
            String basePath, String title) {
        try {
            //Setup local resource
//            ResourceResolver resolver = new LocalResolver(basePath);
            
            //Setup Fop
//            FopFactoryBuilder builder = new FopFactoryBuilder(new File(".").toURI(), resolver);
//            FopFactoryBuilder builder = new FopFactoryBuilder(new File(basePath + FOP_CONFIG_PATH).toURI(), resolver);
//            FopFactoryBuilder builder = new FopFactoryBuilder(new File(basePath + FOP_CONFIG_PATH).toURI());
            
//            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FopFactory fopFactory = FopFactory.newInstance(new File(basePath + FOP_CONFIG_PATH));
//            FopFactory fopFactory = builder.build();
            
            FOUserAgent userAgent = fopFactory.newFOUserAgent();
            userAgent.setProducer("ComputerAccessories");
            userAgent.setCreator("Tuan");
            userAgent.setAuthor("Tuan");
            userAgent.setCreationDate(new Date());
            userAgent.setTitle(title);

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outputStream);
            
            //Setup transfomer
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Source xsltSource = new StreamSource(new File(xslPath));
            Transformer transformer = transformerFactory.newTransformer(xsltSource);
            transformer.setParameter("basePath", basePath);

            //Make XSL transformation result is in pipeline
            Result res = new SAXResult(fop.getDefaultHandler());
            
            //Setup input
            Source src = new DOMSource(dom);
            
            //Start transformation
            transformer.transform(src, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void transformXslfoToPDF() {
//        try {
//            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
//            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, response.getOutputStream());
//            Transformer transformer = tFactory.newTransformer();
//            Source src = new StreamSource("foo.fo");
//            Result res = new SAXResult(fop.getDefaultHandler());
//            transformer.transform(src, res);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
