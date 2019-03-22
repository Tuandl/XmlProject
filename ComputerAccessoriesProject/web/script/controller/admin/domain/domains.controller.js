/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('DomainService');
require('AuthService');
require('NavService');

var DomainsController = function() {
    var domainService = new DomainService();
    
    var authService = new AuthService();
    var navService = new NavService();
    
    authService.checkAdmin();
    var viewIds = {
        button: {
            addDomain: 'btnAddDomain',
        },
        div: {
            table: 'divDomainTable',
            topBar: 'divTopBar',
            navBar: 'divNavbar',
        },
    };
    
    var viewNames = {
        button: {
            edit: 'btnEditDomain',
            delete: 'btnDeleteDomain',
        },
    };
    
    //flow
    authService.renderTopBarAuthorize(viewIds.div.topBar);
    navService.renderNavBarAdmin(viewIds.div.navBar);
    
    domainService.renderDomainTable(viewIds.div.table).then(function() {
        bindEventToButtons();
    });
    
    //methods
    function bindEventToButtons() {
        var btnAddDomain = document.getElementById(viewIds.button.addDomain);
        btnAddDomain.addEventListener('click', onBtnAddDomainClicked);

        var btnEdits = document.getElementsByName(viewNames.button.edit);
        btnEdits.forEach(function(btn) {
            btn.addEventListener('click', onBtnEditClicked);
        });
        
        var btnDeletes = document.getElementsByName(viewNames.button.delete);
        btnDeletes.forEach(function(btn) {
            btn.addEventListener('click', onBtnDeleteClicked);
        });
    }
    
    function onBtnAddDomainClicked() {
        window.location.href = app.url.page.domainInsert;
    }
    
    function onBtnEditClicked(event) {
        var btn = event.srcElement;
        var domainId = app.getIdInDataId(btn);
        
        window.location.href = app.url.page.domainUpdate + "?id=" + domainId;
    }
    
    function onBtnDeleteClicked(event) {
        var btn = event.srcElement;
        var domainId = app.getIdInDataId(btn);
        
        btn.disabled = true;
        domainService.deleteDomain(domainId).then(function(response) {
            domainService.renderDomainTable(viewIds.div.table).then(function() {
                bindEventToButtons();
            })
        })
    }
}