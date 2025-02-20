/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author THANH THAO
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

        if (!newPassword.equals(confirmPassword)) {
            response.getWriter().write("Passwords do not match.");
            return;
        }

        AccountDAO accountDAO = new AccountDAO();
        boolean isPasswordChanged = accountDAO.changePassword(account.getAccountId(), oldPassword, newPassword);

        if (isPasswordChanged) {
            response.sendRedirect("/viewProfile");
        } else {
            response.getWriter().write("Incorrect old password.");
        }
    }
}
