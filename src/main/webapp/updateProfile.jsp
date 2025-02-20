<%-- 
    Document   : updateProfile
    Created on : Feb 20, 2025, 10:00:51 PM
    Author     : THANH THAO
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Profile</title>
</head>
<body>
    <h1>Update Profile</h1>
    <form action="/updateProfile" method="POST">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" value="${account.firstName}" required><br><br>
        
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" value="${account.lastName}" required><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${account.email}" required><br><br>

        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" value="${account.phoneNumber}" required><br><br>

        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="${account.address}" required><br><br>

        <button type="submit">Update Profile</button>
    </form>
</body>
</html>
