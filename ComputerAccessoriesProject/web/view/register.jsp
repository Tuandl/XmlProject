<%-- 
    Document   : register
    Created on : Mar 2, 2019, 11:30:02 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        
        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
        
        <link rel="stylesheet" href="${contextPath}/css/common.css"/>
        
        <script src="${contextPath}/script/common.enum.js"></script>
        
        <script src="${contextPath}/script/app.js"></script>
        <script src="${contextPath}/script/service/ajax.service.js"></script>
        <script src="${contextPath}/script/controller/register.controller.js"></script>
    </head>
    <body onload="new RegisterController(new App(), new AjaxService())">
        <h1>Register</h1>
        <form name="formLogin">
            <input type="text" id="txtUsername" name="txtUsername" value="" />
            <span id="error-username" class="error hidden">Please input username.</span>
            <br/>
            <input type="password" id="txtPassword" name="txtPassword" value="" /> 
            <span id="error-password" class="error hidden">Please input password.</span>
            <br/>
            <input type="text" id="txtFullName" value=""/> 
            <span id="error-full-name" class="error hidden">Please input fullname.</span>
            <br/>
            
            <input type="button" id="btnRegister" value="register" name="btnRegister" />
            <input type="button" id="btnLogin" value="login" name="btnLogin"/>
            <span id="error-register" class="error hidden">Cannot register.</span>
            <span id="success-register" class="success hidden">Register Successfully.</span>
        </form>
    </body>
</html>
