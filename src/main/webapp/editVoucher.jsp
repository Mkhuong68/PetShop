<%-- 
    Document   : editVoucher
    Created on : Mar 3, 2025, 10:36:57 AM
    Author     : tvhun
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="manageStaff.jsp" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Edit Voucher</title>
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
                    <h1 class="content-title">Edit Voucher</h1>
                </div>
                <form action="ManageVoucher" method="post">
                    <input type="hidden" name="action" value="update" />
                    <input type="hidden" name="voucherId" value="${voucher.voucherId}" />

                    <div class="mb-3">
                        <label for="voucherCode" class="form-label">Voucher Name:</label>
                        <input type="text" id="voucherCode" name="voucherCode" class="form-control" 
                               value="${voucher.voucherCode}" required />
                    </div>

                    <div class="mb-3">
                        <label for="voucherDiscount" class="form-label">Voucher Discount:</label>
                        <input type="number" id="voucherDiscount" name="voucherDiscount"  class="form-control" 
                               value="${voucher.voucherDiscount}" required />
                    </div>

                    <div class="mb-3">
                        <label for="voucherDescription" class="form-label">Description:</label>
                        <textarea id="voucherDescription" name="voucherDescription" class="form-control" required>
                            ${voucher.voucherDescription}
                        </textarea>
                    </div>

                    <div class="mb-3">
                        <label for="voucherType" class="form-label">Voucher Type:</label>
                        <select id="voucherType" name="voucherType" class="form-select" required>
                            <option value="high" <c:if test="${voucher.voucherType == 'high'}">selected</c:if>>
                                    High (for orders over 2 million)
                                </option>
                                <option value="medium" <c:if test="${voucher.voucherType == 'medium'}">selected</c:if>>
                                    Medium (for orders between 1 and 2 million)
                                </option>
                                <option value="new" <c:if test="${voucher.voucherType == 'new'}">selected</c:if>>
                                    New (for new customers)
                                </option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="voucherStatus" class="form-label">Voucher Status:</label>
                            <select id="voucherStatus" name="voucherStatus" class="form-select" required>
                                <option value="true" <c:if test="${voucher.voucherStatus}">selected</c:if>>Enabled</option>
                            <option value="false" <c:if test="${!voucher.voucherStatus}">selected</c:if>>Disabled</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="voucherValidTo" class="form-label">Valid Until (yyyy-MM-dd):</label>
                            <input type="date" id="voucherValidTo" name="voucherValidTo" class="form-control" 
                                   value="${voucher.voucherValidTo}" required />
                    </div>

                    <button type="submit" class="btn btn-primary">Update Voucher</button>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
