<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : category-raw.table.xsl
    Created on : March 8, 2019, 8:26 AM
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
        <table>
            <thread>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Domain</th>
                    <th>Category</th>
                    <th>New Product</th>
                    <th>Edited Product</th>
                    <th>Total Product</th>
                    <th>Crawl Product</th>
                    <th>Sync Product</th>
                    <th>Delete</th>
                </tr>
            </thread>
            <tbody>
                <xsl:for-each select="categoryRaws/categoryRaw">
                    <tr>
                        <td><xsl:number value="position()" format="1"/></td>
                        <td>
                            <a href="{url}">
                                <xsl:value-of select="name" disable-output-escaping="yes"/>
                            </a>
                        </td>
                        <td>
                            <xsl:apply-templates/>
                        </td>
                        <td>
                            <xsl:choose>
                                <xsl:when test="isCrawl='true'">
                                    <xsl:value-of select="category/name"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <div name="categoryDiv" data-id="{id}"></div>
                                </xsl:otherwise>
                            </xsl:choose>
                        </td>
                        <td><xsl:value-of select="newProductRawQuantity"/></td>
                        <td><xsl:value-of select="editedProductRawQuantity"/></td>
                        <td><xsl:value-of select="totalProductRaw"/></td>
                        <td>
                            <button name="crawlProduct" data-id="{id}">Crawl</button>
                        </td>
                        <td>
                            <button name="syncProduct" data-id="{id}">Sync</button>
                        </td>
                        <td>
                            <button name="deleteCategoryRaw" data-id="{id}">Delete</button>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>
    
    <xsl:template match="domain">
        <a href="initUrl">
            <xsl:value-of select="domainName"/>
        </a>
    </xsl:template>
    
    <xsl:template match="text()"/>
    
</xsl:stylesheet>
