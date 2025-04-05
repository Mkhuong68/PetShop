<%-- 
    Document   : updateAccountStaff
    Created on : Mar 11, 2025, 11:51:31 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Account" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <%
        Account account = (Account) request.getAttribute("account");  // Lấy đối tượng account từ request
%>
    <head>
        <title>Update Staff Account</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #e9ecef;
            margin: 0;
            padding: 20px;
        }

        h2 {
            color: #343a40;
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            margin: 0 auto;
        }

        input[type="text"],
        input[type="email"],
        input[type="date"],
        select {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ced4da;
            border-radius: 5px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="date"]:focus,
        select:focus {
            border-color: #80bdff;
            outline: none;
        }

        input[type="checkbox"] {
            margin: 10px 0;
        }


        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }

        .button-container {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
            align-items: center;
        }

        button, input[type="submit"], .back-button {
            background: #8AAAE5;
            color: white;
            border: none;
            padding: 20px 20px;
            cursor: pointer;
            border-radius: 5px;
            width: 40%; /* Adjusted width to fit two buttons */
            font-size: 16px;
            text-align: center;
            text-decoration: none; /* Remove underline from link */
            transition: background 0.3s;
            height: 20%;
        }

        button:hover, input[type="submit"]:hover, .back-button:hover {
            background: #7a9bc2;
        }

        .back-button {
            display: inline-block; /* Make it behave like a button */
            margin: 20px;
        }
        .button-container a {
            width: 35%;
            text-align: center;
            height: 18%;
            text-decoration: none;
        }
    </style>
    <body>
        <h2>Update Staff Account</h2>

        <form action="StaffManageAccountController" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${account.accountId}">

            Username: <input type="text" name="username" value="${account.username}" required><br>
            Email: <input type="email" name="email" value="${account.email}" required><br>
            Phone: <input type="text" name="phone" value="${account.phoneNumber}"><br>
            First Name: <input type="text" name="firstName" value="${account.firstName}" required><br>
            Last Name: <input type="text" name="lastName" value="${account.lastName}" required><br>
            Date of Birth: <input type="date" name="dob" value="${account.dateOfBirth}" required><br>

            Gender:
            <select name="gender">
                <option value="Male" ${account.gender == "Male" ? "selected" : ""}>Male</option>
                <option value="Female" ${account.gender == "Female" ? "selected" : ""}>Female</option>
            </select><br>

            <label for="role">Role:</label>
            <select name="role" id="role">
                <c:forEach var="role" items="${roleList}">
                    <option value="${role.roleId}" ${account.roleId == role.roleId ? "selected" : ""}>
                        ${role.roleName}
                    </option>
                </c:forEach>
            </select>
            Active: <input type="checkbox" name="active" <%= account.isActive() ? "checked" : ""%>><br>
            <div class="button-container">
                <input type="submit" value="Update Category">
                <a class="back-button" href="StaffManageAccountController?action=list">Back to List</a>
            </div>
        </form>
    </body>
</html>