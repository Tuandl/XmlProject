/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('ProductService');
require('NavService');
require('AuthService');
require('ProductSearchService');

var ProductDetailController = function() {
    var productService = new ProductService();
    var navService = new NavService();
    var authService = new AuthService();
    var productSearchService = new ProductSearchService();
    
    var viewIds = {
        button: {
            addToCart: 'btnAddCart',
        },
        div: {
            productDetail: 'divProduct',
            navbar: 'navbar',
            topBar: 'divTopBar',
            search: 'divSearch',
        },
    };
    
    var productId = app.getParameter('id');
    
    authService.renderTopBarAuthorize(viewIds.div.topBar);
    navService.renderNavBarCategories(viewIds.div.navbar);
    productService.renderProductDetail(viewIds.div.productDetail, productId);
    productSearchService.renderSearchTopBar(viewIds.div.search);
}