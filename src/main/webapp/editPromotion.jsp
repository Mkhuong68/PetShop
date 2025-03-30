<%-- 
    Document   : editPromotion
    Created on : Mar 29, 2025, 4:05:21 PM
    Author     : THANH THAO
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="en_US" />
<!DOCTYPE html>
<html>
    <head>
        <title>Cập Nhật Khuyến Mãi</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa;
                padding: 20px;
            }
            .container {
                max-width: 600px;
                margin: 0 auto;
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            .form-group {
                margin-bottom: 15px;
            }
            .alert {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center mb-4">Cập Nhật Khuyến Mãi</h2>

            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- Form cập nhật khuyến mãi -->
            <form action="ProductPromotionController" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="productId" value="${promotion.productId}">
                <input type="hidden" name="promotionId" value="${promotion.promotionId}">
                <div class="form-group">
                    <label for="productName">Tên Sản Phẩm:</label>
                    <input type="text" name="productName" id="productName" class="form-control" value="${promotion.productName}" readonly>
                </div>
                <div class="form-group">
                    <label for="originalPrice">Giá Gốc:</label>
                    <input type="text" name="originalPrice" id="originalPrice" class="form-control" value="<fmt:formatNumber value='${promotion.originalPrice}' pattern='#,##0' /> VND" readonly>
                </div>
                <div class="form-group">
                    <label for="discountPercentage">Phần Trăm Giảm Giá (%):</label>
                    <input type="number" name="discountPercentage" id="discountPercentage" class="form-control" value="${promotion.discountPercentage}" min="0" max="100" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Cập Nhật</button>
                <a href="ProductPromotionController?action=list" class="btn btn-secondary w-100 mt-3">Quay Lại</a>
            </form>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>