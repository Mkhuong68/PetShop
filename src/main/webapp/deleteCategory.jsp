<%-- 
    Document   : manageCategory
    Created on : Mar 8, 2025, 7:30:07 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Category</title>
</head>
<body>
    <h2>Delete Category</h2>
    <p>Are you sure you want to delete this category?</p>
    <form action="CategoryController" method="post">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
        <input type="submit" value="Delete">
        <a href="CategoryController?action=list">Cancel</a>
    </form>
</body>
</html>

