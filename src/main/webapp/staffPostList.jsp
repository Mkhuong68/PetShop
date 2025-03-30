<%-- 
    Document   : staffPostList
    Created on : Mar 29, 2025, 4:45:49 PM
    Author     : THANH THAO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff - Post Management</title>
        <link rel="stylesheet" type="text/css" href="assets/css/staff-post.css">
    </head>
    <body>
        <h1>Staff - Manage Customer Posts</h1>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Content</th>
                <th>Author ID</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="post" items="${posts}">
                <tr>
                    <td>${post.postId}</td>
                    <td>${post.title}</td>
                    <td>${post.content}</td>
                    <td>${post.accountId}</td>
                    <td>
                        <c:choose>
                            <c:when test="${post.statusId == 1}">Accepted</c:when>
                            <c:when test="${post.statusId == 2}">Rejected</c:when>
                            <c:when test="${post.statusId == 3}">Pending</c:when>
                            <c:otherwise>Cancelled</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form action="StaffPostController" method="post" style="display:inline;">
                            <input type="hidden" name="postId" value="${post.postId}">
                            <input type="hidden" name="action" value="accept">
                            <button type="submit">Accept</button>
                        </form>
                        <form action="StaffPostController" method="post" style="display:inline;">
                            <input type="hidden" name="postId" value="${post.postId}">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>