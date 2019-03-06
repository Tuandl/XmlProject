/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils.test;

import xml.utils.StringUtils;

/**
 *
 * @author admin
 */
public class TestUtils {
    public static void main(String[] args) {
        String url_1 = "https://dalatlaptop.com/";
        String url_2 = "https://dalatlaptop.com/uploads/2015/03/cpu-core-2-dual-8400.jpg";
        
        String concated = StringUtils.concatUrl(url_1, url_2);
        System.out.println("concated = " + concated);
    }
}
