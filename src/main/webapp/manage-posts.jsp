<%-- 
    Document   : manage-posts
    Created on : Feb 28, 2025, 2:34:08 AM
    Author     : THANH THAO
--%>

<%@ page import="java.util.List"%>
<%@ page import="DAOs.PostDAO, Model.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Manage Posts</title>
</head>
<body>
    <h2>Manage Posts</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author ID</th>
            <th>Created Date</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <%
            PostDAO dao = new PostDAO();
            List<Post> posts = dao.getAllPosts();

            if (posts == null || posts.isEmpty()) {
        %>
            <tr>
                <td colspan="6" style="text-align: center; color: red;">No posts found.</td>
            </tr>
        <%
            } else {
                for (Post post : posts) {
        %>
        <tr>
            <td><%= post.getPostId() %></td>
            <td><%= post.getTitle() %></td>
            <td><%= post.getAccountId() %></td>
            <td><%= post.getCreatedDate() %></td>
            <td><%= post.getStatusId() == 1 ? "Accepted" : "Pending" %></td>
            <td>
                <% if (post.getStatusId() == 0) { %>
                    <a href="AcceptPost?id=<%= post.getPostId() %>">Accept</a>
                <% } %>
                <a href="DeletePost?id=<%= post.getPostId() %>">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
