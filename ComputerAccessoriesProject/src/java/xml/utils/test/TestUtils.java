/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils.test;

import xml.dto.UserDTO;
import xml.formater.SyntaxChecker;
import xml.formater.XMLFormater;
import xml.utils.StringUtils;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
public class TestUtils {
    public static void main(String[] args) {
//        testUnmarshaller();
        testRemovePredefined();
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
}
