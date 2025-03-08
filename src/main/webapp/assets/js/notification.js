/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function() {
    const partnerElement = document.querySelector('.partner');
    const notificationDropdown = document.querySelector('.notification-dropdown');
    let hideTimeout;

    // Hiển thị dropdown khi chuột vào phần tử partner (bao gồm cả icon và dropdown)
    partnerElement.addEventListener('mouseenter', function() {
        if (hideTimeout) {
            clearTimeout(hideTimeout);
        }
        notificationDropdown.style.display = 'block';
    });

    // Ẩn dropdown sau 300ms khi chuột rời khỏi phần tử partner
    partnerElement.addEventListener('mouseleave', function() {
        hideTimeout = setTimeout(function() {
            notificationDropdown.style.display = 'none';
        }, 200); // Thời gian delay có thể điều chỉnh theo nhu cầu
    });
});

