<%-- 
    Document   : comment
    Created on : Feb 23, 2025, 3:44:03 AM
    Author     : NgocNNCE181950
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/css/comment.css">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="CustomerPostController">
            <button>Back to Posts</button>
        </a>
        <h1>Comment</h1>
        <p class="text-danger">${msg}</p>
        <form action="CustomerCommentController" method="post">
            <input type="hidden" value="${post.postId}" name="postId" /><br/>
            <input type="text" name="comment" placeholder="Comment" required/><br/>
            <input type="submit" value="Submit"><br/>
        </form>
        <c:forEach var="c" items="${comments}">
            <div>
                <p>${c.accountId}</p>
                <p>${c.content}</p>
            </div>
        </c:forEach>
    </body>
</html>
