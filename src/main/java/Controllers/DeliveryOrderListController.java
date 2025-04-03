/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.DeliveryInformationDAO;
import Model.Account;
import Model.DeliveryInformation;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 *
 * @author tvhun
 */
public class DeliveryOrderListController extends HttpServlet {

    private DeliveryInformationDAO deliveryDAO;

    @Override
    public void init() throws ServletException {
        deliveryDAO = new DeliveryInformationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Kiểm tra session
        Account currentUser = (Account) request.getSession().getAttribute("account");
        if (currentUser == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            response.sendRedirect("/login");
            return;
        }
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("edit")) {
                try {
                    int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
                    Map<String, Object> orderDetailsMap = deliveryDAO.getDeliveryOrderDetails(deliveryId);
                    if (orderDetailsMap != null) {
                        request.setAttribute("orderDetails", orderDetailsMap.get("orderDetails"));
                        request.setAttribute("orderTotal", orderDetailsMap.get("orderTotal"));
                        request.setAttribute("customerName", orderDetailsMap.get("customerName"));
                        request.setAttribute("customerPhone", orderDetailsMap.get("customerPhone"));
                        request.setAttribute("customerAddress", orderDetailsMap.get("customerAddress"));
                        request.setAttribute("currentStatusId", deliveryDAO.getCurrentStatusId(deliveryId));

                        RequestDispatcher dispatcher = request.getRequestDispatcher("editDeliveryOrder.jsp");
                        dispatcher.forward(request, response);
                        return;
                    } else {
                        request.setAttribute("errorMessage", "Order details not found.");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMessage", "Invalid delivery ID.");
                }
            } else if (action.equals("delete")) {
                try {
                    int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
                    boolean deleted = deliveryDAO.deleteDeliveryOrder(deliveryId);
                    if (deleted) {
                        request.setAttribute("successMessage", "Order deleted successfully.");
                    } else {
                        request.setAttribute("errorMessage", "Order could not be deleted. Only cancelled orders can be deleted.");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMessage", "Invalid delivery ID.");
                }
            }
        }

        // 3. Mặc định hiển thị danh sách đơn giao hàng
        List<Map<String, Object>> deliveryList = deliveryDAO.getDeliveryOrderDisplayList();
        request.setAttribute("deliveryList", deliveryList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/deliveryOrderList.jsp");
        dispatcher.forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String action = request.getParameter("action");

    if (action != null && action.equals("updateStatus")) {
        try {
            int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
            int newStatusId = Integer.parseInt(request.getParameter("newStatus"));

            Map<String, String> result = deliveryDAO.updateDeliveryStatus(deliveryId, newStatusId);

            if ("true".equals(result.get("success"))) {
                request.getSession().setAttribute("successMessage", result.get("message"));
            } else {
                request.getSession().setAttribute("errorMessage", result.get("error"));
            }

            // Redirect back to the delivery order list or edit page
            String referer = request.getHeader("Referer");
            if (referer != null && referer.contains("edit")) {
                // If coming from edit page, redirect back there
                response.sendRedirect("deliveryList?action=edit&deliveryId=" + deliveryId);
            } else {
                // Otherwise redirect to the list
                response.sendRedirect("deliveryList");
            }
            return;

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid input: Delivery ID or Status ID is not a valid number.");
            response.sendRedirect("deliveryList");
            return;
        }
    }

    // If not updateStatus action, forward to doGet
    doGet(request, response);
}

    @Override
    public String getServletInfo() {
        return "Delivery Order List Controller handling edit and delete actions.";
    }
}
