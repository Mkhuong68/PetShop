<%-- 
    Document   : addVoucher
    Created on : Feb 27, 2025, 6:38:06 PM
    Author     : tvhun
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Add Voucher</title>
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
            <h1>Add Voucher</h1>
            <form action="ManageVoucher" method="post">
                <input type="hidden" name="action" value="insert" />
                <div class="mb-3">
                    <label for="voucherCode" class="form-label">Voucher Name:</label>
                    <input type="text" id="voucherCode" name="voucherCode" class="form-control" required />
                </div>
                <div class="mb-3">
                    <label for="voucherDiscount" class="form-label">Voucher Discount:</label>
                    <input type="number" id="voucherDiscount" name="voucherDiscount" class="form-control" required />
                </div>
                <div class="mb-3">
                    <label for="voucherDescription" class="form-label">Description:</label>
                    <textarea id="voucherDescription" name="voucherDescription" class="form-control" required></textarea>
                </div>
                <div class="mb-3">
                    <label for="voucherType" class="form-label">Voucher Type:</label>
                    <select id="voucherType" name="voucherType" class="form-control" required>
                        <option value="high">High (for orders over 2 million)</option>
                        <option value="medium">Medium (for orders between 1 and 2 million)</option>
                        <option value="new">New (for new customers)</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="voucherValidTo" class="form-label">Valid Until (yyyy-MM-dd):</label>
                    <input type="date" id="voucherValidTo" name="voucherValidTo" class="form-control" required />
                </div>
                <button type="submit" class="btn btn-primary">Add Voucher</button>
            </form>
        </div>
    </body>
</html>

