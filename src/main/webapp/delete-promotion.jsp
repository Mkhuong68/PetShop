<%-- 
    Document   : delete-promotion
    Created on : Feb 20, 2025, 10:03:01 PM
    Author     : THANH THAO
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="DAOs.PromotionDAO, Model.Promotion" %>

<%
    String errorMessage = null;
    PromotionDAO dao = new PromotionDAO();
    Promotion promo = null;

    // Kiểm tra nếu có ID truyền vào
    try {
        int promotionId = Integer.parseInt(request.getParameter("id"));
        promo = dao.getPromotionById(promotionId);

        if (promo == null) {
            errorMessage = "Khuyến mãi không tồn tại!";
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            dao.deletePromotion(promotionId);
            response.sendRedirect("manage-promotions.jsp?msg=deleted");
            return; // Kết thúc JSP ngay sau khi redirect
        }
    } catch (NumberFormatException e) {
        errorMessage = "ID không hợp lệ!";
    }
%>

<html>
<head>
    <title>delete promotion</title>
</head>
<body>
    <h2>confirm deletion of promotion</h2>

    <% if (errorMessage != null) { %>
        <p style="color: red;"><%= errorMessage %></p>
        <a href="manage-promotions.jsp">back to list</a>
    <% } else { %>
        <p>are you sure you want to delete the promotion ?"<b><%= promo.getPromotionName() %></b>"?</p>
        <form method="POST">
            <input type="hidden" name="id" value="<%= promo.getPromotionId() %>">
            <input type="submit" value="Xóa">
            <a href="manage-promotions.jsp">cancel</a>
        </form>
    <% } %>
</body>
</html>
