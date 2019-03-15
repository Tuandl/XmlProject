<%-- 
    Document   : Index
    Created on : Mar 11, 2019, 8:55:14 AM
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
        <script src="${contextPath}/script/controller/index.controller.js"></script>
    </head>
    <body onload="new IndexController()">
        <div>
            <h1>Computer Accessories</h1>
            <div class="hidden" id="divLogedIn">
                <div>Welcome <span id="userFullName"></span></div>
                <button id="btnLogout">Logout</button>
            </div>
            <div class="hidden" id="divAnnonymous">
                <button id="btnLogin">Login</button>
                <button id="btnRegister">Register</button>
            </div>
        </div>
        <div id="divNavbar">
            
        </div>
        <div id="divTopProducts">
            
        </div>
        <div id="divTopCategories">
            
        </div>
    </body>
</html>
