<%-- 
    Document   : edit-promotion
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
    <title>Edit Promotion</title>
</head>
<body>
    <h2>Edit Promotion</h2>

    <%
        // Lấy ID từ URL
        int promotionId = Integer.parseInt(request.getParameter("id"));
        PromotionDAO dao = new PromotionDAO();
        Promotion promo = dao.getPromotionById(promotionId);

        if (promo == null) {
            out.println("<p style='color:red;'>Promotion not found.</p>");
        } else {
    %>

    <form action="edit-promotion.jsp?id=<%= promotionId %>" method="POST">
        <label>Promotion Name:</label>
        <input type="text" name="promotionName" value="<%= promo.getPromotionName() %>" required><br>

        <label>Image URL:</label>
        <input type="text" name="promotionImage" value="<%= promo.getPromotionImage() %>"><br>

        <label>Description:</label>
        <textarea name="description" required><%= promo.getDescription() %></textarea><br>

        <label>Discount (%):</label>
        <input type="number" name="discountRate" value="<%= promo.getDiscountRate() %>" min="0" max="100" required><br>

        <label>Start Date:</label>
        <input type="date" name="startDate" value="<%= promo.getStartDate() %>" required><br>

        <label>End Date:</label>
        <input type="date" name="endDate" value="<%= promo.getEndDate() %>" required><br>

        <label>Priority:</label>
        <input type="number" name="priority" value="<%= promo.getPriority() %>" min="1" required><br>

        <input type="submit" value="Update Promotion">
        <a href="manage-promotions.jsp">Cancel</a>
    </form>

    <%
        }
    %>

    <%
        // Xử lý cập nhật khi submit form
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String priorityStr = request.getParameter("priority");
            int priority = promo.getPriority(); // Lấy giá trị cũ

            if (priorityStr != null && !priorityStr.trim().isEmpty()) {
                try {
                    priority = Integer.parseInt(priorityStr);
                } catch (NumberFormatException e) {
                    priority = promo.getPriority(); // Nếu nhập sai, giữ giá trị cũ
                }
            }

            Promotion updatedPromo = new Promotion(
                promo.getPromotionId(),
                request.getParameter("promotionName"),
                request.getParameter("promotionImage"),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("discountRate")),
                LocalDate.parse(request.getParameter("startDate")),
                LocalDate.parse(request.getParameter("endDate")),
                priority,
                promo.getCreatedBy(),
                promo.getIsHidden(),
                promo.getCreatedDate(),
                LocalDateTime.now()
            );

            dao.updatePromotion(updatedPromo);
            response.sendRedirect("manage-promotions.jsp");
        }
    %>
</body>
</html>
