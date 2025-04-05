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
    public boolean updateProduct(Product product) {
        String sql = "UPDATE Products SET product_name=?, product_description=?, product_price=?, product_image=?, category_id=?, last_updated=GETDATE(), is_hidden=?, stock_quantity=?, sold_quantity=? WHERE product_id=?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getProductDescription());
            ps.setBigDecimal(3, product.getProductPrice());
            ps.setString(4, product.getProductImage());
            ps.setInt(5, product.getCategoryId());

            // Nếu stock_quantity = 0, set is_hidden = 1 (ẩn sản phẩm)
            boolean isHidden = product.getStockQuantity() == 0;
            ps.setBoolean(6, isHidden); // Cập nhật is_hidden

            ps.setInt(7, product.getStockQuantity());

            // Kiểm tra nếu sold_quantity > stock_quantity, nếu vượt quá thì giới hạn lại
            if (product.getSoldQuantity() > product.getStockQuantity()) {
                product.setSoldQuantity(product.getStockQuantity()); // Giới hạn sold_quantity
            }

            ps.setInt(8, product.getSoldQuantity());
            ps.setInt(9, product.getProductId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addQuantityProductByName(String productName, int quantity) throws SQLException {
        String sql = "UPDATE Products \n"
                + "SET stock_quantity = stock_quantity + ? \n"
                + "WHERE product_name = ?;";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);       // Đặt quantity vào vị trí đầu tiên
            ps.setString(2, productName); // Đặt productName vào vị trí thứ hai

            int rowsUpdated = ps.executeUpdate(); // Thực thi câu lệnh SQL
            return rowsUpdated > 0; // Nếu có dòng nào được cập nhật thì trả về true
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
