<%-- 
    Document   : hompage
    Created on : Feb 17, 2025, 8:52:37 AM
    Author     : tvhun
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
                        <a href="login.jsp"><img src="assets/images/Pet Heaven.png" alt="PetShop" /></a>
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
                                <li class="active">
                                    <a href="/Home"><i class="bx bxs-home"></i></a>
                                </li>
                                <li class="products">
                                    <a href="/ProductList"><i class="bx bx-archive"></i></a>
                                </li>
                                <li class="community">
                                    <a href="news.jsp"><i class="bx bx-globe"></i></a>
                                </li>
                                <li class="partner">
                                    <a href="partner.jsp"><i class="bx bx-bell"></i></a>
                                </li>
                                <li>
                                    <a href="about.jsp" class="account-link">
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

            <!-- Các nội dung khác của Homepage, ví dụ: categories, hot products, about us, contact, ... -->
            <!-- ... -->

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
    </body>
</html>
