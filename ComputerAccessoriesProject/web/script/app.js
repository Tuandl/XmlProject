/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var App = function () {
    var rootUrl = 'http://localhost:8080/ComputerAccessoriesProject/';
    var url = {
        api: {
            login: 'LoginServlet',
            user: 'UserServlet',
            category: 'CategoryServlet',
            categoryRaw: 'CategoryRawServlet',
            categoryRawCountNewProduct: 'CategoryRaw/NewProduct',
            categoryRawCountEditedProduct: 'CategoryRaw/EditedProduct',
            categoryRawCountTotalProduct: 'CategoryRaw/TotalProduct',
            crawlCategory: 'Crawl/Category',
            crawlProduct: 'Crawl/Product',
            product: 'Product',
        },
        page: {
            login: 'view/login.jsp',
            register: 'view/register.jsp',
            adminDashBoard: 'view/admin/dashboard.jsp',
            categoryList: 'view/admin/category/categories.jsp',
            categoryInsert: 'view/admin/category/category.add.jsp',
            categoryUpdate: 'view/admin/category/category.edit.jsp',
            crawl: 'view/admin/crawl.jsp',
        },
        xsl: {
            user: 'xsl/user.xsl',
            categoryTable: 'xsl/categories.table.xsl',
            categorySelect: 'xsl/categories.select.xsl',
            categoryRawTable: 'xsl/category-raw.table.xsl',
        }
    };
    
    var getRealUrl = function(url) {
        return rootUrl + url;
    };
    
    //format url for API
    Object.keys(url.api).forEach(function(key) {
       var value = url.api[key];
       url.api[key] = getRealUrl(value);
    });
    
    //format URL for page
    Object.keys(url.page).forEach(function(key) {
       var value = url.page[key];
       url.page[key] = getRealUrl(value);
    });
    
    //format URL for XSL file
    Object.keys(url.xsl).forEach(function(key) {
       var value = url.xsl[key];
       url.xsl[key] = getRealUrl(value);
    });
    
    var toggleClass = function(id, className) {
        var element = document.getElementById(id);
        if(element.classList.contains(className)) {
            element.classList.remove(className);
        } else {
            element.classList.add(className);
        }
    }
    
    var addClass = function(id, className) {
        var element = document.getElementById(id);
        if(!element.classList.contains(className)) {
            element.classList.add(className);
        }
    }
    
    var removeClass = function(id, className) {
        var element = document.getElementById(id);
        if(element.classList.contains(className)) {
            element.classList.remove(className);
        }
    }
    
    var resetForm = function(errorIdsObj) {
        if(errorIdsObj != null && typeof errorIdsObj == 'object') {
            Object.keys(errorIdsObj).forEach(function(key) {
                var id = errorIdsObj[key];
                addClass(id, 'hidden');
            });
        }
    }
    
    var getAttributeValue = function(node, attName) {
        var attributes = node.attributes;
        for(var i = 0; i < attributes.length; i++) {
            var att = attributes.item(i);
            if(att.name == attName) {
                return att.value;
            }
        }
    }
    
    var getIdInDataId = function (node) {
        var result = getAttributeValue(node, 'data-id');
        return result;
    }
    
    var getRequestParam = function(paramName) {
        var url = new URL(window.location.href);
        var param = url.searchParams.get(paramName);
        return param;
    }
    
    var makeUrlWithParam = function(url, params) {
        var urlWithParam = url;
        if(params != null && params != undefined && typeof params == 'object') {
            urlWithParam += '?' + Object.keys(params).map(function(key) {
                var value = params[key];
                return key + '=' + value;
            }).join('&');
        }
        return urlWithParam;
    }
    
    this.url = url;
    this.toggleClass = toggleClass;
    this.addClass = addClass;
    this.removeClass = removeClass;
    this.resetForm = resetForm;
    this.getIdInDataId = getIdInDataId;
    this.getRequestParam = getRequestParam;
    this.makeUrlWithParam = makeUrlWithParam;
};
