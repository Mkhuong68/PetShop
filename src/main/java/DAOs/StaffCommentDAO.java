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
import Model.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffCommentDAO {

    private Connection conn = DBConnection.getConnection();

    // Lấy danh sách tất cả bình luận của Customer (role_id = 3)
    public List<Comment> getAllCustomerComments() {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT c.* FROM Comments c "
                + "JOIN Account a ON c.account_id = a.account_id "
                + "WHERE a.role_id = 3 "
                + // Chỉ lấy bình luận của Customer
                "ORDER BY c.created_date DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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

    // Xóa bình luận (Delete Comment)
    public void deleteComment(int commentId) {
        String sql = "DELETE FROM Comments WHERE comment_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, commentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
