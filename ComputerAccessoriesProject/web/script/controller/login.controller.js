/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var LoginController = function (app, stateService, ajaxService, xmlService) {
    //Declaration...
    var viewIds = {
        input: {
            username: 'txtUsername',
            password: 'txtPassword',
        },
        button: {
            login: 'btnLogin',
            register: 'btnRegister',
        },
        error: {
            loginFailed: 'error-login',
        }
    };
    
    //Running Flow
    var btnLogin = document.getElementById(viewIds.button.login);
    btnLogin.addEventListener('click', onBtnLoginClicked);
    var btnRegister = document.getElementById(viewIds.button.register);
    btnRegister.addEventListener('click', onBtnRegisterClicked);
    
    //Handler methods
    function onBtnLoginClicked() {
        //handle login
        var txtUsername = document.getElementById(viewIds.input.username);
        var txtPassword = document.getElementById(viewIds.input.password);
        
        var data = {
            username: txtUsername.value,
            password: txtPassword.value,
        };
        
        ajaxService.post(app.url.api.login, data).then(function(response){
            var data = {
                username: txtUsername.value,
            };
            
            ajaxService.get(app.url.api.user, data).then(function(response) {
                console.log('user data', response);
                var xmlDoc = xmlService.parseStringToXml(response);
                console.log('user xml', xmlDoc);
                var userTmp = xmlService.parseXmlToObject(xmlDoc);
                console.log('user', userTmp);
                
                var rootTag = Object.keys(userTmp)[0];
                var obj = userTmp[rootTag];
                var userXml = xmlService.parseObjectToXml(obj, rootTag);
                console.log('user xml', userXml);
                
                var xmlStr = xmlService.parseXmlToString(xmlDoc);
                console.log('user str', xmlStr);
                var userXmlStr = xmlService.parseXmlToString(userXml);
                console.log('user xml Str', userXmlStr);
            }).catch(function(error) {
                console.error('user error', error);
            });
        }).catch(function(error) {
            console.log('error', error);
            var errorElement = document.getElementById(viewIds.error.loginFailed);
            errorElement.classList.remove('hidden');
        });
    }
    
    function onBtnRegisterClicked() {
        window.location.replace(app.url.page.register);
    }
}