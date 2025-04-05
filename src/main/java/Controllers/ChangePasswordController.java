/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import DAOs.AccountDAO;
import Model.Account;
/**
 *
 * @author THANH THAO
 */
/**
 * Servlet để thay đổi mật khẩu người dùng.
 * Kiểm tra mật khẩu cũ, mật khẩu mới, và xác nhận mật khẩu.
 */
@WebServlet("/changePassword")
public class ChangePasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Xử lý HTTP GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Xử lý HTTP POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Hàm xử lý chung cho cả GET và POST
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");

            // Kiểm tra xem người dùng có đăng nhập không
            if (account == null) {
                response.sendRedirect("/login"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
                return;
            }

            // Lấy mật khẩu cũ, mật khẩu mới, và xác nhận mật khẩu từ form
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            // Kiểm tra mật khẩu mới có khớp với mật khẩu xác nhận không
            if (!newPassword.equals(confirmPassword)) {
                out.println("Passwords do not match.");  // Nếu mật khẩu mới và mật khẩu xác nhận không trùng nhau
                return;
            }

            // Thực hiện thay đổi mật khẩu
            AccountDAO accountDAO = new AccountDAO();
            boolean isPasswordChanged = accountDAO.changePassword(account.getAccountId(), oldPassword, newPassword);

            if (isPasswordChanged) {
                response.sendRedirect("/login");  
            } else {
                out.println("Incorrect old password.");  // Nếu mật khẩu cũ không đúng
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Change Password Controller";
    }
}
