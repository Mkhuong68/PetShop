/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;
/**
 *
 * @author THANH THAO
 */
import DAOs.AccountDAO;
import Model.Account;
import Model.UserAddress;
import java.sql.*;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class UpdateProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountDAO c = new AccountDAO();
        String loggedInUser = null;
        Account account = (Account) request.getSession().getAttribute("account");
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
                request.getRequestDispatcher("viewProfilep").forward(request, response);
                return;
            }
        }
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            request.setAttribute("msg", "No user");
            request.getRequestDispatcher("viewProfile.jsp").forward(request, response);
            return;
        }
        int accountId = c.getAccountId(loggedInUser);

        // Lấy thông tin từ form
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Date dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
        String gender = request.getParameter("gender");
        // Cập nhật tài khoản
        account = new Account(accountId, email, phoneNumber, firstName, lastName, dateOfBirth, gender);
        boolean isUpdated = c.updateAccount(account);
        if (isUpdated) {
            request.setAttribute("account", c.getAccountById(accountId));
            session.setAttribute("account", account);
            request.getRequestDispatcher("viewProfile.jsp").forward(request, response);
        }

    }
}
