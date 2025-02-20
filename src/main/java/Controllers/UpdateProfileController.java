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
@WebServlet("/updateProfile")
public class UpdateProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("/login");
            return;
        }

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Kiểm tra thông tin không để trống
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            response.getWriter().write("All fields are required.");
            return;
        }

        // Kiểm tra định dạng số điện thoại
        if (!phone.matches("\\d{10,11}")) {
            response.getWriter().write("Invalid phone number format.");
            return;
        }

        // Cập nhật thông tin người dùng
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPhoneNumber(phone);
        account.setAddress(address);

        AccountDAO accountDAO = new AccountDAO();
        boolean isUpdated = accountDAO.updateAccount(account);

        if (isUpdated) {
            session.setAttribute("account", account);
            response.sendRedirect("/viewProfile");
        } else {
            response.getWriter().write("Error updating profile.");
        }
    }
}
