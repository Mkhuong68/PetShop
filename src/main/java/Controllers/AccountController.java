package Controllers;

import DAOs.AccountDAO;
import Model.Account;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/AccountController")
public class AccountController extends HttpServlet {
    private AccountDAO accountDAO = new AccountDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password"); // Hash the password before saving

            Account account = new Account();
            account.setUsername(username);
            account.setEmail(email);
            account.setPasswordHash(password); // Hash the password

            accountDAO.createAccount(account);
            response.sendRedirect("accountManagement.jsp");

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            accountDAO.deleteAccount(id);
            response.sendRedirect("accountManagement.jsp");

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password"); // Hash the password before saving

            Account account = new Account();
            account.setAccountId(id);
            account.setUsername(username);
            account.setEmail(email);
            account.setPasswordHash(password); // Hash the password

            accountDAO.updateAccount(id, account);
            response.sendRedirect("accountManagement.jsp");
        }
    }
}