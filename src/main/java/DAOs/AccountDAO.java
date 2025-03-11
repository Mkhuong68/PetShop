package DAOs;

import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DB.DBConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author THANH THAO
 */
/**
 * Lớp AccountDAO thực hiện các thao tác CRUD liên quan đến tài khoản người dùng.
 * Hỗ trợ lấy thông tin tài khoản, cập nhật thông tin, đổi mật khẩu và khóa tài khoản.
 */
public class AccountDAO {

    // Lấy thông tin tài khoản theo account_id
    public Account getAccountById(int accountId) {
        Account account = null;
        String sql = "SELECT * FROM Account WHERE account_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getInt("role_id"),
                        rs.getDate("created_date"),
                        rs.getDate("last_login"),
                        rs.getBoolean("is_active"),
                        rs.getString("profile_image"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("gender"),
                        rs.getBoolean("is_active") ? null : "Banned"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
    // Lấy tất cả tài khoản
    public List<Account> getAllCustomerAccounts() {
        List<Account> customerAccounts = new ArrayList<>();
        String sql = "SELECT * FROM Account WHERE role_id = 3";  // ✅ Chỉ lấy tài khoản Customer (role_id = 3)
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customerAccounts.add(new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getInt("role_id"),
                        rs.getDate("created_date"),
                        rs.getDate("last_login"),
                        rs.getBoolean("is_active"),
                        rs.getString("profile_image"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("gender"),
                        rs.getBoolean("is_active") ? null : "Banned"
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerAccounts;
    }
    // Cập nhật thông tin tài khoản
    public boolean updateAccount(Account account) {
    if (account == null) {
        System.out.println("LỖI: Account bị null!");
        return false;
    }

    String sql = "UPDATE Account SET first_name = ?, last_name = ?, email = ?, phone_number = ?, profile_image = ? WHERE account_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, account.getFirstName());
        ps.setString(2, account.getLastName());
        ps.setString(3, account.getEmail());
        ps.setString(4, account.getPhoneNumber());
        ps.setString(5, account.getProfileImage());
        ps.setInt(6, account.getAccountId());

        int rowsUpdated = ps.executeUpdate();
        System.out.println("Rows updated: " + rowsUpdated);
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// Ban tài khoản Customer
    public boolean banCustomerAccount(int customerId, String reason) {
        String sql = "UPDATE Account SET is_active = 0, banned_reason = ? WHERE account_id = ? AND role_id = 3";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reason);
            ps.setInt(2, customerId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Đổi mật khẩu (Chỉ áp dụng cho tài khoản không phải Google)
    public boolean changePassword(int accountId, String oldPassword, String newPassword) {
        if (oldPassword == null || newPassword == null || oldPassword.isEmpty() || newPassword.isEmpty()) {
            return false;
        }

        // Lấy thông tin tài khoản để kiểm tra mật khẩu cũ
        Account account = getAccountById(accountId);
        if (account == null || account.isGoogleAccount()) {
            System.out.println("🔴 Không thể đổi mật khẩu cho tài khoản Google hoặc tài khoản không tồn tại.");
            return false;
        }

        // Mã hóa mật khẩu cũ và mới bằng MD5
        String oldPasswordHash = hashPasswordMD5(oldPassword);
        String newPasswordHash = hashPasswordMD5(newPassword);

        // Debug để kiểm tra hash
        System.out.println("🔍 Mật khẩu cũ nhập vào (plaintext): " + oldPassword);
        System.out.println("🔍 Mật khẩu cũ sau khi mã hóa: " + oldPasswordHash);
        System.out.println("🔍 Mật khẩu mới sau khi mã hóa: " + newPasswordHash);

        // Kiểm tra mật khẩu cũ trong database
        String sql = "UPDATE Account SET password_hash = ? WHERE account_id = ? AND password_hash = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPasswordHash);
            ps.setInt(2, accountId);
            ps.setString(3, oldPasswordHash); // So sánh với mật khẩu đã mã hóa trong DB

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Đổi mật khẩu thành công!");
                return true;
            } else {
                System.out.println("❌ Mật khẩu cũ không đúng!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mã hóa mật khẩu bằng MD5.
     * @param password Mật khẩu cần mã hóa.
     * @return Chuỗi đã mã hóa bằng MD5.
     */
    public String hashPasswordMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
