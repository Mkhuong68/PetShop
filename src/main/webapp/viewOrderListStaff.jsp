<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="manageStaff.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>View Order List</title>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" href="assets/css/stafforderlist.css"> 
    </head>
    <body>
        <div class="main-content">
            <div class="content-header">
                <h1 class="content-title">View Order List</h1>
            </div>
            <div class="form-container">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Account Id</th>
                            <th>Order Date</th>
                            <th>Status</th>
                            <th>Delivery Address</th>
                            <th>Payment Method</th>
                            <th>Shipping Fee</th>
                            <th>Total Price</th>
                            <th>Voucher Code</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${list}">
                            <tr>
                                <td>${order.orderId}</td>
                                <td>${order.accountId}</td>
                                <td><fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
                                <td>${order.statusName}</td>
                                <td>${order.deliveryAddress}</td>
                                <td>${order.paymentMethod}</td>
                                <td><fmt:formatNumber value="${order.shippingFee}" pattern="#,###" /> VND</td>
                                <td><fmt:formatNumber value="${order.finalPrice}" pattern="#,###" /> VND</td>
                                <td>${order.voucherCode}</td>
                                <td>
                                    <a href="StaffOrderDetailController?orderId=${order.orderId}&&accountId=${order.accountId}" class="btn btn-primary btn-sm">
                                        <i class="bi bi-eye"></i> View Detail
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <p class="text-danger text-center mt-3">${msg}</p>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

