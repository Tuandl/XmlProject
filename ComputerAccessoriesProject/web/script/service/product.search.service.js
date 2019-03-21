/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('XmlService');
require('AjaxService');
require('StateService');
require('ProductStateService');
require('HtmlService');

var ProductSearchService = function () {
    var xmlService = new XmlService();
    var ajaxService = new AjaxService();
    var stateService = new StateService();
    var productStateService = new ProductStateService();
    var htmlService = new HtmlService();
    
    var searchType = {
        offline: 1,
        online: 2,
        smart: 3,
    };
    
    var viewIds = {
        text: {
            search: 'txtSearchValue_searchService',
        },
        button: {
            search: 'btnSearch_searchService',
        },
    };
    
    function searchProductOnline(searchValue, page, pageSize) {
        return new Promise(function(resolve, reject) {
            var params = {
                search: searchValue,
                page: page,
                pageSize: pageSize,
            };
            var paramsXml = xmlService.marshalling(params, 'param');
            var paramsStr = xmlService.parseXmlToString(paramsXml);
            var data = {
                param: paramsStr,
            };
            ajaxService.get(app.url.api.product, data).then(function(response) {
                var obj = xmlService.parseToXmlThenUnmarshalling(response);
                var products = {
                    products: {
                        product: obj.datatable.data,
                    },
                };
                productStateService.addProducts(products);
                resolve(response);
            }).catch(function(error) {
                console.log(error);
                reject(error);
            });
        });
    }
    
    function searchProductOnlineSmart(searchValue, page, pageSize) {
        return new Promise(function(resolve, reject) {
            var params = {
                search: searchValue,
                page: page,
                pageSize: pageSize,
                isSmartSearch: 'true',
            };
            var paramsXml = xmlService.marshalling(params, 'param');
            var paramsStr = xmlService.parseXmlToString(paramsXml);
            var data = {
                param: paramsStr,
            };
            ajaxService.get(app.url.api.product, data).then(function(response) {
                var obj = xmlService.parseToXmlThenUnmarshalling(response);
                var products = {
                    products: {
                        product: obj.datatable.data,
                    },
                };
                productStateService.addProducts(products);
                resolve(response);
            }).catch(function(error) {
                console.log(error);
                reject(error);
            });
        });
    }
    
    function searchProductOffline(searchValue, page, pageSize) {
        return new Promise(function(resolve, reject) {
            var data = productStateService.getProducts();
            if(data == null) {
                reject();
            }
            
            var filtered = data.products.product.filter(function(product) {
                var decoded = htmlService.decodeHtml(product.name);
                decoded = htmlService.decodeHtml(decoded);
                decoded = decoded.toLowerCase().toLocaleLowerCase();
                return searchValue == null || decoded.includes(searchValue.toLowerCase().toLocaleLowerCase());
            });
            
            var dataTable = {
                datatable: {
                    total: filtered.length,
                    data: filtered.splice((page - 1) * pageSize, pageSize),
                },
            };
            
            var str = xmlService.marshallingThenParseToString(dataTable);
            resolve(str);
        });
        
    }
    
    function getSearchTopBarXsl() {
        return new Promise(function(resolve, reject) {
            var xsl = stateService.getItem(stateService.stateConst.searchTopBarXsl);
            
            if(!xsl) {
                ajaxService.get(app.url.xsl.searchTopbar).then(function(response) {
                    stateService.setItem(stateService.stateConst.searchTopBarXsl, response);
                    resolve(response);
                });
            } else {
                resolve(xsl);
            }
        });
    }
    
    function renderSearchTopBar(divId) {
        var div = document.getElementById(divId);
        if(!div) {
            return;
        }
        
        getSearchTopBarXsl().then(function(xsl) {
            var tmpDom = xmlService.marshallingAuto({
                test:{}
            });
            var html = xmlService.transformToDocument(tmpDom, xsl);
            
            xmlService.removeAllChild(div);
            div.appendChild(html);
            
            bindSearchButtonFunction();
        });
    }
    
    function bindSearchButtonFunction() {
        var btn = document.getElementById(viewIds.button.search);
        btn.addEventListener('click', onBtnSearchClicked);
    }
    
    function onBtnSearchClicked() {
        var input = document.getElementById(viewIds.text.search);
        var searchValue = input.value;
        
        window.location.href = app.url.page.search + "?searchValue=" + searchValue;
    }
    
    /**
     * Auto matching search method
     * @param {type} searchValue
     * @param {type} page
     * @param {type} pageSize
     * @param {type} type
     * @returns {Promise}
     */
    function searchProduct(searchValue, page, pageSize, type) {
        return new Promise(function(resolve, reject){
            switch(parseInt(type)) {
                case searchType.offline: 
                    resolve(searchProductOffline(searchValue, page, pageSize));
                    break;
                case searchType.online:
                    resolve(searchProductOnline(searchValue, page, pageSize));
                    break;
                case searchType.smart:
                    resolve(searchProductOnlineSmart(searchValue, page, pageSize));
                    break;
                default: reject();
                    break;
            }    
            
        });
        
    }
    
    this.searchProduct = searchProduct;
    this.searchType = searchType;
    this.renderSearchTopBar = renderSearchTopBar;
}