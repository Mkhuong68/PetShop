/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import Model.Account;
import Model.UserAddress;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateProfileController")
public class UpdateProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("/login"); // Nếu không đăng nhập, chuyển hướng đến trang login
            return;
        }

        // Lấy dữ liệu từ form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Kiểm tra các trường không để trống
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
            return;
        }

        // Kiểm tra định dạng số điện thoại
        if (!phone.matches("\\d{10,11}")) {
            request.setAttribute("error", "Invalid phone number format.");
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
            return;
        }

        // Cập nhật thông tin tài khoản
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPhoneNumber(phone);

        // Tạo đối tượng UserAddress và cập nhật địa chỉ
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress(address);  // Cập nhật địa chỉ mới vào UserAddress
        account.setUserAddress(userAddress);  // Gán UserAddress vào Account

        // Cập nhật tài khoản vào cơ sở dữ liệu
        AccountDAO accountDAO = new AccountDAO();
        boolean isUpdated = accountDAO.updateAccount(account);

        // Nếu cập nhật thành công, lưu thông tin mới vào session và chuyển hướng đến trang profile
        if (isUpdated) {
            session.setAttribute("account", account); // Cập nhật lại session
            response.sendRedirect("viewProfile.jsp");  // Chuyển hướng đến trang viewProfile
        } else {
            request.setAttribute("error", "Error updating profile.");
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
        }
    }
}