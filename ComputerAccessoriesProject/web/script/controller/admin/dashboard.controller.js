/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('StateService');
require('AjaxService');
require('XmlService');

var DashBoardController = function () {
    var stateService = new StateService();
    var ajaxService = new AjaxService();
    var xmlService = new XmlService();
    
    //Declaration...
    var viewIds = {
        input: {
        },
        text: {
            currentUserName: 'adminName',
            test: 'testSection',
        },
        button: {
            triggerCrawl: 'btnTriggerCrawl',
        },
        error: {
        }
    };
    
    //Running Flow  
    setCurrentUserName();
//    testTransform();
    
    //Handler methods
    function setCurrentUserName() {
        var txtAdminName = document.getElementById(viewIds.text.currentUserName);
        
        var currentUserXmlText = stateService.getCurrentUser();
        if(currentUserXmlText == undefined) {
            txtAdminName.innerHTML = 'not found user';
        } else {
            var currentUserXml = xmlService.parseStringToXml(currentUserXmlText);
            var currentUser = xmlService.unmarshalling(currentUserXml);
            txtAdminName.innerHTML = currentUser.user.fullname;
        }
    }
    
//    function testTransform() {
//        ajaxService.get(app.url.xsl.user).then(function(response) {
//            console.log('xsl response', response);
//            var xslFile = response;
//            
//            var currentUserXmlText = stateService.getCurrentUser();
//            var currentUserXml = xmlService.parseStringToXml(currentUserXmlText);
//            var transformResult = xmlService.transformToDocument(currentUserXml, xslFile);
//            console.log(transformResult);
//            
//            var div = document.getElementById(viewIds.text.test);
//            div.appendChild(transformResult);
//
//        }).catch(function(error) {
//            console.error(error);
//        });
//    }
}