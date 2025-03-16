/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// Hàm toggle để hiển thị hoặc ẩn phần View Profile
function toggleViewProfileForm() {
    toggleForm("viewProfile");
}

// Thay đổi hàm toggleUpdateProfileForm() để chuyển hướng trang
function toggleUpdateProfileForm() {
    window.location.href = "updateProfile.jsp"; // Chuyển hướng đến trang updateProfile.jsp
}

function toggleChangePasswordForm() {
    window.location.href = "changePassword.jsp"; 
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