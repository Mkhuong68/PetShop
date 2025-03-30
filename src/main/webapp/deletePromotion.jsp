<%-- 
    Document   : delete-promotion
    Created on : Feb 20, 2025, 10:03:01 PM
    Author     : THANH THAO
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Xóa Khuyến Mãi</title>
</head>
<body>
    <h2>Xác Nhận Xóa Khuyến Mãi</h2>
    <p>Bạn có chắc chắn muốn xóa khuyến mãi với ID: ${promotion.promotionId} không?</p>
    <form action="ProductPromotionController" method="post">
        <input type="hidden" name="action" value="delete"/>
        <input type="hidden" name="promotionId" value="${promotion.promotionId}"/>
        <input type="submit" value="Xóa"/>
    </form>

    <a href="ProductPromotionController?action=view">Quay lại danh sách khuyến mãi</a>
</body>
</html>
