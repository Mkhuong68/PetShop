<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Delivery Issues</title>
    <link rel="stylesheet" href="admin-styles.css">
</head>
<body>
    <div class="container">
        <nav class="sidebar">
            <h2>Admin Panel</h2>
            <ul>
                <li><a href="admin-dashboard.html">? Dashboard</a></li>
                <li><a href="manage-delivery.html" class="active">? Manage Delivery Issues</a></li>
            </ul>
        </nav>

        <main class="main-content">
            <h1>Manage Delivery Issues</h1>

            <div class="form-container">
                <h2>Report an Issue</h2>
                <input type="text" id="issueDesc" placeholder="Issue Description">
                <button onclick="addIssue()">Report Issue</button>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="issueTable">
                    <!-- Data will be added by JavaScript -->
                </tbody>
            </table>
        </main>
    </div>

    <script src="admin-script.js"></script>
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
