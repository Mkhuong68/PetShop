/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import Model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author THANH THAO
 */
@WebServlet("/CustomerAccountController") // ✅ Dành cho STAFF quản lý Customer
public class CustomerAccountController extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // ✅ Không tạo session mới nếu chưa có
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("access-denied.jsp");
            return;
        }

        Account staffAccount = (Account) session.getAttribute("account");

        // ✅ Kiểm tra nếu người dùng không phải Staff (role_id = 2) => Cấm truy cập
        if (staffAccount.getRoleId() != 2) {
            response.sendRedirect("access-denied.jsp");
            return;
        }

        // ✅ Lấy danh sách Customer
        List<Account> customerAccounts = accountDAO.getAllCustomerAccounts();

        if (customerAccounts == null || customerAccounts.isEmpty()) {
            request.setAttribute("error", "No customer accounts found.");
        } else {
            request.setAttribute("accounts", customerAccounts);
        }

        request.getRequestDispatcher("customer-account-list.jsp").forward(request, response);
    }
}
