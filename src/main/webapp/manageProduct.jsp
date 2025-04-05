<%@page import="Model.Category"%>
<%@page import="DAOs.CategoryDAO"%>
<%@page import="DAOs.CategoryDAO"%>
<%@page import="DB.DBConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, Model.Product" %>
<%@ page import="DAOs.StaffManageProductDAO" %>
<%@ page import="java.text.NumberFormat, java.util.Locale" %>


<%
    StaffManageProductDAO productDAO = new StaffManageProductDAO();
    List<Product> products = productDAO.getAllProducts();

    Connection conn = DBConnection.getConnection();
    CategoryDAO categoryDAO = new CategoryDAO(conn);
    List<Category> categories = categoryDAO.getAllCategories();
%>
<%
    NumberFormat currencyFormatter = NumberFormat.getNumberInstance(Locale.US);
%>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Product</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    </head>
    <body>
        <jsp:include page="manageStaff.jsp" />
        <div>
            <table border="1">
                <h2>List Product</h2>
                <button onclick="window.location.href = 'addProduct.jsp'"> Add Product</button>
                <tr>
                    <th>ID</th>
                    <th>Product Name</th>
                    <th>Description</th> 
                    <th>Stock</th> 
                    <th>Category</th>
                    <th>Price</th>
                    <th>Image</th>
                    <th>Operation</th>
                </tr>
                <% int count = 1; %> <!-- Khởi tạo biến đếm -->
                <% for (Product p : products) {%>
                <tr>
                    <td><%= count++%></td> <!-- Hiển thị số thứ tự thay vì ID từ database -->
                    <td><%= p.getProductName()%></td>
                    <td><%= p.getProductDescription()%></td> <!-- Hiển thị mô tả -->
                    <td><%= p.getStockQuantity()%></td> <!-- Hiển thị mô tả -->

                    <td>
                        <%
                            int categoryId = p.getCategoryId();
                            String categoryName = "";
                            for (Category category : categories) {
                                if (category.getCategoryId() == categoryId) {
                                    categoryName = category.getCategoryName();
                                    break; // Dừng vòng lặp khi tìm thấy
                                }
                            }
                        %>
                        <%= categoryName.isEmpty() ? categoryId : categoryName%>
                    </td>


                    <td><%= currencyFormatter.format(p.getProductPrice())%> VND</td>
                    <td><img src="<%= p.getProductImage()%>" width="150x150"></td>
                    <td>
                        <button class="button edit" onclick="location.href = 'editProduct.jsp?id=<%= p.getProductId()%>'">Edit</button>
                        <button class="button delete" onclick="if (confirm('Delete this product?'))
                                    location.href = 'deleteProduct.jsp?id=<%= p.getProductId()%>'">Delete</button>

                    </td>
                </tr>
                <% }%>
            </table>
            <!-- Nút Back sử dụng CSS .button -->
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

    /* Container chính */
    body {
        background-color: #f4f4f4;
        color: #333;
        padding: 20px;
        display: flex;
    }

    h2 {
        color: #2c3e50;
        margin-top: 70px;
        font-size: 30px;
    }

    /* Bảng sản phẩm */
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        background: white;
    }

    th, td {
        border: 1px solid #bdc3c7;
        padding: 12px;
        text-align: left;
    }

    th {
        background: #8AAAE5;
        color: white;
        font-weight: bold;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    tr:hover {
        background-color: #e1e1e1;
    }

    /* Nút */
    button {
        background: #8AAAE5;
        color: white;
        border: none;
        padding: 10px 15px;
        cursor: pointer;
        border-radius: 5px;
        transition: background 0.3s;
    }

    /* Định dạng cho các nút Edit và Delete */
    .btn {
        display: inline-block;
        padding: 8px 12px;
        margin: 0 5px;
        border-radius: 5px;
        text-decoration: none;
        color: white;
        font-weight: bold;
        border: 2px solid transparent; /* Khung cho nút */
        transition: background 0.3s, transform 0.3s, border-color 0.3s;
    }

    .btn.edit {
        background-color: #4CAF50; /* Màu xanh lá cho nút Edit */
        border-color: #4CAF50; /* Khung màu xanh lá */
    }

    .btn.edit:hover {
        background-color: #45a049; /* Màu xanh lá đậm khi hover */
        border-color: #45a049; /* Khung màu xanh lá đậm khi hover */
        transform: scale(1.05); /* Tăng kích thước khi hover */
    }

    .btn.delete {
        background-color: #f44336; /* Màu đỏ cho nút Delete */
        border-color: #f44336; /* Khung màu đỏ */
    }

    .btn.delete:hover {
        background-color: #e53935; /* Màu đỏ đậm khi hover */
        border-color: #e53935; /* Khung màu đỏ đậm khi hover */
        transform: scale(1.05); /* Tăng kích thước khi hover */
    }

    /* Nút Back */
    .button {
        margin-top: 20px;
        display: inline-block;
    }

    /* Hình ảnh sản phẩm */
    img {
        max-width: 150px;
        height: auto;
        border-radius: 5px;
    }

    /* Cải thiện bố cục */
    .container {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .form-container {
        margin-top: 20px;
    }

    .form-container input {
        padding: 8px;
        margin-right: 10px;
        border: 1px solid #bdc3c7;
        border-radius: 5px;
    }

    /* Sidebar */
    .sidebar {
        width: 250px;
        background-color: #8AAAE5;
        color: white;
        padding: 20px;
        border-radius: 5px;
        margin-bottom: 20px;
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
</style>