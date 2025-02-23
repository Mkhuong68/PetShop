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
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 *
 * @author THANH THAO
 */
@WebServlet("/BanCustomerAccountController") // ✅ Chỉ STAFF mới có quyền Ban Customer
public class BanCustomerAccountController extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        try {
            int customerId = Integer.parseInt(request.getParameter("id"));
            String reason = request.getParameter("reason");

            if (reason == null || reason.trim().isEmpty()) {
                reason = "Banned by staff";
            }

            // ✅ Kiểm tra xem `customerId` có tồn tại không trước khi ban
            Account customer = accountDAO.getAccountById(customerId);
            if (customer == null) { 
                response.sendRedirect("customer-account-list.jsp?error=customer_not_found");
                return;
            }
            if (customer.getRoleId() != 3) { // Chỉ ban Customer
                response.sendRedirect("customer-account-list.jsp?error=not_a_customer");
                return;
            }
            if (!customer.isActive()) { // Nếu đã bị ban rồi, không cần ban lại
                response.sendRedirect("customer-account-list.jsp?error=already_banned");
                return;
            }

            boolean result = accountDAO.banCustomerAccount(customerId, reason); // ✅ Chỉ ban Customer

            if (result) {
                response.sendRedirect("customer-account-list.jsp?success=ban");
            } else {
                response.sendRedirect("customer-account-list.jsp?error=ban_failed");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("customer-account-list.jsp?error=invalid_id");
        }
    }
}
