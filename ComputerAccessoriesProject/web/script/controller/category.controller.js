/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var CategoryController = function(app, ajaxService, xmlService, stateService) {
    var viewIds = {
        div: {
            navbar: 'navbar',
            categoryName: 'categoryName',
            products: 'divProducts',
            paging: 'pagination',
        },
    };
    
    var pageSize = 50
    var categoryId = app.getParameter('id');
    var page = app.getParameter('page') || 1;
    var total = 0;
    var products = null;
    var productsXml = null;
    var productsXsl = null;
    var pagingXsl = null;
    
    //Running
    renderNavbar();
    getCategoryDetail();
    getProducts();
    
    function renderNavbar() {
        //TODO: check cache
        //Not have cache -> get category
    }
    
    function getCategoryDetail() {
        var data = {
            id: categoryId,
        };
        ajaxService.getXml(app.url.api.category, data).then(function(response) {
            var category = xmlService.unmarshalling(response);
            renderCategoryName(category.category.name);
        }).catch(function(error) {
            console.log(error);
        })
    }
    
    function getProducts() {
        var params = {
            categoryId: categoryId,
            page: page,
            pageSize: pageSize,
        };
        var paramsXml = xmlService.marshalling(params, 'param');
        var paramsStr = xmlService.parseXmlToString(paramsXml);
        var data = {
            param: paramsStr,
        };
        ajaxService.getXml(app.url.api.product, data).then(function(response) {
            var dataTable = xmlService.unmarshalling(response);
            products = {
                products: {
                    product: dataTable.datatable.data,
                },
            };
            total = dataTable.datatable.total
            productsXml = xmlService.marshallingAuto(products);
            getProductSquareXsl();
            getPagingXsl();
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function getProductSquareXsl() {
        ajaxService.get(app.url.xsl.productSquare).then(function(response) {
            productsXsl = response;
            renderProducts();
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function getPagingXsl() {
        ajaxService.get(app.url.xsl.paging).then(function(response) {
            pagingXsl = response;
            renderPagination();
        }).catch(function(error) {
            console.log(error);
        })
    }
    
    function renderCategoryName(categoryName) {
        var div = document.getElementById(viewIds.div.categoryName);
        div.innerText = categoryName;
    }
    
    function renderProducts() {
        var productsHtml = xmlService.transformToDocument(productsXml, productsXsl)
        var div = document.getElementById(viewIds.div.products);
        xmlService.removeAllChild(div);
        div.appendChild(productsHtml);
    }
    
    function renderPagination() {
        var pagingObj = createPagingObject();
        var pagingXml = xmlService.marshalling(pagingObj, 'root');
        var pagingHtml = xmlService.transformToDocument(pagingXml, pagingXsl);
        var div = document.getElementById(viewIds.div.paging);
        xmlService.removeAllChild(div);
        div.appendChild(pagingHtml);
    }
    
    function createPagingObject(){
        var pagingArray = [];
        var rootUrl = app.url.page.category + '?id=' + categoryId + '&page=';
        
        if(page > 1) {
            pagingArray.push({
                name: '&laquo;',
                url: rootUrl + (parseInt(page) - 1),
            });
        }
        
        var totalPage = Math.floor(total / pageSize) + 1;
        for(var index = parseInt(page) - 2; index <= parseInt(page) + 2; index++) {
            if(index > 0 && index <= totalPage) {
                var pageObj = {
                    name: index,
                    url: rootUrl + index,
                };
                if(index == page) {
                    pageObj.currentPage = 'true';
                }
                pagingArray.push(pageObj);
            }
        }
        
        if(parseInt(page) < totalPage) {
            pagingArray.push({
                name: '&raquo;',
                url: rootUrl + (parseInt(page) + 1)
            });
        }
        
        var result = {
            page: pagingArray,
        };
        
        return result;
    }
}