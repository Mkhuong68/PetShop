<%-- 
    Document   : feedbackList
    Created on : Mar 12, 2025, 10:48:40 PM
    Author     : tvhun
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="manageStaff.jsp" />

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Manage Feedback</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <!-- DataTables CSS -->
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            :root {
                --primary-color: #8AAAE5;
                --hover-color: #7A9AD5;
            }
            .main-content {
                margin-left: 250px;
                padding: 20px;
                background-color: #f8f9fa;
                min-height: 8vh;
            }
            .page-header {
                background-color: white;
                padding: 15px 20px;
                border-radius: 10px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                margin-bottom: 20px;
            }
            .page-title {
                color: var(--primary-color);
                font-weight: 600;
                margin: 0;
                font-size: 1.5rem;
            }
            .btn-primary {
                background-color: var(--primary-color);
                border-color: var(--primary-color);
            }
            .btn-primary:hover {
                background-color: var(--hover-color);
                border-color: var(--hover-color);
            }
            .table-container {
                background-color: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .table thead th {
                background-color: var(--primary-color);
                color: white;
                border: none;
                padding: 12px;
                font-weight: 500;
            }
            .table tbody tr:hover {
                background-color: #f8f9fa;
            }
            .badge {
                padding: 8px 12px;
                font-weight: 500;
            }
            .btn-group .btn {
                padding: 6px 12px;
                margin: 0 2px;
                border-radius: 5px;
                transition: all 0.3s ease;
            }
            .btn-group .btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .alert {
                border-radius: 10px;
                margin-bottom: 20px;
                border: none;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .alert-success {
                background-color: #d4edda;
                border-color: #c3e6cb;
                color: #155724;
            }
            .alert-danger {
                background-color: #f8d7da;
                border-color: #f5c6cb;
                color: #721c24;
            }
            .btn-close {
                opacity: 0.5;
                transition: opacity 0.3s ease;
            }
            .btn-close:hover {
                opacity: 1;
            }
            .table-responsive {
                border-radius: 10px;
                overflow: hidden;
            }
            .empty-state {
                text-align: center;
                padding: 40px;
                color: #6c757d;
            }
            .empty-state i {
                font-size: 48px;
                margin-bottom: 20px;
                color: var(--primary-color);
            }
            .rating-stars {
                color: #ffc107;
            }
            @media (max-width: 768px) {
                .main-content {
                    margin-left: 0;
                    padding: 15px;
                }
                .page-header {
                    flex-direction: column;
                    gap: 10px;
                }
                .page-header .d-flex {
                    flex-direction: column;
                }
                .btn-primary {
                    width: 100%;
                }
            }
        </style>
    </head>
    <body>
        <div class="main-content">
            <div class="page-header">
                <div class="d-flex justify-content-between align-items-center">
                    <h2 class="page-title">Manage Customer Feedback</h2>
                </div>
            </div>

            <!-- Display Messages -->
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle me-2"></i>${sessionScope.successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("successMessage"); %>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-circle me-2"></i>${sessionScope.errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("errorMessage");%>
            </c:if>

            <div class="table-container">
                <div class="table-responsive">
                    <c:choose>
                        <c:when test="${empty feedbackList}">
                            <div class="empty-state">
                                <i class="bi bi-chat-square-text"></i>
                                <h4>No Feedback Found</h4>
                                <p>There are no customer feedbacks at the moment.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table id="feedbackTable" class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Product</th>
                                        <th>Customer</th>
                                        <th>Rating</th>
                                        <th>Comment</th>
                                        <th>Reply Status</th>
                                        <th>Created Date</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${feedbackList}" var="feedback">
                                        <tr>
                                            <td>${feedback.feedbackId}</td>
                                            <td>${feedback.productName}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty feedback.customerFirstName || not empty feedback.customerLastName}">
                                                        ${feedback.customerFirstName} ${feedback.customerLastName}
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${feedback.username}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="rating-stars">
                                                    <c:forEach begin="1" end="5" var="i">
                                                        <i class="bi ${i <= feedback.rating ? 'bi-star-fill' : 'bi-star'}"></i>
                                                    </c:forEach>
                                                </div>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${feedback.comment.length() > 50}">
                                                        ${feedback.comment.substring(0, 50)}...
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${feedback.comment}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <span class="badge ${empty feedback.staffReply ? 'bg-danger' : 'bg-success'}">
                                                    ${empty feedback.staffReply ? 'Not Replied' : 'Replied'}
                                                </span>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${feedback.createdDate}" pattern="MM/dd/yyyy HH:mm"/>
                                            </td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <a href="${pageContext.request.contextPath}/ManageFeedback/details/${feedback.feedbackId}" 
                                                       class="btn btn-primary btn-sm" title="View Details">
                                                        <i class="bi bi-eye"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- jQuery & Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- DataTables JS -->
        <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#feedbackTable').DataTable({
                    "order": [[5, "desc"]], // Sort by date descending
                    "columnDefs": [
                        {"orderable": false, "targets": 6}  // Disable sorting for Actions column
                    ]
                });
            });
        </script>
    </body>
</html>