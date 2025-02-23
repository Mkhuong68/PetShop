/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;
import DAOs.AccountDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author THANH THAO
 */

@WebServlet("/BanAccountController")
public class BanAccountController extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            String reason = request.getParameter("reason"); // ✅ Nhận lý do từ request

            if (reason == null || reason.trim().isEmpty()) {
                reason = "Banned by admin"; // ✅ Nếu không có lý do, đặt mặc định
            }

            boolean result = accountDAO.banAccount(userId, reason);

            if (result) {
                response.sendRedirect("admin-account-list.jsp?success=ban");
            } else {
                response.sendRedirect("admin-account-list.jsp?error=ban");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("admin-account-list.jsp?error=invalid_id");
        }
    }
}