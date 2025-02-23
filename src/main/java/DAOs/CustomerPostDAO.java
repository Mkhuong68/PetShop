/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.Comment;
import Model.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class CustomerPostDAO {

    Connection conn = DBConnection.getConnection();

    public List<Comment> getCommentsByPostId(int postId) {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT * FROM Comments WHERE post_id = ? ORDER BY created_date DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getInt("comment_id"),
                        rs.getInt("post_id"),
                        rs.getInt("account_id"),
                        rs.getString("content"),
                        rs.getTimestamp("created_date")
                );
                list.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addComment(Comment c) {
        String sql = "INSERT INTO comments (post_id, account_id, content, created_date) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, c.getPostId());
            ps.setInt(2, c.getAccountId());
            ps.setString(3, c.getContent());
            ps.setTimestamp(4, c.getCreatedDate());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Post> getAllPost() {
        List<Post> list = new ArrayList<>();
        String sql = "select * from Posts order by created_date desc";
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
                        rs.getTimestamp("created_date"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Post getPostbyId(int postId) {
        Post p = new Post();
        try {
            String sql = "SELECT * FROM Posts WHERE post_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setPostId(rs.getInt("post_id"));
                p.setTitle(rs.getString("title"));
                p.setContent(rs.getString("content"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public boolean isPostOwner(int postId, int accountId) {
        String sql = "SELECT * FROM posts WHERE post_id = ? AND author_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setInt(2, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getAccountId(String username) {
        int id = -1;
        try {
            String sql = "SELECT account_id FROM Account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("account_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void UpdatePost(Post p) {
        String sql = " update posts set title=?, content=?, last_updated=? where post_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getContent());
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis())); 
            ps.setInt(4, p.getPostId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean addPost(Post p) {
        String sql = "insert into posts (title, content, author_id, status_id, created_date) values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getContent());
            ps.setInt(3, p.getAccountId());
            ps.setInt(4, p.getStatusId());
            ps.setTimestamp(5, p.getCreatedDate());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
}
