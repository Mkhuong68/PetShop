package DAOs;

import Model.Account;
import Model.UserAddress;
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
 * Lớp AccountDAO thực hiện các thao tác CRUD liên quan đến tài khoản người
 * dùng. Hỗ trợ lấy thông tin tài khoản, cập nhật thông tin, đổi mật khẩu và
 * khóa tài khoản.
 */
public class AccountDAO {

    Connection conn = DBConnection.getConnection();

    // Phương thức cập nhật thông tin tài khoản vào cơ sở dữ liệu
    public boolean updateProfile(Account account) {
        String query = "  UPDATE Account SET email = ?, phone_number = ?, first_name = ?, last_name = ?, date_of_birth = ? , gender = ? WHERE account_id = ?";

        try ( PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getPhoneNumber());
            stmt.setString(3, account.getFirstName());
            stmt.setString(4, account.getLastName());
            stmt.setDate(5, account.getDateOfBirth());
            stmt.setString(6, account.getGender());
            stmt.setInt(7, account.getAccountId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getAccountId(String username) {
        int id = -1;
        try {
            String sql = "SELECT account_id FROM Account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("account_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    // Lấy thông tin tài khoản theo account_id
    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM Account WHERE account_id = ?";
        Account acc = null;  // Khai báo đối tượng Account

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            // Thiết lập giá trị cho prepared statement
            ps.setInt(1, accountId);

            // Thực hiện truy vấn
            ResultSet rs = ps.executeQuery();

            // Nếu có kết quả trả về, khởi tạo đối tượng Account
            if (rs.next()) {
                acc = new Account(
                        rs.getInt("account_id"), // Lấy account_id
                        rs.getString("username"), // Lấy username
                        rs.getString("password_hash"), // Lấy password_hash
                        rs.getString("email"), // Lấy email
                        rs.getString("phone_number"), // Lấy phone_number
                        rs.getInt("role_id"), // Lấy role_id
                        rs.getDate("created_date"), // Lấy created_date
                        rs.getDate("last_login"), // Lấy last_login
                        rs.getBoolean("is_active"), // Lấy is_active
                        rs.getString("profile_image"), // Lấy profile_image
                        rs.getString("first_name"), // Lấy first_name
                        rs.getString("last_name"), // Lấy last_name
                        rs.getDate("date_of_birth"), // Lấy date_of_birth
                        rs.getString("gender")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return acc;  // Trả về đối tượng Account hoặc null nếu không tìm thấy
    }

    // Lấy thông tin UserAddress theo account_id
    public UserAddress getUserAddressByAccountId(int accountId) {
        UserAddress userAddress = null;
        String sql = "SELECT * FROM UserAddresses WHERE account_id = ? AND is_default = true"; // Giả sử lấy địa chỉ mặc định

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userAddress = new UserAddress(
                        rs.getInt("address_id"),
                        rs.getInt("account_id"),
                        rs.getString("address"),
                        rs.getBoolean("is_default"),
                        rs.getDate("created_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAddress;
    }
    // Cập nhật thông tin tài khoản
    public boolean updateAccount(Account account) {
        String sql = "UPDATE Account SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE account_id = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            // Gán giá trị vào các tham số trong câu lệnh SQL
            ps.setString(1, account.getFirstName());
            ps.setString(2, account.getLastName());
            ps.setString(3, account.getEmail());
            ps.setString(4, account.getPhoneNumber());
            ps.setInt(5, account.getAccountId());

            // Thực hiện cập nhật và kiểm tra số dòng bị ảnh hưởng
            int rowsUpdated = ps.executeUpdate();

            // Nếu ít nhất một dòng được cập nhật, trả về true
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi, trả về false
        }
    }


    // Đổi mật khẩu (Chỉ áp dụng cho tài khoản không phải Google)
    public boolean changePassword(int accountId, String oldPassword, String newPassword) {
        if (oldPassword == null || newPassword == null || oldPassword.isEmpty() || newPassword.isEmpty()) {
            return false;
        }

        // Mã hóa mật khẩu cũ mà người dùng nhập
        String oldPasswordHash = hashPasswordMD5(oldPassword);

        // Kiểm tra mật khẩu cũ trong database
        String sql = "SELECT password_hash FROM Account WHERE account_id = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String currentPasswordHash = rs.getString("password_hash");

                // So sánh mật khẩu đã mã hóa trong database với mật khẩu người dùng nhập vào
                if (oldPasswordHash.equals(currentPasswordHash)) {
                    // Mật khẩu cũ đúng, tiến hành thay đổi mật khẩu
                    String newPasswordHash = hashPasswordMD5(newPassword); // Mã hóa mật khẩu mới

                    String updateSql = "UPDATE Account SET password_hash = ? WHERE account_id = ?";
                    try ( PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                        updatePs.setString(1, newPasswordHash);
                        updatePs.setInt(2, accountId);
                        int rowsUpdated = updatePs.executeUpdate();
                        return rowsUpdated > 0; // Kiểm tra xem có thay đổi thành công không
                    }
                } else {
                    System.out.println("❌ Mật khẩu cũ không đúng!");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mã hóa mật khẩu bằng MD5.
     *
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
