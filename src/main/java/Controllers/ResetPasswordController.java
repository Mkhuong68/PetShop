/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.RegisterDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
public class ResetPasswordController extends HttpServlet {

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
            out.println("<title>Servlet ResetPasswordController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordController at " + request.getContextPath() + "</h1>");
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
        
        // Kiểm tra đã xác thực email chưa
        if (session.getAttribute("resetEmail") == null || 
            !"reset".equals(session.getAttribute("resetPasswordStep"))) {
            
            response.sendRedirect("forgotPassword");
            return;
        }
        
        request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
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
        
        // Kiểm tra đã xác thực email chưa
        if (session.getAttribute("resetEmail") == null || 
            !"reset".equals(session.getAttribute("resetPasswordStep"))) {
            
            response.sendRedirect("forgotPassword");
            return;
        }
        
        String email = (String) session.getAttribute("resetEmail");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Kiểm tra mật khẩu và xác nhận mật khẩu
        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Mật khẩu và xác nhận mật khẩu không khớp");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }
        
        // Cập nhật mật khẩu
        RegisterDAO registerDAO = new RegisterDAO();
        String hashedPassword = registerDAO.hashPasswordMD5(password);
        boolean updated = updatePassword(registerDAO, email, hashedPassword);
        
        if (updated) {
            // Xóa thông tin session
            session.removeAttribute("resetEmail");
            session.removeAttribute("verificationCode");
            session.removeAttribute("resetPasswordStep");
            
            // Thông báo thành công và chuyển đến trang đăng nhập
            request.setAttribute("msg", "Mật khẩu đã được đặt lại thành công. Vui lòng đăng nhập với mật khẩu mới.");
            request.getRequestDispatcher("/login").forward(request, response);
        } else {
            request.setAttribute("message", "Có lỗi xảy ra khi đặt lại mật khẩu. Vui lòng thử lại.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        }
    }
    
    private boolean updatePassword(RegisterDAO registerDAO, String email, String hashedPassword) {
        // Thêm phương thức này vào RegisterDAO
        try {
            // Giả sử phương thức này đã được thêm vào RegisterDAO
            return registerDAO.updatePassword(email, hashedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
