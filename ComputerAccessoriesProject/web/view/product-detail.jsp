<%-- 
    Document   : product
    Created on : Mar 13, 2019, 8:01:31 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Computer Accessories</title>
        
        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
        
        <link rel="stylesheet" href="${contextPath}/css/common.css"/>
        
        <script src="${contextPath}/script/common.enum.js"></script>
        
        <script src="${contextPath}/script/app.js"></script>
        <script src="${contextPath}/script/controller/product-detail.controller.js"></script>
    </head>
    <body onload="new ProductDetailController()">
        <h1>Computer Accessories</h1>
        <div id="navbar">
            
        </div>
        <div id="divProduct">
            
        </div>
    </body>
</html>
