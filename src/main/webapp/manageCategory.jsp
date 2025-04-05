<%-- 
    Document   : manageCategory
    Created on : Mar 8, 2025, 9:02:08 PM
    Author     : Admin
--%>

<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Categories</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    </head>
    <body>
        <jsp:include page="manageStaff.jsp" />
        <div>
            <h2>Category List</h2>

            <!-- Hiển thị thông báo ngoài bảng -->
            <%
                HttpSession sessionObj = request.getSession();
                String errorMessage = (String) sessionObj.getAttribute("errorMessage");
                String successMessage = (String) sessionObj.getAttribute("successMessage");

                String bgColor = "";
                String textColor = "";

                if (errorMessage != null) {
                    bgColor = "#ffdddd"; // Nền đỏ nhạt cho lỗi
                    textColor = "red";
                } else if (successMessage != null) {
                    bgColor = "#ddffdd"; // Nền xanh nhạt cho thành công
                    textColor = "green";
                }

                if (errorMessage != null || successMessage != null) {
            %>
            <div id="messageBox" style="border: 1px solid #ccc; padding: 10px; margin: 10px 0; background: <%= bgColor%>; color: <%= textColor%>;">
                <span><%= errorMessage != null ? errorMessage : successMessage%></span>
                <button onclick="document.getElementById('messageBox').style.display = 'none';" style="margin-left: 10px; padding: 5px 10px; background: #555; color: white; border: none; cursor: pointer;">OK</button>
            </div>
            <%
                    sessionObj.removeAttribute("errorMessage"); // Xóa lỗi sau khi hiển thị
                    sessionObj.removeAttribute("successMessage"); // Xóa thông báo thành công
                }
            %>

            <button onclick="window.location.href = 'CategoryController?action=new'">Add New Category</button>

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
                        <th>Actions</th>
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
                                        <button class="button edit" onclick="location.href = 'CategoryController?action=edit&id=${c.categoryId}'">Edit</button>

                                        <button class="button delete" 
                                                onclick="if (confirm('Are you sure you want to delete this category?'))
                                                            location.href = 'CategoryController?action=delete&id=${c.categoryId}'">
                                            Delete
                                        </button>

                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>

            <!-- Nút Back -->
            <a href="manageStaff.jsp">
                <button class="button">Back to Manage Staff</button>
            </a>
        </div>

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
    body {
        background-color: #f4f4f4;
        color: #333;
        padding: 20px;
        display: flex;
        margin-right: 250px;
    }
    h2 {
        color: #2c3e50;
        margin-top: 40px;
        font-size: 30px;
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
    .button {
        padding: 6px 12px;  /* Giảm padding để nút nhỏ lại */
        font-size: 14px;     /* Điều chỉnh kích thước font nhỏ hơn */
        background-color: #8AAAE5;
        color: white;
        border: none;
        border-radius: 5px;  /* Bo tròn góc nhẹ */
        cursor: pointer;
        text-align: center;  /* Căn giữa văn bản */
        display: inline-block; /* Giúp nút không chiếm hết chiều rộng */
        width: auto;  /* Để nút có kích thước tự động, không kéo dài */
        margin-top: 15px; /* Thêm một chút khoảng cách trên nút */
    }

    .button:hover {
        background-color: #7A9AD5; /* Hiệu ứng hover */
    }


</style>


