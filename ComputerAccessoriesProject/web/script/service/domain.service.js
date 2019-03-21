/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AjaxService');
require('XmlService');
require('StateService');

/**
 * Service use for 
 * @returns {DomainService}
 */
var DomainService = function(domainId) {
    var ajaxService = new AjaxService();
    var stateService = new StateService();
    var xmlService = new XmlService();
    var domainId = domainId;
    var domainData = null;
    
    function getAllDomainsXMl() {
        return new Promise(function(resolve, reject) {
            ajaxService.get(app.url.api.domain).then(function(response) {
                var xml = xmlService.parseStringToXml(response);
                resolve(xml);
            }).catch(function(error) {
                console.log(error);
                reject(error);
            });
        });
    }
    
    function getDomainDetail() {
        return new Promise(function(resolve, reject){
            if(domainData != null) {
                resolve(domainData);
            }

            if(domainId == null || domainId == undefined) {
                reject();
            }
            var data = {
                id: domainId,
            };
            ajaxService.get(app.url.api.domain, data).then(function(response) {
                var obj = xmlService.parseToXmlThenUnmarshalling(response);
                domainData = obj;
                resolve(obj);
            }).catch(function(error) {
                console.log(error);
                reject(error);
            });
        });
    }
    
    function deleteDomain(domainId) {
        return new Promise(function(resolve, reject) {
            if(domainId == null || domainId == undefined) {
                reject();
            }
            
            var data = {
                id: domainId,
            };
            ajaxService.delete(app.url.api.domain, data).then(function(response) {
                console.log('delete success')
                resolve(response);
            }).catch(function(error) {
                console.log(error);
                reject(error);
            })
        });
    }
    
    function insertDomain() {
        return new Promise(function(resolve, reject) {
            if(!domainData) {
                reject('not found domainData');
            }
            
            var data = {
                data: xmlService.marshallingThenParseToString(domainData),
            };
            ajaxService.post(app.url.api.domain, data).then(function(response) {
                resolve(response);
                console.log('update success');
            }).catch(function(error) {
                console.log(error);
                reject(error);
            })
        });
    }
    
    function updateDomain() {
        return new Promise(function(resolve, reject) {
            if(!domainData) {
                reject('not found domainData');
            }
            
            var dataStr = xmlService.marshallingThenParseToString(domainData);
            ajaxService.put(app.url.api.domain, dataStr).then(function(response) {
                resolve(response);
                console.log('update success');
            }).catch(function(error) {
                console.log(error);
                reject(error);
            })
        });
    }
    
    function getDomainTableXsl() {
        return new Promise(function(resolve, reject){
            var xsl = stateService.getItem(stateService.stateConst.domainTable);
            
            if(!xsl) {
                ajaxService.get(app.url.xsl.domainTable).then(function(response) {
                    stateService.setItem(stateService.stateConst.domainTable, response);
                    resolve(response);
                }).catch(function(error) {
                    console.log(error);
                    reject(error);
                });
            } else {
                resolve(xsl);
            }
        });
    }
    
    function getDataMappingFormXsl() {
        return new Promise(function(resolve, reject){
            var xsl = stateService.getItem(stateService.stateConst.dataMappingForm);
            
            if(!xsl) {
                ajaxService.get(app.url.xsl.dataMappingTableForm).then(function(response) {
                    stateService.setItem(stateService.stateConst.dataMappingForm, response);
                    resolve(response);
                }).catch(function(error) {
                    console.log(error);
                    reject(error);
                });
            } else {
                resolve(xsl);
            }
        });
    }
    
    function renderDomainTable(divId) {
        return new Promise(function(resolve, reject) {
            var div = document.getElementById(divId);
            if(!div) {
                reject();
            }

            getAllDomainsXMl().then(function(domainXml) {
                getDomainTableXsl().then(function(xsl) {
                    var html = xmlService.transformToDocument(domainXml, xsl);

                    xmlService.removeAllChild(div);
                    div.appendChild(html);
                    
                    resolve();
                });
            });
        });
        
    }
    
    function renderDataMappingForm(divId) {
        return new Promise(function(resolve, reject) {
            var div = document.getElementById(divId);
            if(!div) {
                reject();
            }

            getDomainDetail().then(function(domainObj) {
                getDataMappingFormXsl().then(function(xsl) {
                    var xml = xmlService.marshallingAuto(domainObj);
                    var html = xmlService.transformToDocument(xml, xsl);

                    xmlService.removeAllChild(div);
                    div.appendChild(html);
                    resolve();
                });
            });
        });
        
    }
    
    this.getDomainDetail = getDomainDetail;
    this.deleteDomain = deleteDomain;
    this.updateDomain = updateDomain;
    this.insertDomain = insertDomain;
    this.renderDomainTable = renderDomainTable;
    this.renderDataMappingForm = renderDataMappingForm;
}