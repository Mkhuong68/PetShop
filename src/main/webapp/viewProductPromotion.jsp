<%-- 
    Document   : viewProductPromotions
    Created on : Mar 16, 2025, 7:48:55 AM
    Author     : THANH THAO
--%>

<%@ page import="java.util.*, Model.ProductPromotion" %>
<html>
    <head>
        <title>Product Promotions</title>
    </head>
    <body>
        <h1>Product Promotions</h1>

        <form action="ProductPromotionController" method="post">
            <h2>Add New Product Promotion</h2>
            <label>Product ID: <input type="text" name="productId" required /></label><br>
            <label>Promotion ID: <input type="text" name="promotionId" required /></label><br>
            <label>Original Price: <input type="text" id="originalPrice" name="originalPrice" required /></label><br>
            <label>Discount Percentage: <input type="text" id="discountPercentage" name="discountPercentage" required /></label><br>
            <button type="submit">Add Promotion</button>
        </form>

        <table border="1">
    <thead>
        <tr>
            <th>Product ID</th>
            <th>Promotion ID</th>
            <th>Original Price</th>
            <th>Discounted Price</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <% 
            List<ProductPromotion> productPromotions = (List<ProductPromotion>) request.getAttribute("productPromotions");
            if (productPromotions != null && !productPromotions.isEmpty()) {
                for (ProductPromotion productPromotion : productPromotions) {
        %>
        <tr>
            <td><%= productPromotion.getProductId() %></td>
            <td><%= productPromotion.getPromotionId() %></td>
            <td><%= productPromotion.getOriginalPrice() %></td>
            <td><%= productPromotion.getDiscountedPrice() %></td>
            <td>
                <a href="ProductPromotionController?action=delete&productId=<%= productPromotion.getProductId() %>&promotionId=<%= productPromotion.getPromotionId() %>">Delete</a>
            </td>
        </tr>
        <% } } else { %>
        <tr>
            <td colspan="5">No Product Promotions available</td>
        </tr>
        <% } %>
    </tbody>
</table>

        <script>
            document.getElementById('originalPrice').addEventListener('input', calculateDiscount);
            document.getElementById('discountPercentage').addEventListener('input', calculateDiscount);

            function calculateDiscount() {
                var originalPrice = parseFloat(document.getElementById('originalPrice').value);
                var discountPercentage = parseFloat(document.getElementById('discountPercentage').value);

                if (isNaN(originalPrice) || isNaN(discountPercentage))
                    return;

                var discountedPrice = originalPrice - (originalPrice * (discountPercentage / 100));
                document.getElementById('discountedPrice').value = discountedPrice.toFixed(2);  
            }
        </script>

    </body>
</html>
