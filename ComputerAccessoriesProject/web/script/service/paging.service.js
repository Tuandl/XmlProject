/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AjaxService');
require('StateService');
require('XmlService');

/**
 * render Pagination service
 * @returns {PagingService}
 */
var PagingService = function() {
    var ajaxService = new AjaxService();
    var stateService = new StateService();
    var xmlService = new XmlService();
    
    function getPagingXsl() {
        return new Promise(function(resolve, reject) {
            var pagingXsl = stateService.getItem(stateService.stateConst.pagingXsl);
            
            if(pagingXsl == null) {
                ajaxService.get(app.url.xsl.paging).then(function(response) {
                    stateService.setItem(stateService.stateConst.pagingXsl, response);
                    resolve(response);
                }).catch(function(error) {
                    console.log(error);
                    reject(error);
                })
            } else {
                resolve(pagingXsl);
            }
        });
    }
    
    function createPagingObject(rootUrl, currentPage, total, pageSize){
        var pagingArray = [];
        
        if(rootUrl.indexOf('?') > 0) {
            rootUrl = rootUrl + '&page=';
        } else {
            rootUrl = rootUrl + '?page=';
        }
        
//        var rootUrl = app.url.page.category + '?id=' + categoryId + '&page=';
        
        if(currentPage > 1) {
            pagingArray.push({
                name: '&laquo;',
                url: rootUrl + (parseInt(currentPage) - 1),
            });
        }
        
        var totalPage = Math.floor(total / pageSize) + 1;
        for(var index = parseInt(currentPage) - 2; index <= parseInt(currentPage) + 2; index++) {
            if(index > 0 && index <= totalPage) {
                var pageObj = {
                    name: index,
                    url: rootUrl + index,
                };
                if(index == currentPage) {
                    pageObj.currentPage = 'true';
                }
                pagingArray.push(pageObj);
            }
        }
        
        if(parseInt(currentPage) < totalPage) {
            pagingArray.push({
                name: '&raquo;',
                url: rootUrl + (parseInt(currentPage) + 1)
            });
        }
        
        var result = {
            page: pagingArray,
        };
        
        return result;
    }
    
    function renderPagination(id, rootUrl, currentPage, total, pageSize) {
        var div = document.getElementById(id);
        if(!div) {
            return;
        }
        
        getPagingXsl().then(function(pagingXsl){
            var pagingObj = createPagingObject(rootUrl, currentPage, total, pageSize);
            var pagingXml = xmlService.marshalling(pagingObj, 'root');
            var pagingHtml = xmlService.transformToDocument(pagingXml, pagingXsl);
            
            xmlService.removeAllChild(div);
            div.appendChild(pagingHtml);
        });
    }
    
    function renderCategoryPagination(divId, categoryId, currentPage, total, pageSize) {
        var rootUrl = app.url.page.category + '?id=' + categoryId;
        renderPagination(divId, rootUrl, currentPage, total, pageSize);
    }
    
    this.renderCategoryPagination = renderCategoryPagination;
}