<%-- 
    Document   : addAccountStaff.jsp
    Created on : Mar 11, 2025, 11:51:16 PM
    Author     : Admin
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add Staff Account</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <h2>Add New Staff Account</h2>

        <!-- Hiển thị thông báo lỗi nếu có -->
    <c:if test="${not empty errorMessage}">
        <div style="color: red; font-weight: bold; text-align: center;">
            ${errorMessage}
        </div>
    </c:if>

    <form action="StaffManageAccountController" method="post">
        <input type="hidden" name="action" value="insert">
        Username: <input type="text" name="username" required><br>
        Password: <input type="password" name="password" required><br>
        Email: <input type="email" name="email" required><br>
        Phone: <input type="text" name="phone"><br>
        First Name: <input type="text" name="firstName" required><br>
        Last Name: <input type="text" name="lastName" required><br>
        Date of Birth: <input type="date" name="dob" required><br>
        Gender:
        <select name="gender">
            <option value="Male">Male</option>
            <option value="Female">Female</option>
        </select><br>
        <label for="role">Role:</label>
        <select name="role" id="role">
            <c:forEach var="role" items="${roleList}">
                <option value="${role.roleId}">
                    ${role.roleName}
                </option>
            </c:forEach>
        </select>
         Active: <input type="checkbox" name="active"><br>
        <div class="button-container">
            <input type="submit" value="Add Account">
            <a class="back-button" href="StaffManageAccountController?action=list">Back to List</a>
        </div>
    </form>

</body>
<c:if test="${not empty errorMessage}">
    <div class="error-message">
        ${errorMessage}
    </div>
</c:if>
</html>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 20px;
    }

    h2 {
        text-align: center;
        color: #333;
    }

    form {
        background-color: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        max-width: 500px;
        margin: 0 auto;
    }

    input[type="text"],
    input[type="password"],
    input[type="email"],
    input[type="date"],
    select {
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    input[type="checkbox"] {
        margin: 10px 0;
    }

    input[type="submit"] {
        background-color: #8AAAE5;
        color: white;
        padding: 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        width: 100%;
    }

    input[type="submit"]:hover {
        background-color: #4cae4c;
    }

    .error-message {
        color: red;
        font-weight: bold;
        text-align: center;
        margin-bottom: 20px;
    }

    a {
        display: block;
        text-align: center;
        margin-top: 20px;
        color: #007bff;
        text-decoration: none;
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
