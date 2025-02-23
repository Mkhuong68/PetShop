<%-- 
    Document   : managedelivery
    Created on : Feb 22, 2025, 5:23:46 PM
    Author     : tvhun
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Manage Delivery</title>
        <!-- File CSS chung -->
        <link rel="stylesheet" href="./assets/css/managedelivery.css" />
    </head>
    <body>
        <!-- SIDEBAR -->
        <div class="sidebar">
            <div>
                <div class="logo">Manage Delivery</div>
                <ul class="menu">
                    <li><a href="deliveryList">Manage Delivery Orders</a></li>
                    <li><a href="#">Manage Delivery Routes</a></li>
                    <li><a href="#">Manage Delivery Notifications</a></li>
                </ul>
            </div>
            <div class="sidebar-footer">
                <div class="dark-mode-toggle">
                    <label for="darkModeSwitch">Dark Mode</label>
                    <input type="checkbox" id="darkModeSwitch"/>
                </div>
                <a href="#" class="logout-btn">Logout</a>
            </div>
        </div>

        <!-- MAIN CONTENT -->
        <div class="main-content">
            <header>
                <div class="user-info">
                    Hello, User
                    <div class="user-avatar"></div>
                </div>
            </header>
