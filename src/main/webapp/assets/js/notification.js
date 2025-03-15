/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function() {
    // Notification dropdown functionality
    const partnerElement = document.querySelector('.partner');
    const notificationDropdown = document.querySelector('.notification-dropdown');
    
    if (partnerElement && notificationDropdown) {
        let hideTimeout;
        
        partnerElement.addEventListener('mouseenter', function() {
            if (hideTimeout) {
                clearTimeout(hideTimeout);
            }
            notificationDropdown.style.display = 'block';
        });
        
        partnerElement.addEventListener('mouseleave', function() {
            hideTimeout = setTimeout(function() {
                notificationDropdown.style.display = 'none';
            }, 200);
        });
        
        // Make sure the dropdown stays visible when mouse is over it
        notificationDropdown.addEventListener('mouseenter', function() {
            if (hideTimeout) {
                clearTimeout(hideTimeout);
            }
        });
        
        notificationDropdown.addEventListener('mouseleave', function() {
            hideTimeout = setTimeout(function() {
                notificationDropdown.style.display = 'none';
            }, 200);
        });
    }
});