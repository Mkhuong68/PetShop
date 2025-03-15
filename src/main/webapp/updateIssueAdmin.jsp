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
            <input type="hidden" name="id" value="<%= issue.getIssueId() %>">
            <label>Description: <%= issue.getIssueDescription() %></label><br>
            <label>Reported Date: <%= issue.getReportedDate() %></label><br>
            <label>Resolved:</label>
            <input type="checkbox" name="isResolved" <%= issue.isIsResolved() ? "checked" : "" %>><br>
            <input type="submit" value="Update">
        </form>
    <%
        }
    %>
    <a href="ManageIssueAdminController?action=list">Back to List</a>
</body>
</html>
<style>
    .form-container {
        width: 40%;
        margin: 50px auto;
        padding: 20px;
        background: #f7f7f7;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    h2 {
        text-align: center;
        color: #333;
        margin-bottom: 20px;
    }

    label {
        display: block;
        margin: 10px 0 5px;
        font-weight: bold;
        color: #2c3e50;
    }

    .issue-info {
        background: #ecf0f1;
        padding: 10px;
        border-radius: 5px;
        margin-bottom: 10px;
        border: 1px solid #bdc3c7;
    }

    input[type="checkbox"] {
        margin-left: 10px;
    }

    button, input[type="submit"] {
        background: #3498db;
        color: white;
        border: none;
        padding: 10px 15px;
        cursor: pointer;
        border-radius: 5px;
        width: 100%;
        margin-top: 15px;
    }

    button:hover, input[type="submit"]:hover {
        background: #2980b9;
    }

    .back-link {
        display: block;
        text-align: center;
        margin-top: 15px;
        color: #2c3e50;
        text-decoration: none;
        font-weight: bold;
    }

    .back-link:hover {
        text-decoration: underline;
    }

    .error-message {
        color: red;
        text-align: center;
        font-weight: bold;
    }
</style>
