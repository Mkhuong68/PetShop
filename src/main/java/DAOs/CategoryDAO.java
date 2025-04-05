package DAOs;

import DB.DBConnection;
import Model.Category;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    Connection conn = DBConnection.getConnection();

    public CategoryDAO(Connection conn) {
        this.conn = conn;
        if (this.conn != null) {
            System.out.println("Database connection established successfully.");
        } else {
            System.out.println("Database connection FAILED!");
        }
    }

    public CategoryDAO() {
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    public List<Category> getAllCategoriesHavePrudct() {
        List<Category> categories = new ArrayList<>();
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement("select * from Categories c \n"
                + "join Products  p on c.category_id = p.category_id");  ResultSet rs = stmt.executeQuery()) {

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

    public int getCategoryIdByName(String categoryName) throws SQLException {
        String sql = "SELECT category_id FROM Categories WHERE category_name = ?";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int categoryId = rs.getInt("category_id");
                    return categoryId;

                }

            }
        }
        return -1; // Trả về -1 nếu không tìm thấy danh mục
    }
 
    public boolean addCategory(String name, String des, int id) throws SQLException {
        String sql = "INSERT INTO Categories (category_name, category_description, parent_category_id, is_hidden, created_date, last_updated) VALUES (?, ?, ?, 0, GETDATE(), GETDATE())";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, des);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
