<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Category" %>
<html>
<head>
    <title>Update Category</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .form-container {
            width: 50%;
            margin: 50px auto;
            padding: 20px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            color: #555;
        }

        input[type="text"], input[type="checkbox"] {
            width: calc(100% - 16px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus {
            border-color: #3498db;
            outline: none;
        }

        input[type="checkbox"] {
            width: auto;
            margin-right: 10px;
        }

        .button-container {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        button, input[type="submit"], .back-button {
            background: #8AAAE5;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
            width: 40%; /* Adjusted width to fit two buttons */
            font-size: 16px;
            text-align: center;
            text-decoration: none; /* Remove underline from link */
            transition: background 0.3s;
        }

        button:hover, input[type="submit"]:hover, .back-button:hover {
            background: #7a9bc2;
        }

        .back-button {
            display: inline-block; /* Make it behave like a button */
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Update Category</h2>
        <%
            Category category = (Category) request.getAttribute("category");
        %>
        <form action="CategoryController" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= category.getCategoryId() %>">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="<%= category.getCategoryName() %>" required>
            <label for="description">Description:</label>
            <input type="text" id="description" name="description" value="<%= category.getCategoryDescription() %>" required>
            <label for="parentId">Parent Category ID:</label>
            <input type="text" id="parentId" name="parentId" value="<%= category.getParentCategoryId() %>">
            <label for="hidden">Hidden:</label>
            <input type="checkbox" id="hidden" name="hidden" <%= category.isIsHidden() ? "checked" : "" %>><br>
            
            <div class="button-container">
                <input type="submit" value="Update Category">
                <a class="back-button" href="CategoryController?action=list">Back to List</a>
            </div>
        </form>
    </div>
</body>
</html>