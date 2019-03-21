<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : domain.table.xsl
    Created on : March 20, 2019, 11:36 PM
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
            <thread>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Url</th>
                    <th>Action</th>
                </tr>
            </thread>
            <tbody>
                <xsl:for-each select="domains/domain">
                    <tr>
                        <td><xsl:number value="position()" format="1"/></td>
                        <td>
                            <xsl:value-of select="domainName"/>
                        </td>
                        <td>
                            <a href="{initUrl}">
                                <xsl:value-of select="initUrl"/>
                            </a>
                        </td>
                        <td>
                            <button class="button button-outter-green" name="btnEditDomain" data-id="{id}">Edit</button>
                            <button class="button button-outter-red" name="btnDeleteDomain" data-id="{id}">Delete</button>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>

</xsl:stylesheet>
