<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : product.detail.xsl
    Created on : March 13, 2019, 7:55 AM
    Author     : admin
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/product">
        <div class="product-detail-container">
            <div class="product-detail-left">
                <div class="product-detail-img-container">
                    <img class="product-detail-img" src="/ComputerAccessoriesProject/Product/Image?name={imageUrl}" alt="name"/>
                </div>
            </div>
            <div class="product-detail-right">
                <div class="product-detail-title">
                    <h3>
                        <xsl:value-of select="name" disable-output-escaping="yes"/>
                    </h3>
                </div>
                <div class="product-detail-price">
                    Price: <xsl:value-of select="format-number(price, '###,###')"/> VND
                </div>
                <div class="product-detail-button">
                    <button class="button button-outter-green" id="btnAddCart" data-id="{id}">Add to Cart</button>
                </div>
            </div>
            <hr/>
            <div class="product-detail-description">
                <h3>Description</h3>
                <xsl:value-of select="description" disable-output-escaping="yes"/>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>
