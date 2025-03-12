<%-- 
    Document   : feedbackDetail
    Created on : Mar 12, 2025, 10:48:53 PM
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
        <title>Feedback Details</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
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
                display: flex;
                justify-content: space-between;
                align-items: center;
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
            .feedback-container {
                background-color: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                margin-bottom: 20px;
            }
            .reply-container {
                background-color: white;
                padding: 20px;
                border-radius: 10px;
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
            .badge {
                padding: 8px 12px;
                font-weight: 500;
            }
            .rating-stars {
                color: #ffc107;
                font-size: 1.5rem;
                margin-bottom: 10px;
            }
            .feedback-info {
                margin-bottom: 15px;
            }
            .feedback-info strong {
                color: #555;
            }
            .feedback-text {
                background-color: #f8f9fa;
                padding: 15px;
                border-radius: 8px;
                margin-bottom: 20px;
            }
            .reply-text {
                background-color: #e9f5ff;
                padding: 15px;
                border-radius: 8px;
                margin-bottom: 20px;
                border-left: 4px solid var(--primary-color);
            }
            textarea {
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 10px;
                font-size: 1rem;
                width: 100%;
                resize: vertical;
            }
            textarea:focus {
                outline: none;
                border-color: var(--primary-color);
                box-shadow: 0 0 0 3px rgba(138, 170, 229, 0.25);
            }
            .btn-back {
                transition: all 0.3s ease;
            }
            .btn-back:hover {
                transform: translateX(-3px);
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
            }
        </style>
    </head>
    <body>
        <div class="main-content">
            <div class="page-header">
                <h2 class="page-title">Feedback Details</h2>
                <a href="${pageContext.request.contextPath}/ManageFeedback/list" class="btn btn-outline-secondary btn-back">
                    <i class="bi bi-arrow-left"></i> Back to List
                </a>
            </div>

            <!-- Display Messages -->
            <c:if test="${not empty param.message}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle me-2"></i>${param.message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${not empty param.error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-circle me-2"></i>${param.error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
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

            <div class="feedback-container">
                <h3>Customer Feedback</h3>
                <hr>
                <div class="row">
                    <div class="col-md-6">
                        <div class="feedback-info">
                            <strong>Feedback ID:</strong> #${feedback.feedbackId}
                        </div>
                        <div class="feedback-info">
                            <strong>Order Detail ID:</strong> #${feedback.orderDetailId}
                        </div>
                        <div class="feedback-info">
                            <strong>Product:</strong> ${feedback.productName}
                        </div>
                        <div class="feedback-info">
                            <strong>Customer:</strong> 
                            <c:choose>
                                <c:when test="${not empty feedback.customerFirstName || not empty feedback.customerLastName}">
                                    ${feedback.customerFirstName} ${feedback.customerLastName}
                                </c:when>
                                <c:otherwise>
                                    ${feedback.username}
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${not empty feedback.email}">
                                <small class="text-muted">(${feedback.email})</small>
                            </c:if>
                        </div>
                        <div class="feedback-info">
                            <strong>Created Date:</strong> <fmt:formatDate value="${feedback.createdDate}" pattern="dd/MM/yyyy HH:mm"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <c:if test="${not empty feedback.productImage}">
                            <div class="feedback-info text-center">
                                <img src="${feedback.productImage}" alt="${feedback.productName}" class="img-thumbnail" style="max-height: 120px; max-width: 120px;">
                            </div>
                        </c:if>
                        <div class="feedback-info">
                            <strong>Rating:</strong>
                            <div class="rating-stars">
                                <c:forEach begin="1" end="5" var="i">
                                    <i class="bi ${i <= feedback.rating ? 'bi-star-fill' : 'bi-star'}"></i>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="feedback-info">
                            <strong>Status:</strong>
                            <span class="badge ${empty feedback.staffReply ? 'bg-danger' : 'bg-success'}">
                                ${empty feedback.staffReply ? 'Not Replied' : 'Replied'}
                            </span>
                        </div>
                    </div>
                </div>



                <div class="feedback-info">
                    <strong>Customer Comment:</strong>
                    <div class="feedback-text">
                        ${feedback.comment}
                    </div>
                </div>

                <c:if test="${not empty feedback.staffReply}">
                    <div class="feedback-info">
                        <strong>Staff Reply:</strong>
                        <div class="reply-text">
                            ${feedback.staffReply}
                        </div>
                    </div>
                </c:if>
            </div>

            <c:if test="${empty feedback.staffReply}">
                <div class="reply-container">
                    <h3>Reply to Customer</h3>
                    <hr>
                    <form action="${pageContext.request.contextPath}/ManageFeedback/reply" method="post">
                        <input type="hidden" name="feedbackId" value="${feedback.feedbackId}">
                        <div class="mb-3">
                            <label for="reply" class="form-label">Your Reply:</label>
                            <textarea name="reply" id="reply" rows="5" class="form-control" placeholder="Enter your response to the customer..." required></textarea>
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-send"></i> Send Reply
                            </button>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>