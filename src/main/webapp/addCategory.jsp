<%-- 
    Document   : addCategory
    Created on : Mar 8, 2025, 8:54:31 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="manageStaff.jsp" />

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

    <style>
        .form-container {
            width: 50%;
            margin: 20px auto;
            padding: 20px;
            background: #8AAAE5;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
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
            border: 1px solid #8AAAE5;
            border-radius: 5px;
            display: block;
        }

        button, input[type="submit"] {
            background: #8AAAE5;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
            width: 100%;
            margin-top: 10px;
        }

        button:hover, input[type="submit"]:hover {
            background: #8AAAE5;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #8AAAE5;
            text-decoration: none;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>