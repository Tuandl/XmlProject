<%-- 
    Document   : crawl
    Created on : Mar 7, 2019, 1:07:51 PM
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
        <script src="${contextPath}/script/controller/admin/crawl.controller.js"></script>
    </head>
    <body onload="new CrawlController()">
        <h1>Crawl page</h1>
        <button class="button button-outter-green" id="btnCrawlCategory">Crawl Category</button>
        <button class="button button-outter-green" id="btnSyncAll">Sync All</button>
        <button class="button button-outter-green" id="btnBack">Back</button>
        <br/>
        <div id="divTotalProduct"></div>
        <div id="divTotalNotSync"></div>
        <br/>
        <div id="divCategoryRaws">
            
        </div>
    </body>
</html>
