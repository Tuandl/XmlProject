/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

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
    
    public static String readStringFromInputStream(InputStream is) {
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
        String datas = readStringFromInputStream(is);
        
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
    
    public static Integer parseStringToInt(String in) {
        Integer result = null;
        
        try {
            result = Integer.parseInt(in);
        } catch (Exception e) {
            //cannot parse;
        }
        
        return result;
    }
    
    public static String normalize(String s) {
        String tmp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(tmp).replaceAll("");
        
        result = result.replace('đ', 'd');
        result = result.replace('Đ', 'D');
        return result;
    }
    
    public static String unescapeHtmlEntities(String s) {
        try {
            HTMLDocument doc = new HTMLDocument();
            new HTMLEditorKit().read( new StringReader( "<html><body>" + s ), doc, 0 );
            return doc.getText( 1, doc.getLength() );
        } catch( Exception ex ) {
            return s;
        }
    }
}
