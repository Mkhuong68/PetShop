<%-- 
    Document   : accountManagement.jsp
    Created on : Feb 20, 2025, 9:00:39 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Account" %>
<%@ page import="DAOs.AccountDAO" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.annotation.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account Management</title>
    <link rel="stylesheet" href="styles.css"> <!-- Add CSS file if needed -->
</head>
<body>
    <h1>Account Management</h1>

    <form action="AccountController" method="post">
        <input type="hidden" name="action" value="create">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
        
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        
        <button type="submit">Add Account</button>
    </form>

    <h2>Account List</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                AccountDAO accountDAO = new AccountDAO();
                List<Account> accounts = accountDAO.getAllAccounts();
                for (Account account : accounts) {
            %>
                <tr>
                    <td><%= account.getAccountId() %></td>
                    <td><%= account.getUsername() %></td>
                    <td><%= account.getEmail() %></td>
                    <td>
                        <form action="AccountController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= account.getAccountId() %>">
                            <button type="submit">Delete</button>
                        </form>
                        <form action="AccountController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="<%= account.getAccountId() %>">
                            <input type="text" name="username" placeholder="Username" required>
                            <input type="email" name="email" placeholder="Email" required>
                            <input type="password" name="password" placeholder="Password" required>
                            <button type="submit">Update</button>
                        </form>
                    </td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
