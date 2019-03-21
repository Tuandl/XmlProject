/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('AjaxService');
require('XmlService');
require('StateService');

/**
 * Use for login, render topbar, authorize admin
 * @returns {AuthService}
 */
var AuthService = function() {
    var ajaxService = new AjaxService();
    var xmlService = new XmlService();
    var stateService = new StateService();
    
    function getCurrentUser() {
        var userXmlStr = stateService.getItem(stateService.stateConst.user);
        return userXmlStr;
    }
    
    /**
     * redirect into home page if current user is not admin
     * @returns {undefined}
     */
    function checkAdmin() {
        var currentUserXmlStr = getCurrentUser();
        if(currentUserXmlStr == null) {
            window.location.replace(app.url.page.home);
        } else {
            var currentUserXml = xmlService.parseStringToXml(currentUserXmlStr);
            var user = xmlService.unmarshalling(currentUserXml);
            if(user.user.isAdmin == 'true') {
                //do nothing
            } else {
                window.location.replace(app.url.page.home);
            }
        }
    }
    
    /**
     * If not logined -> redirect to home page
     * @returns {undefined}
     */
    function checkAuthorized() {
        var currentUser = getCurrentUser();
        if(currentUser == null) {
            window.location.replace(app.url.page.home);
        }
    }
    
    /**
     * Check login and return user object if success
     * Else return error message
     * @param {type} username
     * @param {type} password
     * @returns {Promise}
     */
    function login(username, password) {
        return new Promise(function(resolve, reject) {
            var data = {
                username: username,
                password: password,
            };

            ajaxService.post(app.url.api.login, data).then(function(){
                var data = {
                    username: username,
                };

                ajaxService.get(app.url.api.user, data).then(function(response) {
                    stateService.setCurrentUser(response);
                    var userXml = xmlService.parseStringToXml(response);
                    var user = xmlService.unmarshalling(userXml);
                    resolve(user.user);
                }).catch(function(error) {
                    console.error('user error', error);
                    reject(error);
                });
            }).catch(function(error) {
                console.log('error', error);
                reject(error);
            });
        });
    }
    
    function logout() {
        stateService.removeItem(stateService.stateConst.user);
        window.location.reload();
    }
    
    function getUserAnonymousTopBarXsl() {
        return new Promise(function(resolve, reject) {
            var xsl = stateService.getItem(stateService.stateConst.userTopBarAnonymousXsl);
            if(xsl == null) {
                ajaxService.get(app.url.xsl.userTopBarAnonymous).then(function(response) {
                    stateService.setItem(stateService.stateConst.userTopBarAnonymousXsl, response);
                    resolve(response);
                }).catch(function(error) {
                    console.log(error);
                    reject(error);
                });
            } else {
                resolve(xsl);
            }
        });
    }
    
    function getUserAuthorizeTopBarXsl() {
        return new Promise(function(resolve, reject) {
            var xsl = stateService.getItem(stateService.stateConst.userTopBarAuthorizedXsl);
            if(xsl == null) {
                ajaxService.get(app.url.xsl.userTopBarAuthorized).then(function(response) {
                    stateService.setItem(stateService.stateConst.userTopBarAuthorizedXsl, response);
                    resolve(response);
                }).catch(function(error) {
                    console.log(error);
                    reject(error);
                });
            } else {
                resolve(xsl);
            }
        });
    }
    
    /**
     * render TopBar 
     * @param {type} id
     * @returns {undefined}
     */
    function renderTopBarAuthorize(id) {
        var div = document.getElementById(id);
        if(!div) {
            return;
        }
        
        var userXmlStr = getCurrentUser();
        if(userXmlStr == null) {
            //anonymous
            getUserAnonymousTopBarXsl().then(function(xsl) {
                var userXml = xmlService.parseStringToXml(userXmlStr);
                var html = xmlService.transformToDocument(userXml, xsl);
                
                xmlService.removeAllChild(div);
                div.appendChild(html);
                
                bindTopBarBtnAction();
            })
        } else {
            //authorize
            getUserAuthorizeTopBarXsl().then(function(xsl) {
                var userXml = xmlService.parseStringToXml(userXmlStr);
                var html = xmlService.transformToDocument(userXml, xsl);
                
                xmlService.removeAllChild(div);
                div.appendChild(html);
                
                bindTopBarBtnAction();
            })
        }
    }
    
    function bindTopBarBtnAction() {
        var btnId = {
            login: 'btnLogin',
            register: 'btnRegister',
            viewCart: 'btnViewCart',
            logout: 'btnLogout',
        };
        var btnLogin = document.getElementById(btnId.login);
        var btnRegister = document.getElementById(btnId.register);
        var btnViewCart = document.getElementById(btnId.viewCart);
        var btnLogout = document.getElementById(btnId.logout);
        
        if(btnLogin) {
            btnLogin.addEventListener('click', function() {
                window.location.href = app.url.page.login;
            });
        }
        
        if(btnRegister) {
            btnRegister.addEventListener('click', function() {
                window.location.href = app.url.page.register;
            });
        }
        
        if(btnViewCart) {
            btnViewCart.addEventListener('click', function() {
                window.location.href = app.url.page.cart;
            });
        }
        
        if(btnLogout) {
            btnLogout.addEventListener('click', logout);
        }
    }
    
    this.renderTopBarAuthorize = renderTopBarAuthorize;
    this.login = login;
    this.checkAdmin = checkAdmin;
    this.checkAuthorized = checkAuthorized;
}