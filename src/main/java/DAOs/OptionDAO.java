/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.Option;
import Model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tvhun
 */
 public class OptionDAO {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }

    // Phương thức lấy danh sách sản phẩm cho dropdown
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT product_id, product_name FROM Products WHERE is_hidden = 0 ORDER BY product_name";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                list.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Error getting products: " + e.getMessage());
        } finally {
            closeResources();
        }
        return list;
    }

    // Lấy danh sách tất cả options
    public List<Option> getAllOptions() {
        List<Option> list = new ArrayList<>();
        String query = "SELECT o.option_id, o.option_name, o.option_description, " +
                      "o.option_price, o.product_id, o.is_hidden, " +
                      "o.created_date, o.last_updated, p.product_name " +
                      "FROM Options o " +
                      "LEFT JOIN Products p ON o.product_id = p.product_id " +
                      "ORDER BY o.created_date DESC";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToOption(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all options: " + e.getMessage());
        } finally {
            closeResources();
        }
        return list;
    }

    // Lấy option theo ID
    public Option getOptionById(int optionId) {
        String query = "SELECT o.option_id, o.option_name, o.option_description, " +
                      "o.option_price, o.product_id, o.is_hidden, " +
                      "o.created_date, o.last_updated, p.product_name " +
                      "FROM Options o " +
                      "LEFT JOIN Products p ON o.product_id = p.product_id " +
                      "WHERE o.option_id = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, optionId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToOption(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error getting option by ID: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    // Thêm option mới
    public boolean insertOption(Option option) {
        String query = "INSERT INTO Options (option_name, option_description, option_price, " +
                      "product_id, is_hidden, created_date, last_updated) " +
                      "VALUES (?, ?, ?, ?, ?, GETDATE(), GETDATE())";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, option.getOptionName());
            ps.setString(2, option.getOptionDescription());
            ps.setDouble(3, option.getOptionPrice());
            ps.setInt(4, option.getProductId());
            ps.setBoolean(5, option.isHidden());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting option: " + e.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    // Cập nhật option
    public boolean updateOption(Option option) {
        String query = "UPDATE Options SET " +
                      "option_name = ?, " +
                      "option_description = ?, " +
                      "option_price = ?, " +
                      "product_id = ?, " +
                      "is_hidden = ?, " +
                      "last_updated = GETDATE() " +
                      "WHERE option_id = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, option.getOptionName());
            ps.setString(2, option.getOptionDescription());
            ps.setDouble(3, option.getOptionPrice());
            ps.setInt(4, option.getProductId());
            ps.setBoolean(5, option.isHidden());
            ps.setInt(6, option.getOptionId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating option: " + e.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    // Lấy options theo sản phẩm
    public List<Option> getOptionsByProductId(int productId) {
        List<Option> list = new ArrayList<>();
        String query = "SELECT o.option_id, o.option_name, o.option_description, " +
                      "o.option_price, o.product_id, o.is_hidden, " +
                      "o.created_date, o.last_updated, p.product_name " +
                      "FROM Options o " +
                      "LEFT JOIN Products p ON o.product_id = p.product_id " +
                      "WHERE o.product_id = ? " +
                      "ORDER BY o.created_date DESC";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToOption(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting options by product ID: " + e.getMessage());
        } finally {
            closeResources();
        }
        return list;
    }

    
    private Option mapResultSetToOption(ResultSet rs) throws SQLException {
        Option option = new Option();
        option.setOptionId(rs.getInt("option_id"));
        option.setOptionName(rs.getString("option_name"));
        option.setOptionDescription(rs.getString("option_description"));
        option.setOptionPrice(rs.getDouble("option_price"));
        option.setProductId(rs.getInt("product_id"));
        option.setHidden(rs.getBoolean("is_hidden"));
        option.setCreatedDate(rs.getTimestamp("created_date"));
        option.setLastUpdated(rs.getTimestamp("last_updated"));
        option.setProductName(rs.getString("product_name"));
        return option;
    }
    public boolean toggleOptionStatus(int optionId, boolean newStatus) {
    String query = "UPDATE Options SET is_hidden = ?, last_updated = GETDATE() WHERE option_id = ?";
    try {
        conn = DBConnection.getConnection();
        ps = conn.prepareStatement(query);
        ps.setBoolean(1, newStatus);
        ps.setInt(2, optionId);
        
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Error toggling option status: " + e.getMessage());
        return false;
    } finally {
        closeResources();
    }
}
 }