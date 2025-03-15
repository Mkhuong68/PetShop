<%-- 
    Document   : resetPassword
    Created on : Feb 17, 2025, 8:15:00 AM
    Author     : Diem Quynh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reset Password</title>
        <link rel="stylesheet" href="assets/css/siginandlogin.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    </head>
    <body>
        <div class="container">
            <div class="form-box login">
                <form action="resetPassword" method="POST" onsubmit="return validatePassword()">
                    <h1>Reset Password</h1>
                    <p>Enter your new password below.</p>
                    
                    <% if(request.getAttribute("message") != null) { %>
                        <div class="message <%= request.getAttribute("messageType") %>">
                            <%= request.getAttribute("message") %>
                        </div>
                    <% } %>
                    
                    <div class="input-box">
                        <input type="password" id="password" name="password" placeholder="New Password" required>
                        <i class='bx bxs-lock-alt'></i>
                    </div>
                    <div class="input-box">
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm New Password" required>
                        <i class='bx bxs-lock-alt'></i>
                    </div>
                    <div id="passwordError" class="error"></div>
                    <button type="submit" class="btn">Reset Password</button>
                </form>
            </div>
        </div>
        
        <script>
    function validatePassword() {
        var password = document.getElementById('password').value;
        var confirmPassword = document.getElementById('confirmPassword').value;
        var errorElement = document.getElementById('passwordError');
        
        // Kiểm tra độ mạnh của mật khẩu
        var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).{8,}$/;
        if (!regex.test(password)) {
            errorElement.textContent = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm 1 chữ hoa, 1 chữ thường và 1 ký tự đặc biệt.";
            return false;
        }
        
        // Kiểm tra mật khẩu xác nhận có khớp không
        if (password !== confirmPassword) {
            errorElement.textContent = "Mật khẩu không khớp";
            return false;
        }
        
        return true;
    }
</script>
    </body>
</html>