<%-- 
    Document   : productlist
    Created on : Feb 28, 2025, 5:14:44 PM
    Author     : tvhun
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Service.CartService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
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
        <%-- Thiết lập biến cartCount từ CartService nếu người dùng đã đăng nhập --%>
        <c:set var="cartCount" value="0" />
        <c:if test="${not empty sessionScope.account}">
            <c:set var="cartCount" value="${CartService.getCartCount(pageContext.request)}" />
        </c:if>

        <div class="wrap">
            <header>
                <nav class="container">
                    <div class="logo">
                        <a href="index.html"><img src="assets/images/Pet Heaven.png" alt="PetShop"></a>
                    </div>
                    <div class="menu" data-show="0">
                        <div class="d-flex h-100 justify-content-center align-items-center">
                            <ul>
                                <li class="search-item">
                                    <form class="search-form" action="#" method="get">
                                        <input type="text" placeholder="Tìm kiếm..." />
                                        <button type="submit">
                                            <i class='bx bx-search'></i>
                                        </button>
                                    </form>
                                </li>
                                <li class="active">
                                    <a href="/Home">
                                        <i class='bx bxs-home'></i>
                                    </a>
                                </li>
                                <li class="products">
                                    <a href="/ProductList">
                                        <i class='bx bx-archive'></i>
                                    </a>
                                </li>
                                <li class="community">
                                    <a href="news.html">
                                        <i class='bx bx-globe'></i> 
                                    </a>
                                </li>
                                <li>
                                    <a href="about.html" class="account-link">
                                        <i class='bx bx-user'></i>
                                        <span class="account-text"></span>
                                    </a>
                                </li>
                                <li>
                                    <a href="/Cart" class="cart-link">
                                        <i class='bx bx-cart'></i>
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
                <!-- Form bộ lọc (Filter Form) -->
                <form id="filterForm" action="ProductList" method="get">
                    <aside class="sidebar">
                        <div class="filter-block">
                            <h3>Product Category</h3>
                            <ul>
                                <c:forEach var="category" items="${categories}">
                                    <li>
                                        <label>
                                            <!-- Không auto submit ở đây -->
                                            <input type="checkbox" name="category" value="${category.categoryId}" />
                                            ${category.categoryName}
                                        </label>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="filter-block">
                            <h3>Giá</h3>
                            <ul>
                                <li>
                                    <label>
                                        <input type="checkbox" name="priceRange" value="0-100000" />
                                        Giá dưới 100,000 VND
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
                                        500,000 VND - 1.000,000 VND
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <input type="checkbox" name="priceRange" value="1000000-1500000" />
                                        1.000,000 VND - 1.500,000 VND
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <input type="checkbox" name="priceRange" value="1500000-2000000" />
                                        1.500,000 VND - 2.000,000 VND
                                    </label>
                                </li>
                            </ul>
                        </div>
                    </aside>
                </form>

                <!-- Form sắp xếp (Sort Form) -->
                <form id="sortForm" action="ProductList" method="get" onsubmit="combineFilterAndSortData()">
                    <main class="main-content">
                        <div class="sort-select">
                            <label for="sort">Arrange by: </label>
                            <select id="sort" name="sort">
                                <option value="">-- Select --</option>
                                <option value="popularity">Popular</option>
                                <option value="price-asc">Increasing price</option>
                                <option value="price-desc">Decreasing price</option>
                                <option value="newest">New</option>
                            </select>
                        </div>
                        <!-- Nút submit dùng để gửi cả dữ liệu sắp xếp và bộ lọc -->
                        <button type="submit">Apply Filter &amp; Sort</button>

                        <!-- Danh sách sản phẩm -->
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
                                        <!-- Có thể bổ sung nút Add to Cart ngay ở đây nếu cần -->
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
                                Ninh Kiều, Cần Thơ 
                            </address>
                        </div>
                        <div class="col-md-3">
                            <h5>
                                ABOUT US
                                <span class="line-remove" style="width: 78px;"></span>
                            </h5>
                            <ul>
                                <li><a href="about.html" title="Giới thiệu">About</a></li>
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

        <!-- JavaScript: kết hợp dữ liệu bộ lọc vào form sắp xếp -->
        <script>
            function combineFilterAndSortData() {
                // Lấy form bộ lọc và form sắp xếp
                var filterForm = document.getElementById('filterForm');
                var sortForm = document.getElementById('sortForm');

                // Xóa các hidden inputs cũ nếu có (các input có class "hiddenFilter")
                var existingHidden = sortForm.querySelectorAll('.hiddenFilter');
                existingHidden.forEach(function (input) {
                    input.parentNode.removeChild(input);
                });

                // Lấy tất cả checkbox được chọn từ form bộ lọc
                var checkedBoxes = filterForm.querySelectorAll('input[type="checkbox"]:checked');
                // Với mỗi checkbox được chọn, tạo input ẩn và thêm vào form sắp xếp
                checkedBoxes.forEach(function (box) {
                    var hiddenInput = document.createElement("input");
                    hiddenInput.type = "hidden";
                    hiddenInput.name = box.name; // "category" hoặc "priceRange"
                    hiddenInput.value = box.value;
                    hiddenInput.className = "hiddenFilter";
                    sortForm.appendChild(hiddenInput);
                });
            }
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
