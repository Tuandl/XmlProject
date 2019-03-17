/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AuthService');

var LoginController = function () {
    var authService = new AuthService();
    
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
        app.addClass(viewIds.error.loginFailed, 'hidden');
        
        var txtUsername = document.getElementById(viewIds.input.username);
        var txtPassword = document.getElementById(viewIds.input.password);
        
        authService.login(txtUsername.value, txtPassword.value).then(function(user) {
            if(user.isAdmin == 'true') {
                window.location.replace(app.url.page.adminDashBoard);
            } else {
                window.location.replace(app.url.page.home);
            }
        }).catch(function(error) {
            console.log(error);
            app.removeClass(viewIds.error.loginFailed, 'hidden');
        });
    }
    
    function onBtnRegisterClicked() {
        window.location.href = app.url.page.register;
    }
}