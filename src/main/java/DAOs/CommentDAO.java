/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public List<Comment> getAllCommentsForPost(int postId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.*, a.username, a.profile_image FROM Comments c "
                + "JOIN Account a ON c.account_id = a.account_id "
                + "WHERE c.post_id = ? AND c.is_hidden = 0 "
                + "ORDER BY c.created_date ASC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setPostId(rs.getInt("post_id"));
                comment.setAccountId(rs.getInt("account_id"));
                comment.setContent(rs.getString("content"));
                comment.setCreatedDate(rs.getTimestamp("created_date"));
                
                // Thông tin tác giả
                comment.setAuthorName(rs.getString("username"));
                comment.setAuthorProfileImage(rs.getString("profile_image"));
                
                // Các thuộc tính khác
                if (rs.getObject("parent_comment_id") != null) {
                    comment.setParentCommentId(rs.getInt("parent_comment_id"));
                }
                comment.setLastUpdated(rs.getTimestamp("last_updated"));
                comment.setHidden(rs.getBoolean("is_hidden"));
                
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return comments;
    }
    
    public List<Comment> getTopCommentsForPost(int postId, int limit) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.*, a.username, a.profile_image FROM Comments c "
                + "JOIN Account a ON c.account_id = a.account_id "
                + "WHERE c.post_id = ? AND c.is_hidden = 0 "
                + "ORDER BY c.created_date DESC "
                + "OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setPostId(rs.getInt("post_id"));
                comment.setAccountId(rs.getInt("account_id"));
                comment.setContent(rs.getString("content"));
                comment.setCreatedDate(rs.getTimestamp("created_date"));
                
                // Thông tin tác giả
                comment.setAuthorName(rs.getString("username"));
                comment.setAuthorProfileImage(rs.getString("profile_image"));
                
                // Các thuộc tính khác
                if (rs.getObject("parent_comment_id") != null) {
                    comment.setParentCommentId(rs.getInt("parent_comment_id"));
                }
                comment.setLastUpdated(rs.getTimestamp("last_updated"));
                comment.setHidden(rs.getBoolean("is_hidden"));
                
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return comments;
    }
    
    private List<Comment> getRepliesForComment(int parentCommentId) {
        List<Comment> replies = new ArrayList<>();
        
        String query = "SELECT c.comment_id, c.post_id, c.account_id, c.content, c.parent_comment_id, " +
                      "c.created_date, c.last_updated, c.is_hidden, " +
                      "a.username AS author_name, a.profile_image AS author_avatar " +
                      "FROM Comments c " +
                      "INNER JOIN Account a ON c.account_id = a.account_id " +
                      "WHERE c.parent_comment_id = ? AND c.is_hidden = 0 " +
                      "ORDER BY c.created_date ASC";
        try {
            Connection replyConn = DBConnection.getConnection();
            PreparedStatement replyPs = replyConn.prepareStatement(query);
            replyPs.setInt(1, parentCommentId);
            ResultSet replyRs = replyPs.executeQuery();
            
            while (replyRs.next()) {
                Comment reply = new Comment();
                reply.setCommentId(replyRs.getInt("comment_id"));
                reply.setPostId(replyRs.getInt("post_id"));
                reply.setAccountId(replyRs.getInt("account_id"));
                reply.setContent(replyRs.getString("content"));
                reply.setParentCommentId(replyRs.getInt("parent_comment_id"));
                reply.setCreatedDate(replyRs.getTimestamp("created_date"));
                reply.setLastUpdated(replyRs.getTimestamp("last_updated"));
                reply.setHidden(replyRs.getBoolean("is_hidden"));
                reply.setAuthorName(replyRs.getString("author_name"));
                reply.setAuthorAvatar(replyRs.getString("author_avatar"));
                
                replies.add(reply);
            }
            
            // Đóng tài nguyên
            replyRs.close();
            replyPs.close();
            replyConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return replies;
    }
    
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
