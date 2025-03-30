/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import DB.DBConnection;
import Model.ProductPromotion;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author THANH THAO
 */

public class ProductPromotionDAO {

    private Connection conn;

    public ProductPromotionDAO() {
        this.conn = DBConnection.getConnection();
    }

    // Lấy danh sách tên sản phẩm
    public List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();
        String sql = "SELECT product_name FROM Products WHERE is_hidden = 0";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                productNames.add(rs.getString("product_name"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy danh sách tên sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return productNames;
    }

    // Lấy danh sách sản phẩm kèm giá gốc
    public List<ProductPromotion> getProductsWithPrice() {
        List<ProductPromotion> products = new ArrayList<>();
        String sql = "SELECT product_id, product_name, product_price FROM Products WHERE is_hidden = 0";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProductPromotion product = new ProductPromotion();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setOriginalPrice(rs.getBigDecimal("product_price"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy danh sách sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    // Chuyển đổi product_name thành product_id
    public int getProductIdByName(String productName) {
        String sql = "SELECT product_id FROM Products WHERE product_name = ? AND is_hidden = 0";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("product_id");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy productId theo tên: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    // Tạo bản ghi mới trong bảng Promotions và trả về promotion_id
    public int createPromotion(int discountPercentage, int createdBy) {
        String sql = "INSERT INTO Promotions (promotion_name, promotion_discount, promotion_valid_from, promotion_valid_to, priority, created_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY() AS promotion_id";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Khuyến mãi " + discountPercentage + "%");
            stmt.setInt(2, discountPercentage);
            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Ngày bắt đầu: hôm nay
            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)); // Ngày kết thúc: 30 ngày sau
            stmt.setInt(5, 1); // Priority mặc định
            stmt.setInt(6, createdBy); // created_by (giả sử là admin, account_id = 1)
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("promotion_id");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi tạo khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    // Thêm khuyến mãi vào bảng ProductPromotions
    public boolean addPromotion(ProductPromotion promotion) {
        String sql = "INSERT INTO ProductPromotions (product_id, promotion_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, promotion.getProductId());
            stmt.setInt(2, promotion.getPromotionId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Thêm khuyến mãi thành công: product_id=" + promotion.getProductId() + ", promotion_id=" + promotion.getPromotionId());
                return true;
            } else {
                System.out.println("Thêm khuyến mãi thất bại: Không có hàng nào được thêm.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi thêm khuyến mãi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách khuyến mãi để hiển thị
    public List<ProductPromotion> getAllPromotions() {
        List<ProductPromotion> promotions = new ArrayList<>();
        String sql = "SELECT pp.product_id, pp.promotion_id, p.product_name, p.product_price, p.product_image, pr.promotion_name, pr.promotion_discount " +
                     "FROM ProductPromotions pp " +
                     "JOIN Products p ON pp.product_id = p.product_id " +
                     "JOIN Promotions pr ON pp.promotion_id = pr.promotion_id " +
                     "WHERE p.is_hidden = 0";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProductPromotion promotion = new ProductPromotion();
                promotion.setProductId(rs.getInt("product_id"));
                promotion.setPromotionId(rs.getInt("promotion_id"));
                promotion.setProductName(rs.getString("product_name"));
                promotion.setProductImage(rs.getString("product_image"));
                promotion.setOriginalPrice(rs.getBigDecimal("product_price"));
                promotion.setPromotionName(rs.getString("promotion_name"));
                promotion.setDiscountPercentage(rs.getInt("promotion_discount"));

                // Tính giá sau khuyến mãi
                BigDecimal originalPrice = promotion.getOriginalPrice();
                int discountPercentage = promotion.getDiscountPercentage();
                BigDecimal discountAmount = originalPrice.multiply(BigDecimal.valueOf(discountPercentage)).divide(BigDecimal.valueOf(100));
                BigDecimal discountedPrice = originalPrice.subtract(discountAmount);
                promotion.setDiscountedPrice(discountedPrice);

                promotions.add(promotion);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy danh sách khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }
        return promotions;
    }

    // Cập nhật khuyến mãi
    public boolean updatePromotion(ProductPromotion promotion) {
        String sql = "UPDATE Promotions SET promotion_discount = ? WHERE promotion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, promotion.getDiscountPercentage());
            stmt.setInt(2, promotion.getPromotionId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi cập nhật khuyến mãi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khuyến mãi
    public boolean deletePromotion(int productId, int promotionId) {
        String sql = "DELETE FROM ProductPromotions WHERE product_id = ? AND promotion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, promotionId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi xóa khuyến mãi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin khuyến mãi để chỉnh sửa
    public ProductPromotion getPromotionById(int productId, int promotionId) {
        String sql = "SELECT pp.product_id, pp.promotion_id, p.product_name, p.product_price, p.product_image, pr.promotion_name, pr.promotion_discount " +
                     "FROM ProductPromotions pp " +
                     "JOIN Products p ON pp.product_id = p.product_id " +
                     "JOIN Promotions pr ON pp.promotion_id = pr.promotion_id " +
                     "WHERE pp.product_id = ? AND pp.promotion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, promotionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ProductPromotion promotion = new ProductPromotion();
                promotion.setProductId(rs.getInt("product_id"));
                promotion.setPromotionId(rs.getInt("promotion_id"));
                promotion.setProductName(rs.getString("product_name"));
                promotion.setProductImage(rs.getString("product_image"));
                promotion.setOriginalPrice(rs.getBigDecimal("product_price"));
                promotion.setPromotionName(rs.getString("promotion_name"));
                promotion.setDiscountPercentage(rs.getInt("promotion_discount"));

                // Tính giá sau khuyến mãi
                BigDecimal originalPrice = promotion.getOriginalPrice();
                int discountPercentage = promotion.getDiscountPercentage();
                BigDecimal discountAmount = originalPrice.multiply(BigDecimal.valueOf(discountPercentage)).divide(BigDecimal.valueOf(100));
                BigDecimal discountedPrice = originalPrice.subtract(discountAmount);
                promotion.setDiscountedPrice(discountedPrice);

                return promotion;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy thông tin khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Lấy thông tin khuyến mãi cho một sản phẩm cụ thể
    public ProductPromotion getPromotionForProduct(int productId) {
        String sql = "SELECT pp.product_id, pp.promotion_id, p.product_price, pr.promotion_discount, pr.promotion_valid_from, pr.promotion_valid_to " +
                     "FROM ProductPromotions pp " +
                     "JOIN Products p ON pp.product_id = p.product_id " +
                     "JOIN Promotions pr ON pp.promotion_id = pr.promotion_id " +
                     "WHERE pp.product_id = ? AND p.is_hidden = 0";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ProductPromotion promotion = new ProductPromotion();
                promotion.setProductId(rs.getInt("product_id"));
                promotion.setPromotionId(rs.getInt("promotion_id"));
                promotion.setOriginalPrice(rs.getBigDecimal("product_price"));
                promotion.setDiscountPercentage(rs.getInt("promotion_discount"));
                promotion.setPromotionValidFrom(rs.getDate("promotion_valid_from"));
                promotion.setPromotionValidTo(rs.getDate("promotion_valid_to"));

                // Tính giá sau khuyến mãi
                BigDecimal originalPrice = promotion.getOriginalPrice();
                int discountPercentage = promotion.getDiscountPercentage();
                BigDecimal discountAmount = originalPrice.multiply(BigDecimal.valueOf(discountPercentage)).divide(BigDecimal.valueOf(100));
                BigDecimal discountedPrice = originalPrice.subtract(discountAmount);
                promotion.setDiscountedPrice(discountedPrice);

                return promotion;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy thông tin khuyến mãi cho sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}