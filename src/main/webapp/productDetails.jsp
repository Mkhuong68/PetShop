<%-- 
    Document   : productDetails
    Created on : Feb 28, 2025, 5:15:13 PM
    Author     : tvhun
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Service.CartService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Details - ${product.productName}</title>
        <!-- CSS -->
        <link rel="stylesheet" type="text/css" href="assets/css/global.css">
        <link rel="stylesheet" type="text/css" href="assets/css/detail.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="assets/css/responsive.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="icon" href="Pet Heaven.png" type="image/png">
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

            <!-- Product Details Section -->
            <div class="wrapper">
                <div class="product-detail-container">
                    <!-- Product Gallery -->
                    <div class="product-gallery">
                        <div class="main-image">
                            <img id="current-image" src="${product.productImage}" alt="${product.productName}" />
                        </div>
                        <div class="thumbnail-list">
                            <img class="thumbnail active" src="${product.productImage}" data-full="${product.productImage}" alt="Thumb 1" />
                        </div>
                    </div>

                    <!-- Product Information -->
                    <div class="product-info">
                        <h1 class="product-title">${product.productName}</h1>
                        <p class="product-price">
                            <fmt:formatNumber value="${product.productPrice}" pattern="#,##0" /> VND
                        </p>
                        <div class="product-variations">
                            <label for="color-select">Chọn màu:</label>
                            <select id="color-select" name="color">
                                <option value="Vang">Vàng</option>
                                <option value="Hong">Hồng</option>
                                <option value="Xanh">Xanh</option>
                            </select>
                        </div>

                        <div class="product-quantity">
                            <label for="quantity">Số lượng:</label>
                            <input type="number" id="quantity" name="quantity" value="1" min="1"
                                   onchange="document.getElementById('order-link').href = '/OrderPage?action=orderFromDetail&productId=${product.productId}&quantity=' + this.value;" />
                        </div>

                        <!-- Nút hành động -->
                        <div class="product-actions">
                            <!-- Link "Thêm vào giỏ" sẽ được cập nhật theo số lượng -->
                            <a href="/Cart?action=add&productId=${product.productId}&quantity=1" class="btn btn-add-to-cart">Thêm vào giỏ</a>
                            <a id="order-link" href="/OrderPage?action=orderFromDetail&productId=${product.productId}&quantity=1" class="btn btn-order">Order</a>
                        </div>
                    </div>
                </div>

                <!-- Tabs: Description, Reviews, Q&A -->
                <div class="product-detail-tabs">
                    <ul class="tabs">
                        <li class="tab active" data-tab="description">Mô tả</li>
                        <li class="tab" data-tab="reviews">Đánh giá</li>
                        <li class="tab" data-tab="qna">Hỏi đáp</li>
                    </ul>
                    <div class="tab-content active" id="description">
                        <h2>Mô tả sản phẩm</h2>
                        <p>${product.productDescription}</p>
                    </div>
                    <div class="tab-content" id="reviews">
                        <h2>Đánh giá</h2>
                        <p>Hiển thị các đánh giá từ khách hàng.</p>
                    </div>
                    <div class="tab-content" id="qna">
                        <h2>Hỏi đáp</h2>
                        <p>Nơi khách hàng đặt câu hỏi và được trả lời.</p>
                    </div>
                </div>

                <!-- Related Products Section (demo tĩnh) -->
                <div class="related-products-section">
                    <h2>Sản phẩm liên quan</h2>
                    <div class="related-products">
                        <div class="product-item">
                            <img src="https://via.placeholder.com/150" alt="related1" />
                            <p>Tên SP liên quan 1</p>
                        </div>
                        <div class="product-item">
                            <img src="https://via.placeholder.com/150" alt="related2" />
                            <p>Tên SP liên quan 2</p>
                        </div>
                        <div class="product-item">
                            <img src="https://via.placeholder.com/150" alt="related3" />
                            <p>Tên SP liên quan 3</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Footer (demo mẫu) -->
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
                                <i style="width: 22px;" class="fas fa-map-marker-alt"></i>
                                Ninh Kiều, Cần Thơ
                            </address>
                        </div>
                        <div class="col-md-3">
                            <h5>
                                ABOUT US
                                <span class="line-remove" style="width: 78px;"></span>
                            </h5>
                            <ul>
                                <li><a href="about.jsp" title="Giới thiệu">About</a></li>
                                <li><a href="product.jsp" title="Sản phẩm">Products</a></li>
                                <li><a href="news.jsp" title="Tin tức">Community</a></li>
                                <li><a href="contact.jsp" title="Đối tác">Contact</a></li>
                            </ul>
                        </div>
                        <div class="col-md-3">
                            <h5>
                                CONNECT WITH US
                                <span class="line-remove" style="width: 78px;"></span>
                            </h5>
                            <div class="mt-4 social-icon">
                                <a href="#" target="_blank"><i class="fab fa-facebook-square"></i></a>
                                <a href="#" target="_blank"><i class="far fa-envelope"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </footer>
        </div>

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script type="text/javascript" src="assets/js/dropdown.js"></script>
        <script type="text/javascript" src="assets/js/notification.js"></script>
        <script>
                                       $(document).ready(function () {
                                           // Khi số lượng thay đổi, cập nhật href của nút "Thêm vào giỏ"
                                           $("#quantity").on("change", function () {
                                               var quantity = $(this).val();
                                               var productId = "${product.productId}";
                                               $(".btn-add-to-cart").attr("href", "/Cart?action=add&productId=" + productId + "&quantity=" + quantity);
                                           });

                                           // Xử lý "Thêm vào giỏ" bằng AJAX
                                           $(".btn-add-to-cart").on("click", function (e) {
                                               e.preventDefault();
                                               var url = $(this).attr("href");
                                               $.ajax({
                                                   url: url,
                                                   method: "GET", // hoặc POST nếu cần
                                                   dataType: "json",
                                                   success: function (response) {
                                                       // Cập nhật số lượng giỏ hàng (badge)
                                                       $("#cartCountDisplay").text(response.cartCount);
                                                   },
                                                   error: function () {
                                                       alert("Có lỗi khi thêm sản phẩm vào giỏ hàng.");
                                                   }
                                               });
                                           });

                                           // Khi nhấn "Order", chuyển hướng trang
                                           $(".btn-order").on("click", function (e) {
                                               e.preventDefault();
                                               window.location.href = $(this).attr("href");
                                           });
                                       });
        </script>
        <script>
            function refreshNotifications() {
                $.ajax({
                    url: "notificationsJson",
                    type: "GET",
                    dataType: "json",
                    success: function (notifications) {
                        var container = $(".notification-items");
                        container.empty();
                        if (!notifications || notifications.length === 0) {
                            container.append("<p>None Notifications.</p>");
                        } else {
                            notifications.forEach(function (n) {
                                // Đã thay đổi từ n.notificationDate thành n.createdDate
                                var dateStr = new Date(n.createdDate).toLocaleString();
                                var html = '<div class="notification-item">' +
                                        '<div class="notification-content">' +
                                        '<a href="notificationDetail?notificationId=' + n.notificationId + '">' +
                                        // Đã thay đổi từ n.notificationContent thành n.message
                                        '<p class="notification-title">' + n.message + '</p>' +
                                        '<p class="notification-desc">' + dateStr + '</p>' +
                                        '</a>' +
                                        '</div>' +
                                        '</div>';
                                container.append(html);
                            });
                        }
                    },
                    error: function (xhr, status, error) {
                        console.log("Lỗi khi tải thông báo: " + status + " - " + error);
                        console.log(xhr.responseText);
                    }
                });
            }
        </script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>