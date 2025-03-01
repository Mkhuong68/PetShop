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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("access-denied.jsp");
            return;
        }

        Account staffAccount = (Account) session.getAttribute("account");

        // ✅ Chỉ Staff (`role_id = 1`) mới có quyền ban Customer
        if (staffAccount.getRoleId() != 1) {
            response.sendRedirect("access-denied.jsp");
            return;
        }

        try {
            int customerId = Integer.parseInt(request.getParameter("id"));
            String reason = request.getParameter("reason");

            if (reason == null || reason.trim().isEmpty()) {
                reason = "Banned by staff";
            }

            // ✅ Kiểm tra `customerId` có tồn tại trong DB không
            Account customer = accountDAO.getAccountById(customerId);
            if (customer == null || customer.getRoleId() != 3) { // Chỉ ban Customer (`role_id = 3`)
                response.sendRedirect("customer-account-list.jsp?error=invalid_customer");
                return;
            }
            if (!customer.isActive()) { // Nếu đã bị ban rồi, không cần ban lại
                response.sendRedirect("customer-account-list.jsp?error=already_banned");
                return;
            }

            boolean result = accountDAO.banCustomerAccount(customerId, reason);

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
