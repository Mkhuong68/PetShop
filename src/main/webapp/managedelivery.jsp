<%-- 
    Document   : managedelivery
    Created on : Feb 22, 2025, 5:23:46 PM
    Author     : tvhun
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Manage Delivery</title>
        <!-- Font Awesome icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <!-- Common CSS file -->
        <link rel="stylesheet" href="./assets/css/managedelivery.css" />

        <style>
            /* Reset and general settings */
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

            /* Dark mode switch button */
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
            .notification-container {
                position: relative;
                margin-right: 20px;
            }

            .notification-icon {
                position: relative;
                cursor: pointer;
                display: flex;
                align-items: center;
                justify-content: center;
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background-color: #f0f0f0;
                transition: all 0.3s ease;
            }

            .notification-icon:hover {
                background-color: #e0e0e0;
            }

            .notification-icon i {
                font-size: 18px;
                color: #333;
            }

            .notification-badge {
                position: absolute;
                top: -5px;
                right: -5px;
                background-color: var(--danger-color);
                color: white;
                border-radius: 50%;
                width: 20px;
                height: 20px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 10px;
                font-weight: bold;
                border: 2px solid white;
                transform: scale(0);
                transition: transform 0.3s ease;
            }

            .notification-badge.show {
                transform: scale(1);
            }

            .notification-dropdown {
                position: absolute;
                top: 50px;
                right: 0;
                width: 350px;
                max-height: 450px;
                background-color: white;
                border-radius: 10px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
                z-index: 1000;
                display: none;
                overflow: hidden;
                transition: all 0.3s ease;
            }

            .notification-dropdown.show {
                display: block;
                animation: fadeIn 0.3s ease;
            }

            .notification-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 15px;
                border-bottom: 1px solid var(--border-color);
            }

            .notification-header h3 {
                margin: 0;
                font-size: 16px;
                font-weight: 600;
            }

            #markAllAsRead {
                background: none;
                border: none;
                color: var(--primary-color);
                font-size: 13px;
                cursor: pointer;
                padding: 5px;
            }

            #markAllAsRead:hover {
                text-decoration: underline;
            }

            .notification-list {
                max-height: 350px;
                overflow-y: auto;
                padding: 0;
            }

            .notification-item {
                padding: 15px;
                border-bottom: 1px solid var(--border-color);
                display: flex;
                align-items: flex-start;
                cursor: pointer;
                transition: all 0.2s ease;
            }

            .notification-item:hover {
                background-color: rgba(67, 97, 238, 0.05);
            }

            .notification-item.unread {
                background-color: rgba(67, 97, 238, 0.08);
            }

            .notification-item.unread:hover {
                background-color: rgba(67, 97, 238, 0.12);
            }

            .notification-icon-wrapper {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                margin-right: 15px;
                flex-shrink: 0;
            }

            .notification-icon-wrapper.order {
                background-color: rgba(67, 97, 238, 0.1);
                color: var(--primary-color);
            }

            .notification-icon-wrapper.promotion {
                background-color: rgba(247, 37, 133, 0.1);
                color: var(--secondary-color);
            }

            .notification-content {
                flex: 1;
            }

            .notification-message {
                font-size: 14px;
                color: var(--text-color);
                margin-bottom: 5px;
                line-height: 1.4;
            }

            .notification-time {
                font-size: 12px;
                color: var(--text-light);
            }

            .notification-footer {
                padding: 15px;
                text-align: center;
                border-top: 1px solid var(--border-color);
            }

            .notification-footer a {
                color: var(--primary-color);
                text-decoration: none;
                font-size: 14px;
                font-weight: 500;
            }

            .notification-footer a:hover {
                text-decoration: underline;
            }

            .empty-notification {
                padding: 30px 15px;
                text-align: center;
                color: var(--text-light);
                font-size: 14px;
            }

            /* Animation */
            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(-10px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            /* Dark mode adjustments */
            body.dark-mode .notification-dropdown {
                background-color: var(--bg-sidebar);
                border: 1px solid var(--border-color);
            }

            body.dark-mode .notification-item:hover {
                background-color: rgba(127, 156, 245, 0.1);
            }

            body.dark-mode .notification-item.unread {
                background-color: rgba(127, 156, 245, 0.15);
            }

            body.dark-mode .notification-item.unread:hover {
                background-color: rgba(127, 156, 245, 0.2);
            }

            /* Responsive adjustments */
            @media (max-width: 576px) {
                .notification-dropdown {
                    width: 300px;
                    right: -20px;
                }
            }

            .notification-dropdown.show {
                display: block !important;
                opacity: 1 !important;
                visibility: visible !important;
                z-index: 9999 !important;
            }

            .notification-message {
                font-size: 14px !important;
                color: #333 !important;
                margin-bottom: 5px !important;
                display: block !important;
                opacity: 1 !important;
                visibility: visible !important;
            }

            .notification-time {
                font-size: 12px !important;
                color: #666 !important;
                display: block !important;
                opacity: 1 !important;
                visibility: visible !important;
            }

            .notification-item {
                display: flex !important;
                padding: 15px !important;
                border-bottom: 1px solid #e0e0e0 !important;
                align-items: flex-start !important;
                background-color: #fff !important;
            }

            .notification-item.unread {
                background-color: #f1f5fe !important;
            }

            .notification-content {
                display: block !important;
                visibility: visible !important;
                opacity: 1 !important;
                flex: 1 !important;
            }
        </style>
    </head>
    <body>
        <!-- SIDEBAR -->
        <div class="sidebar">
            <div>
                <div class="logo">Manage Delivery</div>
                <ul class="menu">
                    <li><a href="deliveryList"><i class="fas fa-shipping-fast"></i> <span>Manage Orders</span></a></li>
                    <li><a href="/manage-issues"><i class="fas fa-exclamation-circle"></i> <span>Manage Issues</span></a></li>
                    <li><a href="/notifications?action=view"><i class="fas fa-bell"></i> <span>Manage Notifications</span></a></li>
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
                <div class="notification-container">
                    <div class="notification-icon" id="notificationIcon">
                        <i class="fas fa-bell"></i>
                        <span class="notification-badge" id="notificationCount">0</span>
                    </div>
                    <div class="notification-dropdown" id="notificationDropdown">
                        <div class="notification-header">
                            <h3>Notifications</h3>
                            <button id="markAllAsRead">Mark all as read</button>
                        </div>
                        <div class="notification-list" id="notificationList">
                            <!-- Notification list will be added here through JavaScript -->
                            <div class="empty-notification">No new notifications</div>
                        </div>
                        <div class="notification-footer">
                            <a href="/notifications?action=view">View all notifications</a>
                        </div>
                    </div>
                </div>
                <div class="user-info">
                    Hello, ${account.username}
                    <div class="user-avatar"></div>
                </div>
            </header>
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    const notificationIcon = document.getElementById('notificationIcon');
                    const notificationDropdown = document.getElementById('notificationDropdown');
                    const notificationList = document.getElementById('notificationList');
                    const notificationCount = document.getElementById('notificationCount');
                    const markAllAsReadBtn = document.getElementById('markAllAsRead');

                    let notifications = [];
                    let unreadCount = 0;

                    // Fetch notifications from server
                    function fetchNotifications() {
                        console.log('Đang tải thông báo...');
                        fetch('/delivery-notifications')
                                .then(response => {
                                    console.log('Trạng thái phản hồi:', response.status);
                                    if (!response.ok) {
                                        throw new Error('Network response was not ok');
                                    }
                                    return response.json();
                                })
                                .then(data => {
                                    // In dữ liệu JSON để kiểm tra cấu trúc
                                    console.log('Dữ liệu JSON nhận được:', data);

                                    // Kiểm tra và điều chỉnh cấu trúc dữ liệu nếu cần
                                    if (data.notifications) {
                                        notifications = data.notifications;
                                    } else if (Array.isArray(data)) {
                                        // Trường hợp API trả về mảng thay vì object
                                        notifications = data;
                                    } else {
                                        // Trường hợp cấu trúc khác
                                        console.log('Cấu trúc dữ liệu khác với dự kiến:', data);
                                        notifications = data.notifications || [];
                                    }

                                    // Xác định unreadCount
                                    if (data.unreadCount !== undefined) {
                                        unreadCount = data.unreadCount;
                                    } else {
                                        // Tính toán số lượng thông báo chưa đọc nếu API không trả về
                                        unreadCount = notifications.filter(n => !n.isRead).length;
                                    }

                                    console.log('Số thông báo:', notifications.length);
                                    console.log('Số thông báo chưa đọc:', unreadCount);

                                    updateNotificationBadge();
                                    renderNotifications();
                                })
                                .catch(error => {
                                    console.error('Lỗi khi tải thông báo:', error);
                                });
                    }

                    // Update unread notification count
                    function updateNotificationBadge() {
                        console.log('Cập nhật badge với số lượng:', unreadCount);
                        if (unreadCount > 0) {
                            notificationCount.textContent = unreadCount > 9 ? '9+' : unreadCount;
                            notificationCount.classList.add('show');
                        } else {
                            notificationCount.classList.remove('show');
                        }
                    }

                    // Display notification list
                    function renderNotifications() {
                        console.log('Đang render thông báo, số lượng:', notifications.length);
                        notificationList.innerHTML = '';

                        if (!notifications || notifications.length === 0) {
                            notificationList.innerHTML = '<div class="empty-notification" style="padding:30px;text-align:center;color:#666;">Không có thông báo mới</div>';
                            return;
                        }

                        // Tạo container bao quanh tất cả notification items để đảm bảo chúng hiển thị
                        const container = document.createElement('div');
                        container.style.display = 'block';
                        container.style.width = '100%';
                        container.style.visibility = 'visible';

                        notifications.forEach(notification => {
                            console.log('Đang tạo thông báo:', notification);

                            // Tạo div cho notification item với inline style
                            const item = document.createElement('div');
                            item.className = `notification-item ${!notification.isRead ? 'unread' : ''}`;
                            item.style.display = 'flex';
                            item.style.padding = '15px';
                            item.style.borderBottom = '1px solid #e0e0e0';
                            item.style.backgroundColor = notification.isRead ? '#fff' : '#f1f5fe';

                            // ID của notification
                            const notificationId = notification.notificationId || notification.id;
                            item.dataset.id = notificationId;

                            // Icon
                            const iconWrapper = document.createElement('div');
                            iconWrapper.className = 'notification-icon-wrapper order';
                            iconWrapper.style.width = '40px';
                            iconWrapper.style.height = '40px';
                            iconWrapper.style.backgroundColor = '#e1ebff';
                            iconWrapper.style.borderRadius = '50%';
                            iconWrapper.style.display = 'flex';
                            iconWrapper.style.alignItems = 'center';
                            iconWrapper.style.justifyContent = 'center';
                            iconWrapper.style.marginRight = '15px';
                            iconWrapper.style.flexShrink = '0';
                            iconWrapper.style.color = '#4361ee';

                            const icon = document.createElement('i');
                            icon.className = 'fas fa-shipping-fast';
                            iconWrapper.appendChild(icon);

                            // Content
                            const content = document.createElement('div');
                            content.className = 'notification-content';
                            content.style.flex = '1';
                            content.style.display = 'block';

                            // Message
                            const messageEl = document.createElement('div');
                            messageEl.className = 'notification-message';
                            messageEl.style.fontSize = '14px';
                            messageEl.style.color = '#333';
                            messageEl.style.marginBottom = '5px';
                            messageEl.style.display = 'block';
                            messageEl.textContent = notification.message || notification.content || '';

                            // Time
                            const timeEl = document.createElement('div');
                            timeEl.className = 'notification-time';
                            timeEl.style.fontSize = '12px';
                            timeEl.style.color = '#666';
                            timeEl.style.display = 'block';

                            // Format time
                            let timeDisplay = notification.createdDate || 'Không rõ thời gian';
                            timeEl.textContent = timeDisplay;

                            // Add debug info
                            const debugEl = document.createElement('div');
                            debugEl.style.fontSize = '10px';
                            debugEl.style.color = '#999';
                            debugEl.style.marginTop = '3px';
                            debugEl.textContent = 'ID: ' + notificationId;

                            // Append all elements
                            content.appendChild(messageEl);
                            content.appendChild(timeEl);
                            content.appendChild(debugEl);

                            item.appendChild(iconWrapper);
                            item.appendChild(content);

                            // Handle click
                            item.addEventListener('click', function () {
                                markAsRead(notificationId);
                                window.location.href = '/deliveryList';
                            });

                            container.appendChild(item);
                        });

                        notificationList.appendChild(container);
                    }

                    // Mark notification as read
                    function markAsRead(notificationId) {
                        fetch('/notifications', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded',
                            },
                            body: `action=markAsRead&notificationId=${notificationId}`
                        })
                                .then(response => response.json())
                                .then(data => {
                                    if (data.success) {
                                        unreadCount = data.unreadCount;
                                        updateNotificationBadge();

                                        // Update UI
                                        const notificationItem = document.querySelector(`.notification-item[data-id="${notificationId}"]`);
                                        if (notificationItem) {
                                            notificationItem.classList.remove('unread');
                                        }

                                        // Update notifications array
                                        notifications = notifications.map(notification => {
                                            if (notification.notificationId === notificationId) {
                                                notification.isRead = true;
                                            }
                                            return notification;
                                        });
                                    }
                                })
                                .catch(error => {
                                    console.error('Error marking notification as read:', error);
                                });
                    }

                    // Mark all notifications as read
                    function markAllAsRead() {
                        fetch('/notifications', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded',
                            },
                            body: 'action=markAllAsRead'
                        })
                                .then(response => response.json())
                                .then(data => {
                                    if (data.success) {
                                        unreadCount = 0;
                                        updateNotificationBadge();

                                        // Update UI
                                        const unreadItems = document.querySelectorAll('.notification-item.unread');
                                        unreadItems.forEach(item => {
                                            item.classList.remove('unread');
                                        });

                                        // Update notifications array
                                        notifications = notifications.map(notification => {
                                            notification.isRead = true;
                                            return notification;
                                        });
                                    }
                                })
                                .catch(error => {
                                    console.error('Error marking all notifications as read:', error);
                                });
                    }

                    // Handle notification icon click event
                    notificationIcon.addEventListener('click', function (e) {
                        e.stopPropagation();
                        console.log('Đã click vào icon thông báo');
                        notificationDropdown.classList.toggle('show');

                        // Nếu dropdown đang hiển thị, tải lại thông báo
                        if (notificationDropdown.classList.contains('show')) {
                            fetchNotifications();
                        }
                    });

                    // Đảm bảo dropdown hiển thị đúng
                    document.addEventListener('click', function (e) {
                        if (!notificationDropdown.contains(e.target) && !notificationIcon.contains(e.target)) {
                            notificationDropdown.classList.remove('show');
                        }
                    });

                    // Thêm style đảm bảo dropdown hiển thị
                    const style = document.createElement('style');
                    style.textContent = `
        .notification-dropdown.show {
            display: block !important;
            opacity: 1 !important;
            visibility: visible !important;
            z-index: 9999 !important;
        }
    `;
                    document.head.appendChild(style);

                    // Tải thông báo khi trang tải
                    fetchNotifications();

                    // Gán sự kiện cho nút markAllAsRead
                    markAllAsReadBtn.addEventListener('click', function (e) {
                        e.stopPropagation();
                        markAllAsRead();
                    });
                });
            </script>
            <script>
    document.addEventListener('DOMContentLoaded', function() {
        // Dark mode toggle
        const darkModeSwitch = document.getElementById('darkModeSwitch');
        
        // Đọc trạng thái dark mode từ localStorage
        const isDarkMode = localStorage.getItem('darkMode') === 'true';
        
        // Thiết lập ban đầu
        if (isDarkMode) {
            document.body.classList.add('dark-mode');
            if (darkModeSwitch) darkModeSwitch.checked = true;
        }
        
        // Xử lý toggle dark mode
        if (darkModeSwitch) {
            darkModeSwitch.addEventListener('change', () => {
                document.body.classList.toggle('dark-mode', darkModeSwitch.checked);
                localStorage.setItem('darkMode', darkModeSwitch.checked);
            });
        }
        
        // Xử lý active menu dựa vào URL hiện tại
        const currentPath = window.location.pathname;
        const menuLinks = document.querySelectorAll('.menu a');
        
        menuLinks.forEach(link => {
            // Xóa class active trước
            link.classList.remove('active');
            
            // Lấy đường dẫn của link
            const linkPath = link.getAttribute('href');
            
            // So sánh và thiết lập active class
            if ((currentPath.includes('deliveryList') || currentPath === "/") && linkPath.includes('deliveryList')) {
                link.classList.add('active');
            } else if (currentPath.includes('manage-issues') && linkPath.includes('manage-issues')) {
                link.classList.add('active');
            } else if (currentPath.includes('notifications') && linkPath.includes('notifications')) {
                link.classList.add('active');
            }
        });
    });
</script>
</html>