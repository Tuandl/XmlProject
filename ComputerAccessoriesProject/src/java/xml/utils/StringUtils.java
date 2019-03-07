/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;

/**
 *
 * @author admin
 */
public class StringUtils {
    
    public static String concatUrl(String url_1, String url_2) {
        String result = null;
        URI baseUri = null;
        try {
            baseUri = new URI(url_1);
        } catch (Exception e) {
            //url_1 is not base url
        }
        
        if(baseUri == null) {
            return result;
        } 
        
        URI fullUri = baseUri.resolve(url_2);
        
        result = fullUri.toString();
        
        return result;
    }
    
    public static int hashString(String content) {
        int mod = 1000000007;
        int base = 35381;
        
        int hasValue = 0;
        for(int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            hasValue = (int)(((long) hasValue * base + (long)c) % mod);
        }
        
        return hasValue;
    }
    
    public static String readInputStringStream(InputStream is) {
        StringBuilder builder = new StringBuilder();
        
        try {
            InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isReader);
            String line = null;
        
            while((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String result = builder.toString();
        return result;
    }
    
    public static String getParamFromInputStream(InputStream is, String paramName){
        String result = null;
        String datas = readInputStringStream(is);
        
        result = Arrays.stream(datas.split("&"))
                .filter((data) -> {
                    String[] fragments = data.split("=");
                    if(fragments.length == 2){
                        if(fragments[0].equals(paramName)) {
                            return true;
                        }
                    }
                    return false;
                })
                .map((data) -> {
                    String[] fragments = data.split("=");
                    return fragments[1];
                })
                .findAny()
                .get();
        
        return result;
    }
}
