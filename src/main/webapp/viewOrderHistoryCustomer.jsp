<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/css/cusorderhistory.css">
        <title>Your Order History</title>
    </head>
    <body>
        <h2>Your Order History</h2>

        <div class="order-section">
            <div class="order-column">
                <h3>Received Orders</h3>
                <c:forEach var="order" items="${received}">
                    <a href="CustomerOrderDetailController?orderId=${order.orderId}" class="order-link">
                        <div class="order-container">
                            <div class="order-title">Order ID: <strong>${order.orderId}</strong></div>
                            <div class="order-info">Order Date: <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" /></div>
                            <div class="order-info">Status: <strong>${order.statusName}</strong></div>
                        </div>
                    </a>
                </c:forEach>
                <p>${msgReceived}</p>
            </div>

            <div class="order-column">
                <h3>Pending Orders</h3>
                <c:forEach var="order" items="${pending}">
                    <a href="CustomerOrderDetailController?orderId=${order.orderId}" class="order-link">
                        <div class="order-container">
                            <div class="order-title">Order ID: <strong>${order.orderId}</strong></div>
                            <div class="order-info">Order Date: <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" /></div>
                            <div class="order-info">Status: <strong>${order.statusName}</strong></div>
                        </div>
                    </a>
                </c:forEach>
                <p>${msgPending}</p>

            </div>

            <div class="order-column">
                <h3>Delivered Orders</h3>
                <c:forEach var="order" items="${delivered}">
                    <a href="CustomerOrderDetailController?orderId=${order.orderId}" class="order-link">
                        <div class="order-container">
                            <div class="order-title">Order ID: <strong>${order.orderId}</strong></div>
                            <div class="order-info">Order Date: <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" /></div>
                            <div class="order-info">Status: <strong>${order.statusName}</strong></div>
                            <div>
                                <a href="">Feedback</a>
                            </div>
                        </div>
                    </a>
                </c:forEach>
                <p>${msgDelivered}</p>

            </div>

            <div class="order-column">
                <h3>Cancelled Orders</h3>
                <c:forEach var="order" items="${cancelled}">
                    <a href="CustomerOrderDetailController?orderId=${order.orderId}" class="order-link">
                        <div class="order-container">
                            <div class="order-title">Order ID: <strong>${order.orderId}</strong></div>
                            <div class="order-info">Order Date: <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" /></div>
                            <div class="order-info">Status: <strong>${order.statusName}</strong></div>
                        </div>
                    </a>
                </c:forEach>
                <p>${msgCancelled}</p>
            </div>
        </div>
    </body>


</html>
