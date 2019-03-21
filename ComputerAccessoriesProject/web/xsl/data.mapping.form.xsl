<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : data.mapping.form.xsl
    Created on : March 20, 2019, 11:49 PM
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
        <table class="table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Class Name</th>
                    <th>Field Name</th>
                    <th>XPath Query *</th>
                    <th>Result Type</th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="domain/mapping">
                    <tr>
                        <td><xsl:number value="position()" format="1"/></td>
                        <td>
                            <xsl:value-of select="data/name"/>
                        </td>
                        <td><xsl:value-of select="data/className"/></td>
                        <td><xsl:value-of select="data/propertyName"/></td>
                        <td>
                            <input type="text" name="txtXPathQuery" value="{xPathQuery}" data-id="{id}" data-data-id="{dataId}"/>
                        </td>
                        <td width="14%">
                            <select name="slResultType" data-id="{id}" data-data-id="{dataId}">
                                <xsl:choose>
                                    <xsl:when test="isNodeResult = 'true'">
                                        <option value="false">String</option>
                                        <option value="true" selected="true">Node</option>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <option value="false" selected="true">String</option>
                                        <option value="true">Node</option>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </select>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>
    
    <xsl:template match="text()"></xsl:template>

</xsl:stylesheet>
