<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : search.topbar.xsl
    Created on : March 22, 2019, 1:38 AM
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
        <div style="margin:3px 550px">
            <input type="text" id="txtSearchValue_searchService" placeholder="Search..." style="width:80%;margin-right: 3px;height:35px;"/>
            <button id="btnSearch_searchService" class="button">Search</button>
        </div>
    </xsl:template>

</xsl:stylesheet>
