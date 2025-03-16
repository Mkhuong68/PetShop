<%-- 
    Document   : hompage
    Created on : Feb 17, 2025, 8:52:37 AM
    Author     : Diem Quynh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Service.CartService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="assets/css/global.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="css/responsive.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="icon" href="Pet Heaven.png" type="image/png">
        <title>Homepage</title>
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
                                    <a href="/ProfileController" class="account-link">
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

            <!-- BANNER -->
            <div class="banner">
                <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="assets/images/z6324794496428_a70bdd00a1363116462f20a4b15514de.jpg" class="d-block w-100" alt="...">
                            <div class="content-box-banner">
                                <h2 class="text-uppercase header-banner"> 
                                    Love your pets, every day, completely! <br> <span> QuinnQuinn </span>
                                </h2>
                                <div class="sapo-banner">
                                    <p>We offer high-quality products and services for pets, helping you care for and nurture your four-legged companions with love and the best care.</p>
                                </div>
                                <a href="#" class="text-uppercase btn-banner"> Contact now </a>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="assets/images/z6324794496428_a70bdd00a1363116462f20a4b15514de.jpg" class="d-block w-100" alt="...">
                            <div class="content-box-banner">
                                <h2 class="text-uppercase header-banner"> 
                                    Love your pets, every day, completely! <br> <span> QuinnQuinn </span>
                                </h2>
                                <div class="sapo-banner">
                                    <p>We offer high-quality products and services for pets, helping you care for and nurture your four-legged companions with love and the best care.</p>
                                </div>
                                <a href="#" class="text-uppercase btn-banner"> Contact now </a>
                            </div> 
                        </div>
                        <div class="carousel-item">
                            <img src="assets/images/z6324794496428_a70bdd00a1363116462f20a4b15514de.jpg" class="d-block w-100" alt="...">
                            <div class="content-box-banner">
                                <h2 class="text-uppercase header-banner"> 
                                    Love your pets, every day, completely! <br> <span> QuinnQuinn </span>
                                </h2>
                                <div class="sapo-banner">
                                    <p>We offer high-quality products and services for pets, helping you care for and nurture your four-legged companions with love and the best care.</p>
                                </div>
                                <a href="#" class="text-uppercase btn-banner"> Contact now </a>
                            </div> 
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>

            <!-- HOT PRODUCT -->
            <div class="hot-product-wrap">
                <h2 class="header-prd"> Boss's favorite product. </h2>
                <div class="slide-prd">
                    <c:forEach var="product" items="${topProducts}">
                        <div class="product">
                            <div class="img">
                                <img src="${product.productImage}" alt="${product.productName}" class="img-fluid">
                            </div>
                            <div class="info">
                                <p class="name">
                                    <a href="#">${product.productName}</a>
                                </p>
                                <p class="vote">

                                    <c:forEach var="i" begin="1" end="5">
                                        <span><i class="fas fa-star"></i></span>
                                        </c:forEach>
                                </p>
                                <p class="desc"> ${product.productDescription} </p>
                                <p class="price">
                                    <span>
                                        <fmt:formatNumber value="${product.productPrice}" pattern="#,###" />
                                    </span> VND
                                </p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <!-- ABOUT US -->
            <div class="about-us">
                <div class="container">
                    <h2 class="header-abt"> About Us </h2>
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="img h-100">
                                <img src="assets/images/z6331565599640_38a2d509c16e30ffc3d27db816be7aa9.jpg" alt="Reputable & High-Quality Pet Shop"
                                     class="w-100 h-100">
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="content h-100">
                                <h3> Reputable & High-Quality Pet Shop </h3>
                                <div>
                                    <p>PetShop is a specialized store providing products and services for pets, including food, accessories, toys, and health care products. The shop is committed to offering high-quality, safe products that meet the needs of different pets. A team of experienced staff is always ready to provide advice to help customers choose the best products. Additionally, PetShop offers various promotions and attractive after-sales policies. Customers can shop directly at the store or place orders online with fast home delivery services. PetShop is not just a shopping destination but also a trusted place for pet lovers.</p>
                                </div>
                                <div>
                                    <p><img alt="giới thiệu" src="assets/images/z6331584900980_19d8218ae50aea67108fa1a9ad7beaee.jpg">&nbsp;<img alt="giới thiệu"
                                                                                                                                                src="assets/images/z6331578146709_7d2ab3ef01ab0c8bd9207e8d712614b6.jpg">&nbsp;<img alt=""
                                                                                                                                                src="assets/images/z6331578089266_63c414e2a520308c74a8c96ac4d7292e.jpg">&nbsp;<a href="#"><img alt=""
                                                                                                                                       src="assets/images/z6331578146708_195ae55e7d9ad7d260b29d54061c83be.jpg"></a></p>

                                </div>
                            </div>
                        </div>
                    </div>
                    <br><br>
                    <h2 class="header-abt"> Why Choose Pet Heaven?</h2>
                    <div class="row reason">
                        <div class="col-md-6">
                            <div class="reason-index-item d-flex">
                                <div class="img">
                                    <img src="assets/images/money.png" alt="lý do 1">
                                </div>
                                <div class="content">
                                    <h3 class="title"> Pricing Policy </h3>
                                    <p class="desc"> It is best to publicly display prices on the website.</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="reason-index-item d-flex">
                                <div class="img">
                                    <img src="assets/images/product.png" alt="lý do 1">
                                </div>
                                <div class="content">
                                    <h3 class="title"> Goods </h3>
                                    <p class="desc"> The goods are imported from renowned and reliable brands.</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="reason-index-item d-flex">
                                <div class="img">
                                    <img src="assets/images/medal.png" alt="lý do 1">
                                </div>
                                <div class="content">
                                    <h3 class="title"> Quality </h3>
                                    <p class="desc"> Committed to product quality. </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="reason-index-item d-flex">
                                <div class="img">
                                    <img src="assets/images/open-24-h.png" alt="lý do 1">
                                </div>
                                <div class="content">
                                    <h3 class="title"> Warranty </h3>
                                    <p class="desc"> The best warranty service in the region.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <!-- CONTACT -->
        <div class="contact contact-index">
            <span><img src="assets/images/snapedit_1740325761444.png" alt="Shopping Experience with Pet Heaven"></span>
            <div class="container">
                <div class="row contact-row">
                    <div class="col-lg-6 col-md-5 ">
                        <h2 class="title"> Shopping Experience <br> <strong> with Pet Heaven </strong></h2>
                    </div>
                    <div class="col-lg-6 col-md-7">
                        <p class="mb-1 text-white"> Contact Information </p>
                        <div class="form-group">
                            <input type="text" placeholder="Email/Phone Number">
                            <button class="savePhone"> Send </button>
                        </div>
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
    </div>

    <!-- jQuery & Bootstrap JS (bao gồm cả các thư viện bổ trợ khác) -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
    crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
    crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
    crossorigin="anonymous"></script>
    <script type="text/javascript" src="assets/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="assets/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="https://kit.fontawesome.com/bf61fecb7c.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="assets/js/javascript.js"></script>
    <script type="text/javascript" src="assets/js/dropdown.js"></script>
    <script type="text/javascript" src="assets/js/notification.js"></script>

    <!-- Script để làm mới số lượng giỏ hàng (cart count) trên taskbar -->
    <script>
        $(document).ready(function () {
            $.ajax({
                url: "/Cart?action=getSummary",
                method: "GET",
                dataType: "json",
                success: function (data) {
                    $("#cartCountDisplay").text(data.cartCount);
                },
                error: function () {
                    console.log("Unable to refresh cart summary.");
                }
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
</body>
</html> 
