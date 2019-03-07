/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var CategoriesController = function(app, ajaxService, xmlService) {
    //Declaration
    var viewIds = {
        input: {
        },
        div: {
           table: 'listCategories', 
        },
        button: {
            insert: 'btnInsert',
        },
        error: {
            
        }
    };
    
    var viewNames = {
        button: {
            delete: 'btnDelete',
            edit: 'btnEdit',
        }
    }
    
    //run
    var categoriesDom = null;
    var xsl = null;
    initData();
    
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
}