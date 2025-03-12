<%-- 
    Document   : deleteAccountStaff
    Created on : Mar 4, 2025, 11:51:59 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Staff Account</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Delete Staff Account</h2>
    <p>Are you sure you want to delete this account?</p>
    <form action="StaffManageAccountController" method="post">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
        <input type="submit" value="Delete">
        <a href="StaffManageAccountController?action=list">Cancel</a>
    </form>
</body>
</html>

