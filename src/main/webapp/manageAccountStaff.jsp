<%-- 
    Document   : manageAccountStaff
    Created on : Mar 11, 2025, 11:50:51 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Staff Accounts</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            /* Thiết lập chung */
            body {
                background-color: #f4f4f4;
                color: #333;
                padding: 20px;
                display: flex; /* Sử dụng flexbox */
                margin-left: 100px;
            }

            /* Layout chính */
            .container {
                display: flex;
                flex-direction: row;
                align-items: flex-start; /* Canh trên cùng */
                gap: 20px; /* Khoảng cách giữa các phần */
                margin: 20px;
            }

            /* Sidebar */
            .sidebar {
                width: 250px;
                background-color: white;
                padding: 15px;
                box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }

            /* Nội dung chính */
            .main-content {
                flex-grow: 1;
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                margin-top: 20px;
                margin-left:120px;

                min-height: 500px; /* Đảm bảo chiều cao tối thiểu */
                display: flex;
                flex-direction: column;
                justify-content: space-between; /* Giữ cho phần nội dung không bị đẩy lên */
            }

            /* Tiêu đề */
            h2 {
                color: #2c3e50;
                margin-bottom: 20px;
                font-size: 24px;
            }

            /* Button chung */
            .button,
            a.button {
                background-color: #8AAAE5; /* Màu nền */
                color: white; /* Màu chữ */
                padding: 6px 12px;
                font-size: 14px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-align: center;
                display: inline-block;
                width: 150px;
                margin-top: 15px;
                text-decoration: none;
                height: 50px;
            }

            .button:hover,
            a.button:hover {
                background-color: #7A9AD5; /* Màu nền khi hover */
            }

            /* Bảng */
            table {
                width: auto;
                border-collapse: collapse;
                margin-top: 20px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                background: white;
            }

            table, th, td {
                border: 1px solid #bdc3c7;
            }

            th, td {
                padding: 10px;
                text-align: left;
            }

            th {
                background-color: #8AAAE5; /* Màu nền tiêu đề bảng */
                color: white; /* Màu chữ tiêu đề bảng */
            }

            /* Hàng chẵn có màu nền khác */
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            tr:hover {
                background-color: #ddd;
            }

            /* Link trong bảng */
            td a {
                text-decoration: none;
                color: #2c3e50; /* Màu chữ link */
                padding: 5px;
                margin-right: 5px;
            }

            td a:hover {
                color: #34495e; /* Màu chữ link khi hover */
            }

            /* Nút Edit & Delete */
            td form button {
                background-color: #8AAAE5; /* Màu nền cho nút Edit & Delete */
                color: white; /* Màu chữ cho nút Edit & Delete */
                padding: 5px 10px;
                border-radius: 5px;
                border: none; /* Không có viền */
                cursor: pointer; /* Con trỏ chuột khi di chuột qua */
            }

            td form button:hover {
                background-color: #7A9AD5; /* Màu nền khi hover */
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="sidebar">
                <jsp:include page="manageAdmin.jsp" />
            </div>
            <div class="main-content">
                <h2>Staff Account List</h2>
                <a href="StaffManageAccountController?action=new" class="button">Add New Staff</a>
                <table>
                    <tr>
                        <th>Account ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Phone</th>
                        <th>Status</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Actions</th>
                    </tr>

                    <c:forEach var="account" items="${accountList}">
                        <c:set var="status" value="${statusMap[account.accountId]}" />
                        <c:set var="role" value="${roleLookup[account.roleId]}" />

                        <tr>
                            <td>${account.accountId}</td>
                            <td>${account.username}</td>
                            <td>${account.email}</td>
                            <td>${role != null ? role.roleName : 'No Role'}</td>
                            <td>${account.phoneNumber}</td>
                            <td>${status == 1 ? 'Active' : 'Inactive'}</td>
                            <td>${account.firstName}</td>
                            <td>${account.lastName}</td>
                            <td>${account.dateOfBirth}</td>
                            <td>${account.gender}</td>
                            <td>
                                <form action="StaffManageAccountController" method="get" style="display:inline;">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="id" value="${account.accountId}">
                                    <button type="submit">Edit</button>
                                </form>

                                <form action="StaffManageAccountController" method="post" style="display:inline;" 
                                      onsubmit="return confirm('Are you sure you want to delete this account?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${account.accountId}">
                                    <button type="submit">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <a href="manageAdmin.jsp">
                    <button class="button">Back to Manage Staff</button>
                </a>
            </div>
        </div>
    </body>

</html>


