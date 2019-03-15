/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AjaxService');
require('XmlService');

var CategoryEditController = function() {
    var ajaxService = new AjaxService();
    var xmlService = new XmlService();
    
    //declaration
    var viewIds = {
        text: {
            name: 'txtName',
        },
        button: {
            update: 'btnUpdate',
            back: 'btnBack',
        },
        error: {
            nameRequired: 'error-name-required',
            server: 'error-server',
        }
    };
    var categoryId = parseInt(app.getRequestParam('id'));
    if(!categoryId) {
        //equal 0 or null or undefined
        window.location.href = app.url.page.categoryList;
        return;
    }
    
    //running flow
    getData();
    var btnUpdate = document.getElementById(viewIds.button.update);
    btnUpdate.addEventListener('click', onBtnUpdateClicked);
    
    var btnBack = document.getElementById(viewIds.button.back);
    btnBack.addEventListener('click', onBtnBackClicked);
    
    //handler methods
    function onBtnUpdateClicked() {
        app.resetForm(viewIds.error);
        
        var txtName = document.getElementById(viewIds.text.name);
        if(txtName.value.length == 0) {
            app.removeClass(viewIds.error.nameRequired, 'hidden');
            return;
        }
        
        var category = {
            id: categoryId,
            name: txtName.value,
        };
        
        //marshall with root tag = 'category'
        var categoryDom = xmlService.marshalling(category, 'category');
        var categoryDomStr = xmlService.parseXmlToString(categoryDom);
        
        var data = categoryDomStr;
        
        ajaxService.put(app.url.api.category, data).then(function(response) {
            redirectAfterUpdated()
        }).catch(function(error) {
            console.log(error);
            app.removeClass(viewIds.error.server, 'hidden');
        })
    }
    
    function redirectAfterUpdated() {
        window.location.href = app.url.page.categoryList;
    }
    
    function getData() {
        var data = {
            id: categoryId,
        };
        ajaxService.getXml(app.url.api.category, data).then(function(response) {
            var responseObj = xmlService.unmarshalling(response);
            
            var txtName = document.getElementById(viewIds.text.name);
            txtName.value = responseObj.category.name;
        }).catch(function(error) {
           console.log(error); 
        });
    }
    
    function onBtnBackClicked() {
        window.location.href = app.url.page.categoryList;
    }
}