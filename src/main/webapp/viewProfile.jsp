<%-- 
    Document   : viewProfile
    Created on : Feb 19, 2025, 11:29:58 PM
    Author     : THANH THAO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ page import="Model.Account" %>

<%
    HttpSession currentSession = request.getSession(false);
    if (currentSession == null || currentSession.getAttribute("account") == null) {
        response.sendRedirect("/login"); // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
        return;
    }
%>
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

        <!-- Chia layout thành 2 cột bằng class "row" -->
        <div class="row">
            <!-- Cột bên trái: Các nút điều hướng -->
            <div class="account-actions">
                <h1 class="text-xl font-bold mb-4">Account Page</h1>

                <a href="javascript:void(0);" onclick="toggleUpdateProfileForm()">Update Profile</a>
                <a href="javascript:void(0);" onclick="toggleUpdatePasswordForm()">Update Password</a>
                <a href=""">My Feedback</a>
                <a href="/CustomerOrderHistoryController" ">List Order Detail History</a>
                <a href="logout">Logout</a>
            </div>

            <!-- Cột bên phải: Hiển thị thông tin tài khoản -->
            <div class="profile-info" id="viewProfile" style="display: block;">
                <!-- Ảnh mặc định hoặc ảnh profile -->
                <div style="margin-bottom: 3%" id="profilePictureContainer" class="profile-icon-wrapper">
                    <box-icon id="defaultIcon" name="user"></box-icon>
                    <img id="profilePicture"
                         src="${sessionScope.account.profileImage != null ? sessionScope.account.profileImage : 'assets/images/profile/default-user.png'}"
                         alt="Profile Picture" class="profile-img"/>

                </div>
                <p ><strong>Full Name:</strong> ${account.firstName} ${account.lastName}</p>
                <p><strong>Email:</strong> ${account.email}</p>
                <p><strong>Phone:</strong> ${account.phoneNumber}</p>
            </div>

            <!-- Form Change Password -->
            <div id="changePasswordForm" class="form-right" style="display: none;">
                <h3>Change Password</h3>
                <form action="changePassword" method="POST">
                    <label for="oldPassword">Current Password</label>
                    <input type="password" id="oldPassword" name="oldPassword" placeholder="Enter current password" required><br><br>

                    <label for="newPassword">New Password</label>
                    <input type="password" id="newPassword" name="newPassword" placeholder="Enter new password" required><br><br>

                    <label for="confirmPassword">Confirm New Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" required><br><br>

                    <button type="submit" class="btn-submit">Change Password</button>
                    <button type="button" class="btn-cancel" onclick="toggleChangePasswordForm()">Cancel</button>
                </form>
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
