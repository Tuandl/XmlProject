<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : categories.top.xsl
    Created on : March 12, 2019, 10:49 PM
    Author     : admin
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <xsl:for-each select="categories/category">
            <hr/>
            <div class="top-category-container">
                <h3 class="text-capitalize">
                    <xsl:value-of select="name"/>
                </h3>
                <div class="product-container">
                    <xsl:for-each select="product">
                        <div class="product-item">
                            <a class="product-link" href="/ComputerAccessoriesProject/view/product-detail.jsp?id={id}">
                                <div class="product-img-wrapper"> 
                                    <img src="/ComputerAccessoriesProject/Product/Image?name={imageUrl}" alt="{name}" class="product-img"/>
                                </div>
                                <div class="product-price">
                                    Price: <xsl:value-of select="format-number(price, '###,###')"/> VND
                                </div>
                                <div class="product-title">
                                    <xsl:value-of select="name" disable-output-escaping="yes"/>
                                </div>
                            </a>
                        </div>
                    </xsl:for-each>
                </div>
            </div>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
