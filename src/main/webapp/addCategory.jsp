<%-- 
    Document   : addCategory
    Created on : Mar 8, 2025, 8:54:31 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Add Category</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
            }

            .form-container {
                width: 50%;
                margin: 0 auto;
                padding: 20px;
                background: #ffffff;
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
                color: #555;
            }

            input[type="text"] {
                width: calc(100% - 16px);
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                transition: border-color 0.3s;
            }

            input[type="text"]:focus {
                border-color: #8AAAE5;
                outline: none;
            }

            .button-container {
                display: flex;
                justify-content: space-between;
                margin-top: 20px;
            }

            input[type="submit"], .back-link {
                background: #8AAAE5;
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                border-radius: 5px;
                width: 40%; /* Adjust width to fit both buttons */
                font-size: 16px;
                text-align: center;
                text-decoration: none; /* Remove underline from link */
                display: inline-block; /* Make link behave like a button */
            }

            input[type="submit"]:hover, .back-link:hover {
                background: #7a9bc2;
            }

            .back-link {
                background: #8AAAE5; /* Different background for back link */
                color: white; /* Text color for back link */
            }

            .back-link:hover {
                background: #7a9bc2; /* Darker background on hover */
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <h2>Add New Category</h2>
            <form action="CategoryController" method="post">
                <input type="hidden" name="action" value="insert">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
                <label for="description">Description:</label>
                <input type="text" id="description" name="description" required>
                <label for="parentId">Parent Category ID:</label>
                <input type="text" id="parentId" name="parentId">
                <div class="button-container">
                    <input type="submit" value="Add Category">
                    <a class="back-link" href="CategoryController?action=list">Back to List</a>
                </div>
            </form>
        </div>
    </body>
</html>