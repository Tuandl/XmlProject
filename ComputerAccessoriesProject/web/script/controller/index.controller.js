/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var IndexController = function(app, xmlService, ajaxService, stateService) {
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
    
    var categories = null;
    var categoriesXml = null
    var topProducts = null;
    var topProductsXml = null;
    var topCategories = null;
    var topCategoriesXml = null;
    var navBarXsl = null;
    
    getCategories();
    getTopProduct();
    getTopCategories();
    
    //Handler method
    function getCategories() {
        ajaxService.getXml(app.url.api.category).then(function(response) {
            categoriesXml = response;
            categories = xmlService.unmarshalling(response);
            getNavBarXsl();
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function getTopProduct() {
        
    }
    
    function getTopCategories() {
        
    }
    
    function getNavBarXsl() {
        ajaxService.get(app.url.xsl.categoryNavBar).then(function(response) {
            navBarXsl = response;
            renderNavBar();
        }).catch(function(error) {
            console.log(error);
        })
    }
    
    function renderNavBar() {
        var navBar = xmlService.transformToDocument(categoriesXml, navBarXsl);
        var div = document.getElementById(viewIds.div.navBar);
        xmlService.removeAllChild(div);
        div.appendChild(navBar);
    }
}