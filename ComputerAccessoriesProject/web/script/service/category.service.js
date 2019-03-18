/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AjaxService');
require('StateService');
require('XmlService');
require('ProductService');

/**
 * Category Service
 * @returns {CategoryService}
 */
var CategoryService = function() {
    var ajaxService = new AjaxService();
    var xmlService = new XmlService();
    var stateService = new StateService();
    var productService = new ProductService();
    
    function getTopCategories() {
        return new Promise(function(resolve, reject) {
            var topCategoriesXmlStr = stateService.getItem(stateService.stateConst.topCategoriesXml);
            if(topCategoriesXmlStr == null) {
                ajaxService.getXml(app.url.api.categoryTop).then(function(response) {
//                    stateService.setItem(stateService.stateConst.topCategoriesXml, response);
                    var topCategories = xmlService.unmarshalling(response);
                    
                    if(!Array.isArray(topCategories.categories.category)) {
                        var tmp = topCategories.categories.category;
                        topCategories.categories.category = [tmp,];
                    }
                    var counter = topCategories.categories.category.length;
                    topCategories.categories.category.forEach(function(category) {
                        productService.getTopProductInCategory(category).finally(function() {
                            counter--;
                            if(counter == 0) {
                                var topCategoriesXml = xmlService.marshallingAuto(topCategories);
                                topCategoriesXmlStr = xmlService.parseXmlToString(topCategoriesXml);
                                stateService.setItem(stateService.stateConst.topCategoriesXml, topCategoriesXmlStr);
                                
                                resolve(topCategoriesXmlStr);
                            }
                        });
                    });
                }).catch(function(error) {
                    reject(error);
                    console.log(error);
                });
            } else {
                resolve(topCategoriesXmlStr);
            }
        });
    }
    
    function getTopCategoriesXsl() {
        return new Promise(function(resolve, reject) {
            var topCategoriesXsl = stateService.getItem(stateService.stateConst.topCategoriesXsl);
            
            if(topCategoriesXsl == null) {
                ajaxService.get(app.url.xsl.categoryTop).then(function(response) {
                    stateService.setItem(stateService.stateConst.topCategoriesXsl, response);
                    resolve(response);
                }).catch(function(error) {
                    console.log(error);
                    reject(error);
                });  
            } else {
                resolve(topCategoriesXsl);
            }
        });
    }
    
    /**
     * render top categories, top product in categories
     * @param {type} id
     * @returns {undefined}
     */
    function renderTopCategories(id) {
        var div = document.getElementById(id);
        if(!div) {
            return;
        }
        
        getTopCategories().then(function(categoryXmlStr) {
            getTopCategoriesXsl().then(function(categoryXsl) {
                var categoryXml = xmlService.parseStringToXml(categoryXmlStr);
                var html = xmlService.transformToDocument(categoryXml, categoryXsl);
                
                xmlService.removeAllChild(div);
                div.appendChild(html);
            });
        });
    }
    
    function getCategoryName(categoryId) {
        return new Promise(function(resolve, reject) {
            var categoriesXmlStr = stateService.getItem(stateService.stateConst.categoriesXml);
            if(categoriesXmlStr == null) {
                var data = {
                    id: categoryId,
                };
                ajaxService.getXml(app.url.api.category, data).then(function(response) {
                    var category = xmlService.unmarshalling(response);
                    resolve(category.category.name);
                }).catch(function(error) {
                    console.log(error);
                    reject(error);
                })
            } else {
                var categoriesXml = xmlService.parseStringToXml(categoriesXmlStr);
                var categories = xmlService.unmarshalling(categoriesXml);
                var category = categories.categories.category.find(function(item) {
                    return item.id == categoryId;
                });
                resolve(category.name);
            }
        });
    }
    
    function renderCategoryName(divId, categoryId) {
        var div = document.getElementById(divId);
        if(!div) {
            return;
        }
        
        getCategoryName(categoryId).then(function(categoryName) {
            div.innerText = categoryName;
        });
    }
    
    this.renderTopCategories = renderTopCategories;
    this.renderCategoryName = renderCategoryName;
}