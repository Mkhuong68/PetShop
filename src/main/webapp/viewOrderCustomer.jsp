<%-- 
    Document   : viewOrderCustomer
    Created on : Mar 2, 2025, 8:04:55 PM
    Author     : NgocNNCE181950
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/css/cusorderview.css" />
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <div class="left-section">
                <div class="checkout-header">
                    <h1>Checkout</h1>
                </div>
                <form action="OrderPage" method="post">
                    <h2>Information</h2>
                    <label for="recipientName">Full Name</label><br/>
                    <input type="text" id="name" name="name"required><br/>

                    <label for="phoneNumber">Phone Number</label><br/>
                    <input type="text" id="phoneNumber" name="phoneNumber" required><br/>

                    <label for="address">Address</label><br/>
                    <input id="deliveryAddress" name="deliveryAddress"required><br/><!-- comment -->

                    <h2>Payment Method</h2>
                    <div class="payment-method">
                        <label><input type="radio" name="paymentMethod" value="COD" checked> Cash on Delivery</label>
                        <label><input type="radio" name="paymentMethod" value="MOMO"> Momo</label>
                    </div>

                    <br/>
                    <label for="applyVoucher">Apply Voucher</label>
                    <select name="applyVoucher">
                        <c:forEach var="voucher" items="${voucherList}">
                            <option value="${voucher.code}">${voucher.code}</option>
                        </c:forEach>
                    </select>
                    <br/>
                    <br/>
                    <label for="orderNote">Note</label><br/>
                    <input type="text" id="orderNote" name="orderNote"><br/>    

                    <p id="discountMessage">${msg}</p>
                    <input type="submit" class="checkout-btn" value="Checkout">
                </form>
            </div>
            <div class="right-section">
                <h2>Order Summary</h2>
                <div class="order-items">
                    <c:choose>
                        <c:when test="${not empty selectedItems}">
                            <c:forEach var="item" items="${selectedItems}">
                                <div class="order-item">
                                    <strong>${item.productName}</strong>
                                    <p>Quantity: ${item.quantity} | 
                                        Price: <fmt:formatNumber value="${item.finalPrice}" pattern="#,##0" /> VND | 
                                        Total: <fmt:formatNumber value="${item.finalPrice * item.quantity}" pattern="#,##0" /> VND
                                    </p>
                                </div>
                            </c:forEach>
                        </c:when>


                        <c:when test="${not empty product}">
                            <div class="order-item">
                                <strong>${product.productName}</strong>
                                <p>
                                    Quantity: ${product.quantity} | 
                                    Price: <fmt:formatNumber value="${product.originalPrice}" pattern="#,##0" /> VND| 
                                    Total: <fmt:formatNumber value="${totalAmount}" pattern="#,##0" /> VND
                                </p>
                            </div>
                        </c:when>


                        <c:otherwise>
                            <p>No items selected.</p>
                        </c:otherwise>
                    </c:choose>
                    <p><strong>Total Amount: </strong>
                        <fmt:formatNumber value="${totalAmount}" pattern="#,##0" /> VND
                    </p>
                    <p><strong>Shipping Fee:</strong> 
                        <fmt:formatNumber value="${shippingFee}" pattern="#,##0" /> VND
                    </p>
                    <p><strong>Final Total:</strong> 
                        <fmt:formatNumber value="${totalAmount + shippingFee}" pattern="#,##0" /> VND
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>
