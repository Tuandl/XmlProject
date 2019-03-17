<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : product.cart.table.xsl
    Created on : March 17, 2019, 3:07 PM
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
                    <th>Unit Price VND</th>
                    <th>Quantity</th>
                    <th>Amount VND</th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="products/product">
                    <tr>
                        <td>
                            <xsl:number value="position()" format="1"/>
                        </td>
                        <td><xsl:value-of select="name" disable-output-escaping="yes"/></td>
                        <td>
                            <xsl:value-of select="format-number(price, '###,###')"/>
                        </td>
                        <td>
                            <button class="button button-outter-red button-small" name="btnDecrease" data-id="{id}">-</button>
                            <xsl:value-of select="quantity"/>
                            <button class="button button-outter-green button-small" name="btnIncrease" data-id="{id}">+</button>
                        </td>
                        <td>
                            <xsl:value-of select="format-number(amount, '###,###')"/>
                        </td>
                    </tr>
                </xsl:for-each>
                <tr>
                    <td colspan="3"></td>
                    <td>
                        Total VND:
                    </td>
                    <td>
                        <xsl:value-of select="format-number(products/totalAmount, '###,###')"/>
                    </td>
                </tr>
            </tbody>
        </table>
    </xsl:template>

</xsl:stylesheet>
