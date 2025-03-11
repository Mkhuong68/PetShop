package DAOs;

import DB.DBConnection;
import Model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private Connection conn;

    public CategoryDAO(Connection conn) {
        this.conn = conn;
        if (this.conn != null) {
            System.out.println("Database connection established successfully.");
        } else {
            System.out.println("Database connection FAILED!");
        }
    }

    public CategoryDAO() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Categories");  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("category_description"),
                        rs.getInt("parent_category_id"),
                        rs.getBoolean("is_hidden"),
                        rs.getDate("created_date"),
                        rs.getDate("last_updated")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 🚨 Nếu danh sách rỗng, in ra log để kiểm tra
        if (categories.isEmpty()) {
            System.out.println("⚠ WARNING: No categories found in database.");
        } else {
            System.out.println("✅ Loaded " + categories.size() + " categories from database.");
        }

        return categories;
    }

    public Category getCategoryById(int categoryId) throws SQLException {
        String sql = "SELECT * FROM Categories WHERE category_id = ?";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Category(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            rs.getString("category_description"),
                            rs.getInt("parent_category_id"),
                            rs.getBoolean("is_hidden"),
                            rs.getDate("created_date"),
                            rs.getDate("last_updated")
                    );
                }
            }
        }
        return null;
    }

    public void addCategory(Category category) throws SQLException {
        String sql = "INSERT INTO Categories (category_name, category_description, parent_category_id, is_hidden, created_date, last_updated) VALUES (?, ?, ?, ?, GETDATE(), GETDATE())";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getCategoryDescription());
            stmt.setObject(3, category.getParentCategoryId(), Types.INTEGER);
            stmt.setBoolean(4, category.isIsHidden());
            stmt.executeUpdate();
        }
    }

    public void updateCategory(Category category) {
        String sql = "UPDATE Categories SET category_name = ?, category_description = ?, parent_category_id = ?, is_hidden = ?, last_updated = GETDATE() WHERE category_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getCategoryDescription());
            stmt.setObject(3, category.getParentCategoryId(), java.sql.Types.INTEGER);
            stmt.setBoolean(4, category.isIsHidden());
            stmt.setInt(5, category.getCategoryId());

            stmt.executeUpdate();
            System.out.println("✅ Updated category: " + category.getCategoryName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int categoryId) {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
            System.out.println("✅ Deleted category ID: " + categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
