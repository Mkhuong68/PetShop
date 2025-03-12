<%-- 
    Document   : createOption
    Created on : Mar 12, 2025, 2:43:58 PM
    Author     : tvhun
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="manageStaff.jsp" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add New Option</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            .main-content {
                margin-left: 250px;
                padding: 20px;
                background-color: #f8f9fa;
                min-height: 8vh;
            }

            .form-container {
                background: white;
                border-radius: 10px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                padding: 25px;
                max-width: 800px;
                margin: 0 auto;
            }

            .content-header {
                margin-bottom: 25px;
            }

            .content-title {
                color: #333;
                font-size: 24px;
                margin: 0;
                font-weight: 600;
            }

            .form-label {
                color: #555;
                font-weight: 500;
                margin-bottom: 8px;
            }

            .form-control, .form-select {
                border: 1px solid #ddd;
                border-radius: 6px;
                padding: 10px 15px;
                transition: all 0.3s ease;
            }

            .form-control:focus, .form-select:focus {
                border-color: #8AAAE5;
                box-shadow: 0 0 0 0.2rem rgba(138, 170, 229, 0.25);
            }

            .input-group-text {
                background-color: #f8f9fa;
                border: 1px solid #ddd;
                color: #555;
            }

            .btn {
                padding: 10px 20px;
                font-weight: 500;
                border-radius: 6px;
                transition: all 0.3s ease;
            }

            .btn-primary {
                background-color: #8AAAE5;
                border-color: #8AAAE5;
            }

            .btn-primary:hover {
                background-color: #7A9AD5;
                border-color: #7A9AD5;
            }

            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
            }

            .btn-secondary:hover {
                background-color: #5a6268;
                border-color: #545b62;
            }

            .alert {
                border-radius: 6px;
                padding: 15px;
                margin-bottom: 20px;
                border: none;
            }

            .alert-danger {
                background-color: #f8d7da;
                color: #721c24;
            }

            @media (max-width: 768px) {
                .main-content {
                    margin-left: 0;
                    padding: 15px;
                }

                .form-container {
                    padding: 20px;
                }
            }
        </style>
    </head>
    <body>
        <div class="main-content">
            <div class="form-container">
                <div class="content-header">
                    <h1 class="content-title">Add New Option</h1>
                </div>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert">
                        <i class="bi bi-exclamation-circle me-2"></i>${errorMessage}
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/ManageOption/create" method="post">
                    <div class="mb-3">
                        <label for="optionName" class="form-label">Option Name:</label>
                        <input type="text" class="form-control" id="optionName" name="optionName" 
                               required maxlength="50" placeholder="Enter option name">
                    </div>

                    <div class="mb-3">
                        <label for="optionDescription" class="form-label">Description:</label>
                        <textarea class="form-control" id="optionDescription" name="optionDescription" 
                                  rows="3" required maxlength="500" placeholder="Enter option description"></textarea>
                    </div>

                    <div class="mb-3">
                        <label for="optionPrice" class="form-label">Price:</label>
                        <div class="input-group">
                            <span class="input-group-text">$</span>
                            <input type="number" class="form-control" id="optionPrice" name="optionPrice" 
                                   required min="0" step="0.001" placeholder="Enter price">
                        </div>
                    </div>

                    <div class="mb-4">
                        <label for="productId" class="form-label">Applied Product:</label>
                        <select class="form-select" id="productId" name="productId" required>
                            <option value="">-- Select Product --</option>
                            <c:forEach items="${products}" var="p">
                                <option value="${p.productId}">${p.productName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="d-flex gap-3">
                        <button type="submit" class="btn btn-primary flex-grow-1">
                            <i class="bi bi-plus-circle me-2"></i>Add Option
                        </button>
                        <a href="${pageContext.request.contextPath}/ManageOption/list" class="btn btn-secondary flex-grow-1">
                            <i class="bi bi-x-circle me-2"></i>Cancel
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>