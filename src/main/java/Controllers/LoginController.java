/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.PrintWriter;
import DAOs.LoginDAO;
import DAOs.RegisterDAO;
import Model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginController extends HttpServlet {

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
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("signinandlogin.jsp").forward(request, response);
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
        // Lấy thông tin đăng nhập từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Kiểm tra xem password có null hoặc rỗng không
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Mật khẩu không được để trống.");
            request.getRequestDispatcher("signinandlogin.jsp").forward(request, response);
            return;
        }

        // Băm mật khẩu sử dụng MD5
        String hashedPassword = hashPasswordMD5(password);

        // Gọi LoginDAO để kiểm tra thông tin đăng nhập
        LoginDAO loginDAO = new LoginDAO();
        Account account = loginDAO.loginUser(username, hashedPassword);

        if (account != null) {
            // Nếu đăng nhập thành công, kiểm tra xem có tick Remember Me hay không
            String remember = request.getParameter("remember"); // checkbox có name="remember"
            if (remember != null && remember.equals("true")) {
                // Tạo cookie cho username và password (đã băm)
                Cookie cookieUsername = new Cookie("username", username);
                Cookie cookiePassword = new Cookie("password", hashedPassword);
                // Đặt thời hạn sống của cookie là 7 ngày (7*24*60*60 giây)
                cookieUsername.setMaxAge(7 * 24 * 60 * 60);
                cookiePassword.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(cookieUsername);
                response.addCookie(cookiePassword);
            } else {
                // Nếu không tick Remember Me, có thể xóa cookie cũ (nếu tồn tại)
                Cookie cookieUsername = new Cookie("username", "");
                Cookie cookiePassword = new Cookie("password", "");
                cookieUsername.setMaxAge(0);
                cookiePassword.setMaxAge(0);
                response.addCookie(cookieUsername);
                response.addCookie(cookiePassword);
            }

            // Lưu thông tin tài khoản vào session và chuyển hướng đến homepage
            request.getSession().setAttribute("account", account);
            response.sendRedirect("hompage.jsp");
        } else {
            // Đăng nhập không thành công, chuyển về trang đăng nhập với thông báo lỗi
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng.");
            request.getRequestDispatcher("signinandlogin.jsp").forward(request, response);
        }
    }

    private String hashPasswordMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
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
