<%-- 
    Document   : deliveryOrderList
    Created on : Feb 16, 2025, 10:55:45 PM
    Author     : tvhun
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="managedelivery.jsp" %>

<style>
    /* ---------------------- */
    /* CSS nâng cao cho trang danh sách đơn hàng giao */
    /* ---------------------- */
    
    /* Container chính */
    .dashboard {
        margin: 30px;
        padding: 25px;
        background-color: #fff;
        border-radius: 12px;
        border: 1px solid #e0e0e0;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
        max-width: 1200px;
        margin-left: auto;
        margin-right: auto;
    }

    /* Tiêu đề trang */
    .dashboard-title {
        font-size: 28px;
        font-weight: 700;
        margin-bottom: 25px;
        color: #333;
        border-bottom: 2px solid #f0f0f0;
        padding-bottom: 15px;
        position: relative;
    }
    
    .dashboard-title:after {
        content: '';
        position: absolute;
        bottom: -2px;
        left: 0;
        width: 80px;
        height: 2px;
        background-color: #007BFF;
    }

    /* Thông báo */
    .alert {
        padding: 16px;
        margin-bottom: 25px;
        border: none;
        border-radius: 8px;
        font-weight: 500;
        display: flex;
        align-items: center;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    }
    
    .alert:before {
        margin-right: 12px;
        font-family: "Font Awesome 5 Free";
        font-weight: 900;
        font-size: 18px;
    }
    
    .alert-success {
        color: #0c6b32;
        background-color: #e3f9ec;
    }
    
    .alert-success:before {
        content: '\f058'; /* Checkmark icon */
        color: #10b759;
    }
    
    .alert-danger {
        color: #82231d;
        background-color: #fee7e6;
    }
    
    .alert-danger:before {
        content: '\f057'; /* X icon */
        color: #f25c54;
    }

    /* Bảng hiển thị đơn hàng */
    .table-container {
        overflow-x: auto;
        margin-bottom: 25px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.05);
    }

    .table-container table {
        width: 100%;
        border-collapse: separate;
        border-spacing: 0;
        border: 1px solid #e0e0e0;
        border-radius: 8px;
    }

    .table-container thead {
        background-color: #f7f9fc;
    }
    
    .table-container th {
        font-weight: 600;
        text-transform: uppercase;
        font-size: 13px;
        letter-spacing: 0.5px;
        color: #5c6c7c;
        padding: 16px;
        border-bottom: 2px solid #e0e0e0;
    }

    .table-container td {
        padding: 16px;
        border-bottom: 1px solid #e0e0e0;
        color: #4a5568;
        font-size: 15px;
    }

    .table-container tbody tr:last-child td {
        border-bottom: none;
    }

    .table-container tbody tr:nth-child(even) {
        background-color: #fcfcfc;
    }

    .table-container tbody tr:hover {
        background-color: #f0f7ff;
        transition: background-color 0.3s ease;
    }
    
    /* Tin nhắn trống dữ liệu */
    .text-center {
        text-align: center;
        padding: 30px;
        color: #718096;
        font-style: italic;
    }

    /* Nút hành động */
    .btn-edit, .btn-delete {
        display: inline-block;
        padding: 8px 16px;
        margin-right: 8px;
        border-radius: 6px;
        font-weight: 600;
        font-size: 14px;
        text-decoration: none;
        cursor: pointer;
        transition: all 0.3s ease;
    }

    .btn-edit {
        background-color: #4299e1;
        color: white;
        box-shadow: 0 2px 4px rgba(66, 153, 225, 0.3);
    }

    .btn-edit:hover {
        background-color: #3182ce;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(66, 153, 225, 0.4);
    }
    
    .btn-edit:active {
        transform: translateY(0);
    }

    .btn-delete {
        background-color: #f56565;
        color: white;
        box-shadow: 0 2px 4px rgba(245, 101, 101, 0.3);
    }

    .btn-delete:hover {
        background-color: #e53e3e;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(245, 101, 101, 0.4);
    }
    
    .btn-delete:active {
        transform: translateY(0);
    }
    
    /* Status badge */
    .status-badge {
        display: inline-block;
        padding: 6px 12px;
        border-radius: 20px;
        font-size: 13px;
        font-weight: 600;
    }
    
    .status-pending {
        background-color: #f0f4f8;
        color: #4a5568;
        border: 1px solid #cbd5e0;
    }
    
    .status-confirmed {
        background-color: #ebf8ff;
        color: #2b6cb0;
        border: 1px solid #bee3f8;
    }
    
    .status-packed {
        background-color: #fffaf0;
        color: #c05621;
        border: 1px solid #feebc8;
    }
    
    .status-delivering {
        background-color: #e6fffa;
        color: #2c7a7b;
        border: 1px solid #b2f5ea;
    }
    
    .status-delivered {
        background-color: #f0fff4;
        color: #276749;
        border: 1px solid #c6f6d5;
    }
    
    .status-cancelled {
        background-color: #fff5f5;
        color: #c53030;
        border: 1px solid #fed7d7;
    }

    /* Dark mode styles */
    .dark-mode {
        background-color: #1a202c;
        color: #e2e8f0;
    }

    .dark-mode .dashboard {
        background-color: #2d3748;
        border: 1px solid #4a5568;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    }
    
    .dark-mode .dashboard-title {
        color: #e2e8f0;
        border-bottom-color: #4a5568;
    }
    
    .dark-mode .dashboard-title:after {
        background-color: #4299e1;
    }

    .dark-mode .table-container table,
    .dark-mode .table-container th,
    .dark-mode .table-container td {
        border-color: #4a5568;
    }

    .dark-mode .table-container thead {
        background-color: #2d3748;
    }
    
    .dark-mode .table-container th {
        color: #a0aec0;
    }
    
    .dark-mode .table-container td {
        color: #e2e8f0;
    }

    .dark-mode .table-container tbody tr:nth-child(even) {
        background-color: #323c4e;
    }

    .dark-mode .table-container tbody tr:hover {
        background-color: #3f495c;
    }
    
    .dark-mode .text-center {
        color: #a0aec0;
    }
    
    .dark-mode .btn-edit {
        background-color: #4299e1;
    }
    
    .dark-mode .btn-edit:hover {
        background-color: #3182ce;
    }
    
    .dark-mode .btn-delete {
        background-color: #f56565;
    }
    
    .dark-mode .btn-delete:hover {
        background-color: #e53e3e;
    }
    
    .dark-mode .status-pending {
        background-color: #2d3748;
        color: #e2e8f0;
        border-color: #4a5568;
    }
    
    .dark-mode .status-confirmed {
        background-color: #2c5282;
        color: #bee3f8;
        border-color: #2b6cb0;
    }
    
    .dark-mode .status-packed {
        background-color: #744210;
        color: #feebc8;
        border-color: #c05621;
    }
    
    .dark-mode .status-delivering {
        background-color: #234e52;
        color: #b2f5ea;
        border-color: #2c7a7b;
    }
    
    .dark-mode .status-delivered {
        background-color: #22543d;
        color: #c6f6d5;
        border-color: #276749;
    }
    
    .dark-mode .status-cancelled {
        background-color: #742a2a;
        color: #fed7d7;
        border-color: #c53030;
    }
    
    .dark-mode .alert-success {
        background-color: #1c4532;
        color: #9ae6b4;
    }
    
    .dark-mode .alert-success:before {
        color: #48bb78;
    }
    
    .dark-mode .alert-danger {
        background-color: #742a2a;
        color: #feb2b2;
    }
    
    .dark-mode .alert-danger:before {
        color: #f56565;
    }
    
    /* Responsive adjustments */
    @media (max-width: 768px) {
        .dashboard {
            margin: 15px;
            padding: 15px;
        }
        
        .table-container td, 
        .table-container th {
            padding: 12px 10px;
        }
        
        .btn-edit, .btn-delete {
            padding: 6px 12px;
            font-size: 13px;
        }
    }
</style>

<!-- DASHBOARD: Nội dung chính nằm dưới header "Hello, User" -->
<div class="dashboard">
    <div class="dashboard-title">Delivery Order List</div>
    
    <!-- Thông báo thành công/lỗi -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            ${successMessage}
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
    </c:if>
    
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Delivery ID</th>
                    <th>Status</th>
                    <th>Recipient Name</th>
                    <th>Delivery Address</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty deliveryList}">
                        <c:forEach var="row" items="${deliveryList}">
                            <tr>
                                <td>${row.deliveryId}</td>
                                <td>
                                    <!-- Status với badge màu sắc -->
                                    <span class="status-badge 
                                        <c:choose>
                                            <c:when test="${row.statusName eq 'Pending Confirmation'}">status-pending</c:when>
                                            <c:when test="${row.statusName eq 'Confirmed'}">status-confirmed</c:when>
                                            <c:when test="${row.statusName eq 'Packed'}">status-packed</c:when>
                                            <c:when test="${row.statusName eq 'Out for Delivery'}">status-delivering</c:when>
                                            <c:when test="${row.statusName eq 'Delivered'}">status-delivered</c:when>
                                            <c:when test="${row.statusName eq 'Cancelled'}">status-cancelled</c:when>
                                        </c:choose>
                                    ">
                                        ${row.statusName}
                                    </span>
                                </td>
                                <td>${row.recipientName}</td>
                                <td>${row.deliverTo}</td>
                                <td>
                                    <!-- Nút Edit -->
                                    <a href="deliveryList?action=edit&deliveryId=${row.deliveryId}" class="btn-edit">Edit</a>
                                    
                                    <!-- Nút Delete (chỉ hiển thị nếu trạng thái là Cancelled) -->
                                    <c:if test="${row.statusName eq 'Cancelled'}">
                                        <a href="deliveryList?action=delete&deliveryId=${row.deliveryId}" 
                                           class="btn-delete" 
                                           onclick="return confirm('Are you sure you want to delete this order?');">
                                            Delete
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="5" class="text-center">No Delivery Orders Found</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>
</div> <%-- Đóng thẻ .main-content mở trong managedelivery.jsp --%>

<script>
    // Dark mode toggle
    const darkModeSwitch = document.getElementById('darkModeSwitch');
    if (darkModeSwitch) {
        darkModeSwitch.addEventListener('change', () => {
            document.body.classList.toggle('dark-mode', darkModeSwitch.checked);
        });
    }
</script>
</body>
</html>