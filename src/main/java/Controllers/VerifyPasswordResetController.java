/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Service.EmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Diem Quynh
 */
public class VerifyPasswordResetController extends HttpServlet {

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
            out.println("<title>Servlet VerifyPasswordResetController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyPasswordResetController at " + request.getContextPath() + "</h1>");
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

        // Kiểm tra đã có thông tin xác thực email trong session chưa
        if (session.getAttribute("resetEmail") == null
                || session.getAttribute("verificationCode") == null
                || !"verify".equals(session.getAttribute("resetPasswordStep"))) {

            response.sendRedirect("forgotPassword");
            return;
        }

        request.getRequestDispatcher("verifyPasswordReset.jsp").forward(request, response);
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

        if ("resend".equals(action)) {
            // Xử lý gửi lại mã
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("resetEmail");

            if (email != null) {
                // Tạo mã xác thực mới
                int verificationCode = (int) (Math.random() * 900000) + 100000;
                String verificationCodeStr = String.valueOf(verificationCode);

                // Gửi email
                EmailService emailService = new EmailService();
                emailService.sendVerificationEmail(email, verificationCodeStr);

                // Cập nhật mã mới vào session
                session.setAttribute("verificationCode", verificationCodeStr);

                request.setAttribute("msg", "Mã xác thực mới đã được gửi đến email của bạn.");
            } else {
                response.sendRedirect("forgotPassword");
                return;
            }
        } else {
            // Xử lý xác thực mã
            HttpSession session = request.getSession();
            String verificationCode = request.getParameter("verificationCode");
            String storedCode = (String) session.getAttribute("verificationCode");

            // Kiểm tra mã xác thực
            if (storedCode != null && storedCode.equals(verificationCode)) {
                // Mã xác thực đúng, chuyển sang bước đặt lại mật khẩu
                session.setAttribute("resetPasswordStep", "reset");
                response.sendRedirect("resetPassword");
                return;
            } else {
                // Mã xác thực sai
                request.setAttribute("msg", "Mã xác thực không đúng. Vui lòng thử lại.");
            }
        }

        request.getRequestDispatcher("verifyPasswordReset.jsp").forward(request, response);
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
