/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import DAOs.DeliveryNotificationDAO;
import Model.Account;
import Model.Notification;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tvhun
 */
public class DeliveryNotificationController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeliveryNotificationController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeliveryNotificationController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // Lấy thông tin người dùng hiện tại
        HttpSession session = request.getSession(false);
        Account account = session != null ? (Account) session.getAttribute("account") : null;

        if (account == null) {
            response.sendRedirect("/login");
            return;
        }

        // Kiểm tra xem người dùng có phải là nhân viên giao hàng không
        if (account.getRoleId() != 2) { // 2 là delivery staff
            response.sendRedirect("/Home");
            return;
        }

        // Tạo DAO và lấy dữ liệu
        DeliveryNotificationDAO notificationDAO = new DeliveryNotificationDAO();
        List<Notification> notificationList = notificationDAO.getDeliveryNotificationsByUserId(account.getAccountId());

        // Format thông báo nếu cần
        for (Notification notification : notificationList) {
            String formattedContent = notificationDAO.formatDeliveryNotification(notification.getMessage());
            notification.setMessage(formattedContent);
        }

        int unreadCount = notificationDAO.getUnreadDeliveryNotificationCount(account.getAccountId());

        // Nếu action là "view", chuyển đến trang JSP với dữ liệu đã có sẵn
        if ("view".equals(action)) {
            System.out.println("Forwarding to notifications.jsp with " + notificationList.size() + " notifications");

            // Đặt dữ liệu vào request attributes
            request.setAttribute("notificationList", notificationList);
            request.setAttribute("unreadCount", unreadCount);

            // Forward đến trang JSP
            request.getRequestDispatcher("/notifications.jsp").forward(request, response);
            return;
        }

        // Trả về JSON API response
        System.out.println("Returning JSON with " + notificationList.size() + " notifications");
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try ( PrintWriter out = response.getWriter()) {
            // Tạo JSON bằng Gson thay vì tạo thủ công
            Gson gson = new Gson();
            Map<String, Object> jsonData = new HashMap<>();
            jsonData.put("notifications", notificationList);
            jsonData.put("unreadCount", unreadCount);

            String jsonOutput = gson.toJson(jsonData);
            System.out.println("JSON output: " + jsonOutput);
            out.write(jsonOutput);
        } catch (Exception e) {
            System.out.println("Error processing JSON: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        Account account = session != null ? (Account) session.getAttribute("account") : null;

        if (account == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Đánh dấu một thông báo đã đọc
        if ("markAsRead".equals(action)) {
            try {
                int notificationId = Integer.parseInt(request.getParameter("notificationId"));
                DeliveryNotificationDAO notificationDAO = new DeliveryNotificationDAO();
                boolean success = notificationDAO.markNotificationAsRead(notificationId);

                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("success", success);
                jsonResponse.put("unreadCount", notificationDAO.getUnreadDeliveryNotificationCount(account.getAccountId()));

                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(jsonResponse));
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        // Đánh dấu tất cả thông báo đã đọc
        if ("markAllAsRead".equals(action)) {
            try {
                DeliveryNotificationDAO notificationDAO = new DeliveryNotificationDAO();
                boolean success = notificationDAO.markAllDeliveryNotificationsAsRead(account.getAccountId());

                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("success", success);
                jsonResponse.put("unreadCount", 0);

                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(jsonResponse));
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        // Xử lý mặc định nếu không có action phù hợp
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
