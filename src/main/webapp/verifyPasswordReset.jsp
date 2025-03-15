<%-- 
    Document   : verifyPasswordReset
    Created on : Feb 17, 2025, 9:00:00 AM
    Author     : Diem Quynh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Verify Email for Password Reset</title>
        <link rel="stylesheet" href="assets/css/verifyEmail.css">
    </head>
    <body>
        <div class="container">
            <h2>Verify Email for Password Reset</h2>
            <p>We've sent a verification code to your email. Please enter it below.</p>
            <form action="/verifyPasswordReset" method="POST">
                <c:if test="${not empty msg}">
                    <div class="error">${msg}</div>
                </c:if>
                <input type="text" name="verificationCode" placeholder="Enter verification code" required>
                <button type="submit">Submit</button>
            </form>
            <form action="/verifyPasswordReset" method="POST">
                <input type="hidden" name="action" value="resend">
                <button type="submit">Resend</button>
            </form>
        </div>
    </body>
</html>