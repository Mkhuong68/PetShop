package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import DAOs.AccountDAO;
import Model.Account;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;

@WebServlet("/updateProfile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class UpdateProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/login");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("/login");
            return;
        }

        // Lấy dữ liệu từ form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String profileImage = request.getParameter("profileImage");  // Lấy dữ liệu Base64 của ảnh

        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Profile Image: " + (profileImage != null ? "Có ảnh" : "Không có ảnh"));

        // Kiểm tra các trường dữ liệu không trống
        if (firstName == null || lastName == null || email == null || phone == null
                || firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()) {
            response.getWriter().write("All fields are required.");
            return;
        }

        // Kiểm tra file ảnh
        Part filePart = request.getPart("profilePicture");
        String fileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            // Đường dẫn mới để lưu ảnh vào thư mục profile
            String uploadPath = getServletContext().getRealPath("") + File.separator + "assets" + File.separator + "images" + File.separator + "profile";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            filePart.write(uploadPath + File.separator + fileName);
            account.setProfileImage("assets/images/profile/" + fileName);  // Lưu đường dẫn ảnh mới vào account
        }

        // Cập nhật thông tin tài khoản
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPhoneNumber(phone);

        // Cập nhật database
        AccountDAO accountDAO = new AccountDAO();
        boolean isUpdated = accountDAO.updateAccount(account);

        if (isUpdated) {
            session.setAttribute("account", account);
            response.sendRedirect("viewProfile.jsp");
        } else {
            response.getWriter().write("Error updating profile.");
        }
    }
}
