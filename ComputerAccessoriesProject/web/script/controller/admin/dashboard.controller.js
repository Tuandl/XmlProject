/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AuthService');
require('NavService');
require('AjaxService');

var DashBoardController = function () {
    var authService = new AuthService();
    var navService = new NavService();
    var ajaxService = new AjaxService();
    
    authService.checkAdmin();
    
    //Declaration...
    var viewIds = {
        div: {
            topBar: 'divTopBar',
            navBar: 'divNavbar',
        },
        button: {
            save: 'btnCommission',
        },
        text: {
            commission: 'txtCommission',
        },
    };
    
    //Running Flow  
    authService.renderTopBarAuthorize(viewIds.div.topBar);
    navService.renderNavBarAdmin(viewIds.div.navBar);
    getCommission();
    
    var btn = document.getElementById(viewIds.button.save);
    btn.addEventListener('click', onBtnSaveClicked);
    
    
    function onBtnSaveClicked() {
        saveCommission();
    }
    
    function getCommission() {
        ajaxService.get(app.url.api.commission).then(function(response) {
            var div = document.getElementById(viewIds.text.commission);
            div.value = response;
        });
    }
    
    function saveCommission() {
        var div = document.getElementById(viewIds.text.commission);
        var commission = div.value;
        if(commission == null) {
            return;
        }
        var data = {
            commission: commission,
        };
        ajaxService.post(app.url.api.commission, data).then(function(response) {
            alert('Update Success');
        }).catch(function(error) {
            alert('Update Failed');
            console.log(error);
        })
    }
}