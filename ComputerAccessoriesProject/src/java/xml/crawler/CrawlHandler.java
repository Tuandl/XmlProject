/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.crawler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

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
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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
    
//    public static Object crawlData(String className, )
}
