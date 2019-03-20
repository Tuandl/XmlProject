/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('XmlService');
require('AjaxService');
require('StateService');
require('HtmlService');

var CartService = function(divCartTableId) {
    var divCartTableId = divCartTableId;
    var xmlService = new XmlService();
    var ajaxService = new AjaxService();
    var stateService = new StateService();
    var htmlService = new HtmlService();
    
    function getCartXmlStr() {
        var result = stateService.getItem(stateService.stateConst.cartXml);
        return result;
    }
    
    function saveCartXmlStr(cartXmlStr) {
        stateService.setItem(stateService.stateConst.cartXml, cartXmlStr);
    }
    
    function clearCartXmlStr() {
        stateService.removeItem(stateService.stateConst.cartXml);
    }
    
    function addToCart(product) {
        var cartXmlStr = getCartXmlStr();
        var cart = null;
        if(cartXmlStr == null) {
            cart = {
                products: {
                    product: [],
                },
            };
        } else {
            var cartXml = xmlService.parseStringToXml(cartXmlStr);
            cart = xmlService.unmarshalling(cartXml);
        }
        
        if(!Array.isArray(cart.products.product)) {
            var tmp = cart.products.product;
            cart.products.product = [
                tmp,
            ];
        }
        var existedProduct = cart.products.product.find(function(item){
            return item.id == product.id;
        });
        
        if(existedProduct) {
            existedProduct.quantity = parseInt(existedProduct.quantity) || 1;
            existedProduct.quantity ++;
        } else {
            product.quantity = 1;
            cart.products.product.push(product);
        }
        
        calculateCart(cart);
        
        //save
        cartXml = xmlService.marshallingAuto(cart);
        cartXmlStr = xmlService.parseXmlToString(cartXml);
        saveCartXmlStr(cartXmlStr);
    }
    
    function removeItem(id){
        var cartXmlStr = getCartXmlStr();
        if(cartXmlStr == null) {
            return;
        }
        
        var cartXml = xmlService.parseStringToXml(cartXmlStr);
        var cart = xmlService.unmarshalling(cartXml);
        
        for(var i = 0; i < cart.products.product.length; i++){
            var product = cart.products.product[i];
            if(product.id == id) {
                cart.products.product =  cart.products.product.splice(i, 1);
                break;
            }
        }
        
        calculateCart(cart);
        cartXml = xmlService.marshallingAuto(cart);
        cartXmlStr = xmlService.parseXmlToString(cartXml);
        saveCartXmlStr(cartXmlStr);
    }
    
    function increaseItem(id) {
        var cartXmlStr = getCartXmlStr();
        if(cartXmlStr == null) {
            return;
        }
        
        var cartXml = xmlService.parseStringToXml(cartXmlStr);
        var cart = xmlService.unmarshalling(cartXml);
        
        if(!Array.isArray(cart.products.product)) {
            var tmp = cart.products.product;
            cart.products.product = [tmp,];
        }
        
        cart.products.product.forEach(function(product) {
            if(product.id == id) {
                product.quantity = parseInt(product.quantity) + 1;
            }
        });
        
        calculateCart(cart);
        cartXml = xmlService.marshallingAuto(cart);
        cartXmlStr = xmlService.parseXmlToString(cartXml);
        saveCartXmlStr(cartXmlStr);
    }
    
    function decreaseItem(id) {
        var cartXmlStr = getCartXmlStr();
        if(cartXmlStr == null) {
            return;
        }
        
        var cartXml = xmlService.parseStringToXml(cartXmlStr);
        var cart = xmlService.unmarshalling(cartXml);
        
        if(!Array.isArray(cart.products.product)) {
            var tmp = cart.products.product;
            cart.products.product = [tmp,];
        }
        
        for(var i = 0; i < cart.products.product.length; i++) {
            var product = cart.products.product[i];
            if(product.id == id) {
                product.quantity = parseInt(product.quantity);
                product.quantity --;
                if(product.quantity <= 0) {
                    cart.products.product.splice(i, 1);
                }
                break;
            }
        }
        
        calculateCart(cart);
        cartXml = xmlService.marshallingAuto(cart);
        cartXmlStr = xmlService.parseXmlToString(cartXml);
        saveCartXmlStr(cartXmlStr);
    }
    
    function sendOrder(phoneNo, address) {
        var data = {
            order: {
                phoneNo: phoneNo,
                address: address,
            }
        };
        
        var userStr = stateService.getCurrentUser();
        var user = xmlService.parseToXmlThenUnmarshalling(userStr);
        data.order.customerId = user.user.id;
        
        var cartStr = getCartXmlStr();
        var cart = xmlService.parseToXmlThenUnmarshalling(cartStr);
        data.order.amount = cart.products.totalAmount;
        data.order.orderDetail = [];
        if(!Array.isArray(cart.products.product)) {
            var tmp = cart.products.product;
            cart.products.product = [tmp];
        }
        
        cart.products.product.forEach(function(product) {
            var orderDetailDto = {
                productId: product.id,
                price: product.price,
                quantity: product.quantity,
                amount: product.amount,
                productName: product.name,
            };
            data.order.orderDetail.push(orderDetailDto);
        });
        
        var dataStr = xmlService.marshallingThenParseToString(data);
        console.log(dataStr);
        dataStr = htmlService.decodeHtml(dataStr);
        console.log(dataStr);
        dataStr = htmlService.decodeHtml(dataStr);
        console.log(dataStr);
        var apiData = {
            data: dataStr,
        };
//        console.log(apiData.data);
        ajaxService.post(app.url.api.order, apiData).then(function(response) {
            console.log(response);
//            alert('Send Order Successful');
            clearCartXmlStr();
            getReceipt(response).then(function() {
                window.location.replace(app.url.page.home);
            });
        }).catch(function(error) {
            alert('Send Order Failed');
            console.log(error);
        });
    }
    
    function getReceipt(orderCode) {
        if(orderCode == null) return;
        return new Promise(function(resolve, reject) {
            var data = {
                orderCode: orderCode,
            };
            var fileName = 'Invoice ' + orderCode;
            ajaxService.getPdf(app.url.api.orderReceipt, data, fileName).then(function(response) {
                resolve(response);
            }).catch(function(error) {
                console.log(error);
                reject(error);
            })
        });
        
    }
    
    function getCartXsl() {
        return new Promise(function(resolve, reject) {
            var xsl = stateService.getItem(stateService.stateConst.cartTableXsl);
            if(xsl == null) {
                ajaxService.get(app.url.xsl.cart).then(function(response) {
                    stateService.setItem(stateService.stateConst.cartTableXsl, response);
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
    
    function renderCartTable(id) {
        if(id == undefined || id == null) {
            id = divCartTableId;
        }
        
        var div = document.getElementById(id);
        if(!div) {
            return;
        }
        this.cartTableDivId = id;
        
        var cartXmlStr = getCartXmlStr();
        if(cartXmlStr == null) {
            cartXmlStr = {
                products: {
                    product: [],
                },
            };
        }
        
        var cartXml = xmlService.parseStringToXml(cartXmlStr);
        
        getCartXsl().then(function(xsl) {
            var html = xmlService.transformToDocument(cartXml, xsl);
            xmlService.removeAllChild(div);
            div.appendChild(html);
            
            bindBtnAction();
        });
    }
    
    function bindBtnAction() {
        var btnName  = {
            increase: 'btnIncrease',
            decrease: 'btnDecrease',
        };
        
        var btnIncrease = document.getElementsByName(btnName.increase);
        btnIncrease.forEach(function(btn) {
            btn.addEventListener('click', onBtnIncreaseClicked);
        });
        
        var btnDecrease = document.getElementsByName(btnName.decrease);
        btnDecrease.forEach(function(btn) {
            btn.addEventListener('click', onBtnDecreaseClicked);
        });
    }
    
    function onBtnIncreaseClicked(event) {
        var btn = event.srcElement;
        var id = app.getIdInDataId(btn);
        
        increaseItem(id);
        renderCartTable();
    }
    
    function onBtnDecreaseClicked(event) {
        var btn = event.srcElement;
        var id = app.getIdInDataId(btn);
        
        decreaseItem(id);
        renderCartTable();
    }
    
    function calculateCart(cartObj) {
        var totalAmount = 0;
        cartObj.products.product.forEach(function(product) {
            var unitPrice = parseInt(product.price);
            var quantity = parseInt(product.quantity);
            product.amount = unitPrice * quantity;
            totalAmount += product.amount;
        });
        
        cartObj.products.totalAmount = totalAmount;
    }
    
    this.addToCart = addToCart;
    this.removeItem = removeItem;
    this.increaseItem = increaseItem;
    this.decreaseItem = decreaseItem;
    this.sendOrder = sendOrder;
    this.renderCartTable = renderCartTable;
}