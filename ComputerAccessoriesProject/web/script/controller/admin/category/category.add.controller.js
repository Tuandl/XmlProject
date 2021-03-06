/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AjaxService');
require('XmlService');
require('AuthService');
require('NavService');

var CategoryAddController = function() {
    var ajaxService = new AjaxService();
    var xmlService = new XmlService();
    var authService = new AuthService();
    var navService = new NavService();
    
    authService.checkAdmin();
    
    //declaration
    var viewIds = {
        text: {
            name: 'txtName',
        },
        button: {
            insert: 'btnInsert',
            back: 'btnBack',
        },
        error: {
            nameRequired: 'error-name-required',
            server: 'error-server',
        },
        div: {
            topBar: 'divTopBar',
            navBar: 'divNavbar',
        },
    };
    
    //running flow
    authService.renderTopBarAuthorize(viewIds.div.topBar);
    navService.renderNavBarAdmin(viewIds.div.navBar);
    
    var btnInsert = document.getElementById(viewIds.button.insert);
    btnInsert.addEventListener('click', onBtnInsertClicked);
    
    var btnBack = document.getElementById(viewIds.button.back);
    btnBack.addEventListener('click', onBtnBackClicked);
    
    //handler methods
    function onBtnInsertClicked() {
        app.resetForm(viewIds.error);
        
        var txtName = document.getElementById(viewIds.text.name);
        if(txtName.value.length == 0) {
            app.removeClass(viewIds.error.nameRequired, 'hidden');
            return;
        }
        
        var category = {
            name: txtName.value,
        };
        
        //marshall with root tag = 'category'
        var categoryDom = xmlService.marshalling(category, 'category');
        var categoryDomStr = xmlService.parseXmlToString(categoryDom);
        
        var data = {
            data: categoryDomStr,
        }
        
        ajaxService.post(app.url.api.category, data).then(function(response) {
            redirectAfterInserted()
        }).catch(function(error) {
            console.log(error);
            app.removeClass(viewIds.error.server, 'hidden');
        })
    }
    
    function redirectAfterInserted() {
        window.location.href = app.url.page.categoryList;
    }
    
    function onBtnBackClicked() {
        window.location.href = app.url.page.categoryList;
    }
}