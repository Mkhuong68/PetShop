<%-- 
    Document   : deliveryOrderList
    Created on : Feb 16, 2025, 10:55:45 PM
    Author     : tvhun
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="managedelivery.jsp" %>

<!-- DASHBOARD: Nội dung chính nằm dưới header "Hello, User" -->
<div class="dashboard">
    <div class="dashboard-title">Delivery Order List</div>
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
                                <td>${row.statusName}</td>
                                <td>${row.recipientName}</td>
                                <td>${row.deliverTo}</td>
                                <td>
                                    <!-- Edit button: truyền action=edit -->
                                    <a href="deliveryList?action=edit&deliveryId=${row.deliveryId}" class="btn-edit">Edit</a>
                                    <!-- Delete button: truyền action=delete, chỉ hiển thị nếu trạng thái là 'Cancelled' -->
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
    darkModeSwitch.addEventListener('change', () => {
        document.body.classList.toggle('dark-mode', darkModeSwitch.checked);
    });
</script>
</body>
</html>
