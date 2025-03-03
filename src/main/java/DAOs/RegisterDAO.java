/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.Account;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RegisterDAO {

    public String hashPasswordMD5(String password_hash) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password_hash.getBytes());
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

    public boolean isUserExists(String username) {
        try ( Connection connection = DBConnection.getConnection()) {
            String checkQuery = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet rs = checkStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExists(String email) {
        try ( Connection connection = DBConnection.getConnection()) {
            String checkQuery = "SELECT * FROM Account WHERE email = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, email);
            ResultSet rs = checkStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String registerUser(String username, String password_hash, String email) {
        String insertQuery = "INSERT INTO Account (username, password_hash, email, role_id) VALUES (?,?,?, 3)";
        try ( Connection connection = DBConnection.getConnection();  PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            insertStatement.setString(1, username);
            insertStatement.setString(2, password_hash);
            insertStatement.setString(3, email);

            int affectedRows = insertStatement.executeUpdate();
            if (affectedRows > 0) {
                return "SUCCESS";
            } else {
                System.out.println("No rows affected by insert");
                return "Failed to insert user";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unexpected error: " + e.getMessage();
        }
    }
    public Account getAccountByEmail(String email) {
        Account account = null;
        String sql = "SELECT account_id, username, email, password_hash " +
                     "FROM Account WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setEmail(rs.getString("email"));
                account.setPasswordHash(rs.getString("password_hash"));
                // Nếu có các trường khác, hãy set thêm vào đây
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
}
