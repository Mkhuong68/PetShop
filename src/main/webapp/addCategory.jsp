<%-- 
    Document   : addCategory
    Created on : Mar 8, 2025, 8:54:31 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add Category</title>
</head>
<body>
    <h2>Add New Category</h2>
    <form action="CategoryController" method="post">
        <input type="hidden" name="action" value="insert">
        Name: <input type="text" name="name" required><br>
        Description: <input type="text" name="description" required><br>
        Parent Category ID: <input type="text" name="parentId"><br>
        Hidden: <input type="checkbox" name="hidden"><br>
        <input type="submit" value="Add Category">
    </form>
    <a href="CategoryController?action=list">Back to List</a>
</body>
</html>

