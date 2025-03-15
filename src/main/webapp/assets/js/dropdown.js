/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', function() {
    const productsMenu = document.querySelector('.products');
    const dropdown = document.querySelector('.product-dropdown'); // Sửa thành product-dropdown
    let isOverDropdown = false;
    let isOverProductsMenu = false;
    let timeoutId;

    // Xử lý sự kiện khi hover vào menu products
    productsMenu.addEventListener('mouseenter', function() {
        isOverProductsMenu = true;
        clearTimeout(timeoutId);
        dropdown.style.display = 'block';
    });

    // Xử lý sự kiện khi hover vào dropdown
    dropdown.addEventListener('mouseenter', function() {
        isOverDropdown = true;
        clearTimeout(timeoutId);
    });

    // Xử lý sự kiện khi rời khỏi menu products
    productsMenu.addEventListener('mouseleave', function() {
        isOverProductsMenu = false;
        // Chỉ ẩn dropdown nếu không đang hover trên dropdown
        timeoutId = setTimeout(() => {
            if (!isOverDropdown) {
                dropdown.style.display = 'none';
            }
        }, 200);
    });

    // Xử lý sự kiện khi rời khỏi dropdown
    dropdown.addEventListener('mouseleave', function() {
        isOverDropdown = false;
        // Chỉ ẩn dropdown nếu không đang hover trên menu products
        timeoutId = setTimeout(() => {
            if (!isOverProductsMenu) {
                dropdown.style.display = 'none';
            }
        }, 200);
    });
});