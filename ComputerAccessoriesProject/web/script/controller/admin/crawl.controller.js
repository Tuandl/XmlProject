/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var CrawlController = function(app, ajaxService, xmlService) {
    //declaration 
    var viewIds = {
        button: {
            crawlCategory: 'btnCrawlCategory',
            syncAll: 'btnSyncAll',
            back: 'btnBack',
        },
        div: {
            categoryRaw: 'divCategoryRaws',
            totalProduct: 'divTotalProduct',
            notSync: 'divTotalNotSync',
        }, 
    };
    var viewNames = {
        button: {
            crawlProduct: 'crawlProduct',
            deleteCategoryRaw: 'deleteCategoryRaw',
            syncProduct: 'syncProduct',
        },
        div: {
            categorySelectBox: 'categoryDiv',
        },
    }
    
    var categoriesXml = null;
    var categories = null;
    var categoryRawsXml = null;
    var model = null;
    var categoriesSelectXsl = null;
    var categoryRawsTableXsl = null;
    var categorySelectBox = null;
    
    var pendingUpdateCount = 0;
    
    //running flow
    var btnCrawlCategory = document.getElementById(viewIds.button.crawlCategory);
    btnCrawlCategory.addEventListener('click', onBtnCrawlCategoyClicked);
    
    var btnSyncAll = document.getElementById(viewIds.button.syncAll);
    btnSyncAll.addEventListener('click', onBtnSyncAllClicked);
    
    var btnBack = document.getElementById(viewIds.button.back);
    btnBack.addEventListener('click', onBtnBackClicked);
    
    getCategoriesSelectXsl();
    getCategoryRawsTableXsl();
    initData();
    
    //handler methods
    function initData() {
        //get category
        ajaxService.getXml(app.url.api.category).then(function(responseXml) {
            categoriesXml = responseXml;
            categories = xmlService.unmarshalling(categoriesXml);
            getCategoryRaw();
        }).catch(function(error) {
            console.log(error);
        });
        
    }
    
    function getCategoryRaw() {
        //get CategoryRaws
        ajaxService.getXml(app.url.api.categoryRaw).then(function(responseXml) {
            categoryRawsXml = responseXml;
            console.log(xmlService.parseXmlToString(categoryRawsXml));
            model = xmlService.unmarshalling(categoryRawsXml);
            //format data
            model.categoryRaws.categoryRaw.forEach(function(categoryRaw) {
                categoryRaw.newProductRawQuantity = '...';
                categoryRaw.editedProductRawQuantity = '...';
                categoryRaw.totalProductRaw = '...';
            });
            
            updateTable();
            model.categoryRaws.categoryRaw.forEach(function(categoryRaw) {
                getNewProductQuantity(categoryRaw.id);
                getEditedProductQuantity(categoryRaw.id);
                getTotalProductQuantity(categoryRaw.id);
                pendingUpdateCount+= 3;
            });
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function updateTable() {
        pendingUpdateCount = Math.max(pendingUpdateCount - 1, 0);
        if(pendingUpdateCount > 0) {
            return;
        }
        
        categoryRawsXml = xmlService.marshallingAuto(model);
        transformCategoryRaw();
        transformCategory();
        bindEventToTableButtons();
        calculateTotalProduct();
        calculateTotalNotSync();
    }
    
    function transformCategoryRaw() {
        var div = document.getElementById(viewIds.div.categoryRaw);
        xmlService.removeAllChild(div);

        var table = xmlService.transformToDocument(categoryRawsXml, categoryRawsTableXsl);
        div.appendChild(table);
    }
    
    function transformCategory() {
        if(!categorySelectBox) {
            //not existed -> create one
            categorySelectBox = xmlService.transformToDocument(categoriesXml, categoriesSelectXsl);
        }
        var divs = document.getElementsByName(viewNames.div.categorySelectBox);
        divs.forEach(function(div) {
            xmlService.removeAllChild(div);
            div.appendChild(categorySelectBox.cloneNode(true));
        });
    }
    
    function bindEventToTableButtons() {
        var btnCrawlProducts = document.getElementsByName(viewNames.button.crawlProduct);
        btnCrawlProducts.forEach(function(btn) {
           btn.addEventListener('click', onBtnCrawlProductClicked);
        });
        
        var btnDeleteCategoryRaw = document.getElementsByName(viewNames.button.deleteCategoryRaw);
        btnDeleteCategoryRaw.forEach(function(btn) {
            btn.addEventListener('click', onBtnDeleteCategoryClicked); 
        });
        
        var btnSyncProducts = document.getElementsByName(viewNames.button.syncProduct);
        btnSyncProducts.forEach(function(btn) {
            btn.addEventListener('click', onBtnSyncProductClicked);
        });
    }
    
    function onBtnCrawlProductClicked(event) {
        var btn = event.srcElement;
        var categoryRawId = app.getIdInDataId(btn);
        var categoryId = getCategoryId(categoryRawId);
        
        if(!categoryId) {
            var divSelects = document.getElementsByName(viewNames.div.categorySelectBox)
            divSelects.forEach(function(div) {
                var divId = app.getIdInDataId(div);
                if(divId == categoryRawId) {
                    var select = div.childNodes[0];
                    categoryId = select.options[select.selectedIndex].value;
                }
            });
        }
        
        
        if(categoryId) {
            var data = {
                categoryRawId: categoryRawId,
                categoryId: categoryId,
            };
            btn.disabled = true;
            ajaxService.get(app.url.api.crawlProduct, data).then(function(response) {
                //return number of change product
                updateCategory(categoryRawId, categoryId);

                if(parseInt(response)) {
                    getNewProductQuantity(categoryRawId);
                    getEditedProductQuantity(categoryRawId);
                    getTotalProductQuantity(categoryRawId);
                    
//                    pendingUpdateCount += 3;
                    alert('Changed Product quantity: ' + response)
                } else {
                    console.log('not found new product', response);
                    alert('No changed products');
                }
            }).catch(function(error) {
                console.log(error);
            }).finally(function() {
                btn.disabled = false;
            });
        } else {
            console.log('not found category id');
        }
    }
    
    function onBtnDeleteCategoryClicked(event) {
        var btn = event.srcElement;
        var categoryRawId = app.getIdInDataId(btn);
        
        var data = {
            id: categoryRawId,
        };
        btn.disabled = true;
        ajaxService.delete(app.url.api.categoryRaw, data).then(function(response) {
            btn.disabled = false;
            for(var i = 0; i < model.categoryRaws.categoryRaw.length; i++) {
                var categoryRaw = model.categoryRaws.categoryRaw[i];
                if(categoryRaw.id == categoryRawId) {
                    model.categoryRaws.categoryRaw.splice(i, 1);
                    updateTable();
                    alert('Delete successfull');
                    break;
                }
            }
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function onBtnSyncProductClicked(event) {
        var btn = event.srcElement;
        var categoryRawId = app.getIdInDataId(btn);
        
        var data = {
            categoryRawId: categoryRawId,
        };
        btn.disabled = true;
        ajaxService.post(app.url.api.product, data).then(function(response) {
            getNewProductQuantity(categoryRawId);
            getEditedProductQuantity(categoryRawId);
            btn.disabled = false;
            alert('Sync success!');
        }).catch(function(error) {
            console.log(error);
            alert('Sync not success!');
        });
    }
    
    function getCategoriesSelectXsl() {
        ajaxService.get(app.url.xsl.categorySelect).then(function(response) {
            categoriesSelectXsl = response;
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function getCategoryRawsTableXsl() {
        ajaxService.get(app.url.xsl.categoryRawTable).then(function(response) {
            categoryRawsTableXsl = response;
        }).catch(function(error) {
            console.log(error);
        });
    }
    
    function onBtnCrawlCategoyClicked(event) {
        var btn = event.srcElement;
        btn.disabled = true;
        
        ajaxService.get(app.url.api.crawlCategory).then(function(response) {
            btn.disabled = false;
            if(parseInt(response) > 0) {
                getCategoryRaw(); 
                alert('New category quantity: ' + response);
            }
            else {
                alert('No Change');
            }
        }).catch(function(error) {
            console.log(error);
        })
    }
    
    function getCategoryId(categoryRawId) {
        var expression = '//categoryRaw[id="' + categoryRawId + '"]/category/id';
        var categoryId = xmlService.queryStringXpath(categoryRawsXml, expression);
        return categoryId;
    }
    
    function getNewProductQuantity(categoryRawId) {
        if(categoryRawId) {
            var data = {
                categoryRawId: categoryRawId, 
            };
            ajaxService.get(app.url.api.categoryRawCountNewProduct, data).then(function(response) {
                for(var i = 0; i < model.categoryRaws.categoryRaw.length; i++) {
                    var categoryRaw = model.categoryRaws.categoryRaw[i];
                    if(categoryRaw.id == categoryRawId) {
                        categoryRaw.newProductRawQuantity = response;
                        break;
                   }
                }
                updateTable();
            }).catch(function(error) {
                console.log(error);
            });
        }
    }
    
    function getEditedProductQuantity(categoryRawId) {
        if(categoryRawId) {
            var data = {
                categoryRawId: categoryRawId, 
            };
            ajaxService.get(app.url.api.categoryRawCountEditedProduct, data).then(function(response) {
                for(var i = 0; i < model.categoryRaws.categoryRaw.length; i++) {
                    var categoryRaw = model.categoryRaws.categoryRaw[i];
                    if(categoryRaw.id == categoryRawId) {
                        categoryRaw.editedProductRawQuantity = response;
                        break;
                   }
                }
                updateTable();
            }).catch(function(error) {
                console.log(error);
            });
        }
    }
    
    function getTotalProductQuantity(categoryRawId) {
        if(categoryRawId) {
            var data = {
                categoryRawId: categoryRawId, 
            };
            ajaxService.get(app.url.api.categoryRawCountTotalProduct, data).then(function(response) {
                for(var i = 0; i < model.categoryRaws.categoryRaw.length; i++) {
                    var categoryRaw = model.categoryRaws.categoryRaw[i];
                    if(categoryRaw.id == categoryRawId) {
                        categoryRaw.totalProductRaw = response;
                        break;
                   }
                }
                updateTable();
            }).catch(function(error) {
                console.log(error);
            });
        }
    }
    
    function updateCategory(categoryRawId, categoryId) {
        var category = categories.categories.category.find(function(category) {
            return category.id == categoryId;
        });
        
        if(!category) return;
        
        model.categoryRaws.categoryRaw.forEach(function(categoryRaw) {
            if(categoryRaw.id == categoryRawId) {
                categoryRaw.category = category;
                categoryRaw.isCrawl = 'true';
            }
        });
        
        updateTable();
        console.log(xmlService.parseXmlToString(categoryRawsXml));

    }
    
    function calculateTotalProduct() {
        var total = 0;
        model.categoryRaws.categoryRaw.forEach(function(categoryRaw) {
           total += parseInt(categoryRaw.totalProductRaw); 
        });
        
        var div = document.getElementById(viewIds.div.totalProduct);
        if(isNaN(total)) {
            div.innerHTML = 'Loading...';
        } else {
            div.innerHTML = 'Total Products: ' + total;
        }
    }
    
    function onBtnSyncAllClicked(event) {
        var btn = event.srcElement;
        
        var count = 0;
        var countSuccess = 0;
        var countError = 0;
        
        btn.disabled = true;
        model.categoryRaws.categoryRaw.forEach(function(categoryRaw) {
            count++;
            var data = {
                categoryRawId: categoryRaw.id,
            };
            ajaxService.post(app.url.api.product, data).then(function(response) {
                countSuccess++;
            }).catch(function(error) {
                countError++;
                console.log(error);
            }).finally(function() {
                count--;
                if(count == 0) {
                    getCategoryRaw();
                    btn.disabled = false;
                    alert('Sync All Completed with ' + countSuccess + ' success, ' + countError + ' error.');
                }
            });
        });
    }
    
    function calculateTotalNotSync() {
        var total = 0;
        model.categoryRaws.categoryRaw.forEach(function(categoryRaw) {
            total += parseInt(categoryRaw.newProductRawQuantity);
            total += parseInt(categoryRaw.editedProductRawQuantity);
        });
        
        var div = document.getElementById(viewIds.div.notSync);
        if(isNaN(total)) {
            div.innerHTML = '';
        } else {
            div.innerHTML = 'Total product changes: ' + total;
        }
    }
    
    function onBtnBackClicked() {
        window.location.href = app.url.page.adminDashBoard;
    }
}