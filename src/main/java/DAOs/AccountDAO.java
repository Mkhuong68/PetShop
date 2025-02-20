package DAOs;

import Model.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/petshop";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public AccountDAO() {
        // You might want to initialize a connection pool here instead
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM accounts";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("accountId"));
                account.setUsername(rs.getString("username"));
                account.setEmail(rs.getString("email"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }
        return accounts;
    }

    public boolean createAccount(Account account) {
        String query = "INSERT INTO accounts (username, email, passwordHash) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getEmail());
            pstmt.setBytes(3, account.getpasswordHash()); // Ensure password is hashed
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }
        return false;
    }

    public boolean updateAccount(int id, Account account) {
        String query = "UPDATE accounts SET username = ?, email = ?, passwordHash = ? WHERE accountId = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getEmail());
            pstmt.setBytes(3, account.getpasswordHash()); // Ensure password is hashed
            pstmt.setInt(4, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }
        return false;
    }

    public boolean deleteAccount(int id) {
        String query = "DELETE FROM accounts WHERE accountId = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }
        return false;
    }
}