/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

/**
 *
 * @author tvhun
 */
public class CartDAO {

    public List<CartItem> getCartItems(int accountId) {
        List<CartItem> list = new ArrayList<>();
        // Ví dụ query kết hợp với bảng Products để lấy tên và hình ảnh sản phẩm
        String sql = "SELECT c.cart_item_id, c.account_id, c.product_id, c.quantity, c.final_price, c.original_price, c.created_date, c.last_updated, p.product_name, p.product_image "
                + "FROM CartItems c JOIN Products p ON c.product_id = p.product_id "
                + "WHERE c.account_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setAccountId(rs.getInt("account_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setFinalPrice(rs.getBigDecimal("final_price"));
                item.setOriginalPrice(rs.getBigDecimal("original_price"));
                item.setCreatedDate(rs.getTimestamp("created_date"));
                item.setLastUpdated(rs.getTimestamp("last_updated"));
                item.setProductName(rs.getString("product_name"));
                item.setProductImage(rs.getString("product_image"));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addCartItem(int accountId, CartItem newItem) {
        String checkSql = "SELECT cart_item_id, quantity FROM CartItems WHERE account_id = ? AND product_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setInt(1, accountId);
            checkPs.setInt(2, newItem.getProductId());
            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                int cartItemId = rs.getInt("cart_item_id");
                int currentQty = rs.getInt("quantity");
                int updatedQty = currentQty + newItem.getQuantity();
                return updateCartItemQuantity(cartItemId, updatedQty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Nếu chưa có, insert mới
        String insertSql = "INSERT INTO CartItems(account_id, product_id, quantity, final_price, original_price, created_date) VALUES(?,?,?,?,?,GETDATE())";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setInt(1, accountId);
            ps.setInt(2, newItem.getProductId());
            ps.setInt(3, newItem.getQuantity());
            ps.setBigDecimal(4, newItem.getFinalPrice());
            ps.setBigDecimal(5, newItem.getOriginalPrice());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật số lượng của một mục giỏ hàng
    public boolean updateCartItemQuantity(int cartItemId, int newQuantity) {
        String sql = "UPDATE CartItems SET quantity = ?, last_updated = GETDATE() WHERE cart_item_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa một mục giỏ hàng
    public boolean deleteCartItem(int cartItemId) {
        String sql = "DELETE FROM CartItems WHERE cart_item_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa toàn bộ mục giỏ hàng của một account
    public boolean deleteAllCartItems(int accountId) {
        String sql = "DELETE FROM CartItems WHERE account_id = ?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Áp dụng voucher (ví dụ demo: trả về discount)
    public BigDecimal applyVoucher(int accountId, String voucherCode) {
        // Logic kiểm tra voucher, tính toán giảm giá, …
        // Demo trả về số tiền giảm (ví dụ: 50,000 VND)
        return new BigDecimal("50000");
    }
    public int getUniqueCartCount(int accountId) {
    String sql = "SELECT COUNT(DISTINCT product_id) FROM CartItems WHERE account_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, accountId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}
}
