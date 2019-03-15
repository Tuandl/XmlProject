/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AjaxService');
require('XmlService');
require('NavService');

var ProductDetailController = function() {
    var ajaxService = new AjaxService();
    var xmlService = new XmlService();
    var navService = new NavService();
    
    var viewIds = {
        button: {
            addToCart: 'btnAddCart',
        },
        div: {
            productDetail: 'divProduct',
            navbar: 'navbar',
        }
    };
    
    var productId = app.getParameter('id');
    
    var product = null;
    var productXml = null;
    var productXsl = null;
    
    navService.renderNavBarCategories(viewIds.div.navbar);
    getProduct();
    
    function getProduct() {
        var params = {
            productId: productId,
        };
        var paramsXml = xmlService.marshalling(params, 'param');
        var paramsStr = xmlService.parseXmlToString(paramsXml);
        var data = {
            param: paramsStr,
        };
        ajaxService.getXml(app.url.api.product, data).then(function(response) {
            productXml = response;
            product = xmlService.unmarshalling(response);
            convertInvalidEntities();
            getProductXsl();
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function convertInvalidEntities() {
        product.product.description = product.product.description
    }
    
    function getProductXsl() {
        ajaxService.get(app.url.xsl.productDetail).then(function(response) {
            productXsl = response;
            renderProductDetail();
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function renderProductDetail() {
        productXml = xmlService.marshallingAuto(product);
        var productHtml = xmlService.transformToDocument(productXml, productXsl);
        var div = document.getElementById(viewIds.div.productDetail);
        xmlService.removeAllChild(div);
        div.appendChild(productHtml);
    }
    
}