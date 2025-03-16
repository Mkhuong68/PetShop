<%-- 
    Document   : managedelivery
    Created on : Feb 22, 2025, 5:23:46 PM
    Author     : tvhun
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Manage Delivery</title>
        <!-- Font Awesome cho icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <!-- File CSS chung -->
        <link rel="stylesheet" href="./assets/css/managedelivery.css" />
        
        <style>
            /* Reset và cài đặt chung */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Poppins', sans-serif;
            }
            
            :root {
                --primary-color: #4361ee;
                --primary-light: #4895ef;
                --primary-dark: #3f37c9;
                --secondary-color: #f72585;
                --text-color: #333333;
                --text-light: #718096;
                --bg-color: #f8f9fa;
                --bg-sidebar: #ffffff;
                --border-color: #e2e8f0;
                --shadow-color: rgba(0, 0, 0, 0.1);
                --success-color: #10b981;
                --warning-color: #f59e0b;
                --danger-color: #ef4444;
                
                --transition-speed: 0.3s;
                --border-radius: 10px;
            }
            
            body {
                background-color: var(--bg-color);
                color: var(--text-color);
                transition: all var(--transition-speed) ease;
                min-height: 100vh;
                display: flex;
                overflow-x: hidden;
            }
            
            /* SIDEBAR STYLES */
            .sidebar {
                width: 280px;
                background-color: var(--bg-sidebar);
                border-right: 1px solid var(--border-color);
                padding: 25px 0;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                height: 100vh;
                position: fixed;
                top: 0;
                left: 0;
                box-shadow: 2px 0 10px var(--shadow-color);
                transition: all var(--transition-speed) ease;
                z-index: 1000;
            }
            
            .logo {
                font-size: 22px;
                font-weight: 700;
                color: var(--primary-color);
                text-align: center;
                padding: 20px 25px;
                margin-bottom: 20px;
                position: relative;
                letter-spacing: 0.5px;
            }
            
            .logo:after {
                content: '';
                position: absolute;
                width: 50px;
                height: 3px;
                background-color: var(--primary-color);
                bottom: 10px;
                left: 50%;
                transform: translateX(-50%);
                border-radius: 5px;
            }
            
            .menu {
                list-style: none;
                padding: 0 15px;
                margin-top: 20px;
            }
            
            .menu li {
                margin-bottom: 8px;
            }
            
            .menu a {
                display: flex;
                align-items: center;
                text-decoration: none;
                color: var(--text-color);
                padding: 12px 20px;
                border-radius: var(--border-radius);
                font-weight: 500;
                position: relative;
                transition: all var(--transition-speed) ease;
            }
            
            .menu a:before {
                content: '';
                position: absolute;
                left: 0;
                top: 0;
                height: 100%;
                width: 3px;
                background-color: var(--primary-color);
                border-radius: 0 3px 3px 0;
                transform: scaleY(0);
                transition: transform var(--transition-speed) ease;
            }
            
            .menu a:hover {
                background-color: rgba(67, 97, 238, 0.05);
                color: var(--primary-color);
                transform: translateX(5px);
            }
            
            .menu a:hover:before {
                transform: scaleY(1);
            }
            
            .menu a.active {
                background-color: rgba(67, 97, 238, 0.1);
                color: var(--primary-color);
                font-weight: 600;
            }
            
            .menu a.active:before {
                transform: scaleY(1);
            }
            
            .menu a i {
                margin-right: 10px;
                font-size: 18px;
            }
            
            .sidebar-footer {
                padding: 20px 25px;
                border-top: 1px solid var(--border-color);
                margin-top: auto;
            }
            
            .dark-mode-toggle {
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 20px;
                padding: 5px 0;
            }
            
            .dark-mode-toggle label {
                font-weight: 500;
                font-size: 14px;
                color: var(--text-light);
            }
            
            /* Nút dark mode kiểu switch */
            #darkModeSwitch {
                position: relative;
                appearance: none;
                width: 50px;
                height: 24px;
                background-color: #ccc;
                border-radius: 50px;
                outline: none;
                transition: var(--transition-speed);
                cursor: pointer;
            }
            
            #darkModeSwitch:before {
                content: '';
                position: absolute;
                top: 2px;
                left: 2px;
                width: 20px;
                height: 20px;
                border-radius: 50%;
                background-color: white;
                transition: var(--transition-speed);
            }
            
            #darkModeSwitch:checked {
                background-color: var(--primary-color);
            }
            
            #darkModeSwitch:checked:before {
                transform: translateX(26px);
            }
            
            .logout-btn {
                display: block;
                padding: 12px 20px;
                background-color: var(--primary-color);
                color: white;
                border-radius: var(--border-radius);
                text-align: center;
                text-decoration: none;
                font-weight: 600;
                transition: all var(--transition-speed) ease;
                box-shadow: 0 4px 12px rgba(67, 97, 238, 0.2);
            }
            
            .logout-btn:hover {
                background-color: var(--primary-dark);
                transform: translateY(-2px);
                box-shadow: 0 6px 15px rgba(67, 97, 238, 0.3);
            }
            
            /* MAIN CONTENT STYLES */
            .main-content {
                margin-left: 280px;
                width: calc(100% - 280px);
                padding: 0;
                transition: all var(--transition-speed) ease;
            }
            
            header {
                padding: 15px 30px;
                background-color: var(--bg-sidebar);
                border-bottom: 1px solid var(--border-color);
                display: flex;
                justify-content: flex-end;
                align-items: center;
                position: sticky;
                top: 0;
                z-index: 10;
                box-shadow: 0 2px 10px var(--shadow-color);
            }
            
            .user-info {
                display: flex;
                align-items: center;
                font-weight: 500;
                gap: 12px;
            }
            
            .user-avatar {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background-color: var(--primary-light);
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-weight: bold;
                position: relative;
                overflow: hidden;
                cursor: pointer;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
                transition: all var(--transition-speed) ease;
            }
            
            .user-avatar:hover {
                transform: scale(1.05);
            }
            
            .user-avatar:after {
                content: 'U';
                font-size: 18px;
            }
            
            /* DARK MODE STYLES */
            body.dark-mode {
                --bg-color: #1a202c;
                --bg-sidebar: #2d3748;
                --text-color: #e2e8f0;
                --text-light: #a0aec0;
                --border-color: #4a5568;
                --shadow-color: rgba(0, 0, 0, 0.3);
            }
            
            body.dark-mode .sidebar {
                box-shadow: 2px 0 15px rgba(0, 0, 0, 0.4);
            }
            
            body.dark-mode .logo {
                color: #7f9cf5;
            }
            
            body.dark-mode .logo:after {
                background-color: #7f9cf5;
            }
            
            body.dark-mode .user-avatar {
                background-color: #4a5568;
            }
            
            body.dark-mode header {
                box-shadow: 0 2px 15px rgba(0, 0, 0, 0.4);
            }
            
            /* RESPONSIVE STYLES */
            @media (max-width: 992px) {
                .sidebar {
                    width: 220px;
                    padding: 20px 0;
                }
                
                .logo {
                    font-size: 20px;
                    padding: 15px 20px;
                }
                
                .main-content {
                    margin-left: 220px;
                    width: calc(100% - 220px);
                }
            }
            
            @media (max-width: 768px) {
                .sidebar {
                    width: 70px;
                    padding: 15px 0;
                }
                
                .logo {
                    font-size: 16px;
                    padding: 10px;
                    text-align: center;
                }
                
                .logo:after {
                    width: 30px;
                }
                
                .menu a {
                    padding: 12px;
                    justify-content: center;
                }
                
                .menu a i {
                    margin-right: 0;
                }
                
                .menu a span {
                    display: none;
                }
                
                .sidebar-footer {
                    padding: 15px;
                }
                
                .dark-mode-toggle label {
                    display: none;
                }
                
                .dark-mode-toggle {
                    justify-content: center;
                }
                
                .logout-btn {
                    padding: 10px;
                    font-size: 0;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                }
                
                .logout-btn:after {
                    content: '\f2f5';
                    font-family: "Font Awesome 5 Free";
                    font-weight: 900;
                    font-size: 16px;
                }
                
                .main-content {
                    margin-left: 70px;
                    width: calc(100% - 70px);
                }
            }
            
            @media (max-width: 576px) {
                header {
                    padding: 10px 15px;
                }
                
                .user-info {
                    font-size: 14px;
                }
                
                .user-avatar {
                    width: 35px;
                    height: 35px;
                }
            }
        </style>
    </head>
    <body>
        <!-- SIDEBAR -->
        <div class="sidebar">
            <div>
                <div class="logo">Manage Delivery</div>
                <ul class="menu">
                    <li><a href="deliveryList" class="active"><i class="fas fa-shipping-fast"></i> <span>Manage Orders</span></a></li>
                    <li><a href="#"><i class="fas fa-route"></i> <span>Manage Routes</span></a></li>
                    <li><a href="#"><i class="fas fa-bell"></i> <span>Manage Notifications</span></a></li>
                </ul>
            </div>
            <div class="sidebar-footer">
                <div class="dark-mode-toggle">
                    <label for="darkModeSwitch">Dark Mode</label>
                    <input type="checkbox" id="darkModeSwitch"/>
                </div>
                <a href="#" class="logout-btn"><i class="fas fa-sign-out-alt"></i> <span>Logout</span></a>
            </div>
        </div>

        <!-- MAIN CONTENT -->
        <div class="main-content">
            <header>
                <div class="user-info">
                    Hello, User
                    <div class="user-avatar"></div>
                </div>
            </header>
</html>