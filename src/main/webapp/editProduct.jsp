<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Product, DAOs.StaffManageProductDAO, java.math.BigDecimal" %>

<%
    int productId = Integer.parseInt(request.getParameter("id"));
    StaffManageProductDAO productDAO = new StaffManageProductDAO();
    Product product = productDAO.getProductById(productId);

    // Xử lý hiển thị giá trong input (Loại bỏ .0 nếu không cần)
    BigDecimal price = BigDecimal.valueOf(product.getProductPrice());
    String formattedPrice = (price.stripTrailingZeros().scale() > 0) ? price.toPlainString() : price.toBigInteger().toString();
%>
<html>
    <head>
        <title>Edit Product</title>
    </head>
    <body>
        <h2>Edit Product</h2>
        <form action="<%= request.getContextPath()%>/manageProduct" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= product.getProductId()%>">
            <label>Product Name:</label>
            <input type="text" name="name" value="<%= product.getProductName()%>" required><br>

            <label>Description:</label>
            <input type="text" name="description" value="<%= product.getProductDescription()%>" required><br>

            <label>Price:</label>
            <input type="text" name="price" value="<%= formattedPrice%>" required><br>

            <label>Image:</label>
            <input type="text" name="image" value="<%= product.getProductImage()%>"><br>

            <label>Category:</label>
            <input type="number" name="category" value="<%= product.getCategoryId()%>" required><br>

            <label>Inventory quantity:</label>
            <input type="number" name="stock" value="<%= product.getStockQuantity()%>" required><br>

            <button type="submit">Update</button>
        </form>
        <a href="manageProduct.jsp">BACK</a>
    </body>
</html>


<style>
    .form-container {
        width: 50%;
        margin: 0 auto;
        padding: 20px;
        background: #ecf0f1;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    
    label {
        display: block;
        margin: 10px 0 5px;
        font-weight: bold;
    }
    
    input {
        width: 100%;
        padding: 8px;
        margin-bottom: 10px;
        border: 1px solid #bdc3c7;
        border-radius: 5px;
    }
    
    button {
        background: #8AAAE5;
        color: white;
        border: none;
        padding: 10px 15px;
        cursor: pointer;
        border-radius: 5px;
    }
    
    button:hover {
        background: #8AAAE5;
    }
    
    a {
        display: inline-block;
        margin-top: 10px;
        color: #8AAAE5;
        text-decoration: none;
    }
    
    a:hover {
        text-decoration: underline;
    }
</style>
