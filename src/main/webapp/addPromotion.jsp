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
        <title>Add Promotion</title>
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
            <h2 class="text-center mb-4">Add Promotion</h2>

            <!-- Display error message if any -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- Add promotion form -->
            <form action="ProductPromotionController" method="post">
                <input type="hidden" name="action" value="add">
                <div class="form-group">
                    <label for="productId">Product Name:</label>
                    <select name="productId" id="productId" class="form-control" required onchange="updateOriginalPrice()">
                        <option value="">-- Select a product --</option>
                        <c:forEach var="product" items="${products}">
                            <option value="${product.productId}" data-price="${product.originalPrice}">${product.productName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="originalPrice">Original Price:</label>
                    <input type="text" id="originalPrice" class="form-control" readonly value="">
                </div>
                <div class="form-group">
                    <label for="discountPercentage">Discount Percentage (%):</label>
                    <input type="number" name="discountPercentage" id="discountPercentage" class="form-control" min="0" max="100" required>
                </div>
                <div class="form-group">
                    <label for="validFrom">Start Date:</label>
                    <input type="date" name="validFrom" id="validFrom" required>
                    <div class="date-display" id="validFromDisplay"></div>
                </div>
                <div class="form-group">
                    <label for="validTo">End Date:</label>
                    <input type="date" name="validTo" id="validTo" required>
                    <div class="date-display" id="validToDisplay"></div>
                </div>
                <button type="submit" class="btn btn-primary w-100">Add Promotion</button>
                <a href="ProductPromotionController?action=view" class="btn btn-secondary w-100 mt-3">Back</a>
            </form>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            function updateOriginalPrice() {
                const productSelect = document.getElementById("productId");
                const originalPriceInput = document.getElementById("originalPrice");
                const selectedOption = productSelect.options[productSelect.selectedIndex];
                const price = selectedOption.getAttribute("data-price");

                if (price) {
                    originalPriceInput.value = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'VND'}).format(price);
                } else {
                    originalPriceInput.value = "";
                }
            }
        </script>
    </body>
</html>