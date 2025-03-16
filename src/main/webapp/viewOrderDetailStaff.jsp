<%-- 
    Document   : viewOrderDetailStaff
    Created on : Mar 1, 2025, 4:03:43 PM
    Author     : NgocNNCE181950
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/css/stafforderdetail.css">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Order Detail</h1>
        <form action="StaffOrderDetailController" method="post">
            <input type="hidden" value="${data.orderId}" name="orderId" />

            <table border="1">
                <tr>
                    <th>Account ID</th>
                    <td>${data.accountId}</td>
                </tr>
                <tr>
                    <th>Username</th>
                    <td>${data.username}</td>
                </tr>

                <tr>
                    <th>Order Date</th>
                    <td>${data.orderDate}</td>
                </tr>
                <tr>
                    <th>Status Name</th>
                    <td>
                        <select name="statusName">
                            <c:forEach var="c" items="${statusList}">
                                <option value="${c.statusName}" ${c.statusName == data.statusName ? 'selected' : ''}>
                                    ${c.statusName}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Delivery Address</th>
                    <td>${data.deliveryAddress}</td>
                </tr>
                <tr>
                    <th>Note</th>
                    <td>${data.orderNote}</td>
                </tr>
                <tr>
                    <th>Voucher</th>
                    <td>${data.voucherId}</td>
                </tr>
                <tr>
                    <th>Payment Method</th>
                    <td>${data.paymentMethod}</td>
                </tr>
                <tr>
                    <th>Shipping Fee</th>
                    <td><fmt:formatNumber value="${data.shippingFee}" pattern="#,##0" /> VND</td>
                </tr>
            </table>
            <input type="submit" value="Change">
        </form>
    </body>

</html>
