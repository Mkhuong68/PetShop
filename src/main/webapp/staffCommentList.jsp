<%-- 
    Document   : staffCommentList
    Created on : Mar 29, 2025, 4:48:44 PM
    Author     : THANH THAO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff - Comment Management</title>
        <link rel="stylesheet" type="text/css" href="assets/css/staffcomment.css">
    </head>
    <body>
        <h1>Staff - Manage Customer Comments</h1>
        <a href="manageStaff.jsp" class="back-btn">Back Manage Staff</a> <!-- Added Back button -->
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Post ID</th>
                <th>Author ID</th>
                <th>Content</th>
                <th>Created Date</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="comment" items="${comments}">
                <tr>
                    <td>${comment.commentId}</td>
                    <td>${comment.postId}</td>
                    <td>${comment.accountId}</td>
                    <td>${comment.content}</td>
                    <td>${comment.createdDate}</td>
                    <td>
                        <form action="StaffCommentController" method="post" style="display:inline;">
                            <input type="hidden" name="commentId" value="${comment.commentId}">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>