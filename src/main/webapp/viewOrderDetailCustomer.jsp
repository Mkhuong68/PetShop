<%-- 
    Document   : viewOrderDetailCustomer
    Created on : Mar 12, 2025, 10:20:25 AM
    Author     : NgocNNCE181950
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Order</title>
        <link rel="stylesheet" type="text/css" href="assets/css/cusorderdetail.css">
    </head>
    <body>
        <div class="container">
            <h1>Order Confirmation</h1>

            <div class="order-info">
                <h2>Order Information</h2>
                <p><strong>Order ID:</strong> ${order.orderId}</p>
                <p><strong>Customer:</strong> ${order.username}</p>
                <p><strong>Status:</strong> ${order.statusName}</p>
                <p><strong>Shipping Address:</strong> ${order.deliveryAddress}</p>
                <p><strong>Payment Method:</strong> ${order.paymentMethod}</p>
                <p><strong>Shipping Fee:</strong> 
                    <fmt:formatNumber value="${order.shippingFee}" pattern="#,##0" /> VND</p>
            </div>

            <div class="order-summary">
                <h2>Order Details</h2>
                <table border="1" cellspacing="0" cellpadding="10">
                    <thead>
                        <tr>
                            <th>Product ID</th>
                            <th>Quantity</th>
                            <th>Purchase Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="totalAmount" value="0"/>
                        <c:forEach var="detail" items="${orderDetails}">
                            <tr>
                                <td>${detail.productId}</td>
                                <td>${detail.quantity}</td>
                                <td>
                                    <fmt:formatNumber value="${detail.purchasePrice}" pattern="#,##0" /> VND
                                </td>
                            </tr>
                            <c:set var="totalAmount" value="${totalAmount + detail.finalPrice}"/>
                        </c:forEach>
                    </tbody>
                </table>

                <p style="margin-top:3%"><strong>Total Amount:</strong> 
                    <fmt:formatNumber value="${totalAmount}" pattern="#,##0" /> VND</p>

                <c:if test="${order.statusName == 'Received'}">
                    <form action="CustomerCancelOrderController" method="post">
                        <input type="hidden" name="action" value="cancelOrder">
                        <input type="hidden" name="orderId" value="${order.orderId}">
                        <input type="hidden" name="statusName" value="${order.statusName}">
                        <input type="submit" class="checkout-btn" value="Cancel Order">
                    </form>
                </c:if>
            </div>
            <c:if test="${not empty msg}">
                <p>${msg}</p>
            </c:if>
        </div>
        <form action="CustomerOrderHistoryController">
            <button class="back-btn" type="submit">Back</button>
        </form>
    </body>
</html>

