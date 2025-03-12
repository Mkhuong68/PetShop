<%-- 
    Document   : manageStaff
    Created on : Mar 12, 2025, 4:14:28 PM
    Author     : tvhun
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Staff Management</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            :root {
                --primary-color: #8AAAE5;
                --hover-color: #7A9AD5;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa;
            }

            /* Sidebar Styles */
            .sidebar {
                position: fixed;
                top: 0;
                left: 0;
                height: 100vh;
                width: 250px;
                background-color: var(--primary-color);
                padding-top: 60px;
                z-index: 1000;
                box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            }

            .sidebar .nav-link {
                color: white;
                padding: 12px 20px;
                margin: 4px 0;
                border-radius: 5px;
                transition: all 0.3s ease;
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .sidebar .nav-link:hover {
                background-color: var(--hover-color);
                transform: translateX(5px);
            }

            .sidebar .nav-link.active {
                background-color: var(--hover-color);
                font-weight: bold;
            }

            .sidebar .nav-link i {
                font-size: 1.2rem;
                width: 25px;
                text-align: center;
            }

            /* Top Navigation Bar */
            .top-nav {
                position: fixed;
                top: 0;
                right: 0;
                left: 250px;
                height: 60px;
                background-color: white;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                z-index: 999;
                display: flex;
                align-items: center;
                padding: 0 20px;
            }

            /* Main Content Area */
            .main-content {
                margin-left: 250px;
                padding: 80px 20px 20px;
            }

            /* Profile Dropdown */
            .profile-dropdown {
                position: relative;
                margin-left: auto;
            }

            .profile-btn {
                display: flex;
                align-items: center;
                gap: 10px;
                background: none;
                border: none;
                padding: 8px;
                cursor: pointer;
                border-radius: 50%;
                transition: all 0.3s ease;
            }

            .profile-btn:hover {
                background-color: #f0f0f0;
            }

            .profile-img {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                object-fit: cover;
            }

            .dropdown-menu {
                position: absolute;
                top: 100%;
                right: 0;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                padding: 8px 0;
                min-width: 200px;
                display: none;
            }

            .profile-dropdown:hover .dropdown-menu {
                display: block;
            }

            .dropdown-item {
                padding: 8px 16px;
                color: #333;
                text-decoration: none;
                display: flex;
                align-items: center;
                gap: 10px;
                transition: all 0.3s ease;
            }

            .dropdown-item:hover {
                background-color: #f8f9fa;
                color: var(--primary-color);
            }

            /* Logo Area */
            .logo-area {
                padding: 15px 20px;
                color: white;
                font-size: 1.5rem;
                font-weight: bold;
                border-bottom: 1px solid rgba(255,255,255,0.1);
                margin-bottom: 20px;
            }

            /* Responsive Design */
            @media (max-width: 768px) {
                .sidebar {
                    transform: translateX(-100%);
                }
                .top-nav {
                    left: 0;
                }
                .main-content {
                    margin-left: 0;
                }
            }
        </style>
    </head>
    <body>
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="logo-area">
                <i class="bi bi-shop"></i> PetShop Staff
            </div>
            <nav class="nav flex-column">
                <a class="nav-link" href="">
                    <i class="bi bi-people"></i> Manage Accounts
                </a>
                <a class="nav-link" href="">
                    <i class="bi bi-grid"></i> Manage Categories
                </a>
                <a class="nav-link" href="">
                    <i class="bi bi-box"></i> Manage Products
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/ManageOption/list">
                    <i class="bi bi-gear"></i> Manage Options
                </a>
                <a class="nav-link" href="">
                    <i class="bi bi-tag"></i> Manage Promotion
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/ManageVoucher">
                    <i class="bi bi-ticket-perforated"></i> Manage Voucher
                </a>
                <a class="nav-link" href="">
                    <i class="bi bi-cart"></i> Manage Order
                </a>
                <a class="nav-link" href="">
                    <i class="bi bi-star"></i> Manage Feedback
                </a>
                <a class="nav-link" href="">
                    <i class="bi bi-file-text"></i> Manage Post
                </a>
                <a class="nav-link" href="">
                    <i class="bi bi-chat"></i> Manage Comment
                </a>



            </nav>
        </div>

        <!-- Top Navigation Bar -->
        <div class="top-nav">
            <div class="profile-dropdown">
                <button class="profile-btn">
                    <img src="${pageContext.request.contextPath}/images/staff-avatar.jpg" 
                         alt="Staff Avatar" class="profile-img">
                    <span>${sessionScope.staffName}</span>
                </button>
                <div class="dropdown-menu">
                    <a href="${pageContext.request.contextPath}/staff/profile" class="dropdown-item">
                        <i class="bi bi-person"></i> Profile
                    </a>
                    <a href="${pageContext.request.contextPath}/staff/settings" class="dropdown-item">
                        <i class="bi bi-gear"></i> Settings
                    </a>
                    <a href="${pageContext.request.contextPath}/logout" class="dropdown-item">
                        <i class="bi bi-box-arrow-right"></i> Logout
                    </a>
                </div>
            </div>
        </div>

        <!-- Main Content Area -->
        <div class="main-content">
            <!-- Content will be loaded here -->
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Highlight active menu item
            document.addEventListener('DOMContentLoaded', function () {
                const currentPath = window.location.pathname;
                const navLinks = document.querySelectorAll('.nav-link');

                navLinks.forEach(link => {
                    if (currentPath.includes(link.getAttribute('href'))) {
                        link.classList.add('active');
                    }
                });
            });
        </script>
    </body>
</html>