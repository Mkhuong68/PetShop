<%-- 
    Document   : changePassword
    Created on : Feb 20, 2025, 10:03:01 PM
    Author     : THANH THAO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link rel="stylesheet" href="assets/css/profile.css">
    <script src="assets/js/profile.js"></script>
</head>

<body>

<div class="profile-container">
    <h1>Change Password</h1>

    <form action="changePassword" method="post">
        <label for="oldPassword">Current Password</label>
        <input type="password" id="oldPassword" name="oldPassword" placeholder="Enter current password"
               required><br><br>

        <label for="newPassword">New Password</label>
        <input type="password" id="newPassword" name="newPassword" placeholder="Enter new password"
               required><br><br>

        <label for="confirmPassword">Confirm New Password</label>
        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password"
               required><br><br>

        <button type="submit" class="btn-submit">Change Password</button>
        <button type="button" class="btn-cancel" onclick="window.history.back()">Cancel</button>
    </form>
</div>

</body>
</html>
