/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.RegisterDAO;
import java.io.PrintWriter;
import Service.EmailService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VerifyEmailController extends HttpServlet {

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
            out.println("<title>Servlet VerifyEmailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyEmailController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("verifyEmail.jsp").forward(request, response);
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

        String action = request.getServletPath();
        if ("/verifyEmail".equals(action)) {
            String verificationCode = request.getParameter("verificationCode");
            String sessionCode = (String) request.getSession().getAttribute("verificationCode");
            System.out.println("Mã xác thực người dùng nhập: " + verificationCode);
            System.out.println("Mã xác thực trong session: " + sessionCode);

            if (verificationCode != null && verificationCode.equals(sessionCode)) {
                // Lấy thông tin từ session
                String username = (String) request.getSession().getAttribute("username");
                String password = (String) request.getSession().getAttribute("password_hash");
                String email = (String) request.getSession().getAttribute("email");

                RegisterDAO registerDAO = new RegisterDAO();
                // Hash mật khẩu bằng MD5
                String hashedPassword = registerDAO.hashPasswordMD5(password);
                // Gọi hàm đăng ký với mật khẩu đã hash
                String result = registerDAO.registerUser(username, hashedPassword, email);
                if ("SUCCESS".equals(result)) {
                    response.sendRedirect("registerandlogin.jsp?msg=Xác thực thành công, bạn có thể đăng nhập.");
                } else {
                    request.setAttribute("msg", "Đăng ký không thành công, vui lòng thử lại.");
                    request.getRequestDispatcher("verifyEmail.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg", "Mã xác thực không đúng hoặc đã hết hạn.");
                request.getRequestDispatcher("verifyEmail.jsp").forward(request, response);
            }
        } else if ("/resendCode".equals(action)) {
            // Xử lý gửi lại mã xác thực
            String email = (String) request.getSession().getAttribute("email");
            String newCode = generateVerificationCode();
            request.getSession().setAttribute("verificationCode", newCode);
            EmailService emailService = new EmailService();
            emailService.sendVerificationEmail(email, newCode);
            request.setAttribute("msg", "Mã xác thực mới đã được gửi.");
            request.getRequestDispatcher("verifyEmail.jsp").forward(request, response);
        }
    }

    private String generateVerificationCode() {
        return String.valueOf((int) ((Math.random() * 900000) + 100000));
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
