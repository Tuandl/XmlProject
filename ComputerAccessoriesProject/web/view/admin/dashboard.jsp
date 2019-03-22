<%-- 
    Document   : dashboard
    Created on : Feb 27, 2019, 9:29:06 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard</title>
        
        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
        
        <link rel="stylesheet" href="${contextPath}/css/common.css"/>
        
        <script src="${contextPath}/script/common.enum.js"></script>
        
        <script src="${contextPath}/script/app.js"></script>
        <script src="${contextPath}/script/controller/admin/dashboard.controller.js"></script>
    </head>
    <body onload="new DashBoardController()">
        <div id="divTopBar"></div>
        <div id="divNavbar"></div>
        <div class="container-small">
            <h1>Setting</h1>
            <label>Commission (VND)</label>
            <input type="text" id="txtCommission"/>
            <button class="button button-outter-green" id="btnCommission">Save</button>
        </div>
    </body>
</html>
