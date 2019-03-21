/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require('XmlService');
require('StateService');

var ProductStateService = function() {
    var xmlService = new XmlService();
    var stateService = new StateService();
    
    function saveProducts(data) {
        var productXmlStr = xmlService.marshallingThenParseToString(data);
        stateService.setItem(stateService.stateConst.productsLite, productXmlStr);
    }
    
    function getProducts() {
        var productsXmlStr = stateService.getItem(stateService.stateConst.productsLite);
        if(productsXmlStr == null) {
            return {
                products: {
                    product: [],
                },
            };
        }
        var products = xmlService.parseToXmlThenUnmarshalling(productsXmlStr);
        return products;
    }
    
    /**
     * join and remove all duplicate id
     * @param {type} data1
     * @param {type} data2
     * @returns {ProductStateService.mergeProducts.newData}
     */
    function mergeProducts(data1, data2) {
        var newData = data1.products.product.concat(data2.products.product);
        newData.sort(function(a, b) {
            return a.id - b.id;
        });
        
        for(var index = 1; index < newData.length; index++) {
            while(index < newData.length && newData[index].id == newData[index - 1].id) {
                newData.splice(index, 1);
            }
        }
        
        return {
            products: {
                product: newData,
            },
        };
    }
    
    function trimProducts(data) {
        var products = [];
        data.products.product.forEach(function(product) {
            var trimed = {
                id: product.id,
                name: product.name,
                imageUrl: product.imageUrl,
                price: product.price,
            };
            products.push(trimed);
        });
        var result = {
            products: {
                product: products,
            },
        };
        return result;
    }
    
    /**
     * Trim product then sync with local storage
     * @param {type} data
     * @returns {undefined}
     */
    function addProducts(data) {
        if(data.products.product == null) {
            return;
        }
        var trimed = trimProducts(data);
        var oldData = getProducts();
        var newData = mergeProducts(oldData, trimed);
        saveProducts(newData);
    }
    
    function addProductsXml(xml) {
        var data = xmlService.unmarshalling(xml);
        addProducts(data);
    }
    
    function addProductsXmlStr(str) {
        var data = xmlService.parseToXmlThenUnmarshalling(str);
        addProducts(data);
    }
    
    this.addProducts = addProducts;
    this.addProductsXml = addProductsXml;
    this.addProductsXmlStr = addProductsXmlStr;
    this.getProducts = getProducts;
}