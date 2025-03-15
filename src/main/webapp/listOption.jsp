<%-- 
    Document   : listOption
    Created on : Mar 12, 2025, 2:44:23 PM
    Author     : tvhun
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="manageStaff.jsp" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Options</title>
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
                    <h2 class="page-title">Manage Options</h2>
                    <a href="${pageContext.request.contextPath}/ManageOption/create" class="btn btn-primary">
                        <i class="bi bi-plus-circle"></i> Add New Option
                    </a>
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
                <% session.removeAttribute("errorMessage"); %>
            </c:if>
            
            <div class="table-container">
                <div class="table-responsive">
                    <c:choose>
                        <c:when test="${empty options}">
                            <div class="empty-state">
                                <i class="bi bi-inbox"></i>
                                <h4>No Options Found</h4>
                                <p>You can add new options by clicking the "Add New Option" button</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table id="optionTable" class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Option Name</th>
                                        <th>Applied Product</th>
                                        <th>Price</th>
                                        <th>Status</th>
                                        <th>Created Date</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${options}" var="o">
                                        <tr>
                                            <td>${o.optionId}</td>
                                            <td>${o.optionName}</td>
                                            <td>${o.productName}</td>
                                            <td>
                                                <fmt:formatNumber value="${o.optionPrice}" type="currency" currencySymbol="VND" minFractionDigits="3" maxFractionDigits="3"/>
                                            </td>
                                            <td>
                                                <span class="badge ${o.hidden ? 'bg-danger' : 'bg-success'}">
                                                    ${o.hidden ? 'Hidden' : 'Visible'}
                                                </span>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${o.createdDate}" pattern="MM/dd/yyyy HH:mm"/>
                                            </td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <a href="${pageContext.request.contextPath}/ManageOption/edit?id=${o.optionId}" 
                                                       class="btn btn-warning btn-sm" title="Edit">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/ManageOption/toggle?id=${o.optionId}&status=${o.hidden}" 
                                                       class="btn btn-${o.hidden ? 'success' : 'danger'} btn-sm"
                                                       onclick="return confirm('Are you sure you want to ${o.hidden ? 'show' : 'hide'} this option?')"
                                                       title="${o.hidden ? 'Show' : 'Hide'}">
                                                        <i class="bi bi-${o.hidden ? 'eye' : 'eye-slash'}"></i>
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
            $(document).ready(function(){
                $('#optionTable').DataTable({
                    "order": [[ 0, "asc" ]],
                    "columnDefs": [
                        { "orderable": false, "targets": 6 } // Vô hiệu hóa sắp xếp cho cột "Actions"
                    ]
                });
            });
        </script>
    </body>
</html>
