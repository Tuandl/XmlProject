<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : categories.select.xsl
    Created on : March 8, 2019, 8:52 AM
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
        <select>
            <xsl:for-each select="categories/category">
                <option value="{id}">
                    <xsl:value-of select="name"/>
                </option>
            </xsl:for-each>
        </select>
    </xsl:template>

</xsl:stylesheet>
