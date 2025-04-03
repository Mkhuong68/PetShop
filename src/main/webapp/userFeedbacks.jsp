<%-- 
    Document   : userFeedbacks
    Created on : Mar 15, 2025, 10:30:00 AM
    Author     : Admin
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US" />

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="head.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/feedback.css">
    <title>My Feedback | PetShop</title>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container feedback-container">
        <h1 class="feedback-title">My Feedback</h1>
        
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="alert alert-success alert-dismissible fade show">
                ${sessionScope.successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show">
                ${sessionScope.errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>

        <c:if test="${empty feedbacks}">
            <div class="empty-state">
                <i class='bx bx-message-square'></i>
                <p>You haven't left any feedback yet.</p>
                <a href="${pageContext.request.contextPath}/CustomerOrderHistoryController" class="btn-primary">
                    <i class='bx bx-shopping-bag'></i> View orders to add feedback
                </a>
            </div>
        </c:if>

        <c:if test="${not empty feedbacks}">
            <div class="feedback-list">
                <c:forEach items="${feedbacks}" var="feedback" varStatus="status">
                    <div class="feedback-item">
                        <div class="feedback-header">
                            <div class="product-info">
                                <img src="${pageContext.request.contextPath}${feedback.productImage}" 
                                     alt="${feedback.productName}" class="product-thumbnail">
                                <div>
                                    <h4>${feedback.productName}</h4>
                                    <div class="order-meta">
                                        <span class="order-id">Order #${feedback.orderId}</span>
                                        <span class="feedback-date">
                                            <i class='bx bx-calendar'></i>
                                            <fmt:formatDate value="${feedback.createdDate}" pattern="MM/dd/yyyy" />
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="rating">
                                <c:forEach begin="1" end="5" var="i">
                                    <i class='bx ${i <= feedback.rating ? "bxs-star" : "bx-star"}'></i>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="feedback-body">
                            <p>${feedback.comment}</p>
                        </div>
                        <c:if test="${not empty feedback.staffReply}">
                            <div class="reply">
                                <div class="reply-header">
                                    <i class='bx bx-reply'></i> Response from store
                                </div>
                                <p>${feedback.staffReply}</p>
                            </div>
                        </c:if>
                        <div class="feedback-actions">
                            <a href="${pageContext.request.contextPath}/feedback/edit?id=${feedback.feedbackId}" class="btn-sm btn-edit">
                                <i class='bx bx-edit'></i> Edit
                            </a>
                            <a href="${pageContext.request.contextPath}/feedback/delete?id=${feedback.feedbackId}" class="btn-sm btn-delete"
                               onclick="return confirm('Are you sure you want to delete this feedback?')">
                                <i class='bx bx-trash'></i> Delete
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
    
    <jsp:include page="footer.jsp" />
    <jsp:include page="scripts.jsp" />
</body>
</html> 