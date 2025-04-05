/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.Account;
import Model.Role;
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
            throw new RuntimeException("LỖI: Unable to connect to database!");
        }
    }

    // Lấy danh sách tài khoản nhân viên (role_id = 1)
    public List<Account> getAllStaffAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Account where role_id = 1 or role_id = 2  ";  // Hoặc câu truy vấn SQL khác của bạn
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
                account.setRoleId(rs.getInt("role_id"));

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
                            rs.getString("gender")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Phương thức listRoles để lấy danh sách các Role từ cơ sở dữ liệu
    public List<Role> getListRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "select role_id, role_name from Roles where role_id = 1 or role_id = 2";  // Câu lệnh SQL để lấy tất cả các vai trò từ bảng Role

        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            // Duyệt qua ResultSet để lấy các vai trò và thêm vào danh sách roles
            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));  // Giả sử có thuộc tính roleId trong bảng Role
                role.setRoleName(rs.getString("role_name"));  // Giả sử có thuộc tính roleName trong bảng Role
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Xử lý lỗi kết nối hoặc truy vấn
        }

        return roles;  // Trả về danh sách các vai trò
    }

    public boolean checkUsernameExit(String userName) {
        String checkSql = "SELECT COUNT(*) FROM Account WHERE username = ? OR email = ?";
        try ( PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setString(1, userName);  // Gán userName vào tham số đầu tiên
            checkPs.setString(2, userName);  // Gán userName vào tham số thứ hai (email sẽ được dùng như email = username trong trường hợp này)

            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;  // Trả về true nếu username hoặc email đã tồn tại
            }
        } catch (SQLException e) {
            // Log lỗi chi tiết nếu có
            System.err.println("Error checking if username or email exists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;  // Trả về false nếu không có lỗi và username/email không tồn tại
    }

    // Thêm tài khoản nhân viên
    public boolean addAccount(Account account) {
        String sql = "INSERT INTO Account (username, password_hash, email, phone_number, role_id, created_date, is_active, profile_image, first_name, last_name, date_of_birth, gender)\n"
                + "VALUES (?, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?)";
        int affectedRows = 0;
        try {
            // Kiểm tra kết nối
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Database connection is closed or null.");
            }

            try ( PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, account.getUsername());
                ps.setString(2, account.getPasswordHash());
                ps.setString(3, account.getEmail());
                ps.setString(4, account.getPhoneNumber());
                ps.setInt(5, account.getRoleId()); // Role Staff
                ps.setBoolean(6, account.isActive());
                ps.setString(7, account.getProfileImage());
                ps.setString(8, account.getFirstName());
                ps.setString(9, account.getLastName());
                ps.setDate(10, new java.sql.Date(account.getDateOfBirth().getTime()));
                ps.setString(11, account.getGender());

                // Thực thi câu lệnh SQL
                affectedRows = ps.executeUpdate();
                System.out.println("Affected rows: " + affectedRows);

                if (affectedRows == 0) {
                    throw new SQLException("Adding account failed, no rows affected.");
                }

                // Lấy ID mới tạo
                try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        System.out.println("New account ID: " + generatedId);
                    } else {
                        throw new SQLException("Creating account failed, no ID obtained.");
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                // In lỗi chi tiết và xác định lỗi cụ thể
                System.err.println("Error during account insertion: " + e.getMessage());
                e.printStackTrace();
                // Quay lại commit nếu xảy ra lỗi
                conn.rollback();
            }
        } catch (SQLException e) {
            // Log lỗi khi không thể thực thi câu lệnh SQL
            System.err.println("Error inserting account: " + e.getMessage());
            e.printStackTrace();
        }
        return affectedRows > 0;
    }

    // Cập nhật tài khoản nhân viên
    public boolean updateAccount(Account account) {
        String sql = "UPDATE Account SET username=?, email=?, phone_number=?, is_active=?, profile_image=?, first_name=?, last_name=?, date_of_birth=?, gender=?,role_id=?  WHERE account_id=?";
         int row =0;
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
            ps.setInt(11, account.getAccountId());
            ps.setInt(10, account.getRoleId());
             row = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row > 0;
    }

    // Xóa tài khoản nhân viên
    public boolean deleteAccount(int accountId) {
        String sql = "update  Account set is_active = 0 where is_active = 1 and account_id = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng

        } catch (SQLException e) {
            e.printStackTrace(); // Nên log lỗi thay vì in ra console
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
}
