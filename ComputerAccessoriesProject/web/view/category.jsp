<%-- 
    Document   : category
    Created on : Mar 13, 2019, 12:27:46 AM
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
        <script src="${contextPath}/script/controller/category.controller.js"></script>
    </head>
    <body onload="new CategoryController()">
        <div id="divTopBar"></div>
        <div id="navbar">
            
        </div>
        <h1 class="text-capitalize" id="categoryName"></h1>
        <div id="divProducts">
            
        </div>
        <div id="pagination" class="center">
            
        </div>
    </body>
</html>
