<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="manageStaff.jsp" />
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
                            <th>Customer</th>
                            <th>Order Date</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${list}">
                            <tr>
                                <td>${order.orderId}</td>
                                <td>${order.username}</td>
                                <td>${order.orderDate}</td>
                                <td>${order.statusName}</td>
                                <td>
                                    <a href="StaffOrderDetailController?orderId=${order.orderId}" class="btn btn-primary btn-sm">
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
