<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : paging.xsl
    Created on : March 13, 2019, 7:16 AM
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
        <div class="pagination">
            <xsl:for-each select="root/page">
                <xsl:choose>
                    <xsl:when test="currentPage = 'true'">
                        <a href="#" class="active">
                            <xsl:value-of select="name" disable-output-escaping="yes"/>
                        </a>
                    </xsl:when>
                    <xsl:otherwise>
                        <a href="{url}">
                            <xsl:value-of select="name" disable-output-escaping="yes"/>
                        </a>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
        </div>
        
    </xsl:template>

</xsl:stylesheet>
