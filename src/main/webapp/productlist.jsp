<%-- 
    Document   : productlist
    Created on : Feb 28, 2025, 5:14:44 PM
    Author     : Diem Quynh
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Service.CartService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- CSS -->
        <link rel="stylesheet" type="text/css" href="assets/css/global.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="css/responsive.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" 
              integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" 
              crossorigin="anonymous">
        <link rel="icon" href="Pet Heaven.png" type="image/png">
        <title>Product List</title>
    </head>
    <body class="bgc">
        <c:set var="cartCount" value="0" />
        <c:if test="${not empty sessionScope.account}">
            <c:set var="cartCount" value="${CartService.getCartCount(pageContext.request)}" />
        </c:if>

        <div class="wrap">
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
                                        <input type="text" placeholder="Search..." />
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

            <!-- BANNER -->
            <div class="container-sidebar">
                <!-- Filter Form -->
                <form id="filterForm" action="ProductList" method="get" onsubmit="combineFilterAndSortData()">
                    <aside class="sidebar">
                        <div class="filter-block">
                            <h3>Product Category</h3>
                            <ul>
                                <c:forEach var="category" items="${categories}">
                                    <li>
                                        <label>
                                            <input type="checkbox" name="category" value="${category.categoryId}" 
                                                   <c:if test="${param.category == category.categoryId}">checked</c:if> />
                                            ${category.categoryName}
                                        </label>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="filter-block">
                            <h3>Price</h3>
                            <ul>
                                <li>
                                    <label>
                                        <input type="checkbox" name="priceRange" value="0-100000" />
                                        Under 100,000 VND
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <input type="checkbox" name="priceRange" value="100000-200000" />
                                        100,000 VND - 200,000 VND
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <input type="checkbox" name="priceRange" value="200000-300000" />
                                        200,000 VND - 300,000 VND
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <input type="checkbox" name="priceRange" value="300000-500000" />
                                        300,000 VND - 500,000 VND
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <input type="checkbox" name="priceRange" value="500000-1000000" />
                                        500,000 VND - 1,000,000 VND
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <input type="checkbox" name="priceRange" value="1000000-1500000" />
                                        1,000,000 VND - 1,500,000 VND
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <input type="checkbox" name="priceRange" value="1500000-2000000" />
                                        1,500,000 VND - 2,000,000 VND
                                    </label>
                                </li>
                            </ul>
                        </div>

                        <div class="filter-block">
                            <h3>Sort</h3>
                            <select id="sort" name="sort" class="form-control mb-3">
                                <option value="">-- Choose sort order --</option>
                                <option value="popularity">Most popular</option>
                                <option value="price-asc">Price: Low to High</option>
                                <option value="price-desc">Price: High to Low</option>
                                <option value="newest">Newest</option>
                            </select>
                        </div>

                        <!-- Apply button at the end of the sidebar -->
                        <div class="filter-actions">
                            <button type="submit" class="apply-button">Apply</button>
                        </div>
                    </aside>

                    <main class="main-content">
                        <!-- Product list -->
                        <div class="product-container">
                            <div class="product-grid" id="product-list">
                                <c:forEach var="product" items="${productList}">
                                    <div class="product-item">
                                        <a href="ProductDetail?productId=${product.productId}">
                                            <img src="${product.productImage}" alt="${product.productName}">
                                            <h4>${product.productName}</h4>
                                            <p>${product.productDescription}</p>
                                            <span class="price">
                                                <fmt:formatNumber value="${product.productPrice}" pattern="#,##0" /> VND
                                            </span>
                                        </a>
                                        <!-- Optionally, you can add an Add to Cart button here if needed -->
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </main>
                </form>
            </div> <!-- end .container-sidebar -->

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
                                <i style="width: 22px;" class="fas fa-map-marker-alt"></i>
                                Ninh Kieu, Can Tho
                            </address>
                        </div>
                        <div class="col-md-3">
                            <h5>
                                ABOUT US
                                <span class="line-remove" style="width: 78px;"></span>
                            </h5>
                            <ul>
                                <li><a href="about.html" title="About">About</a></li>
                                <li><a href="product.html" title="Products">Products</a></li>
                                <li><a href="news.html" title="Community">Community</a></li>
                                <li><a href="contact.html" title="Contact">Contact</a></li>
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

        <!-- JavaScript: Combine filter data with sort data -->
        <script>
            // Preselect the category if one was passed in the URL
            document.addEventListener('DOMContentLoaded', function () {
                // Get the category from URL parameters
                const urlParams = new URLSearchParams(window.location.search);
                const categoryId = urlParams.get('category');

                if (categoryId) {
                    // Find and check the checkbox for this category
                    const checkbox = document.querySelector(`input[name="category"][value="${categoryId}"]`);
                    if (checkbox) {
                        checkbox.checked = true;
                    }
                }

                // Also preselect sort option if in URL
                const sortOption = urlParams.get('sort');
                if (sortOption) {
                    const sortSelect = document.getElementById('sort');
                    if (sortSelect) {
                        sortSelect.value = sortOption;
                    }
                }
            });
        </script>

        <!-- Font Awesome -->
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

        <!-- Script to refresh cart count on the taskbar -->
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

