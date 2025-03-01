/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.OrderStatusDAO;
import DAOs.StaffOrderDAO;
import Model.OrderStatus;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class StaffOrderDetailController extends HttpServlet {

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
            out.println("<title>Servlet StaffOrderDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffOrderDetailController at " + request.getContextPath() + "</h1>");
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
        String oId = request.getParameter("orderId");
        StaffOrderDAO s = new StaffOrderDAO();
        OrderStatusDAO o = new OrderStatusDAO();
        int orderId = 0;
        if (oId != null) {
            orderId = Integer.parseInt(oId);
            if (orderId > 0) {
                request.setAttribute("data", s.getOrderbyId(orderId));
                request.setAttribute("statusList", o.getAllOrderStatus());
                request.getRequestDispatcher("viewOrderDetailStaff.jsp").forward(request, response);
            }
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
        String statusName = request.getParameter("statusName");
        String oId = request.getParameter("orderId");
        if (oId != null && statusName != null) {
            int orderId = Integer.parseInt(oId);
            OrderStatusDAO o = new OrderStatusDAO();
            List<OrderStatus> statusList = o.getAllOrderStatus();
            int statusId = -1;
            for (OrderStatus os : statusList) {
                if (os.getStatusName().equalsIgnoreCase(statusName)) {
                    statusId = os.getStatusId();
                    break;
                }
            }
            if (statusId > 0) {
                o.changeStatus(statusId, orderId);
                response.sendRedirect(request.getContextPath() + "StaffOrderController");
            }
        }
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
