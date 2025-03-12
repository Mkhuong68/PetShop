<%-- 
    Document   : updateAccountStaff
    Created on : Mar 4, 2025, 11:51:31 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Account" %>
<html>
    <head>
        <title>Update Staff Account</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <h2>Update Staff Account</h2>
        <%
            Account account = (Account) request.getAttribute("account");  // Lấy đối tượng account từ request
            if (account == null) {
                out.println("<p>Error: Account not found!</p>");
            } else {
        %>
        <form action="StaffManageAccountController" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= account.getAccountId()%>">
            Username: <input type="text" name="username" value="<%= account.getUsername()%>" required><br>
            Email: <input type="email" name="email" value="<%= account.getEmail()%>" required><br>
            Phone: <input type="text" name="phone" value="<%= account.getPhoneNumber()%>"><br>
            Active: <input type="checkbox" name="active" <%= account.isActive() ? "checked" : ""%>><br>
            First Name: <input type="text" name="firstName" value="<%= account.getFirstName()%>" required><br>
            Last Name: <input type="text" name="lastName" value="<%= account.getLastName()%>" required><br>
            Date of Birth: <input type="date" name="dob" value="<%= account.getDateOfBirth()%>" required><br>
            Gender:
            <select name="gender">
                <option value="Male" <%= account.getGender().equals("Male") ? "selected" : ""%>>Male</option>
                <option value="Female" <%= account.getGender().equals("Female") ? "selected" : ""%>>Female</option>
            </select><br>
            Banned Reason: <input type="text" name="bannedReason" value="<%= account.getBannedReason()%>"><br>
            <input type="submit" value="Update Account">
        </form>
        <a href="StaffManageAccountController?action=list">Back to List</a>
        <%
            }
        %>
    </body>
</html>
