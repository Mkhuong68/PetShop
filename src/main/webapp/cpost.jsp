<%-- 
    Document   : cpost
    Created on : Feb 20, 2025, 12:10:44 PM
    Author     : NgocNNCE181950
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/css/cpost.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <div class="post-form">
                <form action="CustomerPostController" method="post">
                    <input type="text" name="title" placeholder="Title"/><br/>
                    <input type="text" name="content" placeholder="Content"/><br/>
                    <input type="submit" name="upload" value="Create Post"/>
                </form>
                <p class="message">
                    ${msg}
                </p>
            </div>
            <div class="post-list">
                <c:forEach var="post" items="${list}">
                    <div class="post-item">
                        <p>${post.accountId}</p>
                        <h2>${post.title}</h2>
                        <p>${post.content}</p>
                        <a class="btn-update" href="/CustomerUpdatePostController?postId=${post.postId}">Update Post</a>
                        <a class="btn-comment" href="/CustomerCommentController?postId=${post.postId}">Comment</a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
