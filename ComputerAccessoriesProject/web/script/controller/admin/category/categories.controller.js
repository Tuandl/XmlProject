/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AjaxService');
require('XmlService');
require('AuthService');
require('NavService')

var CategoriesController = function() {
    var ajaxService = new AjaxService();
    var xmlService = new XmlService();
    var authService = new AuthService();
    var navService = new NavService();
    
    authService.checkAdmin();
    
    //Declaration
    var viewIds = {
        input: {
        },
        div: {
            table: 'listCategories', 
            topBar: 'divTopBar',
            navBar: 'divNavbar',
        },
        button: {
            insert: 'btnInsert',
            back: 'btnBack',
        },
        error: {
            
        }
    };
    
    var viewNames = {
        button: {
            delete: 'btnDelete',
            edit: 'btnEdit',
        }
    };
    
    //run
    authService.renderTopBarAuthorize(viewIds.div.topBar);
    navService.renderNavBarAdmin(viewIds.div.navBar);
    
    var categoriesDom = null;
    var xsl = null;
    initData();
    
    var btnBack = document.getElementById(viewIds.button.back);
    btnBack.addEventListener('click', onBtnBackClicked);
    
    //handling methods
    function initData() {
        //get categories -> get xsl -> display
        ajaxService.getXml(app.url.api.category).then(function(response) {
            categoriesDom = response;
            ajaxService.get(app.url.xsl.categoryTable).then(function(response) {
                xsl = response;
                
                var table = xmlService.transformToDocument(categoriesDom, xsl);
                var div = document.getElementById(viewIds.div.table);
                
                xmlService.removeAllChild(div);
                div.appendChild(table);
                
                addEventListeners();
            }).catch(function(error) {
                console.log(error);
            })
        }).catch(function(error) {
            console.log(error);
        })
    }
    
    function addEventListeners() {
        var deleteBtns = document.getElementsByName(viewNames.button.delete);
        deleteBtns.forEach(function(btnDelete) {
           btnDelete.addEventListener('click', onBtnDeleteClicked); 
        });
        
        var editBtns = document.getElementsByName(viewNames.button.edit);
        editBtns.forEach(function(btnEdit) {
            btnEdit.addEventListener('click', onBtnEditClicked);
        })
        
        var insertBtn = document.getElementById(viewIds.button.insert);
        insertBtn.addEventListener('click', onBtnInsertClicked);
    }
    
    function onBtnDeleteClicked(event) {
        var btn = event.srcElement;
        var id = app.getIdInDataId(btn);
        
        var data = {
            id: id,
        };
        
        ajaxService.delete(app.url.api.category, data).then(function(resposne) {
            initData();
        }).catch(function(error) {
            console.log(error);
        })
    }
    
    function onBtnEditClicked(event) {
        var id = app.getIdInDataId(event.srcElement);
        var param = {
            id: id,
        };
        var url = app.makeUrlWithParam(app.url.page.categoryUpdate, param);
        window.location.href = url;
    }
    
    function onBtnInsertClicked() {
        window.location.href = app.url.page.categoryInsert;
    }
    
    function onBtnBackClicked() {
        window.location.href = app.url.page.adminDashBoard;
    }
}