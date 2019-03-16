/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Html Utils
 * @returns {HtmlService}
 */
var HtmlService = function() {
    
    /**
     * Remove Html Entities
     * @param {type} html
     * @returns {.document@call;createElement.value|txt.value}
     */
    function decodeHtml(html) {
        var txt = document.createElement('textarea');
        txt.innerHTML = html;
        return txt.value;
    }
    
    this.decodeHtml = decodeHtml;
}