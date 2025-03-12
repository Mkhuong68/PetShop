<%-- 
    Document   : manageAccountStaff
    Created on : Mar 4, 2025, 11:50:51 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Manage Staff Accounts</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <h2>Staff Account List</h2>
        <a href="StaffManageAccountController?action=new">Add New Staff</a>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Active</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Date of Birth</th>
                    <th>Gender</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="account" items="${accountList}">
                    <tr>
                        <td>${account.accountId}</td>
                        <td>${account.username}</td>
                        <td>${account.email}</td>
                        <td>${account.phoneNumber}</td>
                        <td>${account[isActive]}</td>
                        <td>${account.firstName}</td>
                        <td>${account.lastName}</td>
                        <td>${account.dateOfBirth}</td>
                        <td>${account.gender}</td>
                        <td>
                            <a href="StaffManageAccountController?action=edit&id=${account.accountId}">Edit</a> |
                            <a href="StaffManageAccountController?action=delete&id=${account.accountId}" onclick="return confirm('Are you sure you want to delete this account?');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty accountList}">
                    <tr>
                        <td colspan="10">No accounts found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </body>
</html>
<style>
/* CSS riêng cho manageAccountStaff.jsp */

body {
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    margin: 0;
    padding: 0;
}

h2 {
    color: #2c3e50;
    margin-bottom: 20px;
    font-size: 24px;
}

a.button {
    background-color: #8AAAE5;
    color: white;
    padding: 10px 20px;
    text-decoration: none;
    border-radius: 5px;
    margin-bottom: 20px;
    display: inline-block;
}

a.button:hover {
    background-color: #8AAAE5;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
}

table, th, td {
    border: 1px solid #bdc3c7;
}

th, td {
    padding: 10px;
    text-align: left;
}

th {
    background-color: #8AAAE5;
    color: white;
}

tr:nth-child(even) {
    background-color: #f2f2f2;
}

tr:hover {
    background-color: #ddd;
}

td a {
    text-decoration: none;
    color: #2c3e50;
    padding: 5px;
    margin-right: 5px;
}

td a:hover {
    color: #34495e;
}

td a.edit {
    background-color: #27ae60;
    color: white;
    padding: 5px 10px;
    border-radius: 5px;
}

td a.delete {
    background-color: #e74c3c;
    color: white;
    padding: 5px 10px;
    border-radius: 5px;
}

td a.edit:hover {
    background-color: #2ecc71;
}

td a.delete:hover {
    background-color: #c0392b;
}

table td,
table th {
    text-align: left;
}

table th {
    padding-left: 10px;
    padding-right: 10px;
}

table td {
    padding-left: 10px;
    padding-right: 10px;
}

/* Thêm khoảng cách và margin cho phần nội dung */
.main-content {
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-top: 20px;
}

/* Đảm bảo tất cả các phần tử được căn chỉnh hợp lý */
.container {
    display: flex;
    flex-direction: column;
    margin: 20px;
}
</style>
