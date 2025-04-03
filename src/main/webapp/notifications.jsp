<%-- 
    Document   : notifications
    Created on : Mar 25, 2025, 11:49:56 PM
    Author     : tvhun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="managedelivery.jsp" %>

<style>
    /* Enhanced styling for notification page */
    .notification-page-container {
        padding: 25px;
        max-width: 1000px;
        margin: 20px auto;
        background-color: white;
        border-radius: 15px;
        box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05);
    }

    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 25px;
        padding-bottom: 15px;
        border-bottom: 1px solid var(--border-color);
    }

    .header h1 {
        font-size: 24px;
        font-weight: 600;
        color: var(--text-color);
        margin: 0;
    }

    .notification-actions {
        display: flex;
        gap: 12px;
    }

    .btn {
        padding: 10px 18px;
        border-radius: 8px;
        border: none;
        font-size: 14px;
        font-weight: 500;
        cursor: pointer;
        transition: all 0.3s ease;
        display: flex;
        align-items: center;
        gap: 6px;
    }

    .btn i {
        font-size: 14px;
    }

    .btn-primary {
        background-color: var(--primary-color);
        color: white;
    }

    .btn-primary:hover {
        background-color: var(--primary-dark);
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .btn-outline {
        background-color: transparent;
        color: var(--primary-color);
        border: 1px solid var(--primary-color);
    }

    .btn-outline:hover {
        background-color: rgba(67, 97, 238, 0.05);
    }

    .notification-list {
        display: flex;
        flex-direction: column;
        gap: 15px;
        margin-bottom: 20px;
    }

    .notification-item {
        display: flex;
        padding: 18px;
        border-radius: 12px;
        background-color: white;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
        transition: all 0.3s ease;
        cursor: pointer;
        border: 1px solid var(--border-color);
    }

    .notification-item:hover {
        transform: translateY(-2px);
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
    }

    .notification-item.unread {
        border-left: 3px solid var(--primary-color);
        background-color: rgba(67, 97, 238, 0.04);
    }

    .notification-icon {
        width: 50px;
        height: 50px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 18px;
        flex-shrink: 0;
        font-size: 18px;
    }

    .notification-icon.order {
        background-color: rgba(67, 97, 238, 0.1);
        color: var(--primary-color);
    }

    .notification-icon.promotion {
        background-color: rgba(247, 37, 133, 0.1);
        color: var(--secondary-color);
    }

    .notification-icon.comment {
        background-color: rgba(245, 158, 11, 0.1);
        color: var(--warning-color);
    }

    .notification-content {
        flex: 1;
    }

    .notification-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 8px;
        align-items: center;
    }

    .notification-title {
        font-weight: 600;
        font-size: 16px;
        color: var(--text-color);
    }

    .notification-time {
        color: var(--text-light);
        font-size: 14px;
        background-color: rgba(0, 0, 0, 0.03);
        padding: 4px 8px;
        border-radius: 6px;
    }

    .notification-message {
        font-size: 15px;
        line-height: 1.5;
        color: var(--text-color);
    }

    .empty-message {
        text-align: center;
        padding: 50px 0;
        color: var(--text-light);
        font-size: 16px;
    }

    .empty-message i {
        font-size: 40px;
        margin-bottom: 15px;
        display: block;
        color: var(--border-color);
    }

    /* Dark mode adjustments */
    body.dark-mode .notification-page-container {
        background-color: var(--bg-sidebar);
    }

    body.dark-mode .notification-item {
        background-color: var(--bg-sidebar);
        border-color: var(--border-color);
    }

    body.dark-mode .notification-item.unread {
        background-color: rgba(127, 156, 245, 0.1);
    }

    body.dark-mode .notification-time {
        background-color: rgba(255, 255, 255, 0.05);
    }

    /* Loading animation */
    .loading-indicator {
        display: flex;
        justify-content: center;
        padding: 30px 0;
    }

    .loading-spinner {
        width: 40px;
        height: 40px;
        border: 3px solid rgba(0, 0, 0, 0.1);
        border-radius: 50%;
        border-top-color: var(--primary-color);
        animation: spin 1s ease-in-out infinite;
    }

    @keyframes spin {
        to {
            transform: rotate(360deg);
        }
    }
</style>

<!-- MAIN CONTENT -->
<div class="notification-page-container">
    <div class="header">
        <h1>Your Notifications</h1>
        <div class="notification-actions">
            <button class="btn btn-primary" id="markAllAsRead">
                <i class="fas fa-check-double"></i> Mark All as Read
            </button>
            <button class="btn btn-outline" id="refreshBtn">
                <i class="fas fa-sync-alt"></i> Refresh
            </button>
        </div>
    </div>

    <div class="notification-list" id="notificationList">
        <!-- Use server-side data if available, otherwise show loading message -->
        <c:choose>
            <c:when test="${not empty notificationList}">
                <c:forEach items="${notificationList}" var="notification">
                    <div class="notification-item ${notification.isRead ? '' : 'unread'}" data-id="${notification.notificationId}">
                        <div class="notification-icon order">
                            <i class="fas fa-shipping-fast"></i>
                        </div>
                        <div class="notification-content">
                            <div class="notification-header">
                                <div class="notification-title">Order</div>
                                <div class="notification-time">${notification.createdDate}</div>
                            </div>
                            <div class="notification-message">${notification.message}</div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="loading-indicator">
                    <div class="loading-spinner"></div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</div> <!-- Close .main-content div opened in managedelivery.jsp -->

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const notificationList = document.getElementById('notificationList');
        const markAllAsReadBtn = document.getElementById('markAllAsRead');
        const refreshBtn = document.getElementById('refreshBtn');

        // Global variables
        let notifications = [];
        let unreadCount = 0;

        // Kiểm tra nếu đã có dữ liệu từ server
    <c:if test="${empty notificationList}">
        // Nếu không có dữ liệu từ server, tải bằng AJAX
        fetchNotifications();
    </c:if>

        // Fetch notifications list
        function fetchNotifications() {
            console.log("Fetching notifications...");

            // Hiển thị đang tải
            notificationList.innerHTML = `
                <div class="loading-indicator">
                    <div class="loading-spinner"></div>
                </div>
            `;

            // Thực hiện request đến API
            fetch('/notifications', {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            })
                    .then(response => {
                        console.log("Response status:", response.status);
                        return response.text().then(text => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            try {
                                return JSON.parse(text);
                            } catch (e) {
                                console.error("JSON parse error:", e);
                                throw new Error('Invalid JSON response');
                            }
                        });
                    })
                    .then(data => {
                        console.log("Data received:", data);
                        if (data && data.notifications) {
                            notifications = data.notifications;
                            unreadCount = data.unreadCount;
                            renderNotifications();
                        } else {
                            console.error("Invalid data format:", data);
                            showEmptyMessage("Unable to load notifications. Please try again later.");
                        }
                    })
                    .catch(error => {
                        console.error('Error fetching notifications:', error);
                        showEmptyMessage("Error loading notifications: " + error.message);
                    });
        }

        // Display error/empty message
        function showEmptyMessage(message) {
            notificationList.innerHTML = `
                <div class="empty-message">
                    <i class="fas fa-bell-slash"></i>
    ${message}
                </div>
            `;
        }

        // Display notification list
        function renderNotifications() {
            notificationList.innerHTML = '';

            if (!notifications || notifications.length === 0) {
                showEmptyMessage("You have no notifications.");
                return;
            }

            notifications.forEach(notification => {
                const notificationItem = document.createElement('div');
                notificationItem.className = `notification-item ${!notification.isRead ? 'unread' : ''}`;
                notificationItem.dataset.id = notification.notificationId;

                let iconClass = 'fa-shipping-fast';
                let iconType = 'order';
                let title = 'Order';

                notificationItem.innerHTML = `
                    <div class="notification-icon ${iconType}">
                        <i class="fas ${iconClass}"></i>
                    </div>
                    <div class="notification-content">
                        <div class="notification-header">
                            <div class="notification-title">${title}</div>
                            <div class="notification-time">${notification.createdDate}</div>
                        </div>
                        <div class="notification-message">${notification.message}</div>
                    </div>
                `;

                notificationItem.addEventListener('click', function () {
                    markAsRead(notification.notificationId);
                });

                notificationList.appendChild(notificationItem);
            });
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
                            const notificationItem = document.querySelector(`.notification-item[data-id="${notificationId}"]`);
                            if (notificationItem) {
                                notificationItem.classList.remove('unread');
                            }
                            unreadCount = Math.max(0, unreadCount - 1);
                        }
                    })
                    .catch(error => {
                        console.error('Error marking notification as read:', error);
                    });
        }

        // Mark all notifications as read
        markAllAsReadBtn.addEventListener('click', function () {
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
                            document.querySelectorAll('.notification-item').forEach(item => {
                                item.classList.remove('unread');
                            });
                            unreadCount = 0;
                        }
                    })
                    .catch(error => {
                        console.error('Error marking all notifications as read:', error);
                    });
        });

        // Refresh button
        if (refreshBtn) {
            refreshBtn.addEventListener('click', fetchNotifications);
        }

        // Đánh dấu tất cả các notification items click events
        document.querySelectorAll('.notification-item').forEach(item => {
            item.addEventListener('click', function () {
                const notificationId = this.dataset.id;
                markAsRead(notificationId);
            });
        });
    });
</script>