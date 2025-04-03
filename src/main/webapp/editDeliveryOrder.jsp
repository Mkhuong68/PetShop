<%-- 
    Document   : editDeliveryOrder.jsp
    Created on : Feb 23, 2025, 5:05:39 PM
    Author     : tvhun
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="managedelivery.jsp" %>

<style>
    /* ---------------------- */
    /* CSS nâng cao cho giao diện đẹp và chuyên nghiệp */
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

    /* Bảng hiển thị sản phẩm */
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
    }

    /* Hàng hiển thị tổng tiền */
    .total-label {
        text-align: right;
        font-weight: 700;
        background-color: #f7f9fc;
        color: #2d3748;
        font-size: 16px;
    }

    .total-amount {
        font-weight: 700;
        background-color: #f7f9fc;
        color: #2d3748;
        font-size: 16px;
    }
    
    /* Price formatting */
    .price {
        position: relative;
        font-weight: 600;
        color: #2d3748;
    }
    
    .price-currency {
        font-size: 0.85em;
        color: #718096;
        margin-left: 4px;
    }
    
    .price-total {
        color: #e53e3e;
        font-weight: 700;
    }

    /* Khối thông tin */
    .customer-info {
        background-color: #f8fafd;
        padding: 20px;
        border: 1px solid #e0e0e0;
        margin-bottom: 25px;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.03);
        transition: all 0.3s ease;
    }
    
    .customer-info:hover {
        box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        transform: translateY(-2px);
    }

    .customer-info h3 {
        margin-top: 0;
        margin-bottom: 15px;
        font-size: 20px;
        font-weight: 600;
        color: #2d3748;
        border-bottom: 1px solid #e0e0e0;
        padding-bottom: 10px;
    }
    
    .customer-info p {
        margin-bottom: 10px;
        font-size: 15px;
        line-height: 1.6;
    }
    
    .customer-info p strong {
        display: inline-block;
        width: 100px;
        font-weight: 600;
        color: #4a5568;
    }

    /* Form elements */
    .form-group {
        margin-bottom: 20px;
    }

    .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: 600;
        color: #4a5568;
        font-size: 15px;
    }

    .form-control {
        width: 100%;
        padding: 12px 15px;
        border-radius: 6px;
        border: 1px solid #ddd;
        background-color: #fff;
        color: #4a5568;
        font-size: 15px;
        transition: all 0.3s ease;
        box-shadow: 0 1px 3px rgba(0,0,0,0.05);
    }
    
    .form-control:focus {
        border-color: #007BFF;
        outline: none;
        box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.25);
    }

    /* Buttons */
    .btn-update {
        display: inline-block;
        padding: 12px 20px;
        background-color: #28a745;
        color: #fff;
        text-decoration: none;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        transition: all 0.3s ease;
        font-weight: 600;
        font-size: 15px;
        box-shadow: 0 2px 5px rgba(40, 167, 69, 0.3);
    }

    .btn-update:hover {
        background-color: #218838;
        transform: translateY(-2px);
        box-shadow: 0 4px 10px rgba(40, 167, 69, 0.4);
    }
    
    .btn-update:active {
        transform: translateY(0);
    }

    /* Khu vực nút hành động */
    .actions {
        margin-top: 30px;
        display: flex;
        justify-content: space-between;
    }

    /* Nút Back */
    .btn-back {
        display: inline-block;
        padding: 12px 20px;
        background-color: #007BFF;
        color: #fff;
        text-decoration: none;
        border-radius: 6px;
        transition: all 0.3s ease;
        font-weight: 600;
        font-size: 15px;
        box-shadow: 0 2px 5px rgba(0, 123, 255, 0.3);
    }

    .btn-back:hover {
        background-color: #0056b3;
        transform: translateY(-2px);
        box-shadow: 0 4px 10px rgba(0, 123, 255, 0.4);
    }
    
    .btn-back:active {
        transform: translateY(0);
    }

    /* Dark mode */
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
    
    .dark-mode .total-label,
    .dark-mode .total-amount {
        background-color: #323c4e;
        color: #e2e8f0;
    }
    
    .dark-mode .price-currency {
        color: #a0aec0;
    }
    
    .dark-mode .price-total {
        color: #fc8181;
    }

    .dark-mode .customer-info {
        background-color: #323c4e;
        border: 1px solid #4a5568;
        box-shadow: 0 2px 8px rgba(0,0,0,0.15);
    }
    
    .dark-mode .customer-info h3 {
        color: #e2e8f0;
        border-bottom-color: #4a5568;
    }
    
    .dark-mode .customer-info p strong {
        color: #a0aec0;
    }

    .dark-mode .form-control {
        background-color: #4a5568;
        color: #e2e8f0;
        border-color: #4a5568;
    }
    
    .dark-mode .form-control:focus {
        border-color: #4299e1;
        box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.25);
    }
    
    .dark-mode .form-group label {
        color: #a0aec0;
    }

    .dark-mode .btn-update {
        background-color: #48bb78;
        box-shadow: 0 2px 5px rgba(72, 187, 120, 0.3);
    }

    .dark-mode .btn-update:hover {
        background-color: #38a169;
        box-shadow: 0 4px 10px rgba(72, 187, 120, 0.4);
    }

    .dark-mode .btn-back {
        background-color: #4299e1;
        box-shadow: 0 2px 5px rgba(66, 153, 225, 0.3);
    }

    .dark-mode .btn-back:hover {
        background-color: #3182ce;
        box-shadow: 0 4px 10px rgba(66, 153, 225, 0.4);
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
</style>

<!-- JavaScript function to format currency -->
<script>
    function formatCurrency(amount) {
        // Format số với dấu phẩy ngăn cách hàng nghìn và thêm đơn vị VND
        return new Intl.NumberFormat('vi-VN', { 
            style: 'decimal',
            maximumFractionDigits: 0 
        }).format(amount) + " VND";
    }
    
    document.addEventListener('DOMContentLoaded', function() {
        // Format tất cả phần tử giá tiền khi trang tải xong
        document.querySelectorAll('.price-format').forEach(function(element) {
            const value = parseFloat(element.getAttribute('data-value'));
            if (!isNaN(value)) {
                element.innerHTML = formatCurrency(value);
            }
        });
    });
</script>

<!-- MAIN CONTENT -->
<div class="dashboard">
    <div class="dashboard-title">Edit Delivery Order</div>
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
                        <td>
                            <span class="price price-format" data-value="${item.totalPrice}">
                                <fmt:formatNumber value="${item.totalPrice}" type="number" pattern="#,##0" /> 
                                <span class="price-currency">VND</span>
                            </span>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3" class="total-label">Total Amount:</td>
                    <td class="total-amount">
                        <span class="price price-total price-format" data-value="${orderTotal}">
                            <fmt:formatNumber value="${orderTotal}" type="number" pattern="#,##0" />
                            <span class="price-currency">VND</span>
                        </span>
                    </td>
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
    <div class="customer-info">
        <h3>Change Delivery Status</h3>
        <form action="deliveryList" method="post">
            <input type="hidden" name="action" value="updateStatus">
            <input type="hidden" name="deliveryId" value="${param.deliveryId}">

            <div class="form-group">
                <label for="newStatus">New Status:</label>
                <select id="newStatus" name="newStatus" class="form-control">
                    <option value="1" ${currentStatusId == 1 ? 'selected' : ''}>Pending Confirmation</option>
                    <option value="2" ${currentStatusId == 2 ? 'selected' : ''}>Confirmed</option>
                    <option value="3" ${currentStatusId == 3 ? 'selected' : ''}>Packed</option>
                    <option value="4" ${currentStatusId == 4 ? 'selected' : ''}>Out for Delivery</option>
                    <option value="5" ${currentStatusId == 5 ? 'selected' : ''}>Delivered</option>
                    <option value="6" ${currentStatusId == 6 ? 'selected' : ''}>Cancelled</option>
                </select>
            </div>

            <button type="submit" class="btn-update">Update Status</button>
        </form>
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