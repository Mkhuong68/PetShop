<%-- 
    Document   : updateCategory
    Created on : Mar 8, 2025, 8:55:03 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Category" %>
<jsp:include page="manageStaff.jsp" />
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

 <style>
        .form-container {
            width: 50%;
            margin: 20px auto;
            padding: 20px;
            background: #f7f7f7;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #333;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
        }

        input[type="text"], input[type="checkbox"] {
            width: calc(100% - 16px);
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            display: block;
        }

        button, input[type="submit"] {
            background: #3498db;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
            width: 100%;
            margin-top: 10px;
        }

        button:hover, input[type="submit"]:hover {
            background: #2980b9;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #2c3e50;
            text-decoration: none;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>

