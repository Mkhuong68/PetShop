<%-- 
    Document   : viewProfile
    Created on : Feb 19, 2025, 11:29:58 PM
    Author     : THANH THAO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ page import="Model.Account" %>


<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Account Details</title>
        <link rel="stylesheet" href="assets/css/profile.css">
        <script src="https://unpkg.com/boxicons@2.1.1/dist/boxicons.js"></script>
        <script src="assets/js/profile.js"></script>
    </head>

    <body>

        <!-- Chia layout thành 2 cột bằng class "row" -->
        <div class="row">
            <!-- Cột bên trái: Các nút điều hướng -->
            <div class="account-actions">
                <h1 class="text-xl font-bold mb-4">Account Page</h1>
                <h2 class="font-bold text-lg mb-4">
                    Hello, ${sessionScope.account.username}!
                </h2>
                <a href="javascript:void(0);" onclick="toggleViewProfileForm()">Account Information</a>
                <a href="javascript:void(0);" onclick="toggleUpdateProfileForm()">Update Profile</a>
                <a href="javascript:void(0);" onclick="toggleChangePasswordForm()">Change Password</a>
                <a href="logout">Logout</a>
            </div>

            <!-- Cột bên phải: Hiển thị thông tin tài khoản -->
            <div class="profile-info" id="viewProfile" style="display: none;">
                <!-- Ảnh mặc định hoặc ảnh profile -->
                <div id="profilePictureContainer" class="profile-icon-wrapper">
                    <box-icon id="defaultIcon" name="user"></box-icon>
                    <img id="profilePicture"
                         src="${sessionScope.account.profileImage != null ? sessionScope.account.profileImage : 'assets/images/default-user.png'}"
                         alt="Profile Picture" class="profile-img"/>
                </div>
                <p><strong>Full Name:</strong> ${sessionScope.account.firstName} ${sessionScope.account.lastName}</p>
                <p><strong>Email:</strong> ${sessionScope.account.email}</p>
                <p><strong>Phone:</strong> ${sessionScope.account.phoneNumber}</p>
            </div>

            <!-- Form Update Profile -->
            <div id="updateProfileForm" class="form-right" style="display: none;">
                <h3>Update Profile</h3>
                <form action="updateProfile" method="POST" enctype="multipart/form-data">
                    <div class="file-upload-container">
                        <label for="profile-upload">Profile Picture:</label>
                        <!-- Bọc ảnh preview trong khung tròn -->
                        <div class="profile-icon-wrapper">
                            <box-icon id="previewIcon" name="user"></box-icon>
                            <img id="previewImage"
                                 src="${sessionScope.account.profileImage != null ? sessionScope.account.profileImage : 'assets/images/default-user.png'}"
                                 alt="Preview Image" class="profile-img"/>
                        </div>

                        <!-- Input file ẩn và nút Change Picture -->
                        <input type="file" id="profile-upload" name="profilePicture" onchange="previewProfileImage(event)" style="display: none;">
                        <button type="button" class="btn-change" onclick="document.getElementById('profile-upload').click()">Change Picture</button>
                        <!-- Nút Delete Picture -->
                        <button type="button" class="btn-delete" onclick="deletePicture()">Delete Picture</button>
                    </div>
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName" value="${sessionScope.account.firstName}" required><br><br>

                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" value="${sessionScope.account.lastName}" required><br><br>

                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${sessionScope.account.email}" required><br><br>

                    <label for="phone">Phone:</label>
                    <input type="text" id="phone" name="phone" value="${sessionScope.account.phoneNumber}" required><br><br>

                    <button type="submit" class="btn-submit">Save Changes</button>
                    <button type="button" class="btn-cancel" onclick="toggleUpdateProfileForm()">Cancel</button>
                </form>
            </div>


            <!-- Form Change Password -->
            <div id="changePasswordForm" class="form-right" style="display: none;">
                <h3>Change Password</h3>
                <form action="changePassword" method="POST">
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
                    <button type="button" class="btn-cancel" onclick="toggleChangePasswordForm()">Cancel</button>
                </form>
            </div>
        </div>

    </body>
</html>
