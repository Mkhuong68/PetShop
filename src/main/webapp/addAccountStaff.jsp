<%-- 
    Document   : addAccountStaff.jsp
    Created on : Mar 4, 2025, 11:51:16 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Staff Account</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Add New Staff Account</h2>
    <form action="StaffManageAccountController" method="post">
        <input type="hidden" name="action" value="insert">
        Username: <input type="text" name="username" required><br>
        Password: <input type="password" name="password" required><br>
        Email: <input type="email" name="email" required><br>
        Phone: <input type="text" name="phone"><br>
        Active: <input type="checkbox" name="active"><br>
        First Name: <input type="text" name="firstName" required><br>
        Last Name: <input type="text" name="lastName" required><br>
        Date of Birth: <input type="date" name="dob" required><br>
        Gender:
        <select name="gender">
            <option value="Male">Male</option>
            <option value="Female">Female</option>
        </select><br>
        Banned Reason: <input type="text" name="bannedReason"><br>
        <input type="submit" value="Add Account">
    </form>
    <a href="StaffManageAccountController?action=list">Back to List</a>
</body>
</html>
<style>
    /* Container cho form */
    .form-container {
        width: 50%;
        margin: 20px auto;
        padding: 20px;
        background: #8AAAE5;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    /* Tiêu đề */
    h2 {
        text-align: center;
        color: #2c3e50;
    }

    /* Label cho các trường dữ liệu */
    label {
        display: block;
        margin: 10px 0 5px;
        font-weight: bold;
        color: #2c3e50;
    }

    /* Input text và checkbox */
    input[type="text"], input[type="password"], input[type="email"], input[type="date"], select {
        width: calc(100% - 16px);
        padding: 8px;
        margin-bottom: 10px;
        border: 1px solid #8AAAE5;
        border-radius: 5px;
        display: block;
    }

    /* Nút bấm submit */
    input[type="submit"], button {
        background: #8AAAE5;
        color: white;
        border: none;
        padding: 10px 15px;
        cursor: pointer;
        border-radius: 5px;
        width: 100%;
        margin-top: 10px;
    }

    /* Hiệu ứng hover cho nút */
    input[type="submit"]:hover, button:hover {
        background: #2c3e50;
    }

    /* Liên kết quay lại */
    .back-link {
        display: block;
        text-align: center;
        margin-top: 15px;
        color: #8AAAE5;
        text-decoration: none;
    }

    .back-link:hover {
        text-decoration: underline;
    }
</style>
