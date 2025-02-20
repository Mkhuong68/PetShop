/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import Model.Account;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author THANH THAO
 */
@WebServlet("/CustomerProfileController")
public class CustomerProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AccountDAO accountDAO = new AccountDAO();

    // Hiển thị trang Profile (GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("/login");
            return;
        }

        request.setAttribute("account", account);
        request.getRequestDispatcher("viewProfile.jsp").forward(request, response);
    }

    // Cập nhật Profile và Đổi mật khẩu (POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("/login");
            return;
        }

        String action = request.getParameter("action");

        if ("updateProfile".equals(action)) {
            // Cập nhật thông tin cá nhân
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
                request.setAttribute("error", "Information cannot be left blank!");
                request.getRequestDispatcher("customer_profile.jsp").forward(request, response);
                return;
            }

            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setEmail(email);
            account.setPhoneNumber(phoneNumber);
            account.setAddress(address);

            boolean success = accountDAO.updateAccount(account);
            request.setAttribute("message", success ? "Update successful!" : "An error occurred!");
            request.getRequestDispatcher("customer_profile.jsp").forward(request, response);
        } 
        else if ("changePassword".equals(action)) {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (oldPassword == null || newPassword == null || oldPassword.isEmpty() || newPassword.isEmpty()) {
                request.setAttribute("error", "Please enter both old and new passwords!");
                request.getRequestDispatcher("customer_profile.jsp").forward(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "New passwords do not match.");
                request.getRequestDispatcher("customer_profile.jsp").forward(request, response);
                return;
            }

            // Thay đổi mật khẩu
            boolean success = accountDAO.changePassword(account.getAccountId(), oldPassword, newPassword);
            request.setAttribute("message", success ? "Password changed successfully!" : "Incorrect old password!");
            request.getRequestDispatcher("customer_profile.jsp").forward(request, response);
        }
    }
}
