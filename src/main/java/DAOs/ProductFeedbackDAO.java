/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import static DB.DBConnection.getConnection;
import Model.ProductFeedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tvhun
 */
public class ProductFeedbackDAO {

    // Lấy thông tin phản hồi theo ID
    public ProductFeedback getFeedbackById(int feedbackId) {
        ProductFeedback feedback = null;
        String sql = "SELECT * FROM ProductFeedback WHERE feedback_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedbackId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                feedback = new ProductFeedback();
                feedback.setFeedbackId(rs.getInt("feedback_id"));
                feedback.setOrderDetailId(rs.getInt("order_detail_id"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setComment(rs.getString("comment"));
                feedback.setStaffReply(rs.getString("staff_reply"));
                feedback.setCreatedDate(rs.getTimestamp("created_date"));
                feedback.setLastUpdated(rs.getTimestamp("last_updated"));
                feedback.setIsHidden(rs.getBoolean("is_hidden"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedback;
    }

    public List<Map<String, Object>> getAllFeedbackWithDetails() {
        List<Map<String, Object>> feedbackList = new ArrayList<>();
        String query = "SELECT pf.*, p.product_name, a.first_name, a.last_name, a.username "
                + "FROM ProductFeedback pf "
                + "JOIN OrderDetails od ON pf.order_detail_id = od.order_detail_id "
                + "JOIN Products p ON od.product_id = p.product_id "
                + "JOIN Orders o ON od.order_id = o.order_id "
                + "JOIN Account a ON o.account_id = a.account_id "
                + "ORDER BY pf.created_date DESC";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> feedbackDetails = new HashMap<>();

                // Original feedback fields
                feedbackDetails.put("feedbackId", rs.getInt("feedback_id"));
                feedbackDetails.put("orderDetailId", rs.getInt("order_detail_id"));
                feedbackDetails.put("rating", rs.getInt("rating"));
                feedbackDetails.put("comment", rs.getString("comment"));
                feedbackDetails.put("staffReply", rs.getString("staff_reply"));
                feedbackDetails.put("createdDate", rs.getTimestamp("created_date"));
                feedbackDetails.put("lastUpdated", rs.getTimestamp("last_updated"));
                feedbackDetails.put("isHidden", rs.getBoolean("is_hidden"));

                // Additional details
                feedbackDetails.put("productName", rs.getString("product_name"));
                feedbackDetails.put("customerFirstName", rs.getString("first_name"));
                feedbackDetails.put("customerLastName", rs.getString("last_name"));
                feedbackDetails.put("username", rs.getString("username"));

                feedbackList.add(feedbackDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackList;
    }

    public Map<String, Object> getFeedbackByIdWithDetails(int feedbackId) {
        Map<String, Object> feedbackDetails = new HashMap<>();
        String query = "SELECT pf.*, p.product_name, p.product_image, a.first_name, a.last_name, a.username, a.email "
                + "FROM ProductFeedback pf "
                + "JOIN OrderDetails od ON pf.order_detail_id = od.order_detail_id "
                + "JOIN Products p ON od.product_id = p.product_id "
                + "JOIN Orders o ON od.order_id = o.order_id "
                + "JOIN Account a ON o.account_id = a.account_id "
                + "WHERE pf.feedback_id = ?";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, feedbackId);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Original feedback fields
                    feedbackDetails.put("feedbackId", rs.getInt("feedback_id"));
                    feedbackDetails.put("orderDetailId", rs.getInt("order_detail_id"));
                    feedbackDetails.put("rating", rs.getInt("rating"));
                    feedbackDetails.put("comment", rs.getString("comment"));
                    feedbackDetails.put("staffReply", rs.getString("staff_reply"));
                    feedbackDetails.put("createdDate", rs.getTimestamp("created_date"));
                    feedbackDetails.put("lastUpdated", rs.getTimestamp("last_updated"));
                    feedbackDetails.put("isHidden", rs.getBoolean("is_hidden"));

                    // Additional details
                    feedbackDetails.put("productName", rs.getString("product_name"));
                    feedbackDetails.put("productImage", rs.getString("product_image"));
                    feedbackDetails.put("customerFirstName", rs.getString("first_name"));
                    feedbackDetails.put("customerLastName", rs.getString("last_name"));
                    feedbackDetails.put("username", rs.getString("username"));
                    feedbackDetails.put("email", rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackDetails;
    }

    // Cập nhật trả lời phản hồi
    public boolean replyFeedback(int feedbackId, String reply) {
        String sql = "UPDATE ProductFeedback SET staff_reply = ?, last_updated = GETDATE() WHERE feedback_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, reply);
            ps.setInt(2, feedbackId);
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
