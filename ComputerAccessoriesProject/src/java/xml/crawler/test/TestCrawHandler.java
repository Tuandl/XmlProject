/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.crawler.test;

import xml.crawler.CrawlHandler;
import xml.formater.XMLFormater;
import xml.utils.FileUtils;

/**
 *
 * @author admin
 */
public class TestCrawHandler {
    public static void main(String[] args) {
//        String url = "https://dalatlaptop.com/";
//        String url = "http://namtruongthinhdalat.vn/";
        String url = "http://namtruongthinhdalat.vn/san-pham-chi-tiet/camera-ip-megapixel-dahua-2mp-ipc-hfw4239tp-ase";
        String htmlData = CrawlHandler.getDataFromHtml(url);
        htmlData = XMLFormater.removeMiscellaneousTags(htmlData);
        String test = XMLFormater.generateXMLWellForm(htmlData);
        
        FileUtils.saveIntoFile("testdata.xml", test);
        System.out.println("done");
        
    }
}
