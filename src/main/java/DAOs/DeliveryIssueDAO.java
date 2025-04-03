/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.DeliveryIssue;
import Model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tvhun
 */
public class DeliveryIssueDAO {

    // Get all issues reported by a specific staff
    public List<DeliveryIssue> getIssuesByReporterId(int reporterId) {
        List<DeliveryIssue> issues = new ArrayList<>();
        String sql = "SELECT i.issue_id, i.reporter_id, i.issue_type, i.reference_id, i.issue_description, "
                + "i.status, i.created_date, i.resolved_date, i.resolved_by, o.order_id, "
                + "CONCAT(a.first_name, ' ', a.last_name) as resolved_by_name "
                + "FROM ReportedIssues i "
                + "LEFT JOIN Orders o ON i.reference_id = o.order_id "
                + "LEFT JOIN Account a ON i.resolved_by = a.account_id "
                + "WHERE i.reporter_id = ? "
                + "ORDER BY i.created_date DESC";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reporterId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DeliveryIssue issue = new DeliveryIssue();
                    issue.setIssueId(rs.getInt("issue_id"));
                    issue.setReporterId(rs.getInt("reporter_id"));
                    issue.setIssueType(rs.getString("issue_type"));
                    issue.setReferenceId(rs.getInt("reference_id"));
                    issue.setIssueDescription(rs.getString("issue_description"));
                    issue.setStatus(rs.getString("status"));
                    issue.setCreatedDate(rs.getTimestamp("created_date"));
                    issue.setResolvedDate(rs.getTimestamp("resolved_date"));
                    issue.setResolvedBy(rs.getInt("resolved_by"));
                    issue.setOrderId(rs.getInt("order_id"));
                    issue.setResolvedByName(rs.getString("resolved_by_name"));
                    issues.add(issue);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting issues: " + e.getMessage());
        }
        return issues;
    }

    // Create a new issue report
    public boolean createIssue(DeliveryIssue issue) {
        String sql = "INSERT INTO ReportedIssues (reporter_id, issue_type, reference_id, issue_description, status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, issue.getReporterId());
            ps.setString(2, issue.getIssueType());
            ps.setInt(3, issue.getReferenceId());
            ps.setString(4, issue.getIssueDescription());
            ps.setString(5, "Pending"); // Default status for new issues

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error creating issue: " + e.getMessage());
            return false;
        }
    }

    // Get delivered orders by staff ID that haven't been reported yet
    public List<Order> getDeliveredOrdersByStaffId(int staffId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.order_id, o.account_id, o.order_date, o.deliver_to as delivery_address, "
                + "o.payment_status, o.payment_method, o.shipping_fee, o.order_note, ds.status_name "
                + "FROM Orders o "
                + "JOIN DeliveryInformation di ON o.order_id = di.order_id "
                + "JOIN DeliveryStatus ds ON di.status_id = ds.status_id "
                + "WHERE di.staff_id = ? AND di.status_id = 5 " // 5 = Delivered in DeliveryStatus table
                + "AND NOT EXISTS (SELECT 1 FROM ReportedIssues ri WHERE ri.reference_id = o.order_id AND ri.reporter_id = ?) "
                + "ORDER BY o.order_date DESC";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, staffId);
            ps.setInt(2, staffId); // Use the same staff ID to check for existing reports

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setAccountId(rs.getInt("account_id"));
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setDeliveryAddress(rs.getString("delivery_address"));
                    order.setPaymentStatus(rs.getBoolean("payment_status"));
                    order.setPaymentMethod(rs.getString("payment_method"));
                    order.setShippingFee(rs.getDouble("shipping_fee"));
                    order.setOrderNote(rs.getString("order_note"));
                    order.setStatusName(rs.getString("status_name"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting delivered orders: " + e.getMessage());
        }
        return orders;
    }

    // Get issue details by ID
    public DeliveryIssue getIssueById(int issueId) {
        String sql = "SELECT i.issue_id, i.reporter_id, i.issue_type, i.reference_id, i.issue_description, "
                + "i.status, i.created_date, i.resolved_date, i.resolved_by, o.order_id, "
                + "CONCAT(a.first_name, ' ', a.last_name) as resolved_by_name "
                + "FROM ReportedIssues i "
                + "LEFT JOIN Orders o ON i.reference_id = o.order_id "
                + "LEFT JOIN Account a ON i.resolved_by = a.account_id "
                + "WHERE i.issue_id = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, issueId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DeliveryIssue issue = new DeliveryIssue();
                    issue.setIssueId(rs.getInt("issue_id"));
                    issue.setReporterId(rs.getInt("reporter_id"));
                    issue.setIssueType(rs.getString("issue_type"));
                    issue.setReferenceId(rs.getInt("reference_id"));
                    issue.setIssueDescription(rs.getString("issue_description"));
                    issue.setStatus(rs.getString("status"));
                    issue.setCreatedDate(rs.getTimestamp("created_date"));
                    issue.setResolvedDate(rs.getTimestamp("resolved_date"));
                    issue.setResolvedBy(rs.getInt("resolved_by"));
                    issue.setOrderId(rs.getInt("order_id"));
                    issue.setResolvedByName(rs.getString("resolved_by_name"));
                    return issue;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting issue details: " + e.getMessage());
        }
        return null;
    }
    // Check if an order has already been reported by this staff

    public boolean isOrderAlreadyReported(int orderId, int reporterId) {
        String sql = "SELECT COUNT(*) FROM ReportedIssues WHERE reference_id = ? AND reporter_id = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, reporterId);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking if order is already reported: " + e.getMessage());
        }
        return false;
    }
}
