/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
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
public class ProductDAO {

    public List<Product> getTopProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 4 p.product_id, p.product_name, p.product_description, p.product_price, p.product_image, p.sold_quantity, "
                + "       AVG(CAST(pf.rating AS FLOAT)) as avg_rating "
                + "FROM Products p "
                + "LEFT JOIN OrderDetails od ON p.product_id = od.product_id "
                + "LEFT JOIN ProductFeedback pf ON od.order_detail_id = pf.order_detail_id "
                + "GROUP BY p.product_id, p.product_name, p.product_description, p.product_price, p.product_image, p.sold_quantity "
                + "ORDER BY p.sold_quantity DESC";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setProductDescription(rs.getString("product_description"));
                p.setProductPrice(rs.getBigDecimal("product_price"));
                p.setProductImage(rs.getString("product_image"));
                p.setSoldQuantity(rs.getInt("sold_quantity"));
                // Nếu avg_rating là null (chưa có phản hồi) thì gán giá trị 0
                double avgRating = rs.getDouble("avg_rating");
                if (rs.wasNull()) {
                    avgRating = 0.0;
                }
                p.setRating(avgRating);
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phiên bản gốc không sắp xếp
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT product_id, product_name, product_description, product_price, product_image, sold_quantity "
                + "FROM Products";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setProductDescription(rs.getString("product_description"));
                p.setProductPrice(rs.getBigDecimal("product_price"));
                p.setProductImage(rs.getString("product_image"));
                p.setSoldQuantity(rs.getInt("sold_quantity"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phiên bản có sắp xếp
    public List<Product> getAllProducts(String sort) {
        List<Product> list = new ArrayList<>();
        // Bao gồm trường created_date để sắp xếp theo "newest"
        StringBuilder sql = new StringBuilder("SELECT product_id, product_name, product_description, product_price, product_image, sold_quantity, created_date FROM Products WHERE 1=1");
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "popularity":
                    sql.append(" ORDER BY sold_quantity DESC");
                    break;
                case "price-asc":
                    sql.append(" ORDER BY product_price ASC");
                    break;
                case "price-desc":
                    sql.append(" ORDER BY product_price DESC");
                    break;
                case "newest":
                    sql.append(" ORDER BY created_date DESC");
                    break;
                default:
                    break;
            }
        }
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql.toString());  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setProductDescription(rs.getString("product_description"));
                p.setProductPrice(rs.getBigDecimal("product_price"));
                p.setProductImage(rs.getString("product_image"));
                p.setSoldQuantity(rs.getInt("sold_quantity"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phiên bản lọc theo danh mục và khoảng giá, không sắp xếp
    public List<Product> getFilteredProducts(String[] categoryIds, String[] priceRanges) {
        List<Product> list = new ArrayList<>();
        // Bắt đầu xây dựng SQL
        StringBuilder sql = new StringBuilder("SELECT product_id, product_name, product_description, product_price, product_image, sold_quantity FROM Products WHERE 1=1 ");

        // Danh sách tham số cho PreparedStatement
        List<Object> params = new ArrayList<>();

        // Nếu lọc theo danh mục
        if (categoryIds != null && categoryIds.length > 0) {
            sql.append(" AND category_id IN (");
            for (int i = 0; i < categoryIds.length; i++) {
                sql.append("?");
                if (i < categoryIds.length - 1) {
                    sql.append(",");
                }
                params.add(Integer.parseInt(categoryIds[i]));
            }
            sql.append(") ");
        }

        // Nếu lọc theo khoảng giá
        if (priceRanges != null && priceRanges.length > 0) {
            // Giả sử sản phẩm cần thỏa mãn một trong các khoảng giá được chọn (dùng OR)
            sql.append(" AND (");
            for (int i = 0; i < priceRanges.length; i++) {
                String[] range = priceRanges[i].split("-");
                sql.append(" (product_price BETWEEN ? AND ?) ");
                params.add(new java.math.BigDecimal(range[0]));
                params.add(new java.math.BigDecimal(range[1]));
                if (i < priceRanges.length - 1) {
                    sql.append(" OR ");
                }
            }
            sql.append(") ");
        }

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Đặt các tham số cho PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setProductDescription(rs.getString("product_description"));
                p.setProductPrice(rs.getBigDecimal("product_price"));
                p.setProductImage(rs.getString("product_image"));
                p.setSoldQuantity(rs.getInt("sold_quantity"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phiên bản lọc theo danh mục, khoảng giá kèm theo sắp xếp
    public List<Product> getFilteredProducts(String[] categoryIds, String[] priceRanges, String sort) {
        List<Product> list = new ArrayList<>();
        // Bao gồm trường created_date để hỗ trợ sắp xếp theo "newest"
        StringBuilder sql = new StringBuilder("SELECT product_id, product_name, product_description, product_price, product_image, sold_quantity, created_date FROM Products WHERE 1=1 ");

        // Danh sách tham số cho PreparedStatement
        List<Object> params = new ArrayList<>();

        // Lọc theo danh mục
        if (categoryIds != null && categoryIds.length > 0) {
            sql.append(" AND category_id IN (");
            for (int i = 0; i < categoryIds.length; i++) {
                sql.append("?");
                if (i < categoryIds.length - 1) {
                    sql.append(",");
                }
                params.add(Integer.parseInt(categoryIds[i]));
            }
            sql.append(") ");
        }

        // Lọc theo khoảng giá
        if (priceRanges != null && priceRanges.length > 0) {
            sql.append(" AND (");
            for (int i = 0; i < priceRanges.length; i++) {
                String[] range = priceRanges[i].split("-");
                sql.append(" (product_price BETWEEN ? AND ?) ");
                params.add(new java.math.BigDecimal(range[0]));
                params.add(new java.math.BigDecimal(range[1]));
                if (i < priceRanges.length - 1) {
                    sql.append(" OR ");
                }
            }
            sql.append(") ");
        }

        // Thêm điều kiện sắp xếp dựa vào tham số sort
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "popularity":
                    sql.append(" ORDER BY sold_quantity DESC");
                    break;
                case "price-asc":
                    sql.append(" ORDER BY product_price ASC");
                    break;
                case "price-desc":
                    sql.append(" ORDER BY product_price DESC");
                    break;
                case "newest":
                    sql.append(" ORDER BY created_date DESC");
                    break;
                default:
                    break;
            }
        }

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Đặt các tham số cho PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setProductDescription(rs.getString("product_description"));
                p.setProductPrice(rs.getBigDecimal("product_price"));
                p.setProductImage(rs.getString("product_image"));
                p.setSoldQuantity(rs.getInt("sold_quantity"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT product_id, product_name, product_description, product_price, product_image, sold_quantity, created_date " +
                     "FROM Products WHERE product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductDescription(rs.getString("product_description"));
                product.setProductPrice(rs.getBigDecimal("product_price"));
                product.setProductImage(rs.getString("product_image"));
                product.setSoldQuantity(rs.getInt("sold_quantity"));
                // Nếu cần, có thể set created_date và rating
                product.setCreatedDate(rs.getTimestamp("created_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
