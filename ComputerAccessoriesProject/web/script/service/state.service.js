/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Storing Application Data
 * @returns {StateService}
 */
var StateService = function() {
    var SUFFIX_EXPIRED = '_expiredAt';
    
    var stateConst = {
        user: 'USER_DATA',
        categoriesXml: 'CATEGORIES_XML',
        navBarCategoriesXsl: 'NAVBAR_CATEGORIES_XSL',
        topProductXml: 'TOP_PRODUCT_XML',
        productListXsl: 'PRODUCT_LIST_XSL',
        productDetailXsl: 'PRODUCT_DETAIL_XSL',
        topCategoriesXml: 'TOP_CATEGORIES_XML',
        topCategoriesXsl: 'TOP_CATEGORIES_XSL',
        pagingXsl: 'PAGING_XSL',
    };
    
    /**
     * remove data in localStorage
     * @param {type} key
     * @returns {undefined}
     */
    function remove(key) {
        localStorage.removeItem(key);
        localStorage.removeItem(key + SUFFIX_EXPIRED);
    }
    
    /**
     * Save data into localStorage with expired date
     * Expired: second
     * Default Expired Date: 24*60*60
     * @param {type} key
     * @param {type} value
     * @param {type} expired
     * @returns {undefined}
     */
    function save(key, value, expired) {
        if(value == null || value == undefined) {
            return;
        }
        
        if(expired == null || expired == undefined) {
            //default is a day
            expired = 24 * 60 * 60;
        }
        expired *= 1000;
        var now = Date.now();
        var expiredAt = now + expired;
        localStorage.setItem(key, value);
        localStorage.setItem(key + SUFFIX_EXPIRED, expiredAt);
    }
    
    /**
     * Get data still valid in local storage
     * @param {type} key
     * @returns {DOMString}
     */
    function get(key){
        var expiredAt = localStorage.getItem(key + SUFFIX_EXPIRED);
        if(expiredAt == null || expiredAt == undefined) {
            return null;
        }
        
        var now = Date.now();
        if(now < expiredAt) {
            //still valid
            var value = localStorage.getItem(key);
            return value;
        } else {
            remove(key);
            return null;
        }
    }
    
    var getCurrentUser = function(){
        var user = localStorage.getItem(stateConst.user);
        return user;
    }
    
    var setCurrentUser = function(user) {
        localStorage.setItem(stateConst.user, user);
    }
    
    this.getCurrentUser = getCurrentUser;
    this.setCurrentUser = setCurrentUser;
    this.getItem = get;
    this.setItem = save;
    this.stateConst = stateConst;
}