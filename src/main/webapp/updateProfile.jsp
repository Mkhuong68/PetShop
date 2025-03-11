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

        <form id="updateForm" action="updateProfile" method="POST" enctype="multipart/form-data">
            <!-- Avatar Display Section -->
            <div class="avatar-section">
                <div class="profile-icon-wrapper">
                    <box-icon id="previewIcon" name="user"></box-icon>
                    <img id="previewImage"
                         src="${sessionScope.account.profileImage != null ? sessionScope.account.profileImage : 'assets/images/default-user.png'}"
                         alt="Profile Image" class="profile-img"/>
                </div>

                <div class="button-group">
                    <input type="file" id="fileInput" name="profilePicture" accept="image/*" onchange="previewProfileImage(event)" style="display: none;"/>
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
        // Xem trước ảnh khi chọn
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

        // Xóa ảnh và khôi phục lại icon mặc định
        function deletePicture() {
            var previewImg = document.getElementById('previewImage');
            var previewIcon = document.getElementById('previewIcon');
            previewImg.src = 'assets/images/default-user.png';
            previewImg.style.display = "block";
            previewIcon.style.display = "none";
        }
    </script>
</body>
</html>
