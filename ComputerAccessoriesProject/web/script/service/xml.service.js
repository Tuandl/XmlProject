/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    //Xml Dom 
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
                    oldObj[key] = data[key];
                    if(!Array.isArray(data[key])) {
                        data[key] = [
                            oldObj,
                            child,
                        ];
                    } else {
                        data[key].push(child);
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
        if(typeof object == 'string' || typeof object == 'number') {
            var textNode = xmlDoc.createTextNode(object);
            node.appendChild(textNode);
            return;
        } 
        else {
            Object.keys(object).forEach(function(key) {
               var value = object[key];
               if(Array.isArray(value)) {
                   value.forEach(function(subObject) {
//                        var newNode = xmlDoc.createElement(key);
                        createSubTree(xmlDoc, node, subObject);
//                        node.appendChild(newNode);
                   });
               } else {
                   var newNode = xmlDoc.createElement(key);
                   createSubTree(xmlDoc, newNode, value);
                   node.appendChild(newNode);
               }
            });
        }
    }
    
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
    
    this.parseStringToXml = parseStringToXml;
    this.parseXmlToString = parseXmlToString;
    this.unmarshalling = unmarshalling;
    this.marshalling = marshalling;
    this.transformToDocument = transformToDocument;
    this.removeAllChild = removeAllChild;
}