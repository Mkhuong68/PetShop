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

<style>
        .form-container {
            width: 40%;
            margin: 50px auto;
            padding: 20px;
            background: #f8d7da;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        h2 {
            text-align: center;
            color: #721c24;
        }

        p {
            font-size: 16px;
            color: #721c24;
        }

        input[type="submit"] {
            background: #dc3545;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
            width: 100%;
            margin-top: 10px;
        }

        input[type="submit"]:hover {
            background: #c82333;
        }

        .cancel-link {
            display: inline-block;
            margin-top: 15px;
            color: #155724;
            text-decoration: none;
            font-weight: bold;
        }

        .cancel-link:hover {
            text-decoration: underline;
        }
    </style>
