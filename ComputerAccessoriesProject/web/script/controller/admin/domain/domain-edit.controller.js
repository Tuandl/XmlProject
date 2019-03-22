/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



require('DomainService');
require('AuthService');
require('NavService');

var DomainEditController = function() {
    
    var authService = new AuthService();
    var navService = new NavService();
    
    authService.checkAdmin();
    
    var domainId = app.getParameter('id');
    if(!domainId) {
        window.location.replace(app.url.page.domainList);
    };
    
    var domainService = new DomainService(domainId);
    
    var viewIds = {
        button: {
            update: 'btnUpdate',
            back: 'btnBack',
        },
        text: {
            domainName: 'txtDomainName',
            initUrl: 'txtInitUrl',
            pagingXPath: 'txtPagingXPath',
        },
        div: {
            detail: 'detailTable',
            topBar: 'divTopBar',
            navBar: 'divNavbar',
        },
        error: {
            domainName: 'errorRequireDomainName',
            initUrl: 'errorInitUrl',
            nextPageQuery: 'errorNextPageXpath',
            detailConfig: 'errorDetailConfig',
        },
    };
    
    var viewNames = {
        text: {
            xpath: 'txtXPathQuery',
        },
        select: {
            resultType: 'slResultType',
        },
    };
    
    //init
    authService.renderTopBarAuthorize(viewIds.div.topBar);
    navService.renderNavBarAdmin(viewIds.div.navBar);
    
    domainService.getDomainDetail().then(function() {
        domainService.renderDataMappingForm(viewIds.div.detail).then(function() {
            domainService.getDomainDetail().then(function(data) {
                bindExistedData(data);
            })
        });
    });
    oneTimeBindEventToButton();
    
    //methods
    function oneTimeBindEventToButton() {
        var updateBtn = document.getElementById(viewIds.button.update);
        updateBtn.addEventListener('click', onBtnUpdateClicked);
        var backBtn = document.getElementById(viewIds.button.back);
        backBtn.addEventListener('click', onBackBtnClicked);
    }
    
    function onBtnUpdateClicked() {
        domainService.getDomainDetail().then(function(data) {
            if(!validateForm(data)) {
                return;
            }

            domainService.updateDomain().then(function(response) {
                alert('Update Success');
                onBackBtnClicked();
            }).catch(function(error) {
                alert('Error');
            })    
        });
        
    }
    
    function onBackBtnClicked() {
        window.location.replace(app.url.page.domainList);
    }
    
    function validateForm(data) {
        app.resetForm(viewIds.error);
        
        var txtDomainName = document.getElementById(viewIds.text.domainName);
        var domainName = txtDomainName.value;
        if(!domainName || domainName.length == 0) {
            app.showElement(viewIds.error.domainName);
            return false;
        }
        data.domain.domainName = domainName
        
        var txtUrl = document.getElementById(viewIds.text.initUrl);
        var initUrl = txtUrl.value;
        if(!initUrl || initUrl.length == 0) {
            app.showElement(viewIds.error.initUrl);
            return false;
        }
        data.domain.initUrl = initUrl;
        
        var txtNextPage = document.getElementById(viewIds.text.pagingXPath);
        var nextPageXpath = txtNextPage.value;
        if(!nextPageXpath || nextPageXpath.length == 0) {
            app.showElement(viewIds.error.nextPageQuery);
            return false;
        }
        data.domain.pagingXPathQuery = nextPageXpath;
        
        var txtXpaths = document.getElementsByName(viewNames.text.xpath);
        var isOk = true;
        txtXpaths.forEach(function(txtXpath){
            var xPath = txtXpath.value;
            if(!xPath || xPath.length == 0) {
                app.showElement(viewIds.error.detailConfig);
                isOk = false;
            }
            
            var dataId = app.getAttributeValue(txtXpath, 'data-data-id');
            var dataMapping = data.domain.mapping.find(function(element){
                return element.dataId == dataId;
            });

            dataMapping.xPathQuery = xPath;
        });
        if(!isOk) {
            return false;
        }
        
        var selects = document.getElementsByName(viewNames.select.resultType);
        selects.forEach(function(select) {
            var dataId = app.getAttributeValue(select, 'data-data-id');
            var dataMapping = data.domain.mapping.find(function(element){
                return element.dataId == dataId;
            });

            dataMapping.isNodeResult = select.options[select.selectedIndex].value;
        })
        
        return true;
    }
    
    function bindExistedData(data) {
        var txtDomainName = document.getElementById(viewIds.text.domainName);
        txtDomainName.value = data.domain.domainName;
        
        var txtUrl = document.getElementById(viewIds.text.initUrl);
        txtUrl.value = data.domain.initUrl;
        
        var txtNextPage = document.getElementById(viewIds.text.pagingXPath);
        txtNextPage.value = data.domain.pagingXPathQuery;
    }
}