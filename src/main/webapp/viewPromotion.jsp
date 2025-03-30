<%-- 
    Document   : viewPromotion
    Created on : Mar 16, 2025, 7:48:55 AM
    Author     : THANH THAO
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="en_US" />
<!DOCTYPE html>
<html>
    <head>
        <title>Quản Lý Khuyến Mãi</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa;
                padding: 20px;
            }
            .container {
                max-width: 1200px;
                margin: 0 auto;
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            .table th, .table td {
                vertical-align: middle;
                text-align: center;
            }
            .original-price {
                text-decoration: line-through;
                color: #888;
            }
            .discounted-price {
                color: red;
                font-weight: bold;
            }
            .alert {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center mb-4">Quản Lý Khuyến Mãi</h2>

            <!-- Hiển thị thông báo thành công hoặc lỗi -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">
                    ${successMessage}
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- Bảng danh sách khuyến mãi -->
            <table class="table table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>Tên Sản Phẩm</th>
                        <th>Giá Gốc</th>
                        <th>Giá Sau Khuyến Mãi</th>
                        <th>Phần Trăm Giảm Giá</th>
                        <th>Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="promotion" items="${productPromotions}">
                        <tr>
                            <td>${promotion.productName}</td>
                            <td class="original-price">
                                <fmt:formatNumber value="${promotion.originalPrice}" pattern="#,##0" /> VND
                            </td>
                            <td class="discounted-price">
                                <fmt:formatNumber value="${promotion.discountedPrice}" pattern="#,##0" /> VND
                            </td>
                            <td>${promotion.discountPercentage}%</td>
                            <td>
                                <a href="ProductPromotionController?action=edit&productId=${promotion.productId}&promotionId=${promotion.promotionId}" class="btn btn-warning btn-sm">Sửa</a>
                                <a href="ProductPromotionController?action=delete&productId=${promotion.productId}&promotionId=${promotion.promotionId}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa khuyến mãi này?')">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty productPromotions}">
                        <tr>
                            <td colspan="5" class="text-center">Không có khuyến mãi nào.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
            <a href="${pageContext.request.contextPath}/manageStaff.jsp" class="btn btn-secondary w-100 mt-3">Quay Lại</a>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>