<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : categories.table.xsl
    Created on : March 7, 2019, 7:08 AM
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
        <table class="table" border="1">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="categories/category">
                    <tr>
                        <td><xsl:number value="position()" format="1"/></td>
                        <td><xsl:value-of select="name"/></td>
                        <td>
                            <button class="button button-outter-green" name="btnEdit" data-id="{id}">Edit</button>
                            <button class="button button-outter-red" name="btnDelete" data-id="{id}">Delete</button>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>

</xsl:stylesheet>
