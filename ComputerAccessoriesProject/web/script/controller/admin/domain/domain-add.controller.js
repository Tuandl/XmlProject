/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('DomainService');

var DomainAddController = function() {
    var domainService = new DomainService(-1);
    
    var viewIds = {
        button: {
            add: 'btnInsert',
            back: 'btnBack',
        },
        text: {
            domainName: 'txtDomainName',
            initUrl: 'txtInitUrl',
            pagingXPath: 'txtPagingXPath',
        },
        div: {
            detail: 'detailTable',
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
    domainService.getDomainDetail().then(function() {
        domainService.renderDataMappingForm(viewIds.div.detail);
    });
    oneTimeBindEventToButton();
    
    //methods
    function oneTimeBindEventToButton() {
        var insertBtn = document.getElementById(viewIds.button.add);
        insertBtn.addEventListener('click', onInsertBtnClicked);
        var backBtn = document.getElementById(viewIds.button.back);
        backBtn.addEventListener('click', onBackBtnClicked);
    }
    
    function onInsertBtnClicked() {
        domainService.getDomainDetail().then(function(data) {
            if(!validateForm(data)) {
                return;
            }

            domainService.insertDomain().then(function(response) {
                alert('Add Success');
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
}