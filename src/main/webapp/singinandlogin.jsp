<%-- 
    Document   : singinandlogin
    Created on : Feb 17, 2025, 7:05:01 AM
    Author     : tvhun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login/Signup Form</title>
        <link rel="stylesheet" href="assets/css/siginandlogin.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    </head>
    <body>

        <div class="container">
            <div class="form-box login">
                <form action="login" method="POST">
                    <h1>Login</h1>
                    <div class="input-box">
                        <input type="text" name="username" placeholder="Username" required>
                        <i class='bx bxs-user'></i>
                    </div>
                    <div class="input-box">
                        <input type="password" name="password" placeholder="Password" required>
                        <i class='bx bxs-lock-alt' ></i>
                    </div>
                    <div class="forgot-link">
                        <label>
                            <input type="checkbox" name="remember" value="true"> Remember Me
                        </label>
                        <a href="#">Forgot Password ?</a>
                    </div>
                    <button type="submit" class="btn">Login</button>
                    <p>or login with </p>
                    <div class="social-icons">
                        <a href="GoogleLogin"><i class='bx bxl-google' ></i></a>
                    </div>
                </form>
            </div>

            <div class="form-box register">
                <form action="Register" method="POST" name="registerForm" onsubmit="return validateForm()">
                    <h1>Registration</h1>
                    <div class="input-box">
                        <input type="text" name="username" placeholder="Username" required>
                        <i class='bx bxs-user'></i>
                    </div>
                    <div class="input-box">
                        <input type="email" name="email" placeholder="Email" required>
                        <i class='bx bxs-envelope'></i>
                    </div>
                    <div class="input-box">
                        <input type="password" name="password_hash" placeholder="Password" required>
                        <i class='bx bxs-lock-alt'></i>
                    </div>
                    <button type="submit" class="btn">Register</button>
                </form>
            </div>
            <div class="toggle-box">
                <div class="toggle-panel toggle-left">
                    <h1>Hello, Welcome!</h1>
                    <p>Don't have an account?</p>
                    <button class="btn register-btn">Register</button>
                </div>

                <div class="toggle-panel toggle-right">
                    <h1>Welcome Back!</h1>
                    <p>Already have an account?</p>
                    <button class="btn login-btn">Login</button>
                </div>
            </div>
        </div>

        <script src="assets/js/signinandlogin.js"></script>
    </body>
</html>