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
            <div class="top-category-container">
                <h3>
                    <xsl:value-of select="name"/>
                </h3>
                <div class="top-category-product-container">
                    <xsl:for-each select="product">
                        <div class="product-item">
                            <a class="product-link" href="/view/product-detail.jsp?id={id}">
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
            </div>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
