/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.formater;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author admin
 */
public class SyntaxChecker {
    static final char OPEN_BRACKET_CHAR = '<';
    static final char CLOSE_BRACKET_CHAR = '>';
    static final char DOUBLE_QUOT = '\"';
    static final char SIGNAL_QUOT = '\'';
    static final char SLASH = '/';
    static final char WHITE_SPACE = ' ';
    static final char EQUAL = '=';
    static final char UNDERSCORE = '_';
    static final char COLON = ':';
    static final char PERIOD = '.';
    static final char HYPHEN = '-';
    
    private static boolean isStartChar(char c){
        return Character.isLetter(c) || c == UNDERSCORE;
    }
    
    private static boolean isNameChar(char c){
        return Character.isLetterOrDigit(c) || c == PERIOD ||
                c == COLON || c == HYPHEN || c == UNDERSCORE;
    }
    
    static boolean isStartTagChar(char c){
        return isStartChar(c);
    }
    
    static boolean isStartAttributeChar(char c){
        return isStartChar(c);
    }
    
    static boolean isTagChar(char c){
        return isNameChar(c);
    }
    
    static boolean isAttributeChar(char c){
        return isNameChar(c);
    }
    
    static boolean isSpaceChar(char c){
        return Character.isWhitespace(c);
    }
    
    static final List<String> INLINE_TAG = Arrays.asList(
            "area", "base", "br", "col", "command", 
            "embeb", "hr", "img", "input", "keygen",
            "link", "meta", "param", "source", 
            "track", "wbr");
    
    static final String removePredefinedEntities(String str){
        
        String result = str.replace("&", "&amp;")
            .replace("\"", "&quot;")
            .replace("\'", "&apos;")
            .replace("<", "&lt;")
            .replace(">", "&gt;");
        
        return result;
    }
    
    static final String removePredefinedEntities(StringBuilder str){
        String result = removePredefinedEntities(str.toString());
        return result;
    }
}
