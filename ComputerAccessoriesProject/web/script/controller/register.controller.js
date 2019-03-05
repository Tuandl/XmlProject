/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var RegisterController = function (app, ajaxService) {
    //Declaration...
    var viewIds = {
        input: {
            username: 'txtUsername',
            password: 'txtPassword',
            fullName: 'txtFullName',
        },
        button: {
            login: 'btnLogin',
            register: 'btnRegister',
        },
        error: {
            username: 'error-username',
            password: 'error-password',
            fullname: 'error-full-name',
            register: 'error-register',
        },
        success: {
            register: 'success-register',
        }
    };
    
    //Running Flow
    var btnLogin = document.getElementById(viewIds.button.login);
    btnLogin.addEventListener('click', onBtnLoginClicked);
    var btnRegister = document.getElementById(viewIds.button.register);
    btnRegister.addEventListener('click', onBtnRegisterClicked);
    
    
    
    //Handler methods
    function onBtnLoginClicked() {
        window.location.href = app.url.page.login;
    }
    
    function onBtnRegisterClicked() {
        var data = getRegisterData();
        if(data != null) {
            ajaxService.post(app.url.api.user, data).then(function(resposne){
                app.resetForm(viewIds.error);
                app.removeClass(viewIds.success.register, 'hidden');
            }).catch(function(error){
                app.addClass(viewIds.success.register, 'hidden');
                var errorSpan = document.getElementById(viewIds.error.register);
                errorSpan.innerText = error.message;
                errorSpan.classList.remove('hidden');
            });
        }
    }
    
    function getRegisterData() {
        var usernameElm = document.getElementById(viewIds.input.username);
        var passwordElm = document.getElementById(viewIds.input.password);
        var fullNameElm = document.getElementById(viewIds.input.fullName);
        
        var data = {
            username: usernameElm.value,
            password: passwordElm.value,
            fullName: fullNameElm.value,
        };
        
        if(data.username.length == 0) {
            app.removeClass(viewIds.error.username, 'hidden');
            return null;
        } else {
            app.addClass(viewIds.error.username, 'hidden');
        }
        
        if(data.password.length == 0) {
            app.removeClass(viewIds.error.password, 'hidden');
            return null;
        } else {
            app.addClass(viewIds.error.password, 'hidden');
        }
        
        if(data.fullName.length == 0) {
            app.removeClass(viewIds.error.fullname, 'hidden');
            return null;
        } else {
            app.addClass(viewIds.error.fullname, 'hidden');
        }
        
        return data;
    }
}