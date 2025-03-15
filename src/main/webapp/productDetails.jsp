<%-- 
    Document   : productDetails
    Created on : Feb 28, 2025, 5:15:13 PM
    Author     : Diem Quynh
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
        <%-- Lấy số lượng giỏ hàng từ CartService nếu người dùng đã đăng nhập --%>
        <c:set var="cartCount" value="0" />
        <c:if test="${not empty sessionScope.account}">
            <c:set var="cartCount" value="${CartService.getCartCount(pageContext.request)}" />
        </c:if>

        <div id="main-content" class="wrap">
            <!-- Header mẫu (không thay đổi các icon) -->
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
                                <option value="Vang">Yellow</option>
                                <option value="Hong">Pink</option>
                                <option value="Xanh">Blue</option>
                            </select>
                        </div>

                        <div class="product-quantity">
                            <label for="quantity">Quantity:</label>
                            <input type="number" id="quantity" name="quantity" value="1" min="1" />
                        </div>

                        <!-- Nút hành động -->
                        <div class="product-actions">
                            <!-- Link "Thêm vào giỏ" sẽ được cập nhật theo số lượng -->
                            <a href="/Cart?action=add&productId=${product.productId}&quantity=1" class="btn btn-add-to-cart">Thêm vào giỏ</a>
                            <a href="OrderPage" class="btn btn-order">Order</a>
                        </div>
                    </div>
                </div>

                <!-- Tabs: Description, Reviews, Q&A -->
                <div class="product-detail-tabs">
                    <ul class="tabs">
                        <li class="tab active" data-tab="description">Description</li>
                        <li class="tab" data-tab="reviews">Feedback</li>
                        <li class="tab" data-tab="qna">Q&A</li>
                    </ul>
                    <div class="tab-content active" id="description">
                        <h2>Description</h2>
                        <p>${product.productDescription}</p>
                    </div>
                    <div class="tab-content" id="reviews">
                        <h2>Feedback</h2>
                        <p>Show reviews from customers.</p>
                    </div>
                    <div class="tab-content" id="qna">
                        <h2>Q&A</h2>
                        <p>Where customers ask questions and get answers.</p>
                    </div>
                </div>

                <!-- Related Products Section (demo tĩnh) -->
                <div class="related-products-section">
                    <h2>Related products</h2>
                    <div class="related-products">
                        <div class="product-item">
                            <img src="https://via.placeholder.com/150" alt="related1" />
                            <p>Related product name 1</p>
                        </div>
                        <div class="product-item">
                            <img src="https://via.placeholder.com/150" alt="related2" />
                            <p>Related product name 2</p>
                        </div>
                        <div class="product-item">
                            <img src="https://via.placeholder.com/150" alt="related3" />
                            <p>Related product name 3</p>
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
     <script src="https://kit.fontawesome.com/bf61fecb7c.js" crossorigin="anonymous"></script>
        <!-- Bootstrap & JQuery JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" 
                integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" 
        crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="assets/js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="assets/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" 
                integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" 
        crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" 
                integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" 
        crossorigin="anonymous"></script>
        <script type="text/javascript" src="assets/js/javascript.js"></script>
        <script type="text/javascript" src="assets/js/dropdown.js"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
<style>
    .product-item img {
        width: 270px;
        height: 270px;
        object-fit: cover; /* Đảm bảo ảnh không bị méo */
    }

    .product-grid {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
        justify-content: space-between;
    }

    .product-item {
        width: 270px;
        text-align: center;
        margin: 10px;
    }

    .product-item h4 {
        font-size: 16px;
        margin-top: 10px;
    }

    .product-item p {
        font-size: 14px;
        color: #777;
    }

    .price {
        font-size: 16px;
        font-weight: bold;
        margin-top: 10px;
    }
</style>

