/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Model.Post;
import DB.DBConnection;
import Model.PostStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author THANH THAO
 */

public class StaffPostDAO {

    // Lấy tất cả bài viết và trạng thái từ cơ sở dữ liệu
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.post_id, p.title, p.content, p.account_id, p.status_id, p.created_date, ps.status_name " +
                     "FROM Posts p JOIN PostStatus ps ON p.status_id = ps.status_id WHERE p.is_hidden = 0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post post = new Post();
                PostStatus status = new PostStatus();
                status.setStatusId(rs.getInt("status_id"));
                status.setStatusName(rs.getString("status_name"));

                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setAccountId(rs.getInt("account_id"));
                post.setCreatedDate(rs.getTimestamp("created_date"));
                post.setStatus(status);  // Gán đối tượng PostStatus vào bài viết

                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // Duyệt bài viết (Cập nhật trạng thái bài viết thành Accepted)
    public boolean acceptPost(int postId) {
        String sql = "UPDATE Posts SET status_id = 1 WHERE post_id = ?";  // 1 = Accepted

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Từ chối bài viết (Cập nhật trạng thái bài viết thành Rejected và lưu lý do từ chối)
    public boolean rejectPost(int postId, String rejectReason) {
        String sql = "UPDATE Posts SET status_id = 2, reject_reason = ? WHERE post_id = ?";  // 2 = Rejected

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rejectReason);
            ps.setInt(2, postId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa bài viết
    public boolean deletePost(int postId) {
        String sql = "UPDATE Posts SET is_hidden = 1 WHERE post_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
