/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.formater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static xml.formater.SyntaxChecker.*;

/**
 *
 * @author admin
 */
public class XMLFormater {
    
    private enum RunningState {
        JUST_START,
        OPEN_BRACKET,
        OPEN_TAG_NAME,
        INNER_TAG,
        ATTRIBUTE_NAME,
        EQUAL_WAIT,
        EQUAL,
        ATTRIBUTE_VALUE,
        ATTRIBUTE_VALUE_NO_QUOTE,
        SLASH_EMPTY_TAG,
        CLOSE_BRACKET,
        CHARACTER,
        SLASH_CLOSE_TAG,
        CLOSE_TAG_NAME,
        WAIT_CLOSE_BRACKET
    }
    
    private enum TagType {
        OPEN_TAG,
        CLOSE_TAG,
        EMPTY_TAG
    }
    
    public static String removeMiscellaneousTags(String src) {
        String result = src;
        
        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");
        
        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");
        
        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");
        
        expression = "<fb:like.*?</fb:like>";
        result = result.replaceAll(expression, "");
        
        expression = "<fb:share-button.*?</fb:share-button>";
        result = result.replaceAll(expression, "");
        
        return result;
    }
    
    public static String generateXMLWellForm(String htmlString){
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> tagQueue = new ArrayList<>();
        
        char[] chars = htmlString.toCharArray();
        StringBuilder currentTag = new StringBuilder();
        StringBuilder currentAttribute = new StringBuilder();
        StringBuilder currentValue = new StringBuilder();
        StringBuilder currentContent = new StringBuilder();
        Map<String, String> attributes = new HashMap<>();
        RunningState currentState = RunningState.JUST_START;
        
        TagType tagType = null;
        char quot = DOUBLE_QUOT;
        String lastTag;
        
        for(int index = 0; index < chars.length; index++) {
            char currentChar = chars[index];
            switch(currentState){
                case JUST_START:
                    if(currentChar == OPEN_BRACKET_CHAR){
                        tagType = TagType.OPEN_TAG;
                        currentState = RunningState.OPEN_BRACKET;
                    }
                    break;
                case CHARACTER:
                    if(currentChar == OPEN_BRACKET_CHAR){
                        //Save content before change state
                        resultBuilder
                                .append(removePredefinedEntities(currentContent));
                        currentContent.setLength(0);
                        
                        //Change state
                        tagType = TagType.OPEN_TAG;
                        currentState = RunningState.OPEN_BRACKET;
                    }
                    else {
                        currentContent.append(currentChar);
                    }
                    break;
                case OPEN_BRACKET: 
                    if(isStartTagChar(currentChar)){
                        //save first character of element and change state
                        currentTag.append(Character.toLowerCase(currentChar));
                        currentState = RunningState.OPEN_TAG_NAME;
                    }
                    else if (currentChar == SLASH){
                        tagType = TagType.CLOSE_TAG;
                        currentState = RunningState.SLASH_CLOSE_TAG;
                    }
                    break;
                case OPEN_TAG_NAME: 
                    if(isTagChar(currentChar)){
                        currentTag.append(Character.toLowerCase(currentChar));
                    } 
                    else if(isSpaceChar(currentChar)){
                        //Change state
                        currentState = RunningState.INNER_TAG;
                    }
                    else if(currentChar == SLASH){
                        tagType = TagType.EMPTY_TAG;
                        currentState = RunningState.SLASH_EMPTY_TAG;
                    }
                    else if(currentChar == CLOSE_BRACKET_CHAR){
                        if(INLINE_TAG.contains(currentTag.toString())){
                            tagType = TagType.EMPTY_TAG;
                        }
                        currentState = RunningState.CLOSE_BRACKET;
                    }
                    break;
                case INNER_TAG:
                    if(isStartAttributeChar(currentChar)){
                        currentAttribute.setLength(0);
                        currentAttribute.append(currentChar);
                        currentState = RunningState.ATTRIBUTE_NAME;
                    }
                    else if(currentChar == SLASH){
                        tagType = TagType.EMPTY_TAG;
                        currentState = RunningState.SLASH_EMPTY_TAG;
                    }
                    else if(currentChar == CLOSE_BRACKET_CHAR){
                        currentState = RunningState.CLOSE_BRACKET;
                    }
                    break;
                case ATTRIBUTE_NAME:
                    if(isAttributeChar(currentChar)){
                        currentAttribute.append(currentChar);
                    }
                    else if(isSpaceChar(currentChar)){
                        currentState = RunningState.EQUAL_WAIT;
                    }
                    else if(currentChar == CLOSE_BRACKET_CHAR){
                        attributes.put(currentAttribute.toString(), "true");
                        currentState = RunningState.CLOSE_BRACKET;
                    }
                    else if(currentChar == SLASH){
                        tagType = TagType.EMPTY_TAG;
                        attributes.put(currentAttribute.toString(), "true");
                        currentState = RunningState.SLASH_EMPTY_TAG;
                    } 
                    else if(currentChar == EQUAL){
                        currentState = RunningState.EQUAL;
                    }
                    break;
                case EQUAL_WAIT: 
                    if(currentChar == EQUAL){
                        currentState = RunningState.EQUAL;
                    } 
                    else if(isAttributeChar(currentChar)){
                        attributes.put(currentAttribute.toString(), "true");
                        currentAttribute.setLength(0);
                        currentAttribute.append(currentChar);
                        currentState = RunningState.ATTRIBUTE_NAME;
                    }
                    else if(currentChar == SLASH){
                        attributes.put(currentAttribute.toString(), "true");
                        tagType = TagType.EMPTY_TAG;                        
                    } 
                    else if(currentChar == CLOSE_BRACKET_CHAR){
                        attributes.put(currentAttribute.toString(), "true");
                        currentState = RunningState.CLOSE_BRACKET;
                    }
                    break;
                case EQUAL: 
                    if(currentChar == DOUBLE_QUOT || currentChar == SIGNAL_QUOT){
                        quot = currentChar;
                        currentValue.setLength(0);
                        currentState = RunningState.ATTRIBUTE_VALUE;
                    }
                    else if(currentChar == SLASH){
                        //Exception -> not save this attribute
                        tagType = TagType.EMPTY_TAG;
                        currentState = RunningState.SLASH_EMPTY_TAG;
                    }
                    else if(currentChar == CLOSE_BRACKET_CHAR){
                        //Exception -> not save this attribute
                        currentState = RunningState.CLOSE_BRACKET;
                    }
                    else if(!isSpaceChar(currentChar)){
                        currentValue.setLength(0);
                        currentValue.append(currentChar);
                        currentState = RunningState.ATTRIBUTE_VALUE_NO_QUOTE;
                    }
                    break;
                case ATTRIBUTE_VALUE:
                    if(currentChar == quot){
                        attributes.put(
                                currentAttribute.toString(),  
                                removePredefinedEntities(currentValue));
                        currentAttribute.setLength(0);
                        currentValue.setLength(0);
                        currentState = RunningState.INNER_TAG;
                    }
                    else {
                        currentValue.append(currentChar);
                    }
                    break;
                case ATTRIBUTE_VALUE_NO_QUOTE:
                    if(isSpaceChar(currentChar)){
                        attributes.put(
                                currentAttribute.toString(),  
                                removePredefinedEntities(currentValue));
                        currentAttribute.setLength(0);
                        currentValue.setLength(0);
                        currentState = RunningState.INNER_TAG;
                    }
                    else if(currentChar == SLASH) {
                        tagType = TagType.EMPTY_TAG;
                        attributes.put(
                                currentAttribute.toString(), 
                                removePredefinedEntities(currentValue));
                        currentAttribute.setLength(0);
                        currentValue.setLength(0);
                        currentState = RunningState.SLASH_EMPTY_TAG;
                    }
                    else if(currentChar == CLOSE_BRACKET_CHAR){
                        attributes.put(
                                currentAttribute.toString(), 
                                removePredefinedEntities(currentValue));
                        currentAttribute.setLength(0);
                        currentValue.setLength(0);
                        currentState = RunningState.CLOSE_BRACKET;
                    }
                    else {
                        currentValue.append(currentChar);
                    }
                    break;
                case CLOSE_TAG_NAME:
                    if(isTagChar(currentChar)){
                        currentTag.append(Character.toLowerCase(currentChar));
                    }
                    else if(currentChar == CLOSE_BRACKET_CHAR){
                        currentState = RunningState.CLOSE_BRACKET;
                    }
                    else {
                        currentState = RunningState.WAIT_CLOSE_BRACKET;
                    }
                    break;
                case SLASH_CLOSE_TAG:
                    if(currentChar == CLOSE_BRACKET_CHAR){
                        currentState = RunningState.CLOSE_BRACKET;
                    }
                    else if(isTagChar(currentChar)){
                        currentTag.append(currentChar);
                    }
                    else if(isSpaceChar(currentChar)){
                        currentState = RunningState.WAIT_CLOSE_BRACKET;
                    }
                    break;
                case SLASH_EMPTY_TAG:
                    if(currentChar == CLOSE_BRACKET_CHAR){
                        currentState = RunningState.CLOSE_BRACKET;
                    }
                    break;
                case WAIT_CLOSE_BRACKET:
                    if(currentChar == CLOSE_BRACKET_CHAR){
                        currentState = RunningState.CLOSE_BRACKET;
                    }
                    break;
                case CLOSE_BRACKET: 
                    //end read a tag -> validate tag and save tag
                    switch(tagType){
                        case OPEN_TAG:
                            enQueue(tagQueue, currentTag.toString());
                            
                            resultBuilder.append(
                                    generateOpenTag(currentTag.toString(), attributes));
                            
                            break;
                        case EMPTY_TAG:
                            resultBuilder.append(
                                    generateEmptyTag(currentTag.toString(), attributes));
                            break;
                        case CLOSE_TAG:
                            
                            lastTag = deQueue(tagQueue);
                            if(!lastTag.equals(currentTag.toString())){
                                if(tagQueue.contains(currentTag.toString())){
                                    //Missing close tag 
                                    while(!lastTag.equals(currentTag.toString())){
                                        resultBuilder.append(generateCloseTag(lastTag));
                                        lastTag = deQueue(tagQueue);
                                    }
                                }
                                else {
                                    //Missing Open Tag
                                    //Delete extra close tag
                                    currentTag.setLength(0);
                                }
                            }
                            
                            resultBuilder.append(generateCloseTag(currentTag.toString()));
                            break;
                    }
                    
                    //Reset data
                    currentAttribute.setLength(0);
                    currentContent.setLength(0);
                    currentTag.setLength(0);
                    currentValue.setLength(0);
                    attributes.clear();
                    
                    //Continue switch state
                    if(currentChar == OPEN_BRACKET_CHAR){
                        currentState = RunningState.OPEN_BRACKET;
                        tagType = TagType.OPEN_TAG;
                    }
                    else {
                        currentContent.append(currentChar);
                        currentState = RunningState.CHARACTER;
                    }
                    break;
            }
        }
        
        if(currentState == RunningState.CHARACTER){
            resultBuilder.append(currentContent);
        }
        
        while(tagQueue.size() > 0){
            lastTag = deQueue(tagQueue);
            resultBuilder.append(generateCloseTag(lastTag));
        }
        
        String result = resultBuilder.toString();
        return result;
    }
    
    private static String convertAttributes(Map<String, String> attributeStrings){
        if(attributeStrings == null || attributeStrings.isEmpty()){
            return "";
        }
        
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> attributeString : attributeStrings.entrySet()) {
            String key = attributeString.getKey();
            String value = removePredefinedEntities(attributeString.getValue());
            
            builder.append(key)
                    .append(EQUAL)
                    .append(DOUBLE_QUOT)
                    .append(value)
                    .append(DOUBLE_QUOT)
                    .append(WHITE_SPACE);
        }
        
        String result = builder.toString().trim();
        if(result.length() > 0){
            result = WHITE_SPACE + result;
        }
        return result;
    }
    
    private static String deQueue(List<String> queue){
        if(queue == null || queue.size() == 0){
            return "";
        }
        String element = queue.get(queue.size() - 1);
        queue.remove(queue.size() - 1);
        return element;
    }
    
    private static void enQueue(List<String> queue, String value){
        queue.add(value);
    }
    
    private static String generateOpenTag(String tagName, 
            Map<String, String> attributes){
        if(tagName == null || tagName.length() == 0){
            return "";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append(OPEN_BRACKET_CHAR)
                .append(tagName)
                .append(convertAttributes(attributes))
                .append(CLOSE_BRACKET_CHAR);
        return builder.toString();
    }
    
    private static String generateEmptyTag(String tagName,
            Map<String, String> attributes){
        if(tagName == null || tagName.length() == 0){
            return "";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append(OPEN_BRACKET_CHAR)
                .append(tagName)
                .append(convertAttributes(attributes))
                .append(SLASH)
                .append(CLOSE_BRACKET_CHAR);
        return builder.toString();
    }
    
    private static String generateCloseTag(String tagName){
        if(tagName == null || tagName.length() == 0){
            return "";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append(OPEN_BRACKET_CHAR)
                .append(SLASH)
                .append(tagName)
                .append(CLOSE_BRACKET_CHAR);
        return builder.toString();
    }
}
