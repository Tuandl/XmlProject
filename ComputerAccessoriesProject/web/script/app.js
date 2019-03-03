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
        },
        page: {
            login: 'view/login.jsp',
            register: 'view/register.jsp',
            adminDashBoard: 'view/admin/dashboard.jsp',
        }
    };
    
    var getRealUrl = function(url) {
        return rootUrl + url;
    };
    
    Object.keys(url.api).forEach(function(key) {
       var value = url.api[key];
       url.api[key] = getRealUrl(value);
    });
    
    Object.keys(url.page).forEach(function(key) {
       var value = url.page[key];
       url.page[key] = getRealUrl(value);
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
    
    this.url = url;
    this.toggleClass = toggleClass;
    this.addClass = addClass;
    this.removeClass = removeClass;
    this.resetForm = resetForm;
};
