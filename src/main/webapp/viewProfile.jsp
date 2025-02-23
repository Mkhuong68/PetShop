<%-- 
    Document   : viewProfile
    Created on : Feb 19, 2025, 11:29:58 PM
    Author     : THANH THAO
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Account Details</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/profile.css">
    </head>
    <body>

        <div class="profile-container">
            <h1>View Profile</h1>
            <div class="profile-info">
                <h2>Hello, ${account.firstName} ${account.lastName}!</h2>
                <p><strong>Email:</strong> ${account.email}</p>
                <p><strong>Phone:</strong> ${account.phoneNumber}</p>
            </div>

            <!-- Update Profile and Change Password -->
            <div class="account-actions">
                <a href="updateProfile.jsp">Update Profile</a>
                <a href="changePassword.jsp">Change Password</a>
                <a href="hompage.jsp">Logout</a>
            </div>


            <!-- Change Password Form (for inline display within the profile page) -->
            <div id="changePasswordForm" style="display: none;">
                <h3>Change Password</h3>
                <form action="/CustomerProfileController" method="POST">
                    <input type="hidden" name="action" value="changePassword">

                    <label for="oldPassword">Current Password</label>
                    <input type="password" id="oldPassword" name="oldPassword" placeholder="Enter current password" required><br><br>

                    <label for="newPassword">New Password</label>
                    <input type="password" id="newPassword" name="newPassword" placeholder="Enter new password" required><br><br>

                    <label for="confirmPassword">Confirm New Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" required><br><br>

                    <button type="submit" class="btn-submit">Change Password</button>
                    <button type="button" class="btn-cancel" onclick="toggleChangePasswordForm()">Cancel</button>
                </form>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/assets/js/profile.js"></script>

        <script>
                        // JavaScript để hiển thị form thay đổi mật khẩu
                        function toggleChangePasswordForm() {
                            var form = document.getElementById("changePasswordForm");
                            if (form.style.display === "none") {
                                form.style.display = "block";
                            } else {
                                form.style.display = "none";
                            }
                        }
        </script>
    </body>
</html>
