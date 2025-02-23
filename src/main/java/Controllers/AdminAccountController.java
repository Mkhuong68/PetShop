/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;
import DAOs.AccountDAO;
import Model.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author THANH THAO
 */

@WebServlet("/AdminAccountController")
public class AdminAccountController extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Account> accounts = accountDAO.getAllAccounts();

        if (accounts == null || accounts.isEmpty()) {
            request.setAttribute("error", "No accounts found.");
        } else {
            request.setAttribute("accounts", accounts);
        }

        request.getRequestDispatcher("admin-account-list.jsp").forward(request, response);
    }
}
