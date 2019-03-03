/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var DashBoardController = function (app, stateService, ajaxService, xmlService) {
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
    var btnTriggerCrawl = document.getElementById(viewIds.button.triggerCrawl);
    btnTriggerCrawl.addEventListener('click', onBtnTriggerCrawlClicked);
    
    testTransform();
    
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
    
    function onBtnTriggerCrawlClicked () {
        console.log('not support crawl yet!');
    }
    
    function testTransform() {
        ajaxService.get(app.url.xsl.user).then(function(response) {
            console.log('xsl response', response);
            var xslFile = response;
            
            var currentUserXmlText = stateService.getCurrentUser();
            var currentUserXml = xmlService.parseStringToXml(currentUserXmlText);
            var transformResult = xmlService.transformToDocument(currentUserXml, xslFile);
            console.log(transformResult);
            
            var div = document.getElementById(viewIds.text.test);
            div.appendChild(transformResult);

        }).catch(function(error) {
            console.error(error);
        });
    }
}