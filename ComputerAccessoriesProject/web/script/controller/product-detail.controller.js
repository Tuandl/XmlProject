/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('ProductService');
require('NavService');

var ProductDetailController = function() {
    var productService = new ProductService();
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
    productService.renderProductDetail(viewIds.div.productDetail, productId);
}