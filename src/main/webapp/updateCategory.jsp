<%-- 
    Document   : updateCategory
    Created on : Mar 8, 2025, 8:55:03 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Category" %>
<html>
<head>
    <title>Update Category</title>
</head>
<body>
    <h2>Update Category</h2>
    <%
        Category category = (Category) request.getAttribute("category");
    %>
    <form action="CategoryController" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= category.getCategoryId() %>">
        Name: <input type="text" name="name" value="<%= category.getCategoryName() %>" required><br>
        Description: <input type="text" name="description" value="<%= category.getCategoryDescription() %>" required><br>
        Parent Category ID: <input type="text" name="parentId" value="<%= category.getParentCategoryId() %>"><br>
        Hidden: <input type="checkbox" name="hidden" <%= category.isIsHidden() ? "checked" : "" %>><br>
        <input type="submit" value="Update Category">
    </form>
    <a href="CategoryController?action=list">Back to List</a>
</body>
</html>

