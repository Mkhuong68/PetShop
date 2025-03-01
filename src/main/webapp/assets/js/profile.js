/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// Hàm toggle để hiển thị hoặc ẩn phần View Profile
function toggleViewProfileForm() {
    toggleForm("viewProfile");
}

// Hàm toggle để hiển thị hoặc ẩn phần Update Profile
function toggleUpdateProfileForm() {
    toggleForm("updateProfileForm");
}

// Hàm toggle để hiển thị hoặc ẩn phần Change Password
function toggleChangePasswordForm() {
    toggleForm("changePasswordForm");
}

// Hàm tổng quát dùng để hiển thị hoặc ẩn các form
function toggleForm(formId) {
    var forms = ["viewProfile", "updateProfileForm", "changePasswordForm"];
    var targetForm = document.getElementById(formId);

    // Ẩn tất cả các form
    forms.forEach(function(form) {
        var formElement = document.getElementById(form);
        if (formElement) {
            formElement.style.display = "none";
        }
    });

    // Hiển thị form cần thiết
    if (targetForm) {
        targetForm.style.display = targetForm.style.display === "none" ? "block" : "none";
    }
}

// Xem trước ảnh khi tải lên
function previewProfileImage(event) {
    var file = event.target.files[0];
    var reader = new FileReader();

    var previewImg = document.getElementById('previewImage');
    var previewIcon = document.getElementById('previewIcon');

    var profileImg = document.getElementById('profilePicture');
    var defaultIcon = document.getElementById('defaultIcon');

    reader.onload = function () {
        var imageUrl = reader.result;

        // Hiển thị ảnh thay vì icon
        previewImg.src = imageUrl;
        profileImg.src = imageUrl;

        previewImg.style.display = "block";
        profileImg.style.display = "block";

        previewIcon.style.display = "none";
        defaultIcon.style.display = "none";
    };

    if (file) {
        reader.readAsDataURL(file);
    }
}

// Hàm để xóa ảnh và khôi phục về icon mặc định
function deletePicture() {
    var previewImg = document.getElementById('previewImage');
    var previewIcon = document.getElementById('previewIcon');

    var profileImg = document.getElementById('profilePicture');
    var defaultIcon = document.getElementById('defaultIcon');

    // Xóa ảnh
    previewImg.src = '';
    previewImg.style.display = "none";
    profileImg.style.display = "none";

    // Hiển thị icon mặc định
    previewIcon.style.display = "block";
    defaultIcon.style.display = "block";
}
