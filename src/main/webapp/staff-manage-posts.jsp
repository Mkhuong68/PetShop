<%-- 
    Document   : manage-posts
    Created on : Feb 28, 2025, 2:34:08 AM
    Author     : THANH THAO
--%>

<%@ page import="java.util.List"%>
<%@ page import="Model.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Staff Manage Posts</title>
</head>
<body>
    <h2>Staff Manage Posts</h2>

    <%-- Hiển thị thông báo nếu có --%>
    <div>
        <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
    </div>

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
            List<Post> posts = (List<Post>) request.getAttribute("posts");

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
            <td>
                <% 
                    // Dùng statusId để xác định trạng thái và hiển thị tên trạng thái từ bảng PostStatus
                    int statusId = post.getStatusId();
                    String statusName = "";
                    if (statusId == 1) {
                        statusName = "Accepted";
                    } else if (statusId == 2) {
                        statusName = "Rejected";
                    } else if (statusId == 0) {
                        statusName = "Pending";
                    } else {
                        statusName = "Cancelled"; // Thêm điều kiện nếu cần hiển thị trạng thái Cancelled
                    }
                %>
                <%= statusName %> <!-- Hiển thị tên trạng thái -->
            </td>
            <td>
                <% if (post.getStatusId() == 0) { %>  <!-- Chỉ hiển thị nút khi status là Pending -->
                    <a href="StaffManagePosts?action=accept&id=<%= post.getPostId() %>">Accept</a> |
                    <a href="StaffManagePosts?action=reject&id=<%= post.getPostId() %>">Reject</a> |
                <% } %>
                <a href="StaffManagePosts?action=delete&id=<%= post.getPostId() %>">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
