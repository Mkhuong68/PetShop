<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/css/cusorderhistory.css">
        <link rel="stylesheet" type="text/css" href="assets/css/global.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="css/responsive.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="icon" href="Pet Heaven.png" type="image/png">
        <title>Your Order History</title>
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
        <h2 style="text-align: center; margin-top: 18px">Your Order History</h2>

        <div class="order-section">
            <div class="order-column">
                <h3>Received Orders</h3>
                <c:forEach var="order" items="${received}">
                    <a href="CustomerOrderDetailController?orderId=${order.orderId}" class="order-link">
                        <div class="order-container">
                            <div class="order-title">Order ID: <strong>${order.orderId}</strong></div>
                            <div class="order-info">Order Date: <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" /></div>
                            <div class="order-info">Status: <strong>${order.statusName}</strong></div>
                        </div>
                    </a>
                </c:forEach>
                <p>${msgReceived}</p>
            </div>

            <div class="order-column">
                <h3>Pending Orders</h3>
                <c:forEach var="order" items="${pending}">
                    <a href="CustomerOrderDetailController?orderId=${order.orderId}" class="order-link">
                        <div class="order-container">
                            <div class="order-title">Order ID: <strong>${order.orderId}</strong></div>
                            <div class="order-info">Order Date: <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" /></div>
                            <div class="order-info">Status: <strong>${order.statusName}</strong></div>
                        </div>
                    </a>
                </c:forEach>
                <p>${msgPending}</p>

            </div>

            <div class="order-column">
                <h3>Delivered Orders</h3>
                <c:forEach var="order" items="${delivered}">
                    <a href="CustomerOrderDetailController?orderId=${order.orderId}" class="order-link">
                        <div class="order-container">
                            <div class="order-title">Order ID: <strong>${order.orderId}</strong></div>
                            <div class="order-info">Order Date: <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" /></div>
                            <div class="order-info">Status: <strong>${order.statusName}</strong></div>
                            <div>
                                <a href="">Feedback</a>
                            </div>
                        </div>
                    </a>
                </c:forEach>
                <p>${msgDelivered}</p>

            </div>

            <div class="order-column">
                <h3>Cancelled Orders</h3>
                <c:forEach var="order" items="${cancelled}">
                    <a href="CustomerOrderDetailController?orderId=${order.orderId}" class="order-link">
                        <div class="order-container">
                            <div class="order-title">Order ID: <strong>${order.orderId}</strong></div>
                            <div class="order-info">Order Date: <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" /></div>
                            <div class="order-info">Status: <strong>${order.statusName}</strong></div>
                        </div>
                    </a>
                </c:forEach>
                <p>${msgCancelled}</p>
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
