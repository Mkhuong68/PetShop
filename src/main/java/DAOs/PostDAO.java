package DAOs;

import Model.Post;
import Model.Comment;
import Model.PostStatus;
import DB.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author THANH THAO
 */
public class PostDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    private CommentDAO commentDAO = new CommentDAO();

    // Lấy danh sách tất cả bài viết
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM Posts WHERE is_hidden = 0 ORDER BY created_date DESC";

        try ( Connection conn = DBConnection.getConnection();  
              PreparedStatement stmt = conn.prepareStatement(sql);  
              ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setAccountId(rs.getInt("author_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setStatusId(rs.getInt("status_id"));
                post.setCreatedDate(rs.getTimestamp("created_date"));
                
                // Đặt các trường bổ sung nếu có trong database
                try {
                    post.setPostImage(rs.getString("post_image"));
                    post.setFeelingActivity(rs.getString("feeling_activity"));
                    post.setLastUpdated(rs.getTimestamp("last_updated"));
                    post.setViewCount(rs.getInt("view_count"));
                    post.setHidden(rs.getBoolean("is_hidden"));
                } catch (SQLException e) {
                    // Bỏ qua nếu trường không tồn tại
                }
                
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
    
    // Lấy danh sách tất cả bài viết với thông tin tương tác cho người dùng đã đăng nhập
    public List<Post> getAllPosts(int accountId) {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT p.post_id, p.title, p.content, p.author_id, p.status_id, "
                + "p.created_date, p.last_updated, p.view_count, p.is_hidden, "
                + "p.post_image, p.feeling_activity, "
                + "a.username AS author_name, a.profile_image AS author_avatar, "
                + "(SELECT COUNT(*) FROM PostReactions WHERE post_id = p.post_id) AS reaction_count, "
                + "(SELECT COUNT(*) FROM Comments WHERE post_id = p.post_id) AS comment_count, "
                + "(SELECT COUNT(*) FROM PostReactions WHERE post_id = p.post_id AND account_id = ?) AS user_reacted, "
                + "(SELECT reaction_type FROM PostReactions WHERE post_id = p.post_id AND account_id = ?) AS user_reaction_type "
                + "FROM Posts p "
                + "INNER JOIN Account a ON p.author_id = a.account_id "
                + "WHERE p.is_hidden = 0 AND p.status_id = (SELECT status_id FROM PostStatus WHERE status_name = 'Accept') "
                + "ORDER BY p.created_date DESC";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, accountId);
            ps.setInt(2, accountId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setAuthorId(rs.getInt("author_id"));
                post.setAuthorName(rs.getString("author_name"));
                post.setAuthorAvatar(rs.getString("author_avatar"));
                post.setStatusId(rs.getInt("status_id"));
                post.setCreatedDate(rs.getTimestamp("created_date"));
                post.setLastUpdated(rs.getTimestamp("last_updated"));
                post.setViewCount(rs.getInt("view_count"));
                post.setHidden(rs.getBoolean("is_hidden"));
                post.setPostImage(rs.getString("post_image"));
                post.setFeelingActivity(rs.getString("feeling_activity"));
                post.setReactionCount(rs.getInt("reaction_count"));
                post.setCommentCount(rs.getInt("comment_count"));
                post.setUserReacted(rs.getInt("user_reacted") > 0);
                post.setUserReactionType(rs.getInt("user_reaction_type") != 0 ? rs.getInt("user_reaction_type") : 0);

                // Lấy danh sách bình luận cho từng bài viết (giới hạn 3 bình luận)
                List<Comment> topComments = commentDAO.getTopCommentsForPost(post.getPostId(), 3);
                post.setComments(topComments);

                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return posts;
    }

    // Lấy bài viết theo ID với thông tin tương tác
    public Post getPostById(int postId, int accountId) {
        Post post = null;
        String query = "SELECT p.post_id, p.title, p.content, p.author_id, p.status_id, "
                + "p.created_date, p.last_updated, p.view_count, p.is_hidden, "
                + "p.post_image, p.feeling_activity, "
                + "a.username AS author_name, a.profile_image AS author_avatar, "
                + "(SELECT COUNT(*) FROM PostReactions WHERE post_id = p.post_id) AS reaction_count, "
                + "(SELECT COUNT(*) FROM Comments WHERE post_id = p.post_id) AS comment_count, "
                + "(SELECT COUNT(*) FROM PostReactions WHERE post_id = p.post_id AND account_id = ?) AS user_reacted, "
                + "(SELECT reaction_type FROM PostReactions WHERE post_id = p.post_id AND account_id = ?) AS user_reaction_type "
                + "FROM Posts p "
                + "INNER JOIN Account a ON p.author_id = a.account_id "
                + "WHERE p.post_id = ? AND p.is_hidden = 0";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, accountId);
            ps.setInt(2, accountId);
            ps.setInt(3, postId);
            rs = ps.executeQuery();
            if (rs.next()) {
                post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setAuthorId(rs.getInt("author_id"));
                post.setAuthorName(rs.getString("author_name"));
                post.setAuthorAvatar(rs.getString("author_avatar"));
                post.setStatusId(rs.getInt("status_id"));
                post.setCreatedDate(rs.getTimestamp("created_date"));
                post.setLastUpdated(rs.getTimestamp("last_updated"));
                post.setViewCount(rs.getInt("view_count"));
                post.setHidden(rs.getBoolean("is_hidden"));
                post.setPostImage(rs.getString("post_image"));
                post.setFeelingActivity(rs.getString("feeling_activity"));
                post.setReactionCount(rs.getInt("reaction_count"));
                post.setCommentCount(rs.getInt("comment_count"));
                post.setUserReacted(rs.getInt("user_reacted") > 0);
                post.setUserReactionType(rs.getInt("user_reaction_type") != 0 ? rs.getInt("user_reaction_type") : 0);

                // Lấy tất cả bình luận cho bài viết
                List<Comment> allComments = commentDAO.getAllCommentsForPost(postId);
                post.setComments(allComments);

                // Tăng lượt xem của bài viết
                incrementViewCount(postId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return post;
    }

    // Lấy tất cả bài viết dành cho khách chưa đăng nhập
    public List<Post> getAllPostsForGuest() {
        List<Post> posts = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT p.post_id, p.content, p.post_image, p.created_date, p.last_updated, p.author_id, " +
                        "p.status_id, p.feeling_activity, ps.status_name, " +
                        "a.username AS author_name, a.profile_image AS author_avatar, " +
                        "(SELECT COUNT(*) FROM PostReactions pr WHERE pr.post_id = p.post_id) AS reaction_count, " +
                        "(SELECT COUNT(*) FROM Comments c WHERE c.post_id = p.post_id) AS comment_count " +
                        "FROM Posts p " +
                        "INNER JOIN Account a ON p.author_id = a.account_id " +
                        "INNER JOIN PostStatus ps ON p.status_id = ps.status_id " +
                        "WHERE p.status_id = 1 " + // Lấy các bài đăng có trạng thái Public
                        "ORDER BY p.created_date DESC";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            Map<Integer, Post> postMap = new HashMap<>();
            
            while (rs.next()) {
                int postId = rs.getInt("post_id");
                
                Post post = new Post();
                post.setPostId(postId);
                post.setContent(rs.getString("content"));
                post.setPostImage(rs.getString("post_image"));
                post.setCreatedDate(rs.getTimestamp("created_date"));
                post.setLastUpdated(rs.getTimestamp("last_updated"));
                post.setAuthorId(rs.getInt("author_id"));
                post.setAuthorName(rs.getString("author_name"));
                post.setAuthorAvatar(rs.getString("author_avatar"));
                post.setFeelingActivity(rs.getString("feeling_activity"));
                
                // Thông tin trạng thái bài đăng
                PostStatus status = new PostStatus();
                status.setStatusId(rs.getInt("status_id"));
                status.setStatusName(rs.getString("status_name"));
                post.setStatusId(status.getStatusId());
                
                // Thông tin lượt tương tác và bình luận
                post.setReactionCount(rs.getInt("reaction_count"));
                post.setCommentCount(rs.getInt("comment_count"));
                
                // Khách không đăng nhập không thể tương tác với bài đăng
                post.setUserReacted(false);
                post.setUserReactionType(0);
                
                postMap.put(postId, post);
            }
            
            // Lấy các bình luận cho mỗi bài đăng (3 bình luận đầu tiên)
            for (Post post : postMap.values()) {
                List<Comment> comments = commentDAO.getTopCommentsForPost(post.getPostId(), 3);
                post.setComments(comments);
                posts.add(post);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return posts;
    }

    // Tăng lượt xem của bài viết
    private void incrementViewCount(int postId) {
        String query = "UPDATE Posts SET view_count = view_count + 1 WHERE post_id = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    // Duyệt bài viết (Accept Post)
    public boolean acceptPost(int postId) {
        String sql = "UPDATE Posts SET status_id = 1 WHERE post_id = ?"; // 1 = Accepted

        try ( Connection conn = DBConnection.getConnection();  
              PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa bài viết (Ẩn thay vì xóa cứng)
    public boolean deletePost(int postId) {
        String sql = "UPDATE Posts SET is_hidden = 1 WHERE post_id = ?";

        try ( Connection conn = DBConnection.getConnection();  
              PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Đóng tài nguyên
    private void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}