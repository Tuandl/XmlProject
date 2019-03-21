/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('NavService');
require('ProductService');
require('CategoryService');
require('AuthService');
require('ProductSearchService');

var IndexController = function() {
    //inject param
    var navService = new NavService();
    var productService = new ProductService();
    var categoryService = new CategoryService();
    var authService = new AuthService();
    var productSearchService = new ProductSearchService();
    
    //declaration
    var viewIds = {
        div: {
            topBar: 'divTopBar',
            userFullName: 'userFullName',
            navBar: 'divNavbar',
            topProducts: 'divTopProducts',
            topCategories: 'divTopCategories',
            search: 'divSearch',
        },
        button: {
            logout: 'btnLogout',
            login: 'btnLogin',
            register: 'btnRegister',
        },
    };
    
    authService.renderTopBarAuthorize(viewIds.div.topBar);
    navService.renderNavBarCategories(viewIds.div.navBar);
    productService.renderTopProducts(viewIds.div.topProducts);
    categoryService.renderTopCategories(viewIds.div.topCategories);
    productSearchService.renderSearchTopBar(viewIds.div.search);
}