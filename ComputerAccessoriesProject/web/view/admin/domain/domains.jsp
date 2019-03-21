<%-- 
    Document   : domains
    Created on : Mar 21, 2019, 12:46:54 AM
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
        <script src="${contextPath}/script/controller/admin/domain/domains.controller.js"></script>
    </head>
    <body onload="new DomainsController()">
        <div class="container-small">
            <h1>Domains</h1>
            <button class="button button-outter-green" id="btnAddDomain">Add</button>
            <div id="divDomainTable"></div>
        </div>
    </body>
</html>
