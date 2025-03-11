<%@ page import="java.util.List"%>
<%@ page import="DAOs.AccountDAO, Model.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Manage Customer Accounts</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="assets/js/script.js"></script>
</head>
<body>

    <%-- ✅ Kiểm tra quyền truy cập (chỉ Staff mới vào được) --%>
    <%
        Account loggedInUser = (Account) session.getAttribute("account");

        if (loggedInUser == null) { // Nếu chưa đăng nhập
            response.sendRedirect("login.jsp");
            return;
        }

        // ✅ Kiểm tra quyền hạn (Chỉ Staff mới được xem danh sách Customer)
        if (loggedInUser.getRoleId() != 1) {
            response.sendRedirect("access-denied.jsp");
            return;
        }
    %>

    <h2>Manage Customer Accounts</h2>

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
            List<Account> customerAccounts = dao.getAllCustomerAccounts(); 

            if (customerAccounts == null || customerAccounts.isEmpty()) {
        %>
            <tr>
                <td colspan="5" style="text-align: center; color: red;">No customer accounts found.</td>
            </tr>
        <%
            } else {
                for (Account acc : customerAccounts) {
        %>
        <tr>
            <td><%= acc.getAccountId() %></td>
            <td><%= acc.getUsername() %></td>
            <td><%= acc.getEmail() %></td>
            <td><%= acc.isActive() ? "Active" : "Banned" %></td>
            <td>
                <% if (acc.isActive()) { %>
                    <button onclick="banCustomerAccount(<%= acc.getAccountId() %>)">Ban</button>
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
