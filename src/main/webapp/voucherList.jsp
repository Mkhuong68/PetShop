<%-- 
    Document   : voucherList
    Created on : Feb 27, 2025, 11:11:45 PM
    Author     : tvhun
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="manageStaff.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Voucher List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
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
        
        .btn-primary {
            background-color: #8AAAE5;
            border-color: #8AAAE5;
        }
        
        .btn-primary:hover {
            background-color: #7A9AD5;
            border-color: #7A9AD5;
        }
        
        .btn-warning {
            background-color: #ffc107;
            border-color: #ffc107;
        }
        
        .btn-warning:hover {
            background-color: #e0a800;
            border-color: #d39e00;
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
        <div class="content-header">
            <h1 class="content-title">Voucher List</h1>
        </div>
        <div class="form-container">
            <a href="ManageVoucher?action=add" class="btn btn-primary mb-3">
                <i class="bi bi-plus-circle me-2"></i>Add New Voucher
            </a>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Voucher Name</th>
                        <th>Description</th>
                        <th>Discount</th>
                        <th>Type</th>
                        <th>Status</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="v" items="${voucherList}">
                        <tr>
                            <td>${v.voucherCode}</td>
                            <td>${v.voucherDescription}</td>
                            <td>
                                                <fmt:formatNumber value="${v.voucherDiscount}" type="currency" currencySymbol="VND" minFractionDigits="3" maxFractionDigits="3"/>
                                            </td>
                            <td>${v.voucherType}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${v.voucherStatus}">Enabled</c:when>
                                    <c:otherwise>Disabled</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="ManageVoucher?action=edit&id=${v.voucherId}" class="btn btn-warning">
                                    Edit
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    ${v.voucherDiscount}
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
