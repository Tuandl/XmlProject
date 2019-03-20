/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * XHttp Request Service
 * @returns {AjaxService}
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
    
    /**
     * Create Get XHttp Request
     * @param {type} url
     * @param {type} params
     * @returns {Promise}
     */
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
    
    /**
     * Create Get Xml XHttp Request
     * @param {type} url
     * @param {type} params
     * @returns {Promise}
     */
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
    
    /**
     * Create Get XHttp Request
     * @param {type} url
     * @param {type} params
     * @returns {Promise}
     */
    this.getPdf = function(url, params, fileName) {
        return new Promise(function(resolve, reject) {
            var xhttp = getXmlHttpObject();
            xhttp.onreadystatechange = function(event) {
                if(this.readyState == 4) {
                    if(this.status == 200) {
                        var response = event.target.response;
                        var file = new Blob([response], {type: 'application/pdf'});
                        var fileUrl = window.URL.createObjectURL(file);
                        var win = window.open(fileUrl, '_blank');
                        if(!win) {
                            //browser does not support open new tab
                            var link = document.createElement('a');
                            link.href = fileUrl;
                            if(fileName) {
                                link.download = fileName;
                            } else {
                                link.download = 'Download';
                            }
                            link.click();
                        }
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
            
            xhttp.responseType = 'blob';
            xhttp.open('GET', urlWithParam, true);
            xhttp.send();
        });
    }
    
    /**
     * Create Post XHttp Request
     * @param {type} url
     * @param {type} params
     * @returns {Promise}
     */
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
            console.log(paramsString);  
            xhttp.send(paramsString);
        });
    }
    
    /**
     * Create PUT XHttp request
     * @param {type} url
     * @param {type} paramString
     * @returns {Promise}
     */
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
            
            xhttp.open('PUT', url, true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send(paramString);
        });
    }
    
    /**
     * Create Delete XHttp Request
     * @param {type} url
     * @param {type} params
     * @returns {Promise}
     */
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