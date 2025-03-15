<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="manageStaff.jsp" />
<html>
<head>
    <title>Add Product</title>
</head>
<body>
    <h2>Add Product</h2>
    <form action="<%= request.getContextPath() %>/manageProduct" method="post" class="form-container">
        <input type="hidden" name="action" value="add">
        <label>Product Name:</label>
        <input type="text" name="name" required><br>
        <label>Description:</label>
        <input type="text" name="description" required><br>
        <label>Price:</label>
        <input type="number" name="price" required><br>
        <label>Image:</label>
        <input type="text" name="image"><br>
        <label>Category:</label>
        <input type="number" name="category" required><br>
        <label>Stock Quantity:</label>
        <input type="number" name="stock" required><br>
        <button type="submit">Add</button>
    </form>
    <a href="manageProduct.jsp">Back</a>
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
