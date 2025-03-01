<%-- 
    Document   : add-promotion
    Created on : Feb 20, 2025, 10:03:01 PM
    Author     : THANH THAO
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="DAOs.PromotionDAO" %>
<%@ page import="Model.Promotion" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalDateTime" %>

<html>
<head>
    <title>Add New Promotion</title>
</head>
<body>
    <h2>Add New Promotion</h2>

    <form action="add-promotion.jsp" method="POST">
        <label>Promotion Name:</label>
        <input type="text" name="promotionName" required><br>

        <label>Image URL:</label>
        <input type="text" name="promotionImage" placeholder="Enter image URL"><br>

        <label>Description:</label>
        <textarea name="description" required></textarea><br>

        <label>Discount (%):</label>
        <input type="number" name="discountRate" min="0" max="100" required><br>

        <label>Start Date:</label>
        <input type="date" name="startDate" required><br>

        <label>End Date:</label>
        <input type="date" name="endDate" required><br>

        <label>Priority:</label>
        <input type="number" name="priority" min="1" required><br>

        <input type="submit" value="Add Promotion">
        <a href="manage-promotions.jsp">Cancel</a>
    </form>

    <%
        // Xử lý dữ liệu khi submit form
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String priorityStr = request.getParameter("priority");
            int priority = 1; // Giá trị mặc định nếu người dùng không nhập

            if (priorityStr != null && !priorityStr.trim().isEmpty()) {
                try {
                    priority = Integer.parseInt(priorityStr);
                } catch (NumberFormatException e) {
                    priority = 1; // Nếu nhập sai, đặt giá trị mặc định
                }
            }

            PromotionDAO dao = new PromotionDAO();
            Promotion promotion = new Promotion(
                0,
                request.getParameter("promotionName"),
                request.getParameter("promotionImage"),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("discountRate")),
                LocalDate.parse(request.getParameter("startDate")),
                LocalDate.parse(request.getParameter("endDate")),
                priority,
                1,
                false,
                LocalDateTime.now(),
                null
            );

            dao.addPromotion(promotion);
            response.sendRedirect("manage-promotions.jsp");
        }
    %>
</body>
</html>
