/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.fop.resolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.MimeConstants;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;

/**
 *  Resolver for external resource
 * @author admin
 */
public class LocalResolver implements ResourceResolver{
    private String baseFolder;
    
    public LocalResolver(String baseFolder) {
        this.baseFolder = baseFolder;
    }

    @Override
    public OutputStream getOutputStream(URI uri) throws IOException {
        System.out.println("Test GetOutputStream " + uri.toASCIIString());
        return new URL(uri.toASCIIString()).openConnection().getOutputStream();
    }

    @Override
    public Resource getResource(URI uri) throws IOException {
        System.out.println("HERE");
        try {
            String realPath = baseFolder + uri.getSchemeSpecificPart().replace("/", "\\");

            System.out.println("Real Path " + realPath);
            File file = new File(realPath);
            InputStream inputStream = new FileInputStream(file);
            return new Resource(MimeConstants.MIME_PNG, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
