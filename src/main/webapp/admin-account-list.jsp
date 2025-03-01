<%-- 
    Document   : staff-account-list
    Created on : Feb 23, 2025, 2:43:16 PM
    Author     : THANH THAO
--%>

<%@ page import="DAOs.AccountDAO, Model.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Account List</title>
        <link rel="stylesheet" href="assets/css/style.css">
    <script src="assets/js/script.js"></script>
</head>
<body>
    <h2>Admin Account List</h2>

    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        <%
            AccountDAO dao = new AccountDAO();
            for (Account acc : dao.getAllAccounts()) {
        %>
        <tr>
            <td><%= acc.getAccountId() %></td>
            <td><%= acc.getUsername() %></td>
            <td><%= acc.getEmail() %></td>
            <td><%= acc.isActive() ? "Active" : "Banned" %></td>
            <td>
                <% if (acc.isActive()) { %>
                    <button onclick="banAccount(<%= acc.getAccountId() %>)">Ban</button>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>
