<%-- 
    Document   : verifyemail
    Created on : Feb 17, 2025, 7:32:54 AM
    Author     : tvhun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Verify Email</title>
        <link rel="stylesheet" href="assets/css/verifyEmail.css">
    </head>
    <body>
        <div class="container">
            <h2>Verify Email</h2>
            <form action="/verifyEmail" method="POST">
                <c:if test="${not empty msg}">
                    <div class="error">${msg}</div>
                </c:if>
                <input type="text" name="verificationCode" placeholder="Enter verification code" required>
                <button type="submit">Submit</button>
            </form>
            <form action="/resendCode" method="POST">
                <button type="submit">Resend</button>
            </form>
        </div>
    </body>
</html>
