<%-- 
    Document   : manageCategory
    Created on : Feb 28, 2025, 9:02:08 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Manage Categories</title>
    </head>
    <body>
        <h2>Category List</h2>
        <button onclick="window.location.href='CategoryController?action=new'">Add New Category</button>

        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Parent Category</th>
                    <th>Hidden</th>
                    <th>Created Date</th>
                    <th>Last Updated</th>
                    <th>Actions</th> <!-- ✅ Thêm cột Actions -->
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty categoryList}">
                        <tr>
                            <td colspan="8">⚠ No categories found.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="c" items="${categoryList}">
                            <tr>
                                <td>${c.categoryId}</td>
                                <td>${c.categoryName}</td>
                                <td>${c.categoryDescription}</td>
                                <td>${c.parentCategoryId}</td>
                                <td>${c.isHidden}</td>
                                <td>${c.createdDate}</td>
                                <td>${c.lastUpdated}</td>
                                <td>
                                    <a href="CategoryController?action=edit&id=${c.categoryId}">Edit</a> | 
                                    <a href="CategoryController?action=delete&id=${c.categoryId}" 
                                       onclick="return confirm('Are you sure you want to delete this category?');">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </body>
</html>

<style>
    /* Đặt lại một số thuộc tính mặc định */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: Arial, sans-serif;
    }

    /* Container chính */
    .container {
        display: flex;
        height: 100vh;
    }

    /* Sidebar */
    .sidebar {
        width: 250px;
        background-color: #8AAAE5;
        color: white;
        padding: 20px;
    }

    .sidebar h2 {
        text-align: center;
        margin-bottom: 20px;
    }

    .sidebar ul {
        list-style: none;
    }

    .sidebar ul li {
        padding: 10px;
        border-radius: 5px;
        transition: background 0.3s;
    }

    .sidebar ul li a {
        text-decoration: none;
        color: white;
        display: block;
    }

    .sidebar ul li:hover {
        background: #34495e;
    }

    /* Phần nội dung chính */
    .main-content {
        flex: 1;
        padding: 20px;
        background: #ecf0f1;
    }

    .main-content h1 {
        color: #2c3e50;
        margin-bottom: 10px;
    }


    .container {
        display: flex;
        height: 100vh;
    }

    .sidebar {
        width: 250px;
        background-color: #8AAAE5;
        color: white;
        padding: 20px;
    }

    .sidebar h2 {
        text-align: center;
        margin-bottom: 20px;
    }

    .sidebar ul {
        list-style: none;
    }

    .sidebar ul li {
        padding: 10px;
    }

    .sidebar ul li a {
        text-decoration: none;
        color: white;
        display: block;
    }

    .sidebar ul li a.active {
        font-weight: bold;
        color: #f1c40f;
    }

    .main-content {
        flex: 1;
        padding: 20px;
        background: #ecf0f1;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }

    th, td {
        border: 1px solid #bdc3c7;
        padding: 10px;
        text-align: left;
    }

    th {
        background: #8AAAE5;
        color: white;
    }

    button {
        background: #8AAAE5;
        color: white;
        border: none;
        padding: 5px 10px;
        cursor: pointer;
    }

    button.edit {
        background: #8AAAE5;
    }

    button.delete {
        background: #8AAAE5;
    }

    .form-container {
        margin-top: 20px;
    }

    .form-container input {
        padding: 5px;
        margin-right: 10px;
    }
    .button{
        padding: 10px 20px;
        font-size: 16px;
        background-color: #8AAAE5;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        padding-bottom: 30px;
    }
</style>


