<%-- 
    Document   : editDeliveryOrder.jsp
    Created on : Feb 23, 2025, 5:05:39 PM
    Author     : tvhun
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="managedelivery.jsp" %>

<style>
    /* ---------------------- */
    /* Thêm CSS để giao diện đẹp hơn */
    /* ---------------------- */

    /* Container chính */
    .dashboard {
        margin: 20px;
        padding: 20px;
        background-color: #fff;
        border-radius: 8px;
        border: 1px solid #ddd;
    }

    /* Tiêu đề trang */
    .dashboard-title {
        font-size: 24px;
        font-weight: 600;
        margin-bottom: 20px;
        color: #333;
    }

    /* Bảng hiển thị sản phẩm */
    .table-container {
        overflow-x: auto; /* Cho phép cuộn ngang nếu bảng quá rộng */
        margin-bottom: 20px;
    }

    .table-container table {
        width: 100%;
        border-collapse: collapse;
        border: 1px solid #ddd;
    }

    .table-container thead {
        background-color: #f5f5f5;
    }

    .table-container th,
    .table-container td {
        padding: 12px 15px;
        border: 1px solid #ddd;
        text-align: left;
    }

    .table-container tbody tr:nth-child(even) {
        background-color: #fafafa;
    }

    .table-container tbody tr:hover {
        background-color: #f1f1f1;
    }

    /* Hàng hiển thị tổng tiền */
    .total-label {
        text-align: right;
        font-weight: bold;
        background-color: #f9f9f9;
    }

    .total-amount {
        font-weight: bold;
        background-color: #f9f9f9;
    }

    /* Khối thông tin khách hàng */
    .customer-info {
        background-color: #fafafa;
        padding: 15px;
        border: 1px solid #ddd;
        margin-bottom: 20px;
        border-radius: 5px;
    }

    .customer-info h3 {
        margin-top: 0;
        margin-bottom: 10px;
    }

    /* Khu vực nút hành động */
    .actions {
        margin-top: 20px;
    }

    /* Nút Back */
    .btn-back {
        display: inline-block;
        padding: 10px 15px;
        background-color: #007BFF;
        color: #fff;
        text-decoration: none;
        border-radius: 4px;
        transition: background-color 0.3s;
    }

    .btn-back:hover {
        background-color: #0056b3;
    }

    /* Dark mode */
    .dark-mode {
        background-color: #333;
        color: #ddd;
    }

    .dark-mode .dashboard {
        background-color: #444;
        border: 1px solid #555;
    }

    .dark-mode .table-container table,
    .dark-mode .table-container th,
    .dark-mode .table-container td {
        border-color: #666;
    }

    .dark-mode .table-container thead {
        background-color: #555;
    }

    .dark-mode .table-container tbody tr:nth-child(even) {
        background-color: #555;
    }

    .dark-mode .table-container tbody tr:hover {
        background-color: #666;
    }

    .dark-mode .customer-info {
        background-color: #555;
        border: 1px solid #666;
    }

    .dark-mode .btn-back {
        background-color: #888;
        color: #fff;
    }
</style>

<!-- MAIN CONTENT -->
<div class="dashboard">
    <div class="dashboard-title">Edit Delivery Order</div>

    <!-- Display the list of products in the order -->
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Product Name</th>
                    <th>Quantity</th>
                    <th>Total Price</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${orderDetails}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${item.productName}</td>
                        <td>${item.quantity}</td>
                        <td>${item.totalPrice}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3" class="total-label">Total Amount:</td>
                    <td class="total-amount">${orderTotal}</td>
                </tr>
            </tbody>
        </table>
    </div>

    <!-- Display customer information -->
    <div class="customer-info">
        <h3>Customer Information</h3>
        <p><strong>Full Name:</strong> ${customerName}</p>
        <p><strong>Phone:</strong> ${customerPhone}</p>
        <p><strong>Address:</strong> ${customerAddress}</p>
    </div>

    <!-- Back button -->
    <div class="actions">
        <a href="deliveryList" class="btn-back">Back to Order List</a>
    </div>
</div>

<script>
    // Dark mode toggle (if available)
    const darkModeSwitch = document.getElementById('darkModeSwitch');
    if (darkModeSwitch) {
        darkModeSwitch.addEventListener('change', () => {
            document.body.classList.toggle('dark-mode', darkModeSwitch.checked);
        });
    }
</script>
</body>
</html>
