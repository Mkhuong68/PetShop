package DAOs;
/**
 *
 * @author THANH THAO
 */
import Model.Promotion;
import DB.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                promotions.add(new Promotion(
                        rs.getInt("promotion_id"),
                        rs.getString("promotion_name"),
                        rs.getString("promotion_image"),
                        rs.getString("promotion_description"),
                        rs.getInt("promotion_discount"),
                        rs.getDate("promotion_valid_from").toLocalDate(),
                        rs.getDate("promotion_valid_to").toLocalDate(),
                        rs.getInt("priority"),
                        rs.getInt("created_by"),
                        rs.getBoolean("is_hidden"),
                        rs.getTimestamp("created_date").toLocalDateTime(),
                        rs.getTimestamp("last_updated") != null ? rs.getTimestamp("last_updated").toLocalDateTime() : null
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
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, promotionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Promotion(
                        rs.getInt("promotion_id"),
                        rs.getString("promotion_name"),
                        rs.getString("promotion_image"),
                        rs.getString("promotion_description"),
                        rs.getInt("promotion_discount"),
                        rs.getDate("promotion_valid_from").toLocalDate(),
                        rs.getDate("promotion_valid_to").toLocalDate(),
                        rs.getInt("priority"),
                        rs.getInt("created_by"),
                        rs.getBoolean("is_hidden"),
                        rs.getTimestamp("created_date").toLocalDateTime(),
                        rs.getTimestamp("last_updated") != null ? rs.getTimestamp("last_updated").toLocalDateTime() : null
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, promotion.getPromotionName());
            ps.setString(2, promotion.getPromotionImage());
            ps.setString(3, promotion.getDescription());
            ps.setInt(4, promotion.getDiscountRate());
            ps.setDate(5, Date.valueOf(promotion.getStartDate()));
            ps.setDate(6, Date.valueOf(promotion.getEndDate()));
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
        String sql = "UPDATE Promotions SET promotion_name = ?, promotion_image = ?, promotion_description = ?, " +
                     "promotion_discount = ?, promotion_valid_from = ?, promotion_valid_to = ?, priority = ?, " +
                     "last_updated = GETDATE() WHERE promotion_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, promotion.getPromotionName());
            ps.setString(2, promotion.getPromotionImage());
            ps.setString(3, promotion.getDescription());
            ps.setInt(4, promotion.getDiscountRate());
            ps.setDate(5, Date.valueOf(promotion.getStartDate()));
            ps.setDate(6, Date.valueOf(promotion.getEndDate()));
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, promotionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi ẩn khuyến mãi", e);
            return false;
        }
    }
}
