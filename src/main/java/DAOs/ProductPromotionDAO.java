/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Model.ProductPromotion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author THANH THAO
 */

public class ProductPromotionDAO {

    private Connection connection;

    public ProductPromotionDAO() {
        try {
            // Kết nối với cơ sở dữ liệu
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách các ProductPromotions
    public List<ProductPromotion> getAllProductPromotions() {
        List<ProductPromotion> productPromotions = new ArrayList<>();
        String sql = "SELECT p.product_id, p.product_name, p.product_price, pr.promotion_id, pr.promotion_discount " +
                     "FROM Products p " +
                     "INNER JOIN ProductPromotions pp ON p.product_id = pp.product_id " +
                     "INNER JOIN Promotions pr ON pp.promotion_id = pr.promotion_id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                double productPrice = rs.getDouble("product_price");
                int promotionId = rs.getInt("promotion_id");
                int promotionDiscount = rs.getInt("promotion_discount");

                // Tính giá giảm từ giá gốc và tỷ lệ khuyến mãi
                double discountedPrice = productPrice - (productPrice * promotionDiscount / 100);

                // Tạo đối tượng ProductPromotion với các giá trị đã lấy
                ProductPromotion productPromotion = new ProductPromotion(productId, promotionId, productPrice, discountedPrice);
                productPromotions.add(productPromotion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productPromotions;
    }

    // Thêm một ProductPromotion vào cơ sở dữ liệu
    public boolean addProductPromotion(ProductPromotion productPromotion) {
        String sql = "INSERT INTO ProductPromotions (product_id, promotion_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productPromotion.getProductId());
            stmt.setInt(2, productPromotion.getPromotionId());
            return stmt.executeUpdate() > 0; // Nếu thành công, trả về true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nếu không thành công, trả về false
    }

    // Xóa một ProductPromotion khỏi cơ sở dữ liệu
    public boolean deleteProductPromotion(int productId, int promotionId) {
        String sql = "DELETE FROM ProductPromotions WHERE product_id = ? AND promotion_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, promotionId);
            return stmt.executeUpdate() > 0; // Nếu thành công, trả về true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nếu không thành công, trả về false
    }
}
