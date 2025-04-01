/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

/**
 *
 * @author THANH THAO
 */
import DB.DBConnection;
import Model.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffPostDAO {

    private Connection conn = DBConnection.getConnection();

    // Lấy danh sách tất cả bài viết của Customer (role_id = 3)
    public List<Post> getAllCustomerPosts() {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT p.* FROM Posts p "
                + "JOIN Account a ON p.author_id = a.account_id "
                + "WHERE a.role_id = 3 "
                + "ORDER BY p.created_date DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post p = new Post(
                        rs.getInt("post_id"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("status_id"),
                        rs.getTimestamp("created_date"),
                        rs.getString("reject_reason"), // Lấy reject_reason
                        rs.getString("delete_reason")  // Lấy delete_reason
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Duyệt bài viết (Accept Post)
    public void acceptPost(int postId) {
        String sql = "UPDATE Posts SET status_id = 1, last_updated = GETDATE() WHERE post_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Từ chối bài viết (Reject Post) với lý do
    public void rejectPost(int postId, String reason) {
        String sql = "UPDATE Posts SET status_id = 2, reject_reason = ?, last_updated = GETDATE() WHERE post_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, reason);
            ps.setInt(2, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa bài viết (Delete Post) với lý do
    public void deletePost(int postId, String reason) {
        // Lưu lý do xóa trước khi xóa bài viết
        String updateSql = "UPDATE Posts SET delete_reason = ?, last_updated = GETDATE() WHERE post_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setString(1, reason);
            ps.setInt(2, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Xóa bài viết
        String deleteSql = "DELETE FROM Posts WHERE post_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(deleteSql);
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}