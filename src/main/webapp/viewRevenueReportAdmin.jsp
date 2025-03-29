<%-- 
    Document   : viewRevenueReportAdmin
    Created on : Mar 14, 2025, 9:16:55 PM
    Author     : NgocNNCE181950
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/css/revenue.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <!-- Left Section: Create Report -->
            <div class="left-container">
                <h2>Create New Report</h2>
                <form action="AdminRevenueReportController" method="post">
                    <input type="hidden" name="action" value="create">
                    <input type="date" name="reportDate" max="<%= java.time.LocalDate.now()%>" required><br>
                    <input type="submit" value="Create Report">
                </form>
            </div>

            <!-- Right Section: List Reports -->
            <div class="right-container">
                <h2>Revenue Reports</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Report ID</th>
                            <th>Report Date</th>
                            <th>Total Revenue</th>
                            <th>Total Orders</th>
                            <th>Average Order Value</th>
                            <th>Created Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="report" items="${reports}">
                            <tr>
                                <td>${report.reportId}</td>
                                <td><fmt:formatDate value="${report.reportDate}" pattern="MM/dd/yyyy" /></td>
                                <td><fmt:formatNumber value="${report.totalRevenue}" pattern="#,##0" /> VND</td>
                                <td>${report.totalOrders}</td>
                                <td><fmt:formatNumber value="${report.averageOrderValue}" pattern="#,##0" /> VND</td>
                                <td><fmt:formatDate value="${report.createdDate}" pattern="MM/dd/yyyy" /></td>
                                <td>
                                    <a href="AdminRevenueReportController?action=delete&reportId=${report.reportId}" onclick="return confirm('Are you sure you want to delete this report?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <c:if test="${not empty msg}">
                    <p>${msg}</p>
                </c:if>
            </div>
        </div>

    </body>
</html>
