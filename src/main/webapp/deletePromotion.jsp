<%-- 
    Document   : deletePromotion
    Created on : Feb 20, 2025, 10:03:01 PM
    Author     : THANH THAO
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Delete Promotion</title>
    </head>
    <body>
        <h2>Confirm Delete Promotion</h2>
        <p>Are you sure you want to delete the promotion with ID: ${promotion.promotionId}?</p>
        <form action="ProductPromotionController" method="post">
            <input type="hidden" name="action" value="delete"/>
            <input type="hidden" name="productId" value="${promotion.productId}"/>
            <input type="hidden" name="promotionId" value="${promotion.promotionId}"/>
            <input type="submit" value="Delete"/>
        </form>

        <a href="ProductPromotionController?action=view">Back to Promotion List</a>
    </body>
</html>