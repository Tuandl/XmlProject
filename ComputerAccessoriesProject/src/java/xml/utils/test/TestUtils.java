/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.w3c.dom.Document;
import xml.dto.OrderDTO;
import xml.dto.UserDTO;
import xml.formater.SyntaxChecker;
import xml.formater.XMLFormater;
import xml.service.OrderService;
import xml.utils.FopUtils;
import xml.utils.StringUtils;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
public class TestUtils {
    public static void main(String[] args) {
//        testUnmarshaller();
//        testRemovePredefined();
//        testPdf();
        testUnescape();
    }
    
    public static void testUnmarshaller() {
        String data = "<user>"
//                + "<username>tuandapchai</username>"
                + "<fullname>Tuấn đập chai</fullname>"
                + "<isAdmin>true</isAdmin>"
                + "</user>";
        
        UserDTO user = (UserDTO) XMLUtils.unmarshallFromString(UserDTO.class, data);
        
        System.out.println("username = " + user.getUsername());
        System.out.println("full name = " + user.getFullname());
        System.out.println("is admnin = " + user.isIsAdmin());
    }
    
    public static void testDomainConcat() {
        String url_1 = "https://dalatlaptop.com/";
        String url_2 = "https://dalatlaptop.com/uploads/2015/03/cpu-core-2-dual-8400.jpg";
        
        String concated = StringUtils.concatUrl(url_1, url_2);
        System.out.println("concated = " + concated);
        
    }
    
    public static void testRemovePredefined() {
        String data = "\"&quot;&media<>&gt;&lt;'&apos;";
        
        System.out.println(SyntaxChecker.removePredefinedEntities(data));
    }
    
    public static void testPdf() {
        OrderService orderService = new OrderService();
        OrderDTO orderDto = orderService.getOrder("20190318135255");
        String domStr = XMLUtils.marshallToString(orderDto);
        Document dom = XMLUtils.parseDomFromString(domStr);
        try {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("order.test.pdf")));
            
            System.out.println(System.getProperty("user.dir"));
            FopUtils.transformDomToPdf(dom, "web/xsl/order.receipt.xsl", out, ".", "111");
            
            out.flush();
            out.close();
            
            System.out.println("FINISH");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void testUnescape(){
        String test = "Ram m&#225;y b&#224;n 1GB DDR3 bus 1333";
        System.out.println(StringUtils.unescapeHtmlEntities(test));
    }
}
