/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AjaxService');
require('StateService');
require('XmlService');

/**
 * Render NavBar
 * @returns {NavService}
 */
var NavService = function() {
    var ajaxService = new AjaxService();
    var stateService = new StateService();
    var xmlService = new XmlService();
    
    function getCategoriesXml() {
        return new Promise(function(resolve, reject) {
            var categoriesXml = stateService.getItem(stateService.stateConst.categoriesXml);
        
            if(categoriesXml == null) {
                ajaxService.get(app.url.api.category).then(function(response) {
                    stateService.setItem(stateService.stateConst.categoriesXml, response);
                    resolve(response);
                }).catch(function(err){
                    console.log(err);
                });
            } else {
                resolve(categoriesXml);
            }
        });
    }
    
    function getNavBarCategoriesXsl() {
        return new Promise(function(resolve, reject) {
            var xsl = stateService.getItem(stateService.stateConst.navBarCategoriesXsl);
            
            if(xsl == null) {
                ajaxService.get(app.url.xsl.categoryNavBar).then(function(response) {
                    stateService.setItem(stateService.stateConst.navBarCategoriesXsl, response);
                    resolve(response);
                }).catch(function(error) {
                    console.log(error);
                });
            } else {
                resolve(xsl);
            }
        });
    }
    
    /**
     * render navbar and append into element has id equal with id param
     * @param {type} id
     * @returns {undefined}
     */
    function renderNavBarCategories(id) {
        var div = document.getElementById(id);
        if(div == null || div == undefined) return;
        
        getCategoriesXml().then(function(categoryXmlStr) {
            getNavBarCategoriesXsl().then(function(navBarXsl) {
                var categoryXml = xmlService.parseStringToXml(categoryXmlStr);
                var navBarHtml = xmlService.transformToDocument(categoryXml, navBarXsl);
                
                xmlService.removeAllChild(div);
                div.appendChild(navBarHtml);
            });
        })
    }
    
    this.renderNavBarCategories = renderNavBarCategories;
}