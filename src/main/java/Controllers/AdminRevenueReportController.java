/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import DAOs.AdminRevenueReportDAO;
import DAOs.CustomerOrderDAO;
import Model.Account;
import Model.RevenueReport;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class AdminRevenueReportController extends HttpServlet {

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
            out.println("<title>Servlet AdminRevenueReportController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminRevenueReportController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        CustomerOrderDAO c = new CustomerOrderDAO();
        String loggedInUser = null;
        Account account = (Account) request.getSession().getAttribute("account");
        if (account != null) {
            loggedInUser = account.getUsername();
        }
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int accountId = c.getAccountId(loggedInUser);

        AdminRevenueReportDAO r = new AdminRevenueReportDAO();
        List<RevenueReport> reports = r.getAllRevenueReports();
        if (reports != null || reports.size() != 0) {
            request.setAttribute("reports", reports);
        }

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int reportId = Integer.parseInt(request.getParameter("reportId"));
            r.deleteRevenueReport(reportId);
        }
        request.getRequestDispatcher("viewRevenueReportAdmin.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        CustomerOrderDAO c = new CustomerOrderDAO();
        String loggedInUser = null;
        Account account = (Account) request.getSession().getAttribute("account");
        if (account != null) {
            loggedInUser = account.getUsername();
        }
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int accountId = c.getAccountId(loggedInUser);

        String action = request.getParameter("action");
        if (action.equals("create")) {
            AdminRevenueReportDAO r = new AdminRevenueReportDAO();
            Date reportDate = Date.valueOf(request.getParameter("reportDate"));
            int totalOrders = r.getTotalOrdersByDate(reportDate);
            BigDecimal totalRevenue = r.getTotalRevenue(reportDate);
            BigDecimal averageOrderValue = r.getAverageOrderValue(reportDate);

            RevenueReport report = new RevenueReport(0, reportDate, totalRevenue, totalOrders,
                    averageOrderValue, accountId, new Timestamp(System.currentTimeMillis())
            );
            boolean isAdded = r.createRevenueReport(report);
            response.sendRedirect(request.getContextPath() + "/AdminRevenueReportController");
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
