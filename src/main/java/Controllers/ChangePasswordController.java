package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import DAOs.AccountDAO;  
import Model.Account;   

/**
 * Servlet để xử lý yêu cầu đổi mật khẩu của người dùng.
 */
@WebServlet("/changePassword")
public class ChangePasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("/login");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra xác nhận mật khẩu mới có khớp không
        if (!newPassword.equals(confirmPassword)) {
            response.getWriter().write("Passwords do not match.");
            return;
        }

        AccountDAO accountDAO = new AccountDAO();
        
        // Mã hóa mật khẩu bằng MD5 để phù hợp với hệ thống đăng nhập
        String hashedOldPassword = accountDAO.hashPasswordMD5(oldPassword);
        String hashedNewPassword = accountDAO.hashPasswordMD5(newPassword);

        // Debug kiểm tra hash
        System.out.println("🔍 Mật khẩu cũ nhập vào: " + oldPassword);
        System.out.println("🔍 Mật khẩu cũ sau khi mã hóa: " + hashedOldPassword);
        System.out.println("🔍 Mật khẩu mới sau khi mã hóa: " + hashedNewPassword);

        boolean isPasswordChanged = accountDAO.changePassword(account.getAccountId(), oldPassword, newPassword);

        if (isPasswordChanged) {
            session.invalidate(); // Đăng xuất người dùng sau khi đổi mật khẩu thành công
            response.sendRedirect("/login");
        } else {
            response.getWriter().write("Incorrect old password.");
        }
    }
}
