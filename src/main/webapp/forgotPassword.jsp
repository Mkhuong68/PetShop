<%-- 
    Document   : forgotPassword
    Created on : Feb 17, 2025, 8:00:00 AM
    Author     : Diem Quynh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Forgot Password</title>
        <link rel="stylesheet" href="assets/css/siginandlogin.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    </head>
    <body>
        <div class="container">
            <div class="form-box login">
                <form action="forgotPassword" method="POST">
                    <h1>Forgot Password</h1>
                    <p>Please enter your email address. We will send you a verification code to reset your password.</p>
                    
                    <% if(request.getAttribute("message") != null) { %>
                        <div class="message <%= request.getAttribute("messageType") %>">
                            <%= request.getAttribute("message") %>
                        </div>
                    <% } %>
                    
                    <div class="input-box">
                        <input type="email" name="email" placeholder="Email" required>
                        <i class='bx bxs-envelope'></i>
                    </div>
                    <button type="submit" class="btn">Send Verification Code</button>
                    <div class="back-to-login">
                        <a href="login">Back to Login</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>