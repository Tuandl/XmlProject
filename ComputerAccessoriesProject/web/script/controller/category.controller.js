/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


require('NavService');
require('CategoryService');
require('ProductService');
require('AuthService');

var CategoryController = function() {
    var navService = new NavService();
    var categoryService = new CategoryService();
    var productService = new ProductService();
    var authService = new AuthService();
    
    var viewIds = {
        div: {
            navbar: 'navbar',
            categoryName: 'categoryName',
            products: 'divProducts',
            paging: 'pagination',
            topBar: 'divTopBar',
        },
    };
    
    var pageSize = 50
    var categoryId = app.getParameter('id');
    var page = app.getParameter('page') || 1;
    
    //Running
    authService.renderTopBarAuthorize(viewIds.div.topBar);
    navService.renderNavBarCategories(viewIds.div.navbar);
    categoryService.renderCategoryName(viewIds.div.categoryName, categoryId);
    productService.renderProductInCategory(viewIds.div.products, 
            viewIds.div.paging, categoryId, page, pageSize);
    
}