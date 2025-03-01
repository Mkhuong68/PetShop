<%-- 
    Document   : staff-dashboard
    Created on : Feb 26, 2025, 9:44:53 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Dashboard</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <!-- Sidebar -->
        <nav class="sidebar">
            <h2>Staff Panel</h2>
            <ul>
                <li><a href="#">📢 Manage Promotion</a></li>
                <li><a href="#">📦 Manage Order</a></li>
                <li><a href="manage-products.html">🛍 Manage Products</a></li>
                <li><a href="manage-categories.html">📂 Manage Categories</a></li>
                <li><a href="#">🎟 Manage Voucher</a></li>
                <li><a href="#">📝 Manage Post</a></li>
                <li><a href="#">💬 Manage Comment</a></li>
                <li><a href="#">👥 Manage Accounts</a></li>
                <li><a href="#">📩 Manage Feedback</a></li>
            </ul>
        </nav>

        <!-- Main Content -->
        <main class="main-content">
            <h1>Welcome to Staff Dashboard</h1>
            <p>Select a category from the sidebar to manage the system.</p>
        </main>
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
.container {
    display: flex;
    height: 100vh;
}

/* Sidebar */
.sidebar {
    width: 250px;
    background-color: #2c3e50;
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

    </style>