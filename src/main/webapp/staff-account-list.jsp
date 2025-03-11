<%-- 
    Document   : staff-account-list
    Created on : Feb 28, 2025, 12:42:09 AM
    Author     : THANH THAO
--%>

<%@ page import="java.util.List"%>
<%@ page import="DAOs.AccountDAO, Model.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Manage Staff Accounts</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="assets/js/script.js"></script>
</head>
<body>

    <%-- ✅ Kiểm tra quyền truy cập (chỉ Admin hoặc Staff mới vào được) --%>
    <%
        Account loggedInUser = (Account) session.getAttribute("account");

        if (loggedInUser == null) { // Nếu chưa đăng nhập
            response.sendRedirect("login.jsp");
            return;
        }

        // ✅ Kiểm tra quyền hạn (Chỉ Admin hoặc Staff được xem danh sách Staff)
        if (loggedInUser.getRoleId() != 1 && loggedInUser.getRoleId() != 2) {
            response.sendRedirect("access-denied.jsp"); // Nếu không đủ quyền, chuyển hướng
            return;
        }
    %>

    <h2>Manage Staff Accounts</h2>

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
            List<Account> staffAccounts = dao.getAllStaffAccounts(); // ✅ Lấy danh sách Staff

            if (staffAccounts == null || staffAccounts.isEmpty()) {
        %>
            <tr>
                <td colspan="5" style="text-align: center; color: red;">No staff accounts found.</td>
            </tr>
        <%
            } else {
                for (Account acc : staffAccounts) {
        %>
        <tr>
            <td><%= acc.getAccountId() %></td>
            <td><%= acc.getUsername() %></td>
            <td><%= acc.getEmail() %></td>
            <td><%= acc.isActive() ? "Active" : "Banned" %></td>
            <td>
                <% if (acc.isActive()) { %>
                    <button onclick="banStaffAccount(<%= acc.getAccountId() %>)">Ban</button>
                <% } else { %>
                    <span style="color:red;">Banned</span>
                <% } %>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
