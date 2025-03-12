<%-- 
    Document   : manageIssueAdmin
    Created on : Feb 25, 2025, 10:16:09 AM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Reported Issues</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Reported Issues</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Issue ID</th>
                <th>Reporter ID</th>
                <th>Description</th>
                <th>Reported Date</th>
                <th>Resolved</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="issue" items="${issueList}">
                <tr>
                    <td>${issue.issueId}</td>
                    <td>${issue.accountId}</td>
                    <td>${issue.issueDescription}</td>
                    <td>${issue.reportedDate}</td>
                    <td>${issue.isIsResolved() ? "Yes" : "No"}</td>
                    <td>
                        <a href="ManageIssueAdminController?action=edit&id=${issue.issueId}">Edit</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty issueList}">
                <tr>
                    <td colspan="6">No reported issues found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>

<style>
    /* Reset mặc định */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: Arial, sans-serif;
    }

    /* Container chính */
    .container {
        display: flex;
        height: 100vh;
    }

    /* Sidebar */
    .sidebar {
        width: 250px;
        background-color: #8AAAE5;
        color: white;
        padding: 20px;
    }

    .sidebar h2 {
        text-align: center;
        margin-bottom: 20px;
    }

    .sidebar ul {
        list-style: none;
    }

    .sidebar ul li {
        padding: 10px;
        border-radius: 5px;
        transition: background 0.3s;
    }

    .sidebar ul li a {
        text-decoration: none;
        color: white;
        display: block;
    }

    .sidebar ul li:hover {
        background: #34495e;
    }

    /* Phần nội dung chính */
    .main-content {
        flex: 1;
        padding: 20px;
        background: #ecf0f1;
    }

    .main-content h2 {
        color: #2c3e50;
        margin-bottom: 15px;
        text-align: center;
    }

    /* Bảng hiển thị danh sách vấn đề */
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        background: white;
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    th, td {
        border: 1px solid #bdc3c7;
        padding: 12px;
        text-align: left;
    }

    th {
        background: #8AAAE5;
        color: white;
        text-align: center;
    }

    td {
        background: #ffffff;
    }

    /* Các nút thao tác */
    .action-btn {
        display: inline-block;
        padding: 5px 10px;
        font-size: 14px;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        transition: 0.3s;
    }

    .edit-btn {
        background: #3498db;
    }

    .edit-btn:hover {
        background: #2980b9;
    }

    /* Trường hợp không có dữ liệu */
    .no-data {
        text-align: center;
        font-style: italic;
        color: #7f8c8d;
    }
</style>
