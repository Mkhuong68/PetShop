/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import Model.Account;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author THANH THAO
 */
@WebServlet("/ProfileController")
public class CustomerProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AccountDAO accountDAO = new AccountDAO();

    // Xử lý HTTP GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountDAO c = new AccountDAO();
        String loggedInUser = null;
        Account account = (Account) request.getSession().getAttribute("account");

        // Kiểm tra xem người dùng đã đăng nhập chưa
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
                request.setAttribute("msg", "No cookie");
                request.getRequestDispatcher("viewProfile.jsp").forward(request, response);
                return;
            }
        }

        if (loggedInUser == null || loggedInUser.isEmpty()) {
            request.setAttribute("msg", "No user logged in");
            request.getRequestDispatcher("viewProfile.jsp").forward(request, response);
            return;
        }

        int accountId = c.getAccountId(loggedInUser);
        // Lấy thông tin tài khoản từ database
        request.setAttribute("account", c.getAccountById(accountId));
        request.getRequestDispatcher("viewProfile.jsp").forward(request, response);
    }

    // Xử lý HTTP POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("changePassword".equals(action)) {
            // Thay đổi mật khẩu
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            // Kiểm tra các trường thông tin
            if (oldPassword == null || newPassword == null || oldPassword.isEmpty() || newPassword.isEmpty()) {
                request.setAttribute("error", "Please enter both old and new passwords!");
                request.getRequestDispatcher("viewProfile.jsp").forward(request, response);  // Chuyển sang trang viewProfile.jsp
                return;
            }

            // Kiểm tra mật khẩu mới có trùng với mật khẩu xác nhận không
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "New passwords do not match.");
                request.getRequestDispatcher("viewProfile.jsp").forward(request, response);  // Chuyển sang trang viewProfile.jsp
                return;
            }

            // Lấy tài khoản từ session
            Account account = (Account) request.getSession().getAttribute("account");
            if (account == null) {
                request.setAttribute("error", "User not logged in.");
                request.getRequestDispatcher("viewProfile.jsp").forward(request, response);
                return;
            }

            // Thực hiện thay đổi mật khẩu thông qua DAO
            boolean success = accountDAO.changePassword(account.getAccountId(), oldPassword, newPassword);
            if (success) {
                request.setAttribute("message", "Password changed successfully!");
            } else {
                request.setAttribute("error", "Incorrect old password.");
            }
            request.getRequestDispatcher("viewProfile.jsp").forward(request, response);  // Chuyển sang trang viewProfile.jsp
        }
    }

    @Override
    public String getServletInfo() {
        return "Customer Profile Controller";
    }
}
