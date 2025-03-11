package DAOs;

import DB.DBConnection;
import Model.Issue;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {

    public List<Issue> getAllIssues() {
        List<Issue> issues = new ArrayList<>();
        String query = "SELECT * FROM issues ORDER BY reportedAt DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                issues.add(new Issue(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("reportedAt")
                ));
            }
        } catch (SQLException e) {
        }
        return issues;
    }

    public boolean updateIssueStatus(int id, String status) {
        String query = "UPDATE issues SET status=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
        }
        return false;
    }
}
