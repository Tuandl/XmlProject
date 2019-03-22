<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : navbar.admin.xsl
    Created on : March 22, 2019, 4:07 AM
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
                <li><a href="/ComputerAccessoriesProject/view/admin/dashboard.jsp">Setting</a></li>
                <li><a href="/ComputerAccessoriesProject/view/admin/category/categories.jsp">Category</a></li>
                <li><a href="/ComputerAccessoriesProject/view/admin/domain/domains.jsp">Domain</a></li>
                <li><a href="/ComputerAccessoriesProject/view/admin/crawl.jsp">Crawler</a></li>
            </ul>
        </div>
    </xsl:template>

</xsl:stylesheet>
