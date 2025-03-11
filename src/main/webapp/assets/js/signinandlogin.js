/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

const container = document.querySelector('.container');
const registerBtn = document.querySelector('.register-btn');
const loginBtn = document.querySelector('.login-btn');

// Khi load trang, dựa trên URL để hiển thị form đúng
window.addEventListener('DOMContentLoaded', () => {
    const path = window.location.pathname.toLowerCase();
    if (path.includes('/register')) {
        container.classList.add('active');
    } else {
        container.classList.remove('active');
    }
});

// Hàm lấy context path (nếu có)
const getContextPath = () => {
    const parts = window.location.pathname.split('/');
    // Nếu ứng dụng được triển khai tại root, parts[1] có thể là rỗng
    return parts[1] ? '/' + parts[1] : '';
};

const contextPath = getContextPath();

// Khi nhấn nút Register, chuyển đổi form và cập nhật URL
registerBtn.addEventListener('click', () => {
    container.classList.add('active');
    history.pushState(null, '', contextPath + '/Register'); // cập nhật URL thành /Register với context path
});

// Khi nhấn nút Login, chuyển đổi form và cập nhật URL
loginBtn.addEventListener('click', () => {
    container.classList.remove('active');
    history.pushState(null, '', contextPath + '/login'); // cập nhật URL thành /login với context path
});

function validateForm() {
    var password = document.getElementById("password").value;
    var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).{8,}$/;
    if (!regex.test(password)) {
        alert("Mật khẩu phải có ít nhất 8 ký tự, bao gồm 1 chữ hoa, 1 chữ thường và 1 ký tự đặc biệt.");
        return false; // Ngăn form gửi đi nếu không đạt yêu cầu
    }
    return true;
}
