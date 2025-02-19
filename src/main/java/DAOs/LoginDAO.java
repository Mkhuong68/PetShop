/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public Account loginUser(String username, String password_hash) {
        Account account = null;
        String query = "SELECT account_id, username, email, role_id FROM Account "
                + "WHERE username = ? AND password_hash = ? AND is_active = 1";

        try ( Connection connection = DBConnection.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {

            // Gán giá trị cho các tham số trong câu truy vấn
            statement.setString(1, username);
            statement.setString(2, password_hash);

            try ( ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Nếu có kết quả, khởi tạo đối tượng Account và thiết lập các thuộc tính cần thiết
                    account = new Account();
                    account.setAccountId(rs.getInt("account_id"));
                    account.setUsername(rs.getString("username"));
                    account.setEmail(rs.getString("email"));
                    account.setRoleId(rs.getInt("role_id"));
                    // Nếu cần, có thể lấy thêm các trường khác như created_date, profile_image, ...
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Ở đây bạn có thể log lỗi hoặc ném ngoại lệ tùy theo cách xử lý của ứng dụng.
        }

        return account;
    }
}
