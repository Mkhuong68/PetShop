<%-- 
    Document   : voucherList
    Created on : Feb 27, 2025, 11:11:45 PM
    Author     : tvhun
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Voucher List</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
        <style>
            .container {
                display: block !important;
                height: auto !important;
            }
            .sidebar {
                position: fixed !important;
                top: 0;
                left: 0;
                bottom: 0;
                width: 250px !important;
            }
            .main-content {
                display: none !important;
            }
            #dynamicContent {
                margin-left: 270px;
                padding: 20px;
                background: #ecf0f1;
                min-height: 100vh;
            }
        </style>
    </head>
    <body style="background: #fff9f0;">
        <jsp:include page="manageStaff.jsp" />
        <div id="dynamicContent">
            <h1>Voucher List</h1>
            <a href="ManageVoucher?action=add" class="btn btn-primary mb-3">Add New Voucher</a>
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
                            <td>${v.voucherDiscount}</td>
                            <td>${v.voucherType}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${v.voucherStatus}">Enabled</c:when>
                                    <c:otherwise>Disabled</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="ManageVoucher?action=edit&id=${v.voucherId}" class="btn btn-warning">Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>


