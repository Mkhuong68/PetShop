<%-- 
    Document   : cart
    Created on : Mar 1, 2025, 1:47:42 AM
    Author     : tvhun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Service.CartService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Cart</title>
        <link rel="stylesheet" type="text/css" href="assets/css/global.css" />
        <link rel="stylesheet" type="text/css" href="assets/css/cart.css" />
        <link rel="stylesheet" type="text/css" href="assets/css/style.css" />
        <link rel="stylesheet" type="text/css" href="assets/css/responsive.css" />
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
        <link rel="icon" href="Pet Heaven.png" type="image/png" />
        <style>
            .cart-item-checkbox {
                margin-right: 10px;
            }
            .item-subtotal {
                margin-top: 5px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <%-- Sử dụng CartService để lấy số lượng giỏ hàng ngay khi load trang --%>
        <c:set var="cartCount" value="0" />
        <c:if test="${not empty sessionScope.account}">
            <c:set var="cartCount" value="${CartService.getCartCount(pageContext.request)}" />
        </c:if>

        <div id="main-content" class="wrap">
            <header class="taskbar">
                <nav class="container">
                    <div class="logo">
                        <a href="login.html"><img src="assets/images/Pet Heaven.png" alt="PetShop"></a>
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
                                    <a href="/ProductList"><i class="bx bx-archive"></i></a>
                                    <div class="product-dropdown">
                                        <div class="column">
                                            <ul>
                                                <li><a href="#">Cat Food</a></li>
                                                <li><a href="#">Cat Treats</a></li>
                                                <li><a href="#">Dog Food</a></li>
                                                <li><a href="#">Dog Treats</a></li>
                                            </ul>
                                        </div>
                                        <div class="column">
                                            <ul>
                                                <li><a href="#">Collars & Leashes</a></li>
                                                <li><a href="#">Beds</a></li>
                                                <li><a href="#">Toys</a></li>
                                                <li><a href="#">Apparel</a></li>
                                            </ul>
                                        </div>
                                        <div class="column">
                                            <ul>
                                                <li><a href="#">Cat Litter</a></li>
                                                <li><a href="#">Shampoo</a></li>
                                                <li><a href="#">Odor Control</a></li>
                                            </ul>
                                        </div>
                                        <div class="column">
                                            <ul>
                                                <li><a href="#">Vitamins</a></li>
                                                <li><a href="#">Dental Care</a></li>
                                                <li><a href="#">Flea & Tick</a></li>
                                            </ul>
                                        </div>
                                        <div class="column">
                                            <ul>
                                                <li><a href="#">Carriers</a></li>
                                                <li><a href="#">Strollers</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>
                                <li class="community">
                                    <a href="news.html"><i class="bx bx-globe"></i></a>
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
                                    <a href="/Cart" class="cart-link">
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

            <div class="cart-page">
                <div class="cart-header">
                    <div class="cart-header-left">
                        <input type="checkbox" id="selectAll" />
                        <label for="selectAll">Select All</label>
                    </div>
                    <div class="cart-header-right" id="deleteAll">
                        <a href="/Cart?action=deleteAll">Delete All Selected</a>
                    </div>
                </div>
                <div class="cart-content">
                    <div class="cart-left">
                        <form action="OrderPage" method="get">
                            <input type="hidden" name="action" value="orderFromCart">
                            <c:forEach var="item" items="${cartItems}">
                                <div class="cart-item">
                                    <input type="checkbox" class="cart-item-checkbox" name="selectedItem" value="${item.cartItemId}">
                                    <div class="item-thumb">
                                        <img src="${item.productImage}" alt="${item.productName}" />
                                    </div>
                                    <div class="item-info">
                                        <div class="item-title">${item.productName}</div>
                                        <div class="item-meta">Product Code: ${item.productId}</div>
                                        <div class="item-price-quantity">
                                            <span class="item-price">
                                                <fmt:formatNumber value="${item.finalPrice}" pattern="#,##0" /> VND
                                            </span>
                                            <div class="quantity-control">
                                                <button type="button" class="minus">-</button>
                                                <input type="number" class="quantity-input" data-cartitemid="${item.cartItemId}" value="${item.quantity}" min="1">
                                                <button type="button" class="plus">+</button>
                                            </div>
                                            <!-- Hiển thị subtotal cho từng mục (để update khi thay đổi số lượng) -->
                                            <div class="item-subtotal">
                                                Subtotal: <span name="subtotal" id="item-${item.cartItemId}-total">
                                                    <fmt:formatNumber value="${item.finalPrice * item.quantity}" pattern="#,##0" /> VND
                                                </span>
                                            </div>
                                            <a href="/Cart?action=delete&cartItemId=${item.cartItemId}" class="deleteItem">Delete</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <input type="hidden" name="totalAmount" value="${total}">
                            <button type="submit" class="confirm-btn">Confirm the order</button>
                        </form>
                    </div>
                    <div class="cart-right">
                        <div class="discount-box">
                            <h3>Select or enter an offer.</h3>
                            <form id="voucherForm" action="/Cart" method="post">
                                <input type="hidden" name="action" value="applyVoucher">
                                <input type="text" name="voucherCode" placeholder="Nhập mã giảm giá">
                                <button type="submit">Apply</button>
                            </form>
                        </div>
                        <div class="order-info">
                            <p>
                                <span>Provisional calculation</span>
                                <span id="cartTotal"><fmt:formatNumber value="${total}" pattern="#,##0" /> VND</span>
                            </p>
                            <p><span>Sale</span> <span>0 đ</span></p>
                            <p class="total">
                                <span>Total</span>
                                <span id="cartTotal2"><fmt:formatNumber value="${total}" pattern="#,##0" /> VND</span>
                            </p>
                        </div>  
                    </div>
                </div>
            </div>
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

        <!-- jQuery & Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            // Hàm tính tổng tiền các mục được chọn
            function calculateSelectedTotal() {
                var total = 0;
                $(".cart-item-checkbox:checked").each(function () {
                    var cartItemId = $(this).closest(".cart-item").find(".quantity-input").data("cartitemid");
                    var itemText = $("#item-" + cartItemId + "-total").text();
                    // Chuyển đổi chuỗi "xxx VND" thành số
                    var itemValue = parseFloat(itemText.replace(/[^\d]/g, ""));
                    total += itemValue;
                });

                // Định dạng số tiền theo locale vi_VN
                var formatter = new Intl.NumberFormat('vi-VN');
                var formattedTotal = formatter.format(total);

                // Cập nhật hiển thị tổng tiền
                $("#cartTotal, #cartTotal2").text(formattedTotal + " VND");

                // Nếu không có mục nào được chọn, hiển thị 0
                if ($(".cart-item-checkbox:checked").length === 0) {
                    $("#cartTotal, #cartTotal2").text("0 VND");
                }
            }

            // Khi số lượng thay đổi, gửi AJAX cập nhật và refresh lại thông tin giỏ hàng
            $(".quantity-input").on("change", function () {
                var newQty = $(this).val();
                var cartItemId = $(this).data("cartitemid");
                $.ajax({
                    url: "/Cart",
                    method: "POST",
                    data: {action: "update", cartItemId: cartItemId, quantity: newQty},
                    dataType: "json",
                    success: function (data) {
                        // Cập nhật subtotal của mục được cập nhật
                        $("#item-" + cartItemId + "-total").text(data.itemTotalFormatted + " VND");
                        // Cập nhật tổng tiền giỏ hàng
                        calculateSelectedTotal();
                        // Cập nhật số lượng giỏ hàng (badge)
                        $("#cartCountDisplay").text(data.cartCount);
                    },
                    error: function () {
                        alert("Error updating quantity.");
                    }
                });
            });

            $(".plus").on("click", function () {
                var input = $(this).siblings(".quantity-input");
                input.val(parseInt(input.val()) + 1).trigger("change");
            });

            $(".minus").on("click", function () {
                var input = $(this).siblings(".quantity-input");
                var currentVal = parseInt(input.val());
                if (currentVal > 1) {
                    input.val(currentVal - 1).trigger("change");
                }
            });

            $(".deleteItem").on("click", function (e) {
                e.preventDefault();
                var url = $(this).attr("href");
                $.ajax({
                    url: url,
                    method: "GET",
                    dataType: "json",
                    success: function (data) {
                        // Refresh lại thông tin giỏ hàng sau khi xóa mục
                        $("#cartCountDisplay").text(data.cartCount);
                        location.reload();
                    },
                    error: function () {
                        alert("Error deleting item.");
                    }
                });
            });

            // Xử lý sự kiện khi checkbox được chọn/bỏ chọn
            $("#selectAll").on("change", function () {
                $(".cart-item-checkbox").prop("checked", $(this).prop("checked"));
                calculateSelectedTotal();
                updateDeleteButton();
            });

            $(".cart-item-checkbox").on("change", function () {
                calculateSelectedTotal();
                updateDeleteButton();

                // Nếu bỏ chọn một mục nhưng Select All đang được chọn, hủy chọn Select All
                if (!$(this).prop("checked") && $("#selectAll").prop("checked")) {
                    $("#selectAll").prop("checked", false);
                }

                // Nếu tất cả các mục đều được chọn, chọn luôn Select All
                if ($(".cart-item-checkbox:checked").length === $(".cart-item-checkbox").length) {
                    $("#selectAll").prop("checked", true);
                }
            });

            // Cập nhật trạng thái nút Delete All
            function updateDeleteButton() {
                if ($(".cart-item-checkbox:checked").length > 0) {
                    $("#deleteAll").show();
                } else {
                    $("#deleteAll").hide();
                }
            }

            // Khi trang load, làm mới thông tin giỏ hàng
            $(document).ready(function () {
                // Ban đầu, ẩn nút Delete All
                $("#deleteAll").hide();

                // Làm mới thông tin giỏ hàng
                $.ajax({
                    url: "/Cart?action=getSummary",
                    method: "GET",
                    dataType: "json",
                    success: function (data) {
                        $("#cartCountDisplay").text(data.cartCount);
                        // Không cập nhật tổng tiền ở đây vì nó sẽ được tính bởi calculateSelectedTotal()
                    },
                    error: function () {
                        console.log("Unable to refresh cart summary.");
                    }
                });

                // Tính tổng tiền ban đầu dựa trên các mục đã chọn
                calculateSelectedTotal();

                // Sửa lại hành vi của nút Delete All Selected
                $("#deleteAll a").on("click", function (e) {
                    e.preventDefault();

                    if ($(".cart-item-checkbox:checked").length === 0) {
                        alert("Vui lòng chọn ít nhất một sản phẩm để xóa!");
                        return;
                    }

                    if (confirm("Bạn có chắc chắn muốn xóa các sản phẩm đã chọn?")) {
                        var selectedItems = [];
                        $(".cart-item-checkbox:checked").each(function () {
                            var cartItemId = $(this).val();
                            selectedItems.push(cartItemId);
                        });

                        // Gửi AJAX request để xóa các mục đã chọn
                        $.ajax({
                            url: "/Cart",
                            method: "POST",
                            data: {
                                action: "deleteSelected",
                                selectedItems: selectedItems.join(",")
                            },
                            dataType: "json",
                            success: function (data) {
                                // Refresh thông tin giỏ hàng
                                $("#cartCountDisplay").text(data.cartCount);
                                location.reload();
                            },
                            error: function () {
                                alert("Có lỗi xảy ra khi xóa sản phẩm.");
                            }
                        });
                    }
                });
            });
        </script>
    </body>
</html>
