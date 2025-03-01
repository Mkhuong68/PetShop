<%-- 
    Document   : manage-promotion
    Created on : Feb 20, 2025, 10:03:01 PM
    Author     : THANH THAO
--%>
<%@ page import="java.util.List"%>
<%@ page import="DAOs.PromotionDAO, Model.Promotion" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Promotion Management</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid black;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .delete-form {
                display: inline;
            }
        </style>
    </head>
    <body>
        <h2>Promotion Management</h2>

        <table>
            <tr>
                <th>ID</th>
                <th>Promotion Name</th>
                <th>Image</th> <!-- 🔹 Thêm cột hiển thị ảnh -->
                <th>Discount (%)</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Actions</th>
            </tr>
            <%
                PromotionDAO dao = new PromotionDAO();
                List<Promotion> promotions = dao.getAllPromotions();

                if (promotions == null || promotions.isEmpty()) {
            %>
            <tr>
                <td colspan="7" style="text-align: center;">No promotions available.</td>
            </tr>
            <%
            } else {
                for (Promotion promo : promotions) {
            %>
            <tr>
                <td><%= promo.getPromotionId()%></td>
                <td><%= promo.getPromotionName()%></td>
                <td>
                    <% if (promo.getPromotionImage() != null && !promo.getPromotionImage().isEmpty()) {%>
                    <img src="<%= promo.getPromotionImage()%>" 
                         alt="Promotion Image" 
                         width="100" 
                         onerror="this.onerror=null; this.src='https://via.placeholder.com/100?text=No+Image';">
                    <% } else { %>
                    <span>No image available</span>
                    <% }%>
                </td>

                <td><%= promo.getDiscountRate()%>%</td>
                <td><%= promo.getStartDate()%></td>
                <td><%= promo.getEndDate()%></td>
                <td>
                    <a href="edit-promotion.jsp?id=<%= promo.getPromotionId()%>">Edit</a>
                    <a href="DeletePromotionServlet?id=<%= promo.getPromotionId()%>" onclick="return confirm('Are you sure you want to delete this promotion?')">Delete</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>


        <br>
        <a href="add-promotion.jsp">Add New Promotion</a>
    </body>
</html>
