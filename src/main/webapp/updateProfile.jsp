<%-- 
    Document   : updateProfile
    Created on : Feb 20, 2025, 10:00:51 PM
    Author     : THANH THAO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ page import="Model.Account" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="assets/css/global.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="css/responsive.css">
        <link rel="stylesheet" type="text/css" href="assets/css/profile.css">

        <script src="https://unpkg.com/boxicons@2.1.1/dist/boxicons.js"></script>
        <script src="assets/js/profile.js"></script>
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

        <body>
            <div class="profile-container">
                <h1>Update Profile</h1>

                <form action="UpdateProfileController" method="POST">
                    
                    <input type="hidden" name="accountId" value="${user.accountId}"/>

                    <label for="email">Email:</label>
                    <input type="email" name="email" value="${user.email}" required/><br/>

                    <label for="phoneNumber">Phone:</label>
                    <input type="text" name="phoneNumber" id="phoneNumber" 
                           pattern="^0\d{9,10}$" 
                           title="Please enter a valid phone number that starts with '0' and contains 10 or 11 digits." 
                           value="${account.phoneNumber}" required>
                    <br>
                    <label for="firstName">First Name:</label>
                    <input type="text" name="firstName" value="${user.firstName}" required/><br/>

                    <label for="lastName">Last Name:</label>
                    <input type="text" name="lastName" value="${user.lastName}" required/><br/>

                    <label for="dateOfBirth">Date of Birth:</label>
                    <input type="date" name="dateOfBirth" value="${user.dateOfBirth}" required/><br/>

                    <label for="gender">Gender:</label>
                    <select name="gender">
                        <option value="Male" ${user.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${user.gender == 'Female' ? 'selected' : ''}>Female</option>
                        <option value="Other" ${user.gender == 'Other' ? 'selected' : ''}>Other</option>
                    </select><br/>

                    <button type="submit">Update</button>
                </form>
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
