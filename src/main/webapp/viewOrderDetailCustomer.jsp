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
                <p><strong>#</strong> ${orderInfo.orderId}</p>
                <p><strong>Name: </strong> ${name}</p>
                <p><strong>Date: </strong> <fmt:formatDate value="${orderInfo.orderDate}" pattern="dd-MM-yyyy" /></p>
                <p><strong>Status: </strong> ${orderInfo.statusName}</p>
                <p><strong>Payment Method: </strong> ${orderInfo.paymentMethod}</p>
                <p><strong>Phone Number: </strong>${phoneNumber}</p>
                <p><strong>Shipping Address: </strong> ${orderInfo.deliveryAddress}</p>
                <p><strong>Note: </strong> ${orderInfo.orderNote}</p>
            </div>

            <div class="order-summary">
                <h2>Order Details</h2>
                <table border="1" cellspacing="0" cellpadding="10">
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Option</th>
                            <th>Quantity</th>
                            <th>Purchase Price</th>
                        </tr>
                    </thead>
                    <c:if test="${not empty orderDetails}">
                        <tbody>
                            <c:set var="totalAmount" value="0"/>
                            <c:forEach var="order" items="${orderDetails}">
                                <tr>
                                    <td>${order.productName}</td>
                                    <td>${order.optionName}</td>
                                    <td>${order.quantity}</td>
                                    <td>
                                        <fmt:formatNumber value="${order.purchasePrice}" pattern="#,##0" /> VND
                                    </td>
                                </tr>
                                <c:set var="totalAmount" value="${totalAmount + order.finalPrice}"/>
                            </c:forEach>
                        </tbody>
                    </c:if>
                </table>

                <p style="margin-top: 10px"><strong>Shipping Fee: </strong><fmt:formatNumber value="${orderInfo.shippingFee}" pattern="#,##0" /> VND</p>
                <p style="margin-top: 10px"><strong>Apply Voucher: </strong>${orderInfo.voucherCode}</p>
                <p style="margin-top: 10px"><strong>Total Amount: </strong> 
                    <fmt:formatNumber value="${orderInfo.finalPrice}" pattern="#,##0" /> VND</p>

                <c:if test="${orderInfo.statusName == 'Received'}">
                    <form action="CustomerCancelOrderController" method="post">
                        <input type="hidden" name="action" value="cancelOrder">
                        <input type="hidden" name="orderId" value="${orderInfo.orderId}">
                        <input type="hidden" name="statusName" value="${orderInfo.statusName}">
                        <input type="submit" class="checkout-btn" value="Cancel Order" onclick="return confirm('Are you sure you want to cancel this order?')">
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

