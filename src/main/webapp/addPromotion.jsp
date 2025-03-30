<%-- 
    Document   : addPromotion
    Created on : Feb 20, 2025, 10:03:01 PM
    Author     : THANH THAO
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="en_US" />
<!DOCTYPE html>
<html>
    <head>
        <title>Thêm Khuyến Mãi</title>
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
            <h2 class="text-center mb-4">Thêm Khuyến Mãi</h2>

            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- Form thêm khuyến mãi -->
            <form action="ProductPromotionController" method="post">
                <input type="hidden" name="action" value="add">
                <div class="form-group">
                    <label for="productName">Tên Sản Phẩm:</label>
                    <select name="productName" id="productName" class="form-control" required>
                        <option value="">-- Chọn sản phẩm --</option>
                        <c:forEach var="productName" items="${productNames}">
                            <option value="${productName}">${productName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Giá Gốc:</label>
                    <select class="form-control" disabled>
                        <c:forEach var="product" items="${products}">
                            <option data-product-name="${product.productName}">
                                <fmt:formatNumber value="${product.originalPrice}" pattern="#,##0" /> VND
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="discountPercentage">Phần Trăm Giảm Giá (%):</label>
                    <input type="number" name="discountPercentage" id="discountPercentage" class="form-control" min="0" max="100" required>
                </div>
                <div class="form-group">
                    <label for="validFrom">Ngày Bắt Đầu:</label>
                    <input type="date" name="validFrom" id="validFrom" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="validTo">Ngày Kết Thúc:</label>
                    <input type="date" name="validTo" id="validTo" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Thêm Khuyến Mãi</button>
                <a href="ProductPromotionController?action=view" class="btn btn-secondary w-100 mt-3">Quay Lại</a>
            </form>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>