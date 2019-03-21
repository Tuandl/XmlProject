/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('ProductService');
require('NavService');
require('AuthService');
require('ProductSearchService');


var SearchController = function() {
    var productService = new ProductService();
    var navService = new NavService();
    var authService = new AuthService();
    var productSearchService = new ProductSearchService();
    
    var viewIds = {
        div: {
            topBar: 'divTopBar',
            search: 'divSearch',
            navbar: 'navbar',
            searchValue: 'divSearchValue',
            searchMethod: 'divSeachTech',
            products: 'divProducts',
            paging: 'pagination',
        },
        button: {
            searchOffline: 'btnSearchOffline',
            searchOnline: 'btnSearchOnline',
            searchSmart: 'btnSearchSmart',
        },
    };
    
    var searchValue = app.getParameter('searchValue') || '';
//    if(searchValue == '') {
//        window.location.replace(app.url.page.home);
//    }
    
    var searchType = app.getParameter('type') || productSearchService.searchType.offline;
    var page = app.getParameter('page') || 1;
    var pageSize = 50;
    
    authService.renderTopBarAuthorize(viewIds.div.topBar);
    productSearchService.renderSearchTopBar(viewIds.div.search);
    navService.renderNavBarCategories(viewIds.div.navbar);
    productService.renderProductInSearch(viewIds.div.products,
            viewIds.div.paging, searchValue, searchType, 
            page, pageSize);
    enrichStatus();
    
    
    var btnSearchOffline = document.getElementById(viewIds.button.searchOffline);
    btnSearchOffline.addEventListener('click', onBtnSearchOfflineClicked);
    
    var btnSearchOnline = document.getElementById(viewIds.button.searchOnline);
    btnSearchOnline.addEventListener('click', onBtnSearchOnlineClicked);
    
    var btnSearchSmart = document.getElementById(viewIds.button.searchSmart);
    btnSearchSmart.addEventListener('click', onBtnSearchSmartClicked);
    
    function onBtnSearchOfflineClicked() {
        searchType = productSearchService.searchType.offline;
        window.location.href = app.url.page.search + "?searchValue=" + searchValue + "&type=" + searchType;
    }
    
    function onBtnSearchOnlineClicked() {
        searchType = productSearchService.searchType.online;
        window.location.href = app.url.page.search + "?searchValue=" + searchValue + "&type=" + searchType;
    }
    
    function onBtnSearchSmartClicked() {
        searchType = productSearchService.searchType.smart;
        window.location.href = app.url.page.search + "?searchValue=" + searchValue + "&type=" + searchType;
    }
    
    function enrichStatus() {
        var searchValueDiv = document.getElementById(viewIds.div.searchValue);
        searchValueDiv.innerHTML = searchValue;

        var searchTech = document.getElementById(viewIds.div.searchMethod);
        switch (parseInt(searchType)) {
            case productSearchService.searchType.offline: 
                searchTech.innerHTML = 'Offline';
                var btnOffline = document.getElementById(viewIds.button.searchOffline);
                btnOffline.disabled = true;
                break;
            case productSearchService.searchType.online:
                searchTech.innerHTML = 'Online';
                var btnOnline = document.getElementById(viewIds.button.searchOnline);
                btnOnline.disabled = true;
                break;
            case productSearchService.searchType.smart:
                searchTech.innerHTML = 'Smart';
                var btnSmart = document.getElementById(viewIds.button.searchSmart);
                btnSmart.disabled = true;
                break;
        }
    }
}