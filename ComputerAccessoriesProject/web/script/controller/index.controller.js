/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('XmlService');
require('AjaxService');
require('StateService');
require('NavService');

var IndexController = function() {
    //inject param
    var xmlService = new XmlService();
    var ajaxService = new AjaxService();
    var stateService = new StateService();
    var navService = new NavService();
    
    //declaration
    var viewIds = {
        div: {
            logedIn: 'divLogedIn',
            notLogedIn: 'divAnnonymous',
            userFullName: 'userFullName',
            navBar: 'divNavbar',
            topProducts: 'divTopProducts',
            topCategories: 'divTopCategories',
        },
        button: {
            logout: 'btnLogout',
            login: 'btnLogin',
            register: 'btnRegister',
        },
    };
    
    var topProducts = null;
    var topProductsXml = null;
    var topCategories = null;
    var topCategoriesXml = null;
    var navBarXsl = null;
    var topProductsXsl = null;
    var topCategoriesXsl = null;
    
    navService.renderNavBarCategories(viewIds.div.navBar);
    getTopProduct();
    getTopCategories();
    getTopCategoriesXsl();


    //Handler method
    function getTopProduct() {
        var params = {
            getTopProduct: 'true',
        };
        var paramsXml = xmlService.marshalling(params, 'param');
        var paramsStr = xmlService.parseXmlToString(paramsXml);
        var data = {
            param: paramsStr,
        };
        ajaxService.getXml(app.url.api.product, data).then(function(response) {
            topProductsXml = response;
            topProducts = xmlService.unmarshalling(topProductsXml);
            getTopProductsXsl();
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function getTopCategories() {
        ajaxService.getXml(app.url.api.categoryTop).then(function(response) {
            topCategoriesXml = response;
            topCategories = xmlService.unmarshalling(response);
            topCategories.categories.category.forEach(function(category) {
                getTopProductInCategory(category);
            });
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function getTopProductInCategory(category) {
        var params = {
            getTopProduct: 'true',
            categoryId: category.id,
        };
        var paramsXml = xmlService.marshalling(params, 'param');
        var paramsStr = xmlService.parseXmlToString(paramsXml);
        var data = {
            param: paramsStr,
        };
        ajaxService.getXml(app.url.api.product, data).then(function(response) {
//            console.log('response top product in category', 
//                    xmlService.parseXmlToString(response))
//            
            var obj = xmlService.unmarshalling(response);
            category.product = obj.products.product;
            renderTopCategories();
        }).catch(function(error) {
            console.log(error);
        });
    } 
    
    function getTopProductsXsl() {
        ajaxService.get(app.url.xsl.productSquare).then(function(response) {
            topProductsXsl = response;
            renderTopProduct();
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function getTopCategoriesXsl() {
        ajaxService.get(app.url.xsl.categoryTop).then(function(response) {
            topCategoriesXsl = response;
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function renderTopProduct() {
        var topProductHtml = xmlService.transformToDocument(topProductsXml, topProductsXsl);
        var div = document.getElementById(viewIds.div.topProducts);
        xmlService.removeAllChild(div);
        div.appendChild(topProductHtml);
    }
    
    function renderTopCategories() {
        topCategoriesXml = xmlService.marshallingAuto(topCategories);
        
        var topCategoriesHtml = xmlService.transformToDocument(topCategoriesXml, topCategoriesXsl);
        var div = document.getElementById(viewIds.div.topCategories);
        xmlService.removeAllChild(div);
        div.appendChild(topCategoriesHtml);
    }
}