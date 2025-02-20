<%-- 
    Document   : productManagement
    Created on : Feb 20, 2025, 9:14:27 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Product" %>
<%@ page import="DAOs.ProductDAO" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.annotation.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Product Management</title>
    <link rel="stylesheet" href="styles.css"> <!-- Add CSS file if needed -->
</head>
<body>
    <h1>Product Management</h1>

    <form action="ProductController" method="post">
        <input type="hidden" name="action" value="create">
        <label for="productName">Product Name:</label>
        <input type="text" id="productName" name="productName" required>
        
        <label for="productDescription">Description:</label>
        <input type="text" id="productDescription" name="productDescription" required>
        
        <label for="productPrice">Price:</label>
        <input type="number" id="productPrice" name="productPrice" required step="0.01">
        
        <label for="categoryId">Category ID:</label>
        <input type="number" id="categoryId" name="categoryId" required>
        
        <button type="submit">Add Product</button>
    </form>

    <h2>Product List</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Category ID</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                ProductDAO productDAO = new ProductDAO();
                List<Product> products = productDAO.getAllProducts();
                for (Product product : products) {
            %>
                <tr>
                    <td><%= product.getProductId() %></td>
                    <td><%= product.getProductName() %></td>
                    <td><%= product.getProductDescription() %></td>
                    <td><%= product.getProductPrice() %></td>
                    <td><%= product.getCategoryId() %></td>
                    <td>
                        <form action="ProductController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= product.getProductId() %>">
                            <button type="submit">Delete</button>
                        </form>
                        <form action="ProductController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="<%= product.getProductId() %>">
                            <input type="text" name="productName" placeholder="Name" required>
                            <input type="text" name="productDescription" placeholder="Description" required>
                            <input type="number" name="productPrice" placeholder="Price" required step="0.01">
                            <input type="number" name="categoryId" placeholder="Category ID" required>
                            <button type="submit">Update</button>
                        </form>
                    </td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
