<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : products.square.xsl
    Created on : March 12, 2019, 8:41 AM
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
        <div class="product-container">
            <xsl:for-each select="products/product">
                <div class="product-item">
                    <a class="product-link" href="/ComputerAccessoriesProject/view/product-detail.jsp?id={id}">
                        <div class="product-img-wrapper"> 
                            <img src="{imageUrl}" alt="{name}" class="product-img"/>
                        </div>
                        <div class="product-price">
                            <xsl:value-of select="price"/>
                        </div>
                        <div class="product-title">
                            <xsl:value-of select="name" disable-output-escaping="yes"/>
                        </div>
                    </a>
                </div>
            </xsl:for-each>
        </div>
    </xsl:template>

</xsl:stylesheet>
