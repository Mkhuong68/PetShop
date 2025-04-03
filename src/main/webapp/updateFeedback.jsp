<%-- 
    Document   : updateFeedback
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
        <title>Edit Feedback</title>
        <style>
            .rating-container {
                direction: rtl;
                text-align: left;
                margin-bottom: 20px;
            }
            
            .rating-container input {
                display: none;
            }
            
            .rating-container label {
                color: #ddd;
                font-size: 30px;
                padding: 0 5px;
                cursor: pointer;
                display: inline-block;
            }
            
            .rating-container label:hover,
            .rating-container label:hover ~ label,
            .rating-container input:checked ~ label {
                color: #ffc107;
            }
            
            .form-card {
                border-radius: 10px;
                box-shadow: 0 5px 15px rgba(0,0,0,0.05);
                transition: all 0.3s ease;
            }
            
            .form-card:hover {
                box-shadow: 0 10px 20px rgba(0,0,0,0.1);
            }
            
            .product-image {
                max-width: 100px;
                max-height: 100px;
                object-fit: cover;
                border-radius: 4px;
                border: 1px solid #eee;
            }
            
            /* Order details styles */
            .order-details {
                background-color: #f8f9fa;
                border-radius: 8px;
                padding: 15px;
                margin-bottom: 20px;
                border: 1px solid #eaeaea;
            }
            
            .order-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding-bottom: 10px;
                border-bottom: 1px solid #e0e0e0;
                margin-bottom: 15px;
            }
            
            .order-id {
                font-weight: bold;
                color: #2f3ba2;
            }
            
            .order-status {
                padding: 4px 8px;
                background-color: #28a745;
                color: white;
                border-radius: 4px;
                font-size: 12px;
            }
            
            .order-info-row {
                display: flex;
                margin-bottom: 8px;
            }
            
            .order-info-label {
                width: 150px;
                font-weight: 500;
                color: #666;
            }
            
            .order-product {
                display: flex;
                padding: 15px 0;
                border-bottom: 1px solid #e0e0e0;
            }
            
            .product-details {
                flex: 1;
                padding-left: 15px;
            }
            
            .product-price {
                display: flex;
                justify-content: space-between;
                margin-top: 8px;
                color: #666;
            }
            
            .product-category {
                color: #777;
                font-size: 12px;
            }
            
            .section-title {
                font-weight: 600;
                margin-bottom: 10px;
                padding-bottom: 5px;
                border-bottom: 2px solid #8AAAE5;
                color: #2f3ba2;
            }
            
            .btn-actions {
                margin-top: 20px;
            }
        </style>
    </head>
    <body class="bgc">
        <div id="main-content" class="wrap">
            <!-- Include header -->
            <jsp:include page="header.jsp" />

            <!-- Main Content -->
            <div class="container mt-4">
                <div class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="card form-card">
                            <div class="card-header bg-white">
                                <h4 class="mb-0">Edit Feedback</h4>
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty sessionScope.errorMessage}">
                                    <div class="alert alert-danger alert-dismissible fade show">
                                        ${sessionScope.errorMessage}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                    <c:remove var="errorMessage" scope="session"/>
                                </c:if>
                                
                                <!-- Order Details -->
                                <div class="order-details">
                                    <div class="order-header">
                                        <div class="order-id">
                                            <i class="fas fa-receipt"></i> Order #${feedback.orderId}
                                        </div>
                                        <div class="order-status">
                                            Delivered
                                        </div>
                                    </div>
                                    
                                    <div class="section-title">
                                        <i class="fas fa-box"></i> Product Review
                                    </div>
                                    
                                    <div class="order-product">
                                        <div>
                                            <img src="${pageContext.request.contextPath}${feedback.productImage}" 
                                                alt="${feedback.productName}" class="product-image">
                                        </div>
                                        <div class="product-details">
                                            <h5 class="product-name">${feedback.productName}</h5>
                                            <div class="product-category">Category: ${feedback.categoryName}</div>
                                            <div class="product-price">
                                                <div>Quantity: ${feedback.quantity}</div>
                                                <div>
                                                    <span class="text-danger fw-bold">
                                                        <fmt:formatNumber value="${feedback.finalPrice / feedback.quantity}" type="currency"/>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- Edit Feedback Form -->
                                <form action="${pageContext.request.contextPath}/feedback/edit" method="post">
                                    <input type="hidden" name="feedbackId" value="${feedback.feedbackId}">
                                    
                                    <div class="mb-3">
                                        <label class="form-label fw-bold">Rating</label>
                                        <div class="rating-container">
                                            <input type="radio" id="star5" name="rating" value="5" ${feedback.rating == 5 ? 'checked' : ''} required>
                                            <label for="star5">★</label>
                                            <input type="radio" id="star4" name="rating" value="4" ${feedback.rating == 4 ? 'checked' : ''} required>
                                            <label for="star4">★</label>
                                            <input type="radio" id="star3" name="rating" value="3" ${feedback.rating == 3 ? 'checked' : ''} required>
                                            <label for="star3">★</label>
                                            <input type="radio" id="star2" name="rating" value="2" ${feedback.rating == 2 ? 'checked' : ''} required>
                                            <label for="star2">★</label>
                                            <input type="radio" id="star1" name="rating" value="1" ${feedback.rating == 1 ? 'checked' : ''} required>
                                            <label for="star1">★</label>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <label for="content" class="form-label fw-bold">Your Comment</label>
                                        <textarea class="form-control" id="content" name="content" rows="5" required>${feedback.comment}</textarea>
                                    </div>

                                    <div class="d-flex justify-content-between btn-actions">
                                        <a href="${pageContext.request.contextPath}/feedback/list" class="btn btn-secondary">
                                            <i class="fas fa-arrow-left"></i> Back
                                        </a>
                                        <button type="submit" class="btn btn-primary">
                                            <i class="fas fa-save"></i> Save Changes
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Include footer -->
            <jsp:include page="footer.jsp" />
        </div>

        <!-- Include common scripts -->
        <jsp:include page="scripts.jsp" />
        
        <!-- Page-specific scripts -->
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                // Auto-dismiss alerts after 5 seconds
                setTimeout(function() {
                    const alerts = document.querySelectorAll('.alert');
                    alerts.forEach(function(alert) {
                        const closeBtn = new bootstrap.Alert(alert);
                        closeBtn.close();
                    });
                }, 5000);
            });
        </script>
    </body>
</html> 