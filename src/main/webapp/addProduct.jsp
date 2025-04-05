<%@page import="DB.DBConnection"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, Model.Category, DAOs.CategoryDAO" %>
<%
    Connection conn = DBConnection.getConnection();
    CategoryDAO categoryDAO = new CategoryDAO(conn);
    List<Category> categories = categoryDAO.getAllCategories();
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Product</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
            }

            h2 {
                text-align: center;
                color: #333;
            }

            .form-container {
                background-color: #fff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                max-width: 500px;
                margin: 0 auto;
            }

            label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
            }

            input[type="text"],
            input[type="number"],
            select {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            button {
                background-color: #8AAAE5;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                width: 30%;
                font-size: 16px;
            }

            button-back{
                background-color: #8AAAE5;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }

            button:hover {
                background-color: #2980b9;
            }

            a {
                display: block;
                text-align: center;
                margin-top: 20px;
                text-decoration: none;
                color: #007bff;
            }

            a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <h2>Add Product</h2>
        <form action="<%= request.getContextPath()%>/manageProduct" method="post" class="form-container">
            <input type="hidden" name="action" value="add">

            <label>Product Name:</label>
            <input type="text" name="name" required><br>

            <label>Description:</label>
            <input type="text" name="description" required><br>

            <label>Price:</label>
            <input type="number" name="price" required><br>

            <label>Image:</label>
            <input type="text" name="image"><br>

            <label>Category:</label>
            <select name="category" required>
                <option value="">Select Category</option>
                <% for (Category category : categories) {%>
                <option value="<%= category.getCategoryName()%>"><%= category.getCategoryName()%></option>
                <% }%>
            </select><br>

            <label>Stock Quantity:</label>
            <input type="number" name="stock" required><br>

            <button type="submit">Add</button>
        </form>
        <a href="manageProduct.jsp">
            <button class="button-back">Back</button>
        </a>

    </body>
</html>