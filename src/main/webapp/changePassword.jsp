<%-- 
    Document   : changePassword
    Created on : Feb 20, 2025, 10:03:01 PM
    Author     : THANH THAO
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/profile.css"> <!-- Liên kết với CSS nếu cần -->
</head>
<body>
    <div class="container">
        <div class="left">
            <h2>Change Password</h2>
            <p>Please enter your current password and new password to change it.</p>
            <form action="/changePassword" method="POST">
                <!-- Input for Old Password -->
                <label for="oldPassword">Current Password</label>
                <input type="password" id="oldPassword" name="oldPassword" placeholder="Enter current password" required><br><br>

                <!-- Input for New Password -->
                <label for="newPassword">New Password</label>
                <input type="password" id="newPassword" name="newPassword" placeholder="Enter new password" required><br><br>

                <!-- Input for Confirm New Password -->
                <label for="confirmPassword">Confirm New Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" required><br><br>

                <button type="submit" class="btn-submit">Change Password</button>
                <button type="button" class="btn-cancel" onclick="window.location.href='/viewProfile';">Cancel</button>
            </form>
        </div>
    </div>
</body>
</html>
