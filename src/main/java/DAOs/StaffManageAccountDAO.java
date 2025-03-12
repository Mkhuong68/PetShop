/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class StaffManageAccountDAO {

    private final Connection conn;

    public StaffManageAccountDAO() {
        this.conn = new DBConnection().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("LỖI: Không thể kết nối đến database!");
        }
    }

    // Lấy danh sách tài khoản nhân viên (role_id = 1)
    public List<Account> getAllStaffAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Account";  // Hoặc câu truy vấn SQL khác của bạn
        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setEmail(rs.getString("email"));
                account.setPhoneNumber(rs.getString("phone_number"));
                account.setActive(rs.getBoolean("is_active"));
                account.setFirstName(rs.getString("first_name"));
                account.setLastName(rs.getString("last_name"));
                account.setDateOfBirth(rs.getDate("date_of_birth"));
                account.setGender(rs.getString("gender"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // Lấy tài khoản theo ID
    public Account getAccountById(int id) {
        String sql = "SELECT * FROM Account WHERE account_id = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(
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
                            rs.getString("bannedReason")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm tài khoản nhân viên
    public void addAccount(Account account) {
        String sql = "INSERT INTO Account (username, password_hash, email, phone_number, role_id, created_date, is_active, profile_image, first_name, last_name, date_of_birth, gender, bannedReason) "
                + "VALUES (?, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPasswordHash());
            ps.setString(3, account.getEmail());
            ps.setString(4, account.getPhoneNumber());
            ps.setInt(5, 1); // Role Staff
            ps.setBoolean(6, account.isActive());
            ps.setString(7, account.getProfileImage());
            ps.setString(8, account.getFirstName());
            ps.setString(9, account.getLastName());
            ps.setDate(10, new java.sql.Date(account.getDateOfBirth().getTime()));
            ps.setString(11, account.getGender());
            ps.setString(12, account.getBannedReason());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật tài khoản nhân viên
    public void updateAccount(Account account) {
        String sql = "UPDATE Account SET username=?, email=?, phone_number=?, is_active=?, profile_image=?, first_name=?, last_name=?, date_of_birth=?, gender=?, bannedReason=? WHERE account_id=?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPhoneNumber());
            ps.setBoolean(4, account.isActive());
            ps.setString(5, account.getProfileImage());
            ps.setString(6, account.getFirstName());
            ps.setString(7, account.getLastName());
            ps.setDate(8, new java.sql.Date(account.getDateOfBirth().getTime()));
            ps.setString(9, account.getGender());
            ps.setString(10, account.getBannedReason());
            ps.setInt(11, account.getAccountId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa tài khoản nhân viên
    public void deleteAccount(int accountId) {
        String sql = "DELETE FROM Account WHERE account_id=?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
