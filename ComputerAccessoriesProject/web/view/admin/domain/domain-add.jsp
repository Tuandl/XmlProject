<%-- 
    Document   : domain-add
    Created on : Mar 21, 2019, 1:07:25 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Computer Accessories</title>
        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
        <link rel="stylesheet" href="${contextPath}/css/common.css"/>
        <script src="${contextPath}/script/common.enum.js"></script>
        <script src="${contextPath}/script/app.js"></script>
        <script src="${contextPath}/script/controller/admin/domain/domain-add.controller.js"></script>
    </head>
    <body onload="new DomainAddController()">
        <div id="divTopBar"></div>
        <div id="divNavbar"></div>
        <div>
            <div class="container-small">
                <h1>Add Domain</h1>
                <button class="button button-outter-green" id="btnInsert">Insert</button>
                <button class="button button-outter-blue" id="btnBack">Back</button>
            </div>
            <div class="container-small">
                <label>Domain Name *</label>
                <input type="text" id="txtDomainName"/>
                <span class="error hidden" id="errorRequireDomainName">
                    <p>Domain Name is required</p>
                </span>
                <label>Init Url *</label>
                <input type="text" id="txtInitUrl"/>
                <span class="error hidden" id="errorInitUrl">
                    <p>Init Url is required</p>
                </span>
                <hr/>
                <h3>XPath Config</h3>
                <label>Next Page Xpath *</label>
                <input type="text" id="txtPagingXPath"/>
                <span class="error hidden" id="errorNextPageXpath">
                    <p>Next Page XPath is required</p>
                </span>
                <span class="error hidden" id="errorDetailConfig">
                    <p>All XPath Queries are required</p>
                </span>
            </div>
            <div class="container-medium">
                <div id="detailTable">
                    
                </div>
            </div>
        </div>
    </body>
</html>
