package DAOs;

import DB.DBConnection;
import Model.ReportedIssue;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class ManageIssueAdminDAO {

    private final Connection conn;

    public ManageIssueAdminDAO() {
        this.conn = new DBConnection().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("ERROR: Cannot connect to database!");
        }
    }

    // Lấy danh sách tất cả các vấn đề được báo cáo
    public List<ReportedIssue> getAllIssues() {
        List<ReportedIssue> issues = new ArrayList<>();
        String sql = "SELECT * FROM ReportedIssues";
        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ReportedIssue issue = new ReportedIssue();
                issue.setIssueId(rs.getInt("issue_id"));
                issue.setAccountId(rs.getInt("reporter_id"));
                issue.setIssueDescription(rs.getString("issue_description"));
                issue.setReportedDate(rs.getDate("created_date"));

                // Kiểm tra trạng thái từ cột "status"
                String status = rs.getString("status");
                issue.setIsResolved("Resolved".equals(status));

                issues.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issues;
    }

    // Lấy một vấn đề cụ thể theo ID
    public ReportedIssue getIssueById(int issueId) {
        String sql = "SELECT * FROM ReportedIssues WHERE issue_id = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ReportedIssue(
                            rs.getInt("issue_id"),
                            rs.getInt("reporter_id"),
                            rs.getString("issue_description"),
                            rs.getDate("created_date"),
                            "Resolved".equals(rs.getString("status")) // Kiểm tra trạng thái
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, Object>> getOrderIdByIssueById(int issueId) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        String sql = "SELECT o.order_id, ri.issue_id\n"
                + "FROM ReportedIssues ri\n"
                + "LEFT JOIN Account reporter ON ri.reporter_id = reporter.account_id\n"
                + "LEFT JOIN Orders o ON ri.reference_id = o.order_id\n"
                + "LEFT JOIN Account customer ON o.account_id = customer.account_id\n"
                + "LEFT JOIN Account resolver ON ri.resolved_by = resolver.account_id\n"
                + "WHERE ri.issue_id = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("issue_id", rs.getInt("issue_id"));
                    row.put("order_id", rs.getInt("order_id"));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    // Cập nhật trạng thái của vấn đề
    public boolean updateIssueStatus(int issueId, boolean isResolved) {
        String sql = "UPDATE ReportedIssues SET status = ? WHERE issue_id = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isResolved ? "Resolved" : "Pending"); // Cập nhật trạng thái
            ps.setInt(2, issueId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
