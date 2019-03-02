/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var StateService = function() {
    var stateConst = {
        user: 'USER_DATA',
    };
    
    var getCurrentUser = function(){
        var user = localStorage.getItem(stateConst.user);
        return user;
    }
    
    var setCurrentUser = function(user) {
        localStorage.setItem(stateConst.user, user);
    }
    
    this.getCurrentUser = getCurrentUser;
    this.setCurrentUser = setCurrentUser;
}