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
        <title>Xác nhận Email</title>
        <link rel="stylesheet" href="assets/css/verifyEmail.css">
    </head>
    <body>
        <div class="container">
            <h2>Xác nhận Email</h2>
            <form action="verifyEmail" method="POST">
                <c:if test="${not empty msg}">
                    <div class="error">${msg}</div>
                </c:if>
                <input type="text" name="verificationCode" placeholder="Nhập mã xác thực" required>
                <button type="submit">Xác nhận</button>
            </form>
            <form action="resendCode" method="POST">
                <button type="submit">Gửi lại mã</button>
            </form>
        </div>
    </body>
</html>
