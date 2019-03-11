<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : categories.navbar.xsl
    Created on : March 11, 2019, 11:53 PM
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
        <div class="navbar">
            <ul>
                <xsl:for-each select="categories/category">
                    <xsl:if test="position() &lt; 15">
                        <li>
                            <a href="/view/category?categoryId={id}" class="text-capitalize">
                                <!--<xsl:value-of select="position()"/>-->
                                <xsl:value-of select="name"/>
                            </a>
                        </li>                   
                    </xsl:if>
                </xsl:for-each>
                
                <xsl:if test="count(categories/category) &gt;= 15">
                    <li class="dropdown">
                        <a href="javascript:void(0)" class="dropbtn">Xem thêm</a>
                        <div class="dropdown-content">
                            <xsl:for-each select="categories/category">
                                <xsl:if test="position() &gt;= 15">
                                    <a href="/view/category?categoryId={id}" class="text-capitalize">
                                        <xsl:value-of select="name"/>
                                    </a>
                                </xsl:if>
                            </xsl:for-each>
                        </div>
                    </li>
                </xsl:if>
            </ul>
        </div>
    </xsl:template>

</xsl:stylesheet>
