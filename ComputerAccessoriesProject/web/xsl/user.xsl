<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : user.xsl
    Created on : March 3, 2019, 6:43 PM
    Author     : admin
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="user">
        <span style="color: red"><xsl:value-of select="fullname"/></span>
        <span style="color: yellow"><xsl:value-of select="username"/></span>
    </xsl:template>

</xsl:stylesheet>
