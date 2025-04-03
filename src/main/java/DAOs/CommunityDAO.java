package DAOs;

import DB.DBConnection;
import Model.Comment;
import Model.Post;
import Model.PostReaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunityDAO {
    
    private Connection conn;
    
    public CommunityDAO() {
        conn = DBConnection.getConnection();
    }
    
    // Lấy tổng số bài viết được chấp nhận 
    public int getTotalPosts() {
        String sql = "SELECT COUNT(*) AS total FROM Posts WHERE status_id = 1 AND is_hidden = 0";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Lấy danh sách bài viết với phân trang
    public List<Map<String, Object>> getPostsWithDetails(int page, int postsPerPage) {
        List<Map<String, Object>> posts = new ArrayList<>();
        int offset = (page - 1) * postsPerPage;
        
        String sql = "SELECT p.*, a.username, a.profile_image, a.first_name, a.last_name, " +
                    "(SELECT COUNT(*) FROM PostReactions pr WHERE pr.post_id = p.post_id) AS like_count, " +
                    "(SELECT COUNT(*) FROM Comments c WHERE c.post_id = p.post_id AND c.is_hidden = 0) AS comment_count " +
                    "FROM Posts p " +
                    "JOIN Account a ON p.author_id = a.account_id " +
                    "WHERE p.status_id = 1 AND p.is_hidden = 0 " +
                    "ORDER BY p.created_date DESC " +
                    "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, postsPerPage);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> post = new HashMap<>();
                    
                    // Thông tin bài viết
                    post.put("postId", rs.getInt("post_id"));
                    post.put("title", rs.getString("title"));
                    post.put("content", rs.getString("content"));
                    post.put("postImage", rs.getString("post_image"));
                    post.put("createdDate", rs.getTimestamp("created_date"));
                    post.put("lastUpdated", rs.getTimestamp("last_updated"));
                    post.put("viewCount", rs.getInt("view_count"));
                    post.put("authorId", rs.getInt("author_id"));
                    
                    // Thông tin tác giả
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String username = rs.getString("username");
                    
                    if (firstName != null && lastName != null) {
                        post.put("authorName", firstName + " " + lastName);
                    } else {
                        post.put("authorName", username);
                    }
                    
                    post.put("authorProfileImage", rs.getString("profile_image"));
                    
                    // Số lượt thích và bình luận
                    post.put("likeCount", rs.getInt("like_count"));
                    post.put("commentCount", rs.getInt("comment_count"));
                    
                    // Lấy 3 bình luận gần nhất cho mỗi bài viết
                    post.put("comments", getRecentComments(rs.getInt("post_id"), 3));
                    
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return posts;
    }
    
    // Lấy các bình luận gần nhất của bài viết
    public List<Map<String, Object>> getRecentComments(int postId, int limit) {
        List<Map<String, Object>> comments = new ArrayList<>();
        
        String sql = "SELECT c.*, a.username, a.profile_image, a.first_name, a.last_name " +
                    "FROM Comments c " +
                    "JOIN Account a ON c.account_id = a.account_id " +
                    "WHERE c.post_id = ? AND c.is_hidden = 0 " +
                    "ORDER BY c.created_date DESC " +
                    "OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.setInt(2, limit);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> comment = new HashMap<>();
                    
                    comment.put("commentId", rs.getInt("comment_id"));
                    comment.put("content", rs.getString("content"));
                    comment.put("createdDate", rs.getTimestamp("created_date"));
                    comment.put("accountId", rs.getInt("account_id"));
                    
                    // Thông tin tác giả bình luận
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String username = rs.getString("username");
                    
                    if (firstName != null && lastName != null) {
                        comment.put("authorName", firstName + " " + lastName);
                    } else {
                        comment.put("authorName", username);
                    }
                    
                    comment.put("authorProfileImage", rs.getString("profile_image"));
                    
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return comments;
    }
    
    // Kiểm tra bài viết có tồn tại không
    public boolean isPostExist(int postId) {
        String sql = "SELECT 1 FROM Posts WHERE post_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Xóa bài viết
    public boolean deletePost(int postId) {
        try {
            conn.setAutoCommit(false);
            
            // Xóa các bình luận của bài viết
            String deleteCommentsSql = "DELETE FROM Comments WHERE post_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteCommentsSql)) {
                ps.setInt(1, postId);
                ps.executeUpdate();
            }
            
            // Xóa các reaction của bài viết
            String deleteReactionsSql = "DELETE FROM PostReactions WHERE post_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteReactionsSql)) {
                ps.setInt(1, postId);
                ps.executeUpdate();
            }
            
            // Xóa các media của bài viết (nếu có)
            String deleteMediaSql = "DELETE FROM PostMedia WHERE post_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteMediaSql)) {
                ps.setInt(1, postId);
                ps.executeUpdate();
            }
            
            // Xóa bài viết
            String deletePostSql = "DELETE FROM Posts WHERE post_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deletePostSql)) {
                ps.setInt(1, postId);
                int result = ps.executeUpdate();
                
                conn.commit();
                return result > 0;
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    // Kiểm tra người dùng đã thích bài viết chưa
    public boolean hasUserLikedPost(int accountId, int postId) {
        String sql = "SELECT 1 FROM PostReactions WHERE post_id = ? AND account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.setInt(2, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Thêm lượt thích vào bài viết
    public boolean likePost(int accountId, int postId) {
        String sql = "INSERT INTO PostReactions (post_id, account_id, reaction_type, created_date) VALUES (?, ?, 1, GETDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.setInt(2, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Hủy lượt thích bài viết
    public boolean unlikePost(int accountId, int postId) {
        String sql = "DELETE FROM PostReactions WHERE post_id = ? AND account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.setInt(2, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Lấy số lượt thích của bài viết
    public int getPostLikeCount(int postId) {
        String sql = "SELECT COUNT(*) AS like_count FROM PostReactions WHERE post_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("like_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Tăng lượt xem bài viết
    public void incrementViewCount(int postId) {
        String sql = "UPDATE Posts SET view_count = view_count + 1 WHERE post_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 