<%-- 
    Document   : search
    Created on : Mar 22, 2019, 1:08:56 AM
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
        <script src="${contextPath}/script/controller/search.controller.js"></script>
    </head>
    <body onload="new SearchController()">
        <div id="divTopBar"></div>
        <div id="divSearch"></div>
        <div id="navbar"></div>
        <div class="container">
            <div class="center">
                <button class="button button-outter-green" id="btnSearchOffline">Search Offline</button>
                <button class="button button-outter-green" id="btnSearchOnline">Search Online</button>
                <button class="button button-outter-green" id="btnSearchSmart">Search Smart</button>
            </div>
            <h1 class="center">Search result for "<span id="divSearchValue"></span>"</h1>
            <h2 class="center">Using search technique: <span id="divSeachTech"></span></h2>
        </div>
        <div id="divProducts"></div>
        <div id="pagination" class="center"></div>
    </body>
</html>
