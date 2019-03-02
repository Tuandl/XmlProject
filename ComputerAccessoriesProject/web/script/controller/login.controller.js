/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var LoginController = function (app, stateService, ajaxService) {
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
        
        var loginUrl = app.url.login;
        
//        console.log('data login', data);
//        console.log('loginUrl', loginUrl);
        
        ajaxService.post(loginUrl, data).then(function(response){
            console.log('success', response); 
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