<%-- 
    Document   : viewOrderListStaff
    Created on : Feb 28, 2025, 9:53:51 AM
    Author     : NgocNNCE181950
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/css/stafforderlist.css">

        <title>JSP Page</title>
    </head>
    <body>
        <h1>View Order List</h1>
        <table border="1">
            <tr>
                <th>Order ID</th>
                <th>Customer</th>
                <th>Order Date</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <c:forEach var="order" items="${list}">
                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.username}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.statusName}</td>
                    <td><a href="StaffOrderDetailController?orderId=${order.orderId}">View Detail</a></td>
                </tr>
            </c:forEach>
        </table>
        <p>${msg}</p>
    </body>
</html>
