<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, Model.Product" %>
<%@ page import="DAOs.StaffManageProductDAO" %>
<%@ page import="java.text.NumberFormat, java.util.Locale" %>

<%
    StaffManageProductDAO productDAO = new StaffManageProductDAO();
    List<Product> products = productDAO.getAllProducts();
%>
<%
    NumberFormat currencyFormatter = NumberFormat.getNumberInstance(Locale.US);
%>

<html>
    <head>
        <title>Manage Product</title>
    </head>
    <body>
        <h2>List Product</h2>
        <button onclick="window.location.href='addProduct.jsp'"> Add Product</button>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Product Name</th>
                <th>Description</th> 
                <th>Price</th>
                <th>Image</th>
                <th>Operation</th>
            </tr>
            <% int count = 1; %> <!-- Khởi tạo biến đếm -->
            <% for (Product p : products) {%>
            <tr>
                <td><%= count++%></td> <!-- Hiển thị số thứ tự thay vì ID từ database -->
                <td><%= p.getProductName()%></td>
                <td><%= p.getProductDescription() %></td> <!-- Hiển thị mô tả -->
                <td><%= currencyFormatter.format(p.getProductPrice())%> VND</td>
                <td><img src="<%= p.getProductImage()%>" width="50"></td>
                <td>
                    <a href="editProduct.jsp?id=<%= p.getProductId()%>">Edit</a>
                    <a href="deleteProduct.jsp?id=<%= p.getProductId()%>" onclick="return confirm('Xóa sản phẩm này?')">Delete</a>
                </td>
            </tr>
            <% }%>
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
.button{
    padding: 10px 20px; 
    font-size: 16px; 
    background-color: #8AAAE5; 
    color: white; 
    border: none; 
    border-radius: 5px; 
    cursor: pointer;
}
</style>
