/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
require('AjaxService');
require('StateService');
require('XmlService');
require('PagingService');
require('HtmlService');
require('CartService');
require('ProductStateService');
require('ProductSearchService');

/**
 * Provide service for process product
 * @returns {ProductService}
 */
var ProductService = function() {
    var ajaxService = new AjaxService();
    var stateService = new StateService();
    var xmlService = new XmlService();
    var pagingService = new PagingService();
    var htmlService = new HtmlService();
    var cartService = new CartService();
    var productStateService = new ProductStateService();
    var productSearchService = new ProductSearchService();
    
    function getTopProductXml() {
        return new Promise(function(resolve, reject){
//            var topProductXml = stateService.getItem(stateService.stateConst.topProductXml);
//            if(topProductXml == null) {
                var params = {
                    getTopProduct: 'true',
                };
                var paramsXml = xmlService.marshalling(params, 'param');
                var paramsStr = xmlService.parseXmlToString(paramsXml);
                var data = {
                    param: paramsStr,
                };
                ajaxService.get(app.url.api.product, data).then(function(response) {
//                    stateService.setItem(stateService.stateConst.topProductXml, response);
                    productStateService.addProductsXmlStr(response);
                    resolve(response);
                }).catch(function(error){
                    console.log(error);
                    reject(error);
                });
//            } else {
//                resolve(topProductXml);
//            }
        });
    }
    
    function getProductXsl() {
        return new Promise(function(resolve, reject) {
            var topProductXsl = stateService.getItem(stateService.stateConst.productListXsl);
            if(topProductXsl == null) {
                ajaxService.get(app.url.xsl.productSquare).then(function(response){
                    stateService.setItem(stateService.stateConst.productListXsl, response);
                    resolve(response);
                }).catch(function(error) {
                    console.log(error);
                    reject(error);
                });
            } else {
                resolve(topProductXsl);
            }
        });
    }
    
    /**
     * Render top products 
     * @param {type} id
     * @returns {undefined}
     */
    function renderTopProducts(id) {
        var div = document.getElementById(id);
        if(!div){
            return;
        }
        
        getTopProductXml().then(function(topProductsXmlStr){
            getProductXsl().then(function(xsl) {
                var topProductXml = xmlService.parseStringToXml(topProductsXmlStr);
                var html = xmlService.transformToDocument(topProductXml, xsl);
                
                xmlService.removeAllChild(div);
                div.appendChild(html);
            }).catch(function(error) {
                console.log(error);
            });
        }).catch(function(error){
            console.log(error);
        });
    }
    
    /**
     * get products and set into category Object
     * @param {type} category
     * @returns {Promise}
     */
    function getTopProductInCategory(category) {
        return new Promise(function(resolve, reject) {
            var params = {
                getTopProduct: 'true',
                categoryId: category.id,
            };
            var paramsXml = xmlService.marshalling(params, 'param');
            var paramsStr = xmlService.parseXmlToString(paramsXml);
            var data = {
                param: paramsStr,
            };
            ajaxService.get(app.url.api.product, data).then(function(response) {
                productStateService.addProductsXmlStr(response);
                var productsXml = xmlService.parseStringToXml(response);
                var obj = xmlService.unmarshalling(productsXml);
                category.product = obj.products.product;
                resolve();
            }).catch(function(error) {
                console.log(error);
                reject(error);
            });
        });
    }
    
    function getProductTableInCategory(categoryId, page, pageSize) {
        return new Promise(function(resolve, reject) {
            var params = {
                categoryId: categoryId,
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
                    }
                }
                productStateService.addProducts(products);
                resolve(response);
            }).catch(function(error) {
                console.log(error);
                reject(error);
            });
        });
    }
    
    /**
     * render product table and pagination area
     * @param {type} productDivId
     * @param {type} pagingDivId
     * @param {type} categoryId
     * @param {type} currentPage
     * @param {type} pageSize
     * @returns {undefined}
     */
    function renderProductInCategory(productDivId, pagingDivId, categoryId, currentPage, pageSize) {
        var divProduct = document.getElementById(productDivId);
        
        if(!divProduct) {
            return;
        }
        
        getProductTableInCategory(categoryId, currentPage, pageSize).then(function(productTableXmlStr){
            var productTableXml = xmlService.parseStringToXml(productTableXmlStr);
            var productTable = xmlService.unmarshalling(productTableXml);
            var products = {
                products: {
                    product: productTable.datatable.data,
                },
            };
            var total = parseInt(productTable.datatable.total);
            if(total == 0) {
                divProduct.innerHTML = '<h3 class="center">No Item</h3>';
                return;
            }
            var productsXml = xmlService.marshallingAuto(products);
            
            getProductXsl().then(function(productXsl) {
                var html = xmlService.transformToDocument(productsXml, productXsl);
                xmlService.removeAllChild(divProduct);
                divProduct.appendChild(html);
            });
            pagingService.renderCategoryPagination(pagingDivId, categoryId, currentPage, total, pageSize);
        });
    }
    
    function getProductDetailXmlStr(productId) {
        return new Promise(function(resolve, reject) {
            var params = {
                productId: productId,
            };
            var paramsXml = xmlService.marshalling(params, 'param');
            var paramsStr = xmlService.parseXmlToString(paramsXml);
            var data = {
                param: paramsStr,
            };
            ajaxService.get(app.url.api.product, data).then(function(response) {
                resolve(response);
            }).catch(function(error) {
                console.log(error);
                reject(error);
            });
        });
    }
    
    function getProductDetailObj(productId) {
        return new Promise(function(resolve, reject) {
            getProductDetailXmlStr(productId).then(function(productXmlStr){
                var productXml = xmlService.parseStringToXml(productXmlStr);
                var product = xmlService.unmarshalling(productXml);
                resolve(product.product);
            }).catch(function(error) {
                console.log(error);
                resolve(error);
            });
        });
    }
    
    function getProductDetailXsl() {
        return new Promise(function(resolve, reject) {
            var xsl = stateService.getItem(stateService.stateConst.productDetailXsl);
            
            if(xsl == null) {
                ajaxService.get(app.url.xsl.productDetail).then(function(response) {
                    stateService.setItem(stateService.stateConst.productDetailXsl, response);
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
    
    /**
     * Render Product detail 
     * @param {type} divId
     * @param {type} productId
     * @returns {undefined}
     */
    function renderProductDetail(divId, productId) {
        var div = document.getElementById(divId);
        if(!div) {
            return;
        }
        
        var btnAddToCartId = 'btnAddCart';
        
        getProductDetailXmlStr(productId).then(function(productXmlStr){
            getProductDetailXsl().then(function(xsl) {
                var productXml = xmlService.parseStringToXml(productXmlStr);
                //decode
                var product = xmlService.unmarshalling(productXml);
                if(product.product.description != null && Object.keys(product.product.description).length != 0) {
                    product.product.description = htmlService.decodeHtml(product.product.description);
                }
                productXml = xmlService.marshallingAuto(product);
                
                var html = xmlService.transformToDocument(productXml, xsl);
                
                xmlService.removeAllChild(div);
                div.appendChild(html);
                
                var btnAddToCart = document.getElementById(btnAddToCartId);
                btnAddToCart.addEventListener('click', onBtnAddToCartClicked);
            });
        });
    }
    
    function onBtnAddToCartClicked(event) {
        var btn = event.srcElement;
        var id = app.getIdInDataId(btn);
        getProductDetailObj(id).then(function(productDetail) {
            cartService.addToCart(productDetail);
            alert('Add to cart successful!');
        });
    }
    
    /**
     * 
     * @param {type} productDivId
     * @param {type} pagingDivId
     * @param {type} searchValue
     * @param {type} currentPage
     * @param {type} pageSize
     * @param {type} type
     * @returns {undefined}
     */
    function renderProductInSearch(productDivId, pagingDivId, searchValue, searchType, currentPage, pageSize) {
        var divProduct = document.getElementById(productDivId);
        
        if(!divProduct) {
            return;
        }
        
        productSearchService.searchProduct(searchValue, currentPage, pageSize, searchType).then(function(productTableXmlStr){
            var productTableXml = xmlService.parseStringToXml(productTableXmlStr);
            var productTable = xmlService.unmarshalling(productTableXml);
            var products = {
                products: {
                    product: productTable.datatable.data,
                },
            };
            var total = parseInt(productTable.datatable.total);
            if(total == 0) {
                divProduct.innerHTML = '<h3 class="center">Not found.</h3>';
                return;
            }
            var productsXml = xmlService.marshallingAuto(products);
            
            getProductXsl().then(function(productXsl) {
                var html = xmlService.transformToDocument(productsXml, productXsl);
                xmlService.removeAllChild(divProduct);
                divProduct.appendChild(html);
            });
            pagingService.renderSearchPagination(pagingDivId, searchValue, searchType, currentPage, total, pageSize);
        });
    }
    
    this.renderTopProducts = renderTopProducts;
    this.getTopProductInCategory = getTopProductInCategory;
    this.renderProductInCategory = renderProductInCategory;
    this.renderProductDetail = renderProductDetail;
    this.renderProductInSearch = renderProductInSearch;
}