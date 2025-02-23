/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DB.DBConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
/**
 *
 * @author THANH THAO
 */

public class AccountDAO {

    // Lấy thông tin tài khoản theo account_id
    public Account getAccountById(int accountId) {
        Account account = null;
        String sql = "SELECT a.account_id, a.username, a.password_hash, a.email, a.phone_number, a.role_id, "
                   + "a.created_date, a.last_login, a.is_active, a.profile_image, a.first_name, a.last_name, "
                   + "a.date_of_birth, a.gender FROM Account a WHERE a.account_id = ?";

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
                    rs.getBoolean("is_active") ? null : "Banned"  // ✅ Đã thêm dấu phẩy
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    // Lấy tất cả tài khoản
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Account";  // Đảm bảo bảng có dữ liệu
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(new Account(
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
        return accounts;
    }

    // Cập nhật thông tin tài khoản
    public boolean updateAccount(Account account) {
        String sql = "UPDATE Account SET first_name = ?, last_name = ?, email = ?, phone_number = ?, role_id = ?, is_active = ? WHERE account_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account.getFirstName());
            ps.setString(2, account.getLastName());
            ps.setString(3, account.getEmail());
            ps.setString(4, account.getPhoneNumber());
            ps.setInt(5, account.getRoleId());
            ps.setBoolean(6, account.isActive());
            ps.setInt(7, account.getAccountId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ban tài khoản với lý do cụ thể
public boolean banAccount(int accountId, String reason) {
    String sql = "UPDATE Account SET is_active = 0, banned_reason = ? WHERE account_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, reason);
        ps.setInt(2, accountId);

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    // Đổi mật khẩu (chỉ áp dụng cho tài khoản không phải Google)
    public boolean changePassword(int accountId, String oldPassword, String newPassword) {
        if (oldPassword == null || newPassword == null || oldPassword.isEmpty() || newPassword.isEmpty()) {
            return false;
        }

        // Kiểm tra xem tài khoản có phải là tài khoản Google hay không
        Account account = getAccountById(accountId);
        if (account.isGoogleAccount()) {
            return false; // Không thể thay đổi mật khẩu cho tài khoản Google
        }

        // Mã hóa mật khẩu
        String oldPasswordHash = hashPassword(oldPassword);
        String newPasswordHash = hashPassword(newPassword);

        String sql = "UPDATE Account SET password_hash = ? WHERE account_id = ? AND password_hash = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPasswordHash);
            ps.setInt(2, accountId);
            ps.setString(3, oldPasswordHash);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mã hóa mật khẩu
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
