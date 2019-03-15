/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Core function of application
 * @returns {App}
 */
var App = function () {
    var rootUrl = 'http://localhost:8080/ComputerAccessoriesProject/';
    var url = {
        api: {
            login: 'LoginServlet',
            user: 'UserServlet',
            category: 'CategoryServlet',
            categoryTop: 'Category/Top',
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
            category: 'view/category.jsp',
            categoryList: 'view/admin/category/categories.jsp',
            categoryInsert: 'view/admin/category/category.add.jsp',
            categoryUpdate: 'view/admin/category/category.edit.jsp',
            crawl: 'view/admin/crawl.jsp',
            home: 'view/index.jsp',
            product: 'view/product-detail.jsp',
        },
        xsl: {
            user: 'xsl/user.xsl',
            categoryTable: 'xsl/categories.table.xsl',
            categorySelect: 'xsl/categories.select.xsl',
            categoryTop: 'xsl/categories.top.xsl',
            categoryRawTable: 'xsl/category-raw.table.xsl',
            categoryNavBar: 'xsl/categories.navbar.xsl',
            productSquare: 'xsl/products.square.xsl',
            productDetail: 'xsl/product.detail.xsl',
            paging: 'xsl/paging.xsl',
        }
    };
    
    /**
     * Define module for dynamic loading
     * @type Array
     */
    var modules = [
        {
            name: 'App',
            isLoaded: true,
        },
        {
            name: 'AjaxService',
            url: 'script/service/ajax.service.js',
        },
        {
            name: 'StateService',
            url: 'script/service/state.service.js',
        },
        {
            name: 'XmlService',
            url: 'script/service/xml.service.js',
        }, 
        {
            name: 'NavService',
            url: 'script/service/nav.service.js',
        },
    ];
    
    function getRealUrl(url) {
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
    
    //format URL for module
    Object.keys(modules).forEach(function(key) {
        var value = modules[key].url;
        modules[key].url = getRealUrl(value);
    });
    
    function toggleClass(id, className) {
        var element = document.getElementById(id);
        if(element.classList.contains(className)) {
            element.classList.remove(className);
        } else {
            element.classList.add(className);
        }
    }
    
    function addClass(id, className) {
        var element = document.getElementById(id);
        if(!element.classList.contains(className)) {
            element.classList.add(className);
        }
    }
    
    function removeClass(id, className) {
        var element = document.getElementById(id);
        if(element.classList.contains(className)) {
            element.classList.remove(className);
        }
    }
    
    function resetForm(errorIdsObj) {
        if(errorIdsObj != null && typeof errorIdsObj == 'object') {
            Object.keys(errorIdsObj).forEach(function(key) {
                var id = errorIdsObj[key];
                addClass(id, 'hidden');
            });
        }
    }
    
    function getAttributeValue(node, attName) {
        var attributes = node.attributes;
        for(var i = 0; i < attributes.length; i++) {
            var att = attributes.item(i);
            if(att.name == attName) {
                return att.value;
            }
        }
    }
    
    function getIdInDataId(node) {
        var result = getAttributeValue(node, 'data-id');
        return result;
    }
    
    function getRequestParam(paramName) {
        var url = new URL(window.location.href);
        var param = url.searchParams.get(paramName);
        return param;
    }
    
    function makeUrlWithParam(url, params) {
        var urlWithParam = url;
        if(params != null && params != undefined && typeof params == 'object') {
            urlWithParam += '?' + Object.keys(params).map(function(key) {
                var value = params[key];
                return key + '=' + value;
            }).join('&');
        }
        return urlWithParam;
    }
    
    function getParameter(name) {
        var currentUrlStr = window.location.href;
        var url = new URL(currentUrlStr);
        var result = url.searchParams.get(name);
        return result;
    }
    
    /**
     * For Dynamic Loading module
     * @param {type} moduleName
     * @param {type} deep
     * @returns {undefined}
     */
    function require(moduleName, deep) {
        console.log('required ' + moduleName + ' ' + deep);
        var module = modules.find(function(entity) {
            return entity.name == moduleName;
        });
        if(!module) {
            console.error('Cannot find module ' + moduleName);
            return undefined;
        }
        
        if(!module.isLoaded) {
            if(!deep) {
                deep = [moduleName];
            } else {
                var existed = deep.find(function(entity) {
                    return entity == moduleName;
                });
                if(existed) {
                    console.error('Error!! Circula injection', deep);
                    return;
                }
                deep.push(moduleName);
            }
            loadModuleSync(module.url, deep);
            module.isLoaded = true;
        }
        
//        return new window[moduleName];
    }
    
    function loadModuleSync(url) {
        var head = document.head;
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = url;
        
        script.onload = function() {
            console.log('on load');
        }
        
        script.async = false;
        
        head.appendChild(script);
    }
    
    this.url = url;
    this.toggleClass = toggleClass;
    this.addClass = addClass;
    this.removeClass = removeClass;
    this.resetForm = resetForm;
    this.getIdInDataId = getIdInDataId;
    this.getRequestParam = getRequestParam;
    this.makeUrlWithParam = makeUrlWithParam;
    this.getParameter = getParameter;
    this.require = require;
};

var app = new App();

/**
 * Dynamic load module
 * @param {type} moduleName
 * @returns {undefined}
 */
function require(moduleName) {
    app.require(moduleName);
}