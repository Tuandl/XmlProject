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
        String url = "https://dalatlaptop.com/";
        String htmlData = CrawlHandler.getDataFromHtml(url);
        String test = XMLFormater.generateXMLWellForm(htmlData);
        
        FileUtils.saveIntoFile("testdata.xml", test);
        System.out.println("done");
        
    }
}
