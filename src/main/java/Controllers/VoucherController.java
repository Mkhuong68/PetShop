/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.VoucherDAO;
import Model.Voucher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tvhun
 */
public class VoucherController extends HttpServlet {

    private VoucherDAO voucherDAO;

    @Override
    public void init() {
        voucherDAO = new VoucherDAO();
    }

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
            out.println("<title>Servlet VoucherController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VoucherController at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "list":
            default:
                listVoucher(request, response);
                break;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "insert":
                insertVoucher(request, response);
                break;
            case "update":
                updateVoucher(request, response);
                break;
            default:
                doGet(request, response);
                break;
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

    private void listVoucher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Voucher> list = voucherDAO.getAllVouchers();
        request.setAttribute("voucherList", list);
        request.getRequestDispatcher("voucherList.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("addVoucher.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Voucher voucher = voucherDAO.getVoucherById(id);
        request.setAttribute("voucher", voucher);
        request.getRequestDispatcher("editVoucher.jsp").forward(request, response);
    }

    // Khi staff/admin tạo voucher, voucher được tạo chung
    private void insertVoucher(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String voucherCode = request.getParameter("voucherCode");
        String voucherDescription = request.getParameter("voucherDescription");
        int voucherDiscount = Integer.parseInt(request.getParameter("voucherDiscount"));
        String validToStr = request.getParameter("voucherValidTo");
        Date voucherValidTo = null;
        try {
            voucherValidTo = new SimpleDateFormat("yyyy-MM-dd").parse(validToStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String voucherType = request.getParameter("voucherType");
        
        Voucher voucher = new Voucher();
        voucher.setVoucherCode(voucherCode);
        voucher.setVoucherDescription(voucherDescription);
        voucher.setVoucherDiscount(voucherDiscount);
        voucher.setVoucherValidTo(voucherValidTo);
        voucher.setVoucherStatus(true); // Mặc định voucher được kích hoạt
        voucher.setVoucherType(voucherType);
        // Thời gian hiệu lực bắt đầu từ hiện tại
        voucher.setVoucherValidFrom(new Date());

        voucherDAO.addVoucher(voucher);
        response.sendRedirect("ManageVoucher?action=list");
    }

    private void updateVoucher(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        String voucherCode = request.getParameter("voucherCode");
        String voucherDescription = request.getParameter("voucherDescription");
        int voucherDiscount = Integer.parseInt(request.getParameter("voucherDiscount"));
        String validToStr = request.getParameter("voucherValidTo");
        Date voucherValidTo = null;
        try {
            voucherValidTo = new SimpleDateFormat("yyyy-MM-dd").parse(validToStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean voucherStatus = Boolean.parseBoolean(request.getParameter("voucherStatus"));
        String voucherType = request.getParameter("voucherType");

        Voucher voucher = voucherDAO.getVoucherById(voucherId);
        if (voucher != null) {
            voucher.setVoucherCode(voucherCode);
            voucher.setVoucherDescription(voucherDescription);
            voucher.setVoucherDiscount(voucherDiscount);
            voucher.setVoucherValidTo(voucherValidTo);
            voucher.setVoucherStatus(voucherStatus);
            voucher.setVoucherType(voucherType);
            voucherDAO.updateVoucher(voucher);
        }
        response.sendRedirect("ManageVoucher?action=list");
    }
}
