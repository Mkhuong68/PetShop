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
        <title>Staff - Manage Customer Posts</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            /* Tùy chỉnh bảng */
            .table-custom {
                border-collapse: separate;
                border-spacing: 0;
                width: 100%;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                overflow: hidden;
            }
            .table-custom thead {
                background-color: #8AAAE5; /* Màu tiêu đề bảng */
                color: white;
            }

            .table-custom th, .table-custom td {
                padding: 12px 15px;
                text-align: left;
                border: none;
                border-bottom: 1px solid #dee2e6;
            }

            /* Giới hạn chiều rộng cột Content và xử lý nội dung dài */
            .table-custom td:nth-child(3) { /* Cột Content */
                max-width: 200px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }

            .table-custom tbody tr {
                background-color: #ffffff; /* Màu nền hàng */
                transition: background-color 0.3s ease;
            }

            .table-custom tbody tr:hover {
                background-color: #f1f3f5; /* Hiệu ứng hover */
            }

            /* Tùy chỉnh nút */
            .btn-custom {
                border-radius: 5px;
                padding: 6px 12px;
                font-size: 14px;
                margin-right: 5px; /* Khoảng cách giữa các nút */
                transition: all 0.3s ease;
            }

            /* Nút Quay lại */
            .btn-back {
                background-color: #8AAAE5;
                border: none;
                color: white;
            }

            .btn-back:hover {
                background-color: #6B8DD6; /* Màu đậm hơn khi hover */
                color: white;
            }

            /* Nút Chấp nhận */
            .btn-accept {
                background-color: #8AAAE5;
                border: none;
                color: white;
            }

            .btn-accept:hover {
                background-color: #6B8DD6;
                color: white;
            }

            /* Nút Từ chối */
            .btn-reject {
                background-color: #f1c40f; /* Màu vàng */
                border: none;
                color: white;
            }

            .btn-reject:hover {
                background-color: #d4ac0d;
                color: white;
            }

            /* Nút Xóa */
            .btn-delete {
                background-color: #e74c3c; /* Màu đỏ */
                border: none;
                color: white;
            }

            .btn-delete:hover {
                background-color: #c0392b;
                color: white;
            }

            /* Nút trong modal */
            .modal-footer .btn-secondary {
                background-color: #8AAAE5;
                border: none;
                color: white;
            }

            .modal-footer .btn-secondary:hover {
                background-color: #6B8DD6;
                color: white;
            }

            .modal-footer .btn-warning {
                background-color: #f1c40f;
                border: none;
                color: white;
            }

            .modal-footer .btn-warning:hover {
                background-color: #d4ac0d;
                color: white;
            }

            .modal-footer .btn-danger {
                background-color: #e74c3c;
                border: none;
                color: white;
            }

            .modal-footer .btn-danger:hover {
                background-color: #c0392b;
                color: white;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1 class="my-4">Staff - Manage Customer Posts</h1>
            <!-- Nút Quay lại -->
            <a href="manageStaff.jsp" class="btn btn-back mb-3">Back to Staff Management</a>
            <table class="table table-custom">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Content</th>
                        <th>Author ID</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
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
                                <!-- Hiển thị nút Chấp nhận nếu bài viết chưa được chấp nhận -->
                                <c:if test="${post.statusId != 1}">
                                    <form action="StaffPostController" method="post" style="display:inline;">
                                        <input type="hidden" name="postId" value="${post.postId}">
                                        <input type="hidden" name="action" value="accept">
                                        <button type="submit" class="btn btn-accept btn-custom">Accept</button>
                                    </form>
                                </c:if>
                                <!-- Hiển thị nút Từ chối nếu bài viết chưa bị từ chối -->
                                <c:if test="${post.statusId != 2}">
                                    <button type="button" class="btn btn-reject btn-custom" data-bs-toggle="modal" data-bs-target="#rejectModal${post.postId}">
                                        Reject
                                    </button>
                                    <!-- Modal Từ chối -->
                                    <div class="modal fade" id="rejectModal${post.postId}" tabindex="-1" aria-labelledby="rejectModalLabel${post.postId}" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="rejectModalLabel${post.postId}">Reject Post</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <p>Are you sure you want to reject this post (ID: ${post.postId})?</p>
                                                    <form action="StaffPostController" method="post">
                                                        <input type="hidden" name="postId" value="${post.postId}">
                                                        <input type="hidden" name="action" value="reject">
                                                        <div class="mb-3">
                                                            <label for="rejectReason${post.postId}" class="form-label">Reason for Rejection</label>
                                                            <textarea class="form-control" id="rejectReason${post.postId}" name="rejectReason" rows="3" required></textarea>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                            <button type="submit" class="btn btn-warning">Confirm Rejection</button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <!-- Nút Xóa với modal -->
                                <button type="button" class="btn btn-delete btn-custom" data-bs-toggle="modal" data-bs-target="#deleteModal${post.postId}">
                                    Delete
                                </button>
                                <!-- Modal Xóa -->
                                <div class="modal fade" id="deleteModal${post.postId}" tabindex="-1" aria-labelledby="deleteModalLabel${post.postId}" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="deleteModalLabel${post.postId}">Delete Post</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <p>Are you sure you want to delete this post (ID: ${post.postId})?</p>
                                                <form action="StaffPostController" method="post">
                                                    <input type="hidden" name="postId" value="${post.postId}">
                                                    <input type="hidden" name="action" value="delete">
                                                    <div class="mb-3">
                                                        <label for="deleteReason${post.postId}" class="form-label">Reason for Deletion</label>
                                                        <textarea class="form-control" id="deleteReason${post.postId}" name="deleteReason" rows="3" required></textarea>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                        <button type="submit" class="btn btn-danger">Confirm Deletion</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>