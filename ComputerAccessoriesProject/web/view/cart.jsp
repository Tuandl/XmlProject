<%-- 
    Document   : cart
    Created on : Mar 17, 2019, 3:51:01 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Computer Accessories</title>
        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
        <link rel="stylesheet" href="${contextPath}/css/common.css"/>
        <script src="${contextPath}/script/common.enum.js"></script>
        <script src="${contextPath}/script/app.js"></script>
        <script src="${contextPath}/script/controller/cart.controller.js"></script>
    </head>
    <body onload="new CartController()">
        <div id="divTopBar"></div>
        <div id="divNavbar"></div>
        
        <div class="container">
            <h1>Cart</h1>
            <div id="divCartTable"></div>
            <form class="container-small">
                <h2>Shipping Information</h2>
                <div class="form-group">
                    <label class="label-control">Phone No *:</label>
                    <input type="text" id="txtPhoneNo" />
                    <span id="error-require-phoneno" class="error hidden">Please input phone number.</span>
                </div>
                <div class="form-group">
                    <label class="label-control">Address *: </label>
                    <input type="text" id="txtAddress" />
                    <span id="error-require-address" class="error hidden">Please input address.</span>
                </div>
            </form>
            <button class="button button-outter-green right" id="btnOrder">Order</button>
        </div>
        
    </body>
</html>
