/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAOs;
import Model.Category;
import DB.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */

public class CategoryDAO {

    // Lấy danh sách tất cả danh mục
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("category_description"),
                    rs.getObject("parent_category_id") != null ? rs.getInt("parent_category_id") : null,
                    rs.getString("category_image"),
                    rs.getBoolean("is_hidden"),
                    rs.getTimestamp("created_date"),
                    rs.getTimestamp("last_updated")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Lấy danh mục theo ID
    public Category getCategoryById(int categoryId) {
        Category category = null;
        String sql = "SELECT * FROM Categories WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                category = new Category(
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("category_description"),
                    rs.getObject("parent_category_id") != null ? rs.getInt("parent_category_id") : null,
                    rs.getString("category_image"),
                    rs.getBoolean("is_hidden"),
                    rs.getTimestamp("created_date"),
                    rs.getTimestamp("last_updated")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    // Thêm danh mục mới
   public boolean insertCategory(Category category) {
    String sql = "INSERT INTO Categories (category_name, category_description, parent_category_id, category_image, is_hidden, created_date) "
               + "VALUES (?, ?, ?, ?, ?, GETDATE())";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, category.getCategoryName());
        ps.setString(2, category.getCategoryDescription());
        ps.setObject(3, category.getParentCategoryId());
        ps.setString(4, category.getCategoryImage());
        ps.setBoolean(5, category.isHidden());

        int rowsInserted = ps.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Category added successfully.");
        }
        return rowsInserted > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    // Cập nhật danh mục
    public boolean updateCategory(Category category) {
        String sql = "UPDATE Categories SET category_name = ?, category_description = ?, parent_category_id = ?, category_image = ?, is_hidden = ?, last_updated = GETDATE() "
                   + "WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getCategoryDescription());
            ps.setObject(3, category.getParentCategoryId());
            ps.setString(4, category.getCategoryImage());
            ps.setBoolean(5, category.isHidden());
            ps.setInt(6, category.getCategoryId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa danh mục
    public boolean deleteCategory(int categoryId) {
        String sql = "DELETE FROM Categories WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
