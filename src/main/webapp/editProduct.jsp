<%@page import="Model.Category"%>
<%@page import="DB.DBConnection"%>
<%@page import="java.util.List"%>
<%@page import="DAOs.CategoryDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Product, DAOs.StaffManageProductDAO, java.math.BigDecimal" %>

<%
    int productId = Integer.parseInt(request.getParameter("id"));
    StaffManageProductDAO productDAO = new StaffManageProductDAO();
    Product product = productDAO.getProductById(productId);

    // Xử lý hiển thị giá trong input (Loại bỏ .0 nếu không cần)
    BigDecimal price = product.getProductPrice();

    Connection conn = DBConnection.getConnection();
    CategoryDAO categoryDAO = new CategoryDAO(conn);
    List<Category> categories = categoryDAO.getAllCategories();
    String formattedPrice = (price.stripTrailingZeros().scale() > 0) ? price.toPlainString() : price.toBigInteger().toString();
%>
<html>
    <head>
        <title>Update Product</title>
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
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
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

            input[type="text"],
            input[type="number"],
            select {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #bdc3c7;
                border-radius: 5px;
                transition: border-color 0.3s;
            }

            input[type="text"]:focus,
            input[type="number"]:focus,
            select:focus {
                border-color: #3498db;
                outline: none;
            }

            button {
                background: #8AAAE5;
                color: white;
                border: none;
                padding: 10px 15px;
                cursor: pointer;
                border-radius: 5px;
                font-size: 16px;
                transition: background 0.3s;
                width: 100%;
            }
            button-back{
                background-color: #8AAAE5;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                width: 100%;
                font-size: 16px;
            }

            button:hover {
                background: #2980b9;
            }

            a {
                display: inline-block;
                margin-top: 15px;
                color: #3498db;
                text-decoration: none;
                text-align: center;
                font-weight: bold;
            }

            a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <h2>Update Product</h2>
            <form action="<%= request.getContextPath()%>/manageProduct" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="<%= product.getProductId()%>">
                <label>Product Name:</label>
                <input type="text" name="name" value="<%= product.getProductName()%>" required>

                <label>Description:</label>
                <input type="text" name="description" value="<%= product.getProductDescription()%>" required>

                <label>Price:</label>
                <input type="text" name="price" value="<%= formattedPrice%>" required>

                <label>Image:</label>
                <input type="text" name="image" value="<%= product.getProductImage()%>">

                <label>Category:</label>
                <select name="category" id="categorySelect" required>
                    <option value="">Select Category</option>
                    <% for (Category category : categories) {%>
                    <option value="<%= category.getCategoryName()%>" 
                            data-id="<%= category.getCategoryId()%>">
                        <%= category.getCategoryName()%>
                    </option>
                    <% }%>
                </select>

                <input type="hidden" name="categoryId" id="categoryId">

                <label>Inventory quantity:</label>
                <input type="number" name="stock" value="<%= product.getStockQuantity()%>" required>

                <button type="submit">Update</button>
            </form>
            <a href="manageProduct.jsp">
                <button class="button-back">Back to Manage Staff</button>
            </a>
        </div>

        <script>
            document.getElementById("categorySelect").addEventListener("change", function () {
                let selectedOption = this.options[this.selectedIndex]; // Lấy option được chọn
                let categoryId = selectedOption.getAttribute("data-id"); // Lấy ID từ data-id
                document.getElementById("categoryId").value = categoryId; // Gán vào input ẩn
            });
        </script>
    </body>
</html>