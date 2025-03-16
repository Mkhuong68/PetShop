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
                    <input type="text" name="title" placeholder="Title" required/><br/>
                    <input type="text" name="content" placeholder="Content" required/><br/>
                    <input type="submit" name="upload" value="Create Post"/>
                </form>
                <p class="message">${msg}</p>
            </div>

            <div class="post-list">
                <c:forEach var="post" items="${list}">
                    <div class="post-item">
                        <h2>${post.title}</h2>
                        <p>${post.content}</p>
                        <a class="btn-update" href="/CustomerUpdatePostController?postId=${post.postId}">Update Post</a>
                        <a class="btn-comment" href="?postId=${post.postId}&show=true">Comment</a>
                    </div>

                    <c:if test="${param.show == 'true' && param.postId == post.postId}">
                        <div class="comment-section">
                            <h3>Comments</h3    >
                            <c:forEach var="c" items="${comments}">
                                <c:if test="${c.postId == post.postId}">
                                    <div class="comment-item">
                                        <p>${c.content}</p>
                                    </div>
                                </c:if>
                            </c:forEach>

                            <form action="CustomerCommentController" method="post">
                                <input type="hidden" name="postId" value="${post.postId}" />
                                <input type="text" name="comment" placeholder="Write a comment..." required/>
                                <input type="submit" value="Submit"/>
                            </form>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
