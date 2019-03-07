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
        <script src="${contextPath}/script/service/state.service.js"></script>
        <script src="${contextPath}/script/service/ajax.service.js"></script>
        <script src="${contextPath}/script/service/xml.service.js"></script>
        <script src="${contextPath}/script/controller/admin/dashboard.controller.js"></script>
    </head>
    <body onload="new DashBoardController(new App(), new StateService(), new AjaxService(), new XmlService())">
        <h1>DashBoard</h1>
        <h3>Welcome <span id="adminName"></span></h3>
        
        <button id="btnInsert">Insert</button>
        <ul>
            <li><a href="${contextPath}/view/admin/categories.jsp">Config category</a></li>
        </ul>
        
        <button id="btnTriggerCrawl">Crawl</button>
        
        <div id="testSection"></div>
    </body>
</html>
