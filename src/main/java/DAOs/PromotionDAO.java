package DAOs;
/**
 *
 * @author THANH THAO
 */
import Model.Promotion;
import DB.DBConnection;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PromotionDAO {
    private static final Logger LOGGER = Logger.getLogger(PromotionDAO.class.getName());

    // Lấy danh sách tất cả khuyến mãi
    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM Promotions WHERE is_hidden = 0 ORDER BY created_date DESC";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Timestamp lastUpdatedTs = rs.getTimestamp("last_updated");
                Date lastUpdatedDate = null;
                if (lastUpdatedTs != null) {
                    lastUpdatedDate = new Date(lastUpdatedTs.getTime());
                }
                
                promotions.add(new Promotion(
                        rs.getInt("promotion_id"),
                        rs.getString("promotion_name"),
                        rs.getString("promotion_image"),
                        rs.getString("promotion_description"),
                        rs.getInt("promotion_discount"),
                        rs.getTimestamp("promotion_valid_from"),
                        rs.getTimestamp("promotion_valid_to"),
                        rs.getInt("priority"),
                        rs.getInt("created_by"),
                        rs.getBoolean("is_hidden"),
                        rs.getTimestamp("created_date"),
                        lastUpdatedDate
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách khuyến mãi", e);
        }
        return promotions;
    }

    // Lấy thông tin một khuyến mãi theo ID
    public Promotion getPromotionById(int promotionId) {
        String sql = "SELECT * FROM Promotions WHERE promotion_id = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, promotionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Timestamp lastUpdatedTs = rs.getTimestamp("last_updated");
                Date lastUpdatedDate = null;
                if (lastUpdatedTs != null) {
                    lastUpdatedDate = new Date(lastUpdatedTs.getTime());
                }
                
                return new Promotion(
                        rs.getInt("promotion_id"),
                        rs.getString("promotion_name"),
                        rs.getString("promotion_image"),
                        rs.getString("promotion_description"),
                        rs.getInt("promotion_discount"),
                        rs.getTimestamp("promotion_valid_from"),
                        rs.getTimestamp("promotion_valid_to"),
                        rs.getInt("priority"),
                        rs.getInt("created_by"),
                        rs.getBoolean("is_hidden"),
                        rs.getTimestamp("created_date"),
                        lastUpdatedDate
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy khuyến mãi theo ID", e);
        }
        return null; // Trả về null nếu không tìm thấy khuyến mãi
    }

    // Thêm khuyến mãi mới
    public boolean addPromotion(Promotion promotion) {
        String sql = "INSERT INTO Promotions (promotion_name, promotion_image, promotion_description, promotion_discount, promotion_valid_from, promotion_valid_to, priority, created_by, is_hidden, created_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0, GETDATE())";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, promotion.getPromotionName());
            ps.setString(2, promotion.getPromotionImage());
            ps.setString(3, promotion.getPromotionDescription());
            ps.setInt(4, (int) promotion.getPromotionDiscount());
            ps.setTimestamp(5, promotion.getPromotionValidFrom());
            ps.setTimestamp(6, promotion.getPromotionValidTo());
            ps.setInt(7, promotion.getPriority());
            ps.setInt(8, promotion.getCreatedBy());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi thêm khuyến mãi", e);
            return false;
        }
    }

    // Cập nhật khuyến mãi
    public boolean updatePromotion(Promotion promotion) {
        String sql = " UPDATE Promotions SET promotion_name = ?, promotion_image = ?, promotion_description = ?,\n"
                + "                     promotion_discount = ?, promotion_valid_from = ?, promotion_valid_to = ?, priority = ?\n"
                + "                    WHERE promotion_id = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, promotion.getPromotionName());
            ps.setString(2, promotion.getPromotionImage());
            ps.setString(3, promotion.getPromotionDescription());
            ps.setInt(4, (int) promotion.getPromotionDiscount());
            ps.setTimestamp(5, promotion.getPromotionValidFrom());
            ps.setTimestamp(6, promotion.getPromotionValidTo());
            ps.setInt(7, promotion.getPriority());
            ps.setInt(8, promotion.getPromotionId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi cập nhật khuyến mãi", e);
            return false;
        }
    }

    // Xóa khuyến mãi (ẩn thay vì xóa)
    public boolean deletePromotion(int promotionId) {
        String sql = "UPDATE Promotions SET is_hidden = 1 WHERE promotion_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, promotionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi ẩn khuyến mãi", e);
            return false;
        }
    }
}