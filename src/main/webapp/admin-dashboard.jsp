<%-- 
    Document   : admin-dashboard
    Created on : Feb 26, 2025, 9:50:42 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="admin-styles.css">
</head>
<body>
    <div class="container">
        <nav class="sidebar">
            <h2>Admin Panel</h2>
            <ul>
                <li><a href="admin-dashboard.html" class="active">🏠 Dashboard</a></li>
                <li><a href="manage-delivery.html">🚚 Manage Delivery Issues</a></li>
                <li><a href="manage-staff.html">👥 Manage Staff Accounts</a></li>
                <li><a href="manage-revenue.html">💰 Manage Revenue Statistics</a></li>
            </ul>
        </nav>

        <main class="main-content">
            <h1>Welcome, Admin</h1>
            <p>Select a section from the menu to manage the system.</p>
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
    background-color: #8aaae5;
    color: white;
    padding: 20px;
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

</style>

