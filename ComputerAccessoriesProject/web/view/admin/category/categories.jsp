<%-- 
    Document   : categories
    Created on : Mar 7, 2019, 7:05:29 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
        
        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
        
        <link rel="stylesheet" href="${contextPath}/css/common.css"/>
        
        <script src="${contextPath}/script/common.enum.js"></script>
        
        <script src="${contextPath}/script/app.js"></script>
        <script src="${contextPath}/script/controller/admin/category/categories.controller.js"></script>
    </head>
    <body onload="new CategoriesController()">
        <div id="divTopBar"></div>
        <div id="divNavbar"></div>
        <div class="container-small">
            <h1>Category</h1>

            <button class="button button-outter-blue" id="btnInsert">Add</button> 
            <button class="button button-outter-blue" id="btnBack">Back</button>

            <div id="listCategories"> 

            </div>    
        </div>
        
    </body>
</html>
