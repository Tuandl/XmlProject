/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('NavService');
require('ProductService');
require('CategoryService');

var IndexController = function() {
    //inject param
    var navService = new NavService();
    var productService = new ProductService();
    var categoryService = new CategoryService();
    
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
    
    navService.renderNavBarCategories(viewIds.div.navBar);
    productService.renderTopProducts(viewIds.div.topProducts);
    categoryService.renderTopCategories(viewIds.div.topCategories);
}