<%-- 
    Document   : updateIssueAdmin
    Created on : Feb 28, 2025, 10:18:22 AM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.ReportedIssue" %>
<html>
    <head>
        <title>Update Issue</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <h2>Update Issue Status</h2>
        <%
            ReportedIssue issue = (ReportedIssue) request.getAttribute("issue");
            if (issue == null) {
        %>
        <p>Error: Issue not found!</p>
        <%
        } else {
        %>
        <form action="ManageIssueAdminController" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= issue.getIssueId()%>">
            <label>Description: <%= issue.getIssueDescription()%></label><br>
            <label>Reported Date: <%= issue.getReportedDate()%></label><br>
            <label>Resolved:</label>
            <input type="checkbox" name="isResolved" <%= issue.isIsResolved() ? "checked" : ""%>><br>
            <div>
                <input type="submit" value="Update">
                <button class="button" onclick="location.href = 'ManageIssueAdminController?action=list'">Back to List</button>
            </div>
        </form>
        <%
            }
        %>
    </body>
</html>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 20px;
    }

    h2 {
        color: #333;
        text-align: center;
    }

    form {
        background: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        max-width: 400px;
        margin: auto;
    }

    label {
        display: block;
        margin-bottom: 10px;
        font-weight: bold;
    }

    input[type="checkbox"] {
        margin-right: 10px;
    }

    input[type="submit"] {
        background-color: #8AAAE5;
        color: white;
        border: none;
        padding: 10px 15px;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
    }

    input[type="submit"]:hover {
        background-color: #0056b3;
    }

    a {
        display: inline-block;
        margin-top: 20px;
        text-decoration: none;
        color: #007bff;
    }

    a:hover {
        text-decoration: underline;
    }
    .button {
        background-color: #8AAAE5; /* Màu nền */
        color: white; /* Màu chữ */
        border: none; /* Không có viền */
        padding: 10px 20px; /* Khoảng cách bên trong */
        border-radius: 5px; /* Bo góc */
        cursor: pointer; /* Con trỏ khi di chuột */
        font-size: 16px; /* Kích thước chữ */
        transition: background-color 0.3s ease; /* Hiệu ứng chuyển màu */
        margin-top: 10px;
        height: 50px;
        width: 100px;
    }

    .button:hover {
        background-color: #0056b3; /* Màu nền khi di chuột */
    }
    .button, input[type="submit"] {
        background-color: #8AAAE5; /* Màu nền */
        color: white; /* Màu chữ */
        border: none; /* Không có viền */
        padding: 10px 20px; /* Khoảng cách bên trong */
        border-radius: 5px; /* Bo góc */
        cursor: pointer; /* Con trỏ khi di chuột */
        font-size: 16px; /* Kích thước chữ */
        transition: background-color 0.3s ease; /* Hiệu ứng chuyển màu */
        margin-top: 10px; /* Khoảng cách giữa hai nút */
    }

    .button:hover, input[type="submit"]:hover {
        background-color: #0056b3; /* Màu nền khi di chuột */
    }

    div {
        display: flex;
        justify-content: space-between;
        gap: 10px;
    }
</style>
