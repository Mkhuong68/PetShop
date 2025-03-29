package Controllers;

import DAOs.CartDAO;
import DAOs.CustomerOrderDAO;
import DAOs.OrderDetailDAO;
import DAOs.VoucherDAO;
import Model.Account;
import Model.CartItem;

import Model.Order;
import Model.OrderDetail;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

public class ConfirmCheckOut extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerOrderDAO orderDAO = new CustomerOrderDAO();
        HttpSession session = request.getSession();
        String loggedInUser = null;
        Account account = (Account) request.getSession().getAttribute("account");
        if (account != null) {
            loggedInUser = account.getUsername();
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int accountId = orderDAO.getAccountId(loggedInUser);
        String resultCode = request.getParameter("resultCode");
        int lastId = orderDAO.getLastInsertedOrderId(accountId);
        if (lastId > 0 && resultCode != null) {
            if ("0".equals(resultCode)) { // Thanh toán thành công
                orderDAO.updateOrderStatus(lastId, true);
            }
        }
        response.sendRedirect(request.getContextPath() + "/CustomerOrderDetailController?orderId=" + lastId);
    }
}
