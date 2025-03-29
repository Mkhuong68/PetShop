<%-- 
    Document   : viewOrderDetailStaff
    Created on : Mar 1, 2025, 4:03:43 PM
    Author     : NgocNNCE181950
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/css/cusorderdetail.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <h1>Order Confirmation</h1>

            <div class="order-info">
                <h2>Order Information</h2>
                <p><strong>#</strong> ${orderInfo.orderId}</p>
                <p><strong>Name: </strong> ${account.firstName} ${account.lastName}</p>
                <p><strong>Date: </strong> <fmt:formatDate value="${orderInfo.orderDate}" pattern="dd-MM-yyyy" /></p>
                <p><strong>Status Name: </strong> ${orderInfo.statusName}</p>
                <p><strong>Payment Method: </strong> ${orderInfo.paymentMethod}</p>
                <p><strong>Phone Number: </strong>${account.phoneNumber}</p>
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
            </div>
            <form action="StaffOrderDetailController" method="post">
                <input type="hidden" value="${orderInfo.orderId}" name="orderId" />
                <table>
                    <tr>
                        <th>Status Name</th>
                        <td>
                            <select name="statusName" id="statusSelect" class="status-select"
                                    ${orderInfo.statusName == 'Delivered' || orderInfo.statusName == 'Cancelled' ? 'disabled' : ''}>
                                <c:forEach var="c" items="${statusList}">
                                    <option value="${c.statusName}" ${c.statusName == data.statusName ? 'selected' : ''}>
                                        ${c.statusName}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
                <input type="submit" value="Change" id="changeButton" class="submit-btn" ${orderInfo.statusName == 'Delivered' || orderInfo.statusName == 'Cancelled' ? 'disabled' : ''}>
            </form>
            <c:if test="${not empty msg}">
                <p>${msg}</p>
            </c:if>
        </div>
    </body>

</html>
