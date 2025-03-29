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
        <link rel="stylesheet" type="text/css" href="assets/css/global.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="css/responsive.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="icon" href="Pet Heaven.png" type="image/png">
        <title>JSP Page</title>
    </head>
    <body>
        <%-- Thiết lập biến cartCount dựa trên session (nếu người dùng đã đăng nhập) --%>
        <c:set var="cartCount" value="0" />
        <c:if test="${not empty sessionScope.account}">
            <c:set var="cartCount" value="${CartService.getCartCount(pageContext.request)}" />
        </c:if>

        <div id="main-content" class="wrap">
            <header class="taskbar">
                <nav class="container">
                    <div class="logo">
                        <a href="/Home"><img src="assets/images/Pet Heaven.png" alt="PetShop" /></a>
                    </div>
                    <div class="menu" data-show="0">
                        <div class="d-flex h-100 justify-content-center align-items-center">
                            <ul>
                                <li class="search-item">
                                    <form class="search-form" action="#" method="get">
                                        <input type="text" placeholder="Tìm kiếm..." />
                                        <button type="submit"><i class="bx bx-search"></i></button>
                                    </form>
                                </li>

                                <li class="products">
                                    <a href="/ProductList">
                                        <i class='bx bx-archive'></i>
                                    </a>
                                    <div class="product-dropdown">
                                        <div class="dropdown-grid">
                                            <c:forEach var="category" items="${categories}" varStatus="status">
                                                <div class="dropdown-category">
                                                    <h4>${category.categoryName}</h4>
                                                    <ul>
                                                        <li><a href="/ProductList?category=${category.categoryId}">${category.categoryName}</a></li>
                                                    </ul>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </li>
                                <li class="community">
                                    <a href="news.jsp"><i class="bx bx-globe"></i></a>
                                </li>
                                <li class="partner">
                                    <a href="#" class="account-link">
                                        <i class="fas fa-bell"></i>

                                    </a>
                                    <div class="notification-dropdown">
                                        <div class="notification-header">
                                            <h3>Notifications </h3>
                                        </div>
                                        <div class="notification-items">
                                            <c:if test="${empty notifications}">
                                                <div class="notification-item">
                                                    <div class="notification-content">
                                                        <p class="notification-desc">None notifications</p>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:forEach items="${notifications}" var="notification">
                                                <a href="NotificationDetail?notificationId=${notification.notificationId}" class="notification-item ${notification.isRead ? 'read' : 'unread'}">
                                                    <div class="notification-content">
                                                        <h4 class="notification-title">Notifications</h4>
                                                        <p class="notification-desc">${notification.message}</p>
                                                        <small><fmt:formatDate value="${notification.createdDate}" pattern="dd/MM/yyyy HH:mm" /></small>
                                                    </div>
                                                </a>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <a href="viewProfile.jsp" class="account-link">
                                        <i class="bx bx-user"></i>
                                        <span class="account-text"></span>
                                    </a>
                                </li>
                                <li>
                                    <a href="/Cart" class="cart-link" style="position: relative;">
                                        <i class="bx bx-cart"></i>
                                        <span class="cart-badge" id="cartCountDisplay">${cartCount}</span>
                                        <span class="cart-text"></span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="d-flex align-items-center d-block d-lg-none">
                        <button class="res-menu d-block d-lg-none">
                            <span></span>
                            <span></span>
                            <span></span>
                        </button>
                    </div>
                </nav>
            </header>
            <div class="container_1">
                <div class="left-section">
                    <div class="checkout-header">
                        <h1 style="font-weight: bold; text-align: center">Checkout</h1>
                    </div>
                    <form action="OrderPage" method="post">
                        <h2>Information</h2>
                        <label for="recipientName">Full Name</label><br/>
                        <input style="width: 100%" type="text" id="name" name="name"value="${firstName} ${lastName}" readonly><br/>
                        <input type="hidden" name="hiddenName" value="${firstName} ${lastName}">

                        <label for="phoneNumber">Phone Number</label><br/>
                        <input style="width: 100%" type="text" id="phoneNumber" name="phoneNumber" value="${phoneNumber}" readonly><br/>
                        <input type="hidden" name="hiddenPhoneNumber" value="${phoneNumber}">
                        <p>${phoneError}</p>
                        
                        <label for="address">Address</label><br/>
                        <input style="width: 100%" type="text" id="address" name="deliveryAddress" required><br/>

                        <h2 style="margin-top:5%">Payment Method</h2>
                        <div class="payment-method">
                            <label><input type="radio" name="paymentMethod" value="COD" checked> Cash on Delivery</label>
                            <label><input type="radio" name="paymentMethod" value="MOMO"> MoMo</label>
                        </div>

                        <br/>
                        <label for="applyVoucher">Apply Voucher</label>
                        <select style="width: 100%" name="applyVoucher">
                            <option value="Select Voucher" selected>Select Voucher</option> 
                            <c:if test="${not empty voucherList}">
                                <c:forEach var="voucher" items="${voucherList}">
                                    <option value="${voucher.voucherCode}">${voucher.voucherCode}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <br/>
                        <br/>
                        <label for="orderNote">Note</label><br/>
                        <input style="width: 100%" type="text" id="orderNote" name="orderNote"><br/>    

                        <p id="discountMessage">${msg}</p>
                        <input type="submit" class="checkout-btn" style="width: 100%" value="Checkout">
                    </form>
                </div>
                <div class="right-section">
                    <h2 style="margin-top: 50px">Order Summary</h2>
                    <div class="order-items">
                        <c:choose>
                            <c:when test="${not empty selectedItems}">
                                <c:forEach var="item" items="${selectedItems}">
                                    <div class="order-item">
                                        <strong style="font-size: 20px">${item.productName}</strong>
                                        <p style="font-size: 15px; margin-top: 10px">Quantity: ${item.quantity} | 
                                            Price: <fmt:formatNumber value="${item.finalPrice}" pattern="#,##0" /> VND | 
                                            Total: <fmt:formatNumber value="${item.finalPrice * item.quantity}" pattern="#,##0" /> VND
                                        </p>
                                    </div>
                                </c:forEach>
                            </c:when>


                            <c:when test="${not empty product}">
                                <div class="order-item">
                                    <strong style="font-size: 20px">${product.productName}</strong>
                                    <p style="font-size: 15px; margin-top: 10px">
                                        Quantity: ${product.quantity} | 
                                        Price: <fmt:formatNumber value="${product.originalPrice}" pattern="#,##0" /> VND| 
                                        Total: <fmt:formatNumber value="${totalAmount}" pattern="#,##0" /> VND
                                    </p>
                                </div>
                            </c:when>
                        </c:choose>
                        <div style="font-size: 18px">
                            <p style="margin-top:5%"><strong>Total Amount: </strong>
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
            </div>
            <!-- FOOTER -->
            <footer class="w-100" style="background:#8AAAE5">
                <div class="container">
                    <div class="row mt-3 mb-3">
                        <div class="col-md-6">
                            <h5>
                                General Information
                                <span class="line-remove" style="width: 78px;"></span>
                            </h5>
                            <h4 class="mt-2 pt-2 com-name">Pet Heaven</h4>
                            <p class="com-phone">
                                <i class="fas fa-phone-alt"></i>
                                <a href="#" title="0999.999.999">0999 999 999</a>
                            </p>
                            <p class="com-email">
                                <i class="fas fa-envelope"></i>
                                <a href="#" title="cskh@petheaven.vn">cskh@petheaven.vn</a>
                            </p>
                            <address class="com-address">
                                <i style="width: 22px;" class="fas fa-map-marker-alt"></i>Ninh Kiều, Cần Thơ 
                            </address>
                        </div>
                        <div class="col-md-3">
                            <h5>
                                ABOUT US
                                <span class="line-remove" style="width: 78px;"></span>
                            </h5>
                            <ul>
                                <li><a href="about.html" title="Giới thiệu">About </a></li>
                                <li><a href="product.html" title="Sản phẩm">Products</a></li>
                                <li><a href="news.html" title="Tin tức">Community</a></li>
                                <li><a href="contact.html" title="Đối tác">Contact</a></li>
                            </ul>
                        </div>
                        <div class="col-md-3">
                            <h5>
                                CONNECT WITH US
                                <span class="line-remove" style="width: 78px;"></span>
                            </h5>
                            <div class="mt-4 social-icon">
                                <a href="#" target="_blank">
                                    <i class="fab fa-facebook-square"></i>
                                </a>
                                <a href="#" target="_blank">
                                    <i class="far fa-envelope"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </footer>
    </body>
</html>
