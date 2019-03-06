/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.thread;

import xml.service.CrawlService;

/**
 *
 * @author admin
 */
public class CrawlProductDetailRawThread extends Thread {
    private CrawlService crawlService;
    private int productRawId;
    
    public CrawlProductDetailRawThread(int productRawId) {
        crawlService = new CrawlService();
        this.productRawId = productRawId;
    }

    @Override
    public void run() {
        crawlService.crawlProductDetailRaw(productRawId);
    }
    
}
