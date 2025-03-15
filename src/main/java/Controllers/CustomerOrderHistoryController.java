/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.CustomerOrderDAO;
import Model.Account;
import Model.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class CustomerOrderHistoryController extends HttpServlet {

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
            out.println("<title>Servlet CustomerOrderHistoryController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerOrderHistoryController at " + request.getContextPath() + "</h1>");
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
        CustomerOrderDAO c = new CustomerOrderDAO();

        // Kiem tra dang nhap nguoi dung
        HttpSession session = request.getSession();
        String loggedInUser = null;
        Account account = (Account) request.getSession().getAttribute("account");
        if (account != null) {
            loggedInUser = account.getUsername();

        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        loggedInUser = cookie.getValue();
                        break;
                    }
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
        int accountId = c.getAccountId(loggedInUser);

        List<Order> delivered = c.getAllOrderDelivered(accountId);
        List<Order> cancelled = c.getAllOrderCancelled(accountId);
        List<Order> received = c.getAllOrderReceived(accountId);
        List<Order> pending = c.getAllOrderPending(accountId);

        if (delivered != null && cancelled != null && received != null && pending != null) {
            if (delivered.isEmpty()) {
                request.setAttribute("msgDelivered", "No Order");
            }
            if (cancelled.isEmpty()) {
                request.setAttribute("msgCancelled", "No Order");
            }
            if (received.isEmpty()) {
                request.setAttribute("msgReceived", "No Order");
            }
            if (pending.isEmpty()) {
                request.setAttribute("msgPending", "No Order");
            }
            request.setAttribute("delivered", delivered);
            request.setAttribute("cancelled", cancelled);
            request.setAttribute("pending", pending);
            request.setAttribute("received", received);
            request.getRequestDispatcher("viewOrderHistoryCustomer.jsp").forward(request, response);
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
