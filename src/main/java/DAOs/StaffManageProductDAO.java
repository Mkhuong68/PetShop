package DAOs;

import DB.DBConnection;
import Model.Product;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffManageProductDAO {

    private final Connection conn;

    public StaffManageProductDAO() {
        this.conn = new DBConnection().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("LỖI: Không thể kết nối đến database!");
        }
    }

    // Lấy tất cả sản phẩm từ database
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products ORDER BY created_date DESC";
        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("product_description"),
                        rs.getBigDecimal("product_price"),
                        rs.getString("product_image"),
                        rs.getInt("category_id"),
                        rs.getDate("created_date"),
                        rs.getDate("last_updated"),
                        rs.getBoolean("is_hidden"),
                        rs.getInt("stock_quantity"),
                        rs.getInt("sold_quantity"),
                        rs.getInt("views")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Lấy thông tin sản phẩm theo ID
    public Product getProductById(int id) {
        String sql = "SELECT * FROM Products WHERE product_id = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("product_description"),
                            rs.getBigDecimal("product_price"),
                            rs.getString("product_image"),
                            rs.getInt("category_id"),
                            rs.getDate("created_date"),
                            rs.getDate("last_updated"),
                            rs.getBoolean("is_hidden"),
                            rs.getInt("stock_quantity"),
                            rs.getInt("sold_quantity"),
                            rs.getInt("views")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

    // Thêm sản phẩm mới
    public void addProduct(Product product) {
        String sql = "INSERT INTO Products (product_name, product_description, product_price, product_image, category_id, created_date, last_updated, is_hidden, stock_quantity, sold_quantity, views) "
                + "VALUES (?, ?, ?, ?, ?, GETDATE(), GETDATE(), ?, ?, ?, ?)";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getProductDescription());
            ps.setBigDecimal(3, product.getProductPrice());
            ps.setString(4, product.getProductImage());
            ps.setInt(5, product.getCategoryId());
            ps.setBoolean(6, product.isIsHidden());
            ps.setInt(7, product.getStockQuantity());
            ps.setInt(8, product.getSoldQuantity());
            ps.setInt(9, product.getViews());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật sản phẩm
    public void updateProduct(Product product) {
        String sql = "UPDATE Products SET product_name=?, product_description=?, product_price=?, product_image=?, category_id=?, last_updated=GETDATE(), is_hidden=?, stock_quantity=? WHERE product_id=?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getProductDescription());
            ps.setBigDecimal(3, product.getProductPrice());
            ps.setString(4, product.getProductImage());
            ps.setInt(5, product.getCategoryId());
            ps.setBoolean(6, product.isIsHidden());
            ps.setInt(7, product.getStockQuantity());
            ps.setInt(8, product.getProductId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa sản phẩm
    public void deleteProduct(int productId) {
        String sql = "DELETE FROM Products WHERE product_id=?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
