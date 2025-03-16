/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.StaffManageAccountDAO;
import Model.Account;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

/**
 *
 * @author Admin
 */
@WebServlet("/StaffManageAccountController")
public class StaffManageAccountController extends HttpServlet {

    private StaffManageAccountDAO accountDAO;

    @Override
    public void init() {
        accountDAO = new StaffManageAccountDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    request.getRequestDispatcher("addAccountStaff.jsp").forward(request, response);
                    break;
                case "edit":
                    int accountId = Integer.parseInt(request.getParameter("id"));
                    System.out.println("Editing account with ID: " + accountId);  // Thêm dòng này để kiểm tra ID
                    Account existingAccount = accountDAO.getAccountById(accountId);
                    if (existingAccount == null) {
                        System.out.println("Account not found!");  // Kiểm tra tài khoản có tồn tại không
                    }
                    request.setAttribute("account", existingAccount);
                    request.getRequestDispatcher("updateAccountStaff.jsp").forward(request, response);
                    break;
                case "delete":
                    accountId = Integer.parseInt(request.getParameter("id"));
                    accountDAO.deleteAccount(accountId);
                    response.sendRedirect("StaffManageAccountController?action=list");
                    break;
                default:
                    List<Account> accounts = accountDAO.getAllStaffAccounts();
                    request.setAttribute("accountList", accounts);
                    request.getRequestDispatcher("manageAccountStaff.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();  // Thêm dòng này để kiểm tra lỗi
            throw new ServletException(e);
        }
    }

// Trong phần code của StaffManageAccountController.java
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "insert":
                    // Lấy thông tin từ form và tạo một đối tượng Account mới
                    Account newAccount = new Account();
                    newAccount.setUsername(request.getParameter("username"));
                    newAccount.setPasswordHash(request.getParameter("password"));
                    newAccount.setEmail(request.getParameter("email"));
                    newAccount.setPhoneNumber(request.getParameter("phone"));
                    newAccount.setActive(request.getParameter("active") != null);
                    newAccount.setProfileImage(request.getParameter("profileImage"));
                    newAccount.setFirstName(request.getParameter("firstName"));
                    newAccount.setLastName(request.getParameter("lastName"));
                    newAccount.setDateOfBirth(Date.valueOf(request.getParameter("dob")));
                    newAccount.setGender(request.getParameter("gender"));

                    // Dùng reflection để set giá trị của bannedReason
                    Field bannedReasonField = Account.class.getDeclaredField("bannedReason");
                    bannedReasonField.setAccessible(true);
                    bannedReasonField.set(newAccount, request.getParameter("bannedReason"));

                    // Thêm tài khoản vào cơ sở dữ liệu
                    accountDAO.addAccount(newAccount);

                    // Sau khi thêm, lấy lại danh sách tài khoản
                    List<Account> accounts = accountDAO.getAllStaffAccounts();
                    // Gửi danh sách tài khoản tới JSP để hiển thị
                    request.setAttribute("accountList", accounts);

                    // Chuyển hướng tới trang manageAccountStaff.jsp
                    request.getRequestDispatcher("manageAccountStaff.jsp").forward(request, response);
                    break;
                case "update":
                    Account updatedAccount = new Account();
                    updatedAccount.setAccountId(Integer.parseInt(request.getParameter("id")));
                    updatedAccount.setUsername(request.getParameter("username"));
                    updatedAccount.setEmail(request.getParameter("email"));
                    updatedAccount.setPhoneNumber(request.getParameter("phone"));
                    updatedAccount.setActive(request.getParameter("active") != null);
                    updatedAccount.setProfileImage(request.getParameter("profileImage"));
                    updatedAccount.setFirstName(request.getParameter("firstName"));
                    updatedAccount.setLastName(request.getParameter("lastName"));
                    updatedAccount.setDateOfBirth(Date.valueOf(request.getParameter("dob")));
                    updatedAccount.setGender(request.getParameter("gender"));

                    // Dùng reflection để set giá trị của bannedReason
                    Field bannedReasonFieldUpdate = Account.class.getDeclaredField("bannedReason");
                    bannedReasonFieldUpdate.setAccessible(true);
                    bannedReasonFieldUpdate.set(updatedAccount, request.getParameter("bannedReason"));

                    // Cập nhật tài khoản trong cơ sở dữ liệu
                    accountDAO.updateAccount(updatedAccount);

                    // Sau khi cập nhật, lấy lại danh sách tài khoản
                    accounts = accountDAO.getAllStaffAccounts();
                    request.setAttribute("accountList", accounts);

                    // Chuyển hướng tới trang manageAccountStaff.jsp
                    request.getRequestDispatcher("manageAccountStaff.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
