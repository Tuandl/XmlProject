<%-- 
    Document   : login
    Created on : Feb 27, 2019, 9:28:23 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        
        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
        
        <link rel="stylesheet" href="${contextPath}/css/common.css"/>
        
        <script src="${contextPath}/script/common.enum.js"></script>
        
        <script src="${contextPath}/script/app.js"></script>
        <script src="${contextPath}/script/controller/login.controller.js"></script>
    </head>
    <body onload="new LoginController()">
        <div class="container-small">
            <h1>Login</h1>
            <form name="formLogin">
                <label>Username:</label>
                <input type="text" id="txtUsername" name="txtUsername" value="" /> <br/>
                
                <label>Password</label>
                <input type="password" id="txtPassword" name="txtPassword" value="" /> <br/>
                <span id="error-login" class="error hidden">Invalid Username or Password.</span><br/>
                
                <input class="button button-outter-green" type="button" id="btnLogin" value="Login" name="btnLogin" />
                <input class="button button-outter-green" type="button" id="btnRegister" value="Register" name="btnRegister" />
            </form>
        </div>
    </body>
</html>
