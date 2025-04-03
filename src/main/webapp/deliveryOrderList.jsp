<%-- 
    Document   : deliveryOrderList
    Created on : Feb 16, 2025, 10:55:45 PM
    Author     : tvhun
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="managedelivery.jsp" %>

<style>
    /* Giữ nguyên CSS hiện tại */

    /* Thêm style cho nút Track */
    .btn-track {
        display: inline-block;
        padding: 8px 16px;
        margin-right: 8px;
        border-radius: 6px;
        font-weight: 600;
        font-size: 14px;
        text-decoration: none;
        cursor: pointer;
        transition: all 0.3s ease;
        background-color: #4c51bf;
        color: white;
        box-shadow: 0 2px 4px rgba(76, 81, 191, 0.3);
    }

    .btn-track:hover {
        background-color: #434190;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(76, 81, 191, 0.4);
    }

    .btn-track:active {
        transform: translateY(0);
    }

    /* Dark mode cho nút Track */
    .dark-mode .btn-track {
        background-color: #6b46c1;
    }

    .dark-mode .btn-track:hover {
        background-color: #553c9a;
    }
    /* CSS nâng cao cho các nút trong deliveryOrderList */
    .btn-edit {
        display: inline-block;
        padding: 8px 16px;
        margin-right: 8px;
        border-radius: 6px;
        font-weight: 600;
        font-size: 14px;
        text-decoration: none;
        cursor: pointer;
        transition: all 0.3s ease;
        background-color: #3498db;
        color: white;
        box-shadow: 0 2px 5px rgba(52, 152, 219, 0.3);
    }

    .btn-edit:hover {
        background-color: #2980b9;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(52, 152, 219, 0.4);
    }

    .btn-delete {
        display: inline-block;
        padding: 8px 16px;
        border-radius: 6px;
        font-weight: 600;
        font-size: 14px;
        text-decoration: none;
        cursor: pointer;
        transition: all 0.3s ease;
        background-color: #e74c3c;
        color: white;
        box-shadow: 0 2px 5px rgba(231, 76, 60, 0.3);
    }

    .btn-delete:hover {
        background-color: #c0392b;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(231, 76, 60, 0.4);
    }
    /* CSS nâng cao cho các nút trong deliveryOrderList */
    .btn-edit {
        display: inline-block;
        padding: 8px 16px;
        margin-right: 8px;
        border-radius: 6px;
        font-weight: 600;
        font-size: 14px;
        text-decoration: none;
        cursor: pointer;
        transition: all 0.3s ease;
        background-color: #3498db;
        color: white;
        box-shadow: 0 2px 5px rgba(52, 152, 219, 0.3);
    }

    .btn-edit:hover {
        background-color: #2980b9;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(52, 152, 219, 0.4);
    }

    .btn-delete {
        display: inline-block;
        padding: 8px 16px;
        border-radius: 6px;
        font-weight: 600;
        font-size: 14px;
        text-decoration: none;
        cursor: pointer;
        transition: all 0.3s ease;
        background-color: #e74c3c;
        color: white;
        box-shadow: 0 2px 5px rgba(231, 76, 60, 0.3);
    }

    .btn-delete:hover {
        background-color: #c0392b;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(231, 76, 60, 0.4);
    }

    /* Dark mode cho các nút */
    .dark-mode .btn-edit {
        background-color: #3498db;
        box-shadow: 0 2px 5px rgba(52, 152, 219, 0.5);
    }

    .dark-mode .btn-edit:hover {
        background-color: #2980b9;
        box-shadow: 0 4px 8px rgba(52, 152, 219, 0.6);
    }

    .dark-mode .btn-delete {
        background-color: #e74c3c;
        box-shadow: 0 2px 5px rgba(231, 76, 60, 0.5);
    }

    .dark-mode .btn-delete:hover {
        background-color: #c0392b;
        box-shadow: 0 4px 8px rgba(231, 76, 60, 0.6);
    }
    
    /* Payment Status Styles */
    .payment-status {
        display: inline-block;
        padding: 4px 8px;
        border-radius: 4px;
        font-weight: 600;
        font-size: 13px;
    }
    
    .payment-status.paid {
        background-color: #4CAF50;
        color: white;
    }
    
    .payment-status.unpaid {
        background-color: #FF9800;
        color: white;
    }
    
    /* Dark mode for payment status */
    .dark-mode .payment-status.paid {
        background-color: #43A047;
    }
    
    .dark-mode .payment-status.unpaid {
        background-color: #F57C00;
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

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Delivery ID</th>
                    <th>Status</th>
                    <th>Recipient Name</th>
                    <th>Delivery Address</th>
                    <th>Payment Status</th>
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
                                    <span class="payment-status ${row.paymentStatus == 1 ? 'paid' : 'unpaid'}">
                                        ${row.paymentStatus == 1 ? 'Paid' : 'Unpaid'}
                                    </span>
                                </td>
                                <td>
                                    <!-- Nút Edit -->
                                    <a href="deliveryList?action=edit&deliveryId=${row.deliveryId}" class="btn-edit">Edit</a>

                                    <c:if test="${row.statusName eq 'Out for Delivery'}">
                                        <a href="delivery-tracking?deliveryId=${row.deliveryId}" class="btn btn-tracking">
                                            <i class="fas fa-map-marker-alt"></i> View
                                        </a>
                                    </c:if>

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