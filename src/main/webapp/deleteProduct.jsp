%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="DAOs.StaffManageProductDAO" %>
<%
    int productId = Integer.parseInt(request.getParameter("id"));
    StaffManageProductDAO productDAO = new StaffManageProductDAO();
    productDAO.deleteProduct(productId);
    response.sendRedirect("manageProduct.jsp");
%>