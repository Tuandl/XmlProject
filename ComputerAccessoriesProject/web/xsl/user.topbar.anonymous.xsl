<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : user.topbar.anonymous.xsl
    Created on : March 17, 2019, 1:16 AM
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
        <h1 class="title">
            <a href="/ComputerAccessoriesProject">Computer Accessories</a>
        </h1>
        <div class="top-bar-container">
            <button class="button button-outter-blue" id="btnLogin">Login</button>
            <button class="button button-outter-blue" id="btnRegister">Register</button>
        </div>
    </xsl:template>
    
    <xsl:template match="text()"></xsl:template>

</xsl:stylesheet>
