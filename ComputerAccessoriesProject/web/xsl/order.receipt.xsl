<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : order.receipt.xsl
    Created on : March 19, 2019, 11:42 AM
    Author     : admin
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <!--<xsl:param name="basePath"/>-->
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4"
                                       margin-left="1.5cm"
                                       margin-right="1.5cm"
                                       margin-bottom="2cm"
                                       margin-top="2cm"
                                       page-width="21cm"
                                       page-height="29.7cm">
                    <fo:region-body margin-top="4cm"/>
                    <fo:region-before extent="4cm"/>
                    <fo:region-after extent="1.5cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:table table-layout="fixed" width="100%">
                        <fo:table-column column-width="50%" />
                        <fo:table-column column-width="50%"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block>
<!--                                        <fo:external-graphic src="{concat($basePath, 'WEB-INF\img\logo.png')}"
                                                             content-width="5cm"
                                                             content-height="5cm"
                                        />-->
                                        <fo:external-graphic src="img\logo.png"
                                                             content-width="2cm"
                                                             content-height="2cm"
                                        />
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">
                                        <fo:block font-size="10pt" line-height="14pt">
                                            Computer Accessories
                                        </fo:block>
                                        <fo:block>
                                            Ngày: <xsl:value-of select="order/createdAtString"/>
                                        </fo:block>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>

                    <fo:block line-height="35pt" font-size="20pt" text-align="center"
                        font-family="Arial">
                        Hóa đơn bán hàng
                    </fo:block>
                </fo:static-content>

                <fo:static-content flow-name="xsl-region-after">
                    <fo:block line-height="14pt" font-size="10pt" text-align="end">
                        Trang <fo:page-number/>
                    </fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body" font-family="Arial">
                    <fo:block>
                        Mã đơn hàng: 
                        <fo:inline>
                            <xsl:value-of select="order/orderCode"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block>
                        Khách hàng: 
                        <fo:inline>
                            <xsl:value-of select="order/customerName"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block>
                        Địa chỉ:
                        <fo:inline>
                            <xsl:value-of select="order/address"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block>
                        Số điện thoại
                        <fo:inline>
                            <xsl:value-of select="order/phoneNo"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block margin-top="1cm">
                        <fo:table table-layout="fixed" width="105%">
                            <fo:table-column column-width="6%"/>
                            <fo:table-column column-width="50%"/>
                            <fo:table-column column-width="14%"/>
                            <fo:table-column column-width="11%"/>
                            <fo:table-column column-width="14%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border="solid 0.1mm black">
                                        <fo:block text-align="center" 
                                                  font-weight="bold"
                                                  line-height="16pt">
                                            STT
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="solid 0.1mm black">
                                        <fo:block text-align="center" 
                                                  font-weight="bold"
                                                  line-height="16pt">
                                            Tên sản phẩm
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="solid 0.1mm black">
                                        <fo:block text-align="center" 
                                                  font-weight="bold"
                                                  line-height="16pt">
                                            Giá
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="solid 0.1mm black">
                                        <fo:block text-align="center" 
                                                  font-weight="bold"
                                                  line-height="16pt">
                                            Số lượng
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="solid 0.1mm black">
                                        <fo:block text-align="center" 
                                                  font-weight="bold"
                                                  line-height="16pt">
                                            Thành tiền
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                
                                <xsl:for-each select="order/orderDetail">
                                    <fo:table-row font-size="9pt">
                                        <fo:table-cell border="solid 0.1mm black">
                                            <fo:block text-align="center"
                                                      padding="2mm">
                                                <xsl:number value="position()" format="1"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="solid 0.1mm black">
                                            <fo:block text-align="center"
                                                      padding="2mm">
                                                <xsl:value-of select="productName" disable-output-escaping="yes"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="solid 0.1mm black">
                                            <fo:block text-align="center"
                                                      padding="2mm">
                                                <xsl:value-of select="format-number(price, '###,###')"/> VND
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="solid 0.1mm black">
                                            <fo:block text-align="center"
                                                      padding="2mm">
                                                <xsl:value-of select="quantity"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="solid 0.1mm black">
                                            <fo:block text-align="center"
                                                      padding="2mm">
                                                <xsl:value-of select="format-number(amount, '###,###')"/> VND
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>    
                                </xsl:for-each>
                                
                                <fo:table-row font-size="9pt">
                                    <fo:table-cell>
                                        <fo:block>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="center"
                                                  padding="2mm">
                                            Tổng tiền:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="center"
                                                  padding="2mm">
                                            <xsl:value-of select="format-number(order/amount, '###,###')"/> VND
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>
