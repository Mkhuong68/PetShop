<%-- 
    Document   : updateProfile
    Created on : Feb 20, 2025, 10:00:51 PM
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
    <title>Update Profile</title>
    <link rel="stylesheet" href="assets/css/profile.css">
    <script src="assets/js/profile.js"></script>
</head>

<body>

<div class="profile-container">
    <h1>Update Profile</h1>

    <form action="updateProfile" method="post" enctype="multipart/form-data">
        <!-- Avatar Display Section -->
        <div class="avatar-section">
            <!-- Khung tròn cho ảnh đại diện -->
            <div class="profile-icon-wrapper">
                <box-icon id="previewIcon" name="user"></box-icon>
                <img id="previewImage"
                     src="${sessionScope.account.profileImage != null ? sessionScope.account.profileImage : 'assets/images/default-user.png'}"
                     alt="Profile Image" class="profile-img"/>
            </div>

            <div class="button-group">
                <!-- Nút Change và Delete -->
                <input type="file" name="profilePicture" id="fileInput" onchange="previewProfileImage(event)" style="display: none;"/>
                <button type="button" class="btn-change" onclick="document.getElementById('fileInput').click()">Change Picture</button>
                <button type="button" class="btn-delete" onclick="deletePicture()">Delete Picture</button>
            </div>
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
        <button type="button" class="btn-cancel" onclick="window.history.back()">Cancel</button>
    </form>
</div>

<script>
    function previewProfileImage(event) {
        var file = event.target.files[0];
        var reader = new FileReader();

        var previewImg = document.getElementById('previewImage');
        var previewIcon = document.getElementById('previewIcon');

        reader.onload = function () {
            var imageUrl = reader.result;
            previewImg.src = imageUrl;
            previewImg.style.display = "block";
            previewIcon.style.display = "none";
        };

        if (file) {
            reader.readAsDataURL(file);
        }
    }

    function deletePicture() {
        var previewImg = document.getElementById('previewImage');
        var previewIcon = document.getElementById('previewIcon');
        previewImg.src = '';
        previewImg.style.display = "none";
        previewIcon.style.display = "block";
    }
</script>

</body>
</html>
