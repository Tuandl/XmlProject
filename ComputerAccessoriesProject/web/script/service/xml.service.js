/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * For Common Usage of Xml
 * @returns {XmlService}
 */
var XmlService = function() {
    
    var parseStringToXml = function(text) {
        if(window.DOMParser) {
            var parser = new DOMParser();
            var xmlDoc = parser.parseFromString(text, 'text/xml')
            return xmlDoc;
        } else {
            var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
            xmlDoc.async = false;
            xmlDoc.loadXML(text);
        }
    }
    
    var parseXmlToString = function(xml) {
        var processingInstruction = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>';
        var objectSerializer = new XMLSerializer();
        var result = objectSerializer.serializeToString(xml);
        return result;
    }
    
    /**
     * Convert xmlDom into Object like JAXB
     * @param {type} xmlDom
     * @returns {unresolved}
     */
    var unmarshalling = function(xmlDom) {
        var isDocumentNode = xmlDom.nodeType == 9;
        var isTextNode = xmlDom.nodeType == 3;
        
        if(isDocumentNode) {
            //if document node, ignore this node
            return unmarshalling(xmlDom.childNodes[0]);
        }
        
        if(isTextNode) {
            //if text node, return value for field of object
            return xmlDom.nodeValue.trim();
        }
        
        var childDatas = [];
        xmlDom.childNodes.forEach(function(child) {
            childDatas.push(unmarshalling(child));
        });
        
        var data = {};
        
        childDatas.forEach(function(child) {
            if(typeof child != 'string' && typeof child != 'number'){
                var key = Object.keys(child)[0];
                
                if(data[key] != undefined) {
                    //existed -> array
                    var oldObj = {};
                    oldObj = data[key];
                    if(!Array.isArray(data[key])) {
                        data[key] = [
                            oldObj,
                            child[key],
                        ];
                    } else {
                        data[key].push(child[key]);
                    }
                } else {
                    data[key] = child[key];
                }
            } else {
                data = child;
            }
        });
        
        var result = {};
        result[xmlDom.nodeName] = data;
        
        return result;
    }
    
    /**
     * Marshalling Object to xmlDom like JAXB
     * @param {type} object
     * @param {type} rootTag
     * @returns {Document|XmlService.marshalling.xmlDoc}
     */
    var marshalling = function(object, rootTag) {
        var xmlDoc = createXmlDocument();
        var piNode = xmlDoc.createProcessingInstruction('xml', 'version="1.0" encoding="UTF-8" standalone="yes"');
        xmlDoc.appendChild(piNode);
        
        var rootNode = xmlDoc.createElement(rootTag);
        createSubTree(xmlDoc, rootNode, object);
        xmlDoc.appendChild(rootNode);
        return xmlDoc;
    }
    
    var createXmlDocument = function() {
        var xmlDoc = document.implementation.createDocument(null, null);
        return xmlDoc;
    }
    
    var createSubTree = function(xmlDoc, node, object) {
        if(object == null || object == undefined) {
            return;
        } 
        else if(typeof object == 'string' || typeof object == 'number') {
            var textNode = xmlDoc.createTextNode(object);
            node.appendChild(textNode);
            return;
        } 
        else {
            Object.keys(object).forEach(function(key) {
               var value = object[key];
               if(Array.isArray(value)) {
                   value.forEach(function(subObject) {
                        var newNode = xmlDoc.createElement(key);
                        createSubTree(xmlDoc, newNode, subObject);
                        node.appendChild(newNode);
                   });
               } else {
                   var newNode = xmlDoc.createElement(key);
                   createSubTree(xmlDoc, newNode, value);
                   node.appendChild(newNode);
               }
            });
        }
    }
    
    /**
     * Transform XMLDom with XSL to create new XMLDom
     * @param {type} xmlDom
     * @param {type} xslString
     * @returns {unresolved}
     */
    var transformToDocument = function(xmlDom, xslString) {
        var xsltProcessor = new XSLTProcessor();
        var xslDom = parseStringToXml(xslString)
        xsltProcessor.importStylesheet(xslDom);
        var result = xsltProcessor.transformToFragment(xmlDom, document);
        
        return result;
    }
    
    var removeAllChild = function(node) {
        while(node.firstChild) {
            node.removeChild(node.firstChild);
        }
    } 
    
    var queryStringXpath = function(xmlDom, query) {
        var xpathResult = document.evaluate(query, xmlDom, null, XPathResult.STRING_TYPE, null);
        return xpathResult.stringValue;
    }
    
    /**
     * Auto Detech root tag and call marshalling()
     * @param {type} object
     * @returns {Document|XmlService.marshalling.xmlDoc|Node}
     */
    var marshallingAuto = function(object) {
        var rootTag = Object.keys(object)[0];
        var result = marshalling(object[rootTag], rootTag);
        return result;
    }
    
    var marshallingThenParseToString = function(obj) {
        var xml = marshallingAuto(obj);
        var str = parseXmlToString(xml);
        return str;
    }
    
    var parseToXmlThenUnmarshalling = function(str) {
        var xml = parseStringToXml(str);
        var obj = unmarshalling(xml);
        return obj;
    }
    
    this.parseStringToXml = parseStringToXml;
    this.parseXmlToString = parseXmlToString;
    this.unmarshalling = unmarshalling;
    this.marshalling = marshalling;
    this.marshallingAuto = marshallingAuto;
    this.transformToDocument = transformToDocument;
    this.removeAllChild = removeAllChild;
    this.queryStringXpath = queryStringXpath;
    this.marshallingThenParseToString = marshallingThenParseToString;
    this.parseToXmlThenUnmarshalling = parseToXmlThenUnmarshalling;
}