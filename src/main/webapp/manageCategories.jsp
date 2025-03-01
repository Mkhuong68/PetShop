<%-- 
    Document   : manageCategories
    Created on : Feb 26, 2025, 9:49:31 AM
    Author     : Admin
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Categories</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <nav class="sidebar">
            <h2>Staff Panel</h2>
            <ul>
                <li><a href="index.jsp">🏠 Dashboard</a></li>
                <li><a href="manageCategories.jsp" class="active">📂 Manage Categories</a></li>
            </ul>
        </nav>

        <main class="main-content">
            <h1>Manage Categories</h1>

            <!-- Form thêm danh mục -->
            <div class="form-container">
                <h2>Add New Category</h2>
                <form action="${pageContext.request.contextPath}/categories" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="text" name="category_name" placeholder="Category Name" required>
                    <input type="text" name="category_description" placeholder="Category Description">
                    <input type="text" name="parent_category_id" placeholder="Parent Category ID">
                    <input type="text" name="category_image" placeholder="Image URL">
                    <label>
                        <input type="checkbox" name="is_hidden"> Hidden
                    </label>
                    <button type="submit">Add Category</button>
                </form>
            </div>

            <!-- Bảng hiển thị danh sách danh mục -->
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Category Name</th>
                        <th>Description</th>
                        <th>Parent ID</th>
                        <th>Image</th>
                        <th>Hidden</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td>${category.id}</td>
                            <td>${category.name}</td>
                            <td>${category.description}</td>
                            <td>${category.parentId}</td>
                            <td><img src="${category.image}" width="50"></td>
                            <td>${category.hidden ? 'Yes' : 'No'}</td>
                            <td>
                                <a href="CategoryController?action=edit&id=${category.id}">✏️ Edit</a> |
                                <a href="CategoryController?action=delete&id=${category.id}" onclick="return confirm('Delete this category?')">🗑 Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </main>
    </div>
</body>
</html>


<style>
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
    background: #34495e;
    color: white;
}

button {
    background: #2ecc71;
    color: white;
    border: none;
    padding: 5px 10px;
    cursor: pointer;
}

button.edit {
    background: #f39c12;
}

button.delete {
    background: #e74c3c;
}

.form-container {
    margin-top: 20px;
}

.form-container input {
    padding: 5px;
    margin-right: 10px;
}

</style>

