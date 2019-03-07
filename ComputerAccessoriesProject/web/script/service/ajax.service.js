/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var AjaxService = function() {
    
    var getXmlHttpObject = function() {
        if(window.XMLHttpRequest) {
            //for modern browser
            return new XMLHttpRequest();
        } else {
            //for old IE browsers
            return new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    
    this.get = function(url, params) {
        return new Promise(function(resolve, reject) {
            var xhttp = getXmlHttpObject();
            xhttp.onreadystatechange = function() {
                if(this.readyState == 4) {
                    if(this.status == 200) {
                        resolve(xhttp.response);
                    }
                    else {
                        reject({
                            status: this.status,
                            message: xhttp.response,
                        });
                    }
                }
            }
            
            var urlWithParam = url;
            if(params != null && params != undefined && typeof params == 'object') {
                urlWithParam += '?' + Object.keys(params).map(function(key) {
                    var value = params[key];
                    return key + '=' + value;
                }).join('&');
            }
            
            xhttp.open('GET', urlWithParam, true);
            xhttp.send();
        });
    }
    
    this.getXml = function(url, params) {
        return new Promise(function(resolve, reject) {
            var xhttp = getXmlHttpObject();
            xhttp.onreadystatechange = function() {
                if(this.readyState == 4) {
                    if(this.status == 200) {
                        resolve(xhttp.responseXML);
                    }
                    else {
                        reject({
                            status: this.status,
                            message: xhttp.response,
                        });
                    }
                }
            }
            
            var urlWithParam = url;
            if(params != null && params != undefined && typeof params == 'object') {
                urlWithParam += '?' + Object.keys(params).map(function(key) {
                    var value = params[key];
                    return key + '=' + value;
                }).join('&');
            }
            
            xhttp.open('GET', urlWithParam, true);
            xhttp.send();
        });
    }
    
    this.post = function(url, params) {
        return new Promise(function(resolve, reject) {
            var xhttp = getXmlHttpObject();
            xhttp.onreadystatechange = function() {
                if(this.readyState == 4) {
                    if(this.status == 200) {
                        resolve(xhttp.response);
                    }
                    else {
                        reject({
                            status: this.status,
                            message: xhttp.response,
                        });
                    }
                }
            }
            
            var paramsString = '';
            if(params != null && params != undefined && typeof params == 'object') {
                paramsString = Object.keys(params).map(function(key) {
                    var value = params[key];
                    return key + '=' + value;
                }).join('&');
            }
            
            xhttp.open('POST', url, true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send(paramsString);
        });
    }
    
    this.put = function(url, paramString) {
        return new Promise(function(resolve, reject) {
            var xhttp = getXmlHttpObject();
            xhttp.onreadystatechange = function() {
                if(this.readyState == 4) {
                    if(this.status == 200) {
                        resolve(xhttp.response);
                    }
                    else {
                        reject({
                            status: this.status,
                            message: xhttp.response,
                        });
                    }
                }
            }
            
//            var paramsString = '';
//            if(params != null && params != undefined && typeof params == 'object') {
//                paramsString = Object.keys(params).map(function(key) {
//                    var value = params[key];
//                    return key + '=' + value;
//                }).join('&');
//            }
            
            xhttp.open('PUT', url, true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send(paramString);
        });
    }
    
    this.delete = function(url, params) {
        return new Promise(function(resolve, reject) {
            var xhttp = getXmlHttpObject();
            xhttp.onreadystatechange = function() {
                if(this.readyState == 4) {
                    if(this.status == 200) {
                        resolve(xhttp.response);
                    }
                    else {
                        reject({
                            status: this.status,
                            message: xhttp.response,
                        });
                    }
                }
            }
            
            var urlWithParam = url;
            if(params != null && params != undefined && typeof params == 'object') {
                urlWithParam += '?' + Object.keys(params).map(function(key) {
                    var value = params[key];
                    return key + '=' + value;
                }).join('&');
            }
            
            xhttp.open('DELETE', urlWithParam, true);
            xhttp.send();
        });
    }
}