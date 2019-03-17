/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('CartService');
require('NavService');
require('AuthService');

var CartController = function() {
    var viewIds = {
        div: {
            topBar: 'divTopBar',
            navBar: 'divNavbar',
            divCartTable: 'divCartTable',
        },
        button: {
            order: 'btnOrder',
        },
        text: {
            phoneNo: 'txtPhoneNo',
            address: 'txtAddress',
        },
        error: {
            requiredPhoneNo: 'error-require-phoneno',
            requiredAddress: 'error-require-address',
        },
    };
    
    var cartService = new CartService(viewIds.div.divCartTable);
    var navService = new NavService();
    var authService = new AuthService();
    
    authService.checkAuthorized();
    authService.renderTopBarAuthorize(viewIds.div.topBar);
    navService.renderNavBarCategories(viewIds.div.navBar);
    cartService.renderCartTable(viewIds.div.divCartTable);
    
    var btnOrder = document.getElementById(viewIds.button.order);
    btnOrder.addEventListener('click', onBtnOrderClicked);
    
    function onBtnOrderClicked() {
        app.resetForm(viewIds.error);
        
        var txtPhoneNo = document.getElementById(viewIds.text.phoneNo);
        var txtAddress = document.getElementById(viewIds.text.address);
        
        var phoneNo = txtPhoneNo.value;
        var address = txtAddress.value;
        if(phoneNo == null || phoneNo.length == 0) {
            app.showElement(viewIds.error.requiredPhoneNo);
            return;
        }
        if(address == null || address.length == 0) {
            app.showElement(viewIds.error.requiredAddress);
            return;
        }
        
        cartService.sendOrder(phoneNo, address);
    }
}