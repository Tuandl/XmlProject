<%-- 
    Document   : category.add
    Created on : Mar 7, 2019, 7:42:59 AM
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
        <script src="${contextPath}/script/controller/admin/category/category.add.controller.js"></script>
    </head>
    <body onload="new CategoryAddController()">
        <h1>Insert category</h1>
        <form>
            <input type="text" id="txtName"/>
            <span id="error-name-required" class="error hidden">Please input name</span>
            <br/>
            <button type="button" id="btnInsert">Add</button>
            <button type="button" id="btnBack">Back</button>
            <span id="error-server" class="error hidden"></span>
        </form>
    </body>
</html>
