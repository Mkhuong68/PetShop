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
    forms.forEach(function (form) {
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

// Xem trước ảnh khi tải lên và lưu Base64
function previewProfileImage(event) {
    var file = event.target.files[0]; // Lấy file từ input
    var reader = new FileReader();

    reader.onload = function (e) {
        // Hiển thị ảnh ngay lập tức trên giao diện
        var previewImg = document.getElementById('previewImage');
        previewImg.src = e.target.result;  // Cập nhật src của ảnh preview
        previewImg.style.display = "block";  // Hiển thị ảnh preview
        document.getElementById('previewIcon').style.display = "none"; // Ẩn icon mặc định
    };

    if (file) {
        reader.readAsDataURL(file); // Đọc ảnh dưới dạng Base64
    }
}

// Hàm để xóa ảnh và khôi phục về icon mặc định
function deletePicture() {
    document.getElementById('previewImage').src = 'assets/images/default-user.png';
    document.getElementById('previewIcon').style.display = "block";
    document.getElementById('base64Image').value = ''; // Xóa giá trị Base64
}
