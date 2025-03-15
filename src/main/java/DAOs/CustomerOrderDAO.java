/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.CartItem;
import Model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class CustomerOrderDAO {

    Connection conn = DBConnection.getConnection();

    public boolean cancelOrder(int orderId) {
        String sql = "UPDATE Orders SET status_id = 5 WHERE order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getLastInsertedOrderId(int accountId) {
        int orderId = -1;
        String sql = "SELECT TOP 1 order_id \n"
                + "FROM Orders \n"
                + "WHERE account_id = ?\n"
                + "ORDER BY order_date DESC;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                orderId = rs.getInt("order_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    public boolean addOrder(Order o) {
        int orderId = -1;
        String sql = "INSERT INTO orders (account_id, staff_id, voucher_id, order_note, shipping_fee, status_id, order_date, payment_status, payment_method, deliver_to ,last_updated) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o.getAccountId());
            ps.setNull(2, java.sql.Types.INTEGER);
            ps.setInt(3, o.getVoucherId());
            ps.setString(4, o.getOrderNote());
            ps.setDouble(5, o.getShippingFee());
            ps.setInt(6, o.getStatusId());
            ps.setTimestamp(7, o.getOrderDate());
            ps.setBoolean(8, o.getPaymentStatus());
            ps.setString(9, o.getPaymentMethod());
            ps.setString(10, o.getDeliveryAddress());
            ps.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getAccountId(String username) {
        int id = -1;
        try {
            String sql = "SELECT account_id FROM Account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("account_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public CartItem getProductInCart(int cartItemId) {
        String sql = "SELECT c.cart_item_id, c.account_id, c.product_id, c.quantity, c.final_price,\n"
                + "               c.original_price, c.created_date, c.last_updated, p.product_name, p.product_image \n"
                + "                FROM CartItems c JOIN Products p ON c.product_id = p.product_id \n"
                + "                WHERE c.cart_item_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartItemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CartItem c = new CartItem();
                c.setCartItemId(rs.getInt("cart_item_id"));
                c.setAccountId(rs.getInt("account_id"));
                c.setProductId(rs.getInt("product_id"));
                c.setQuantity(rs.getInt("quantity"));
                c.setFinalPrice(rs.getBigDecimal("final_price"));
                c.setOriginalPrice(rs.getBigDecimal("original_price"));
                c.setCreatedDate(rs.getTimestamp("created_date"));
                c.setLastUpdated(rs.getTimestamp("last_updated"));
                c.setProductName(rs.getString("product_name"));
                c.setProductImage(rs.getString("product_image"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> getAllOrderCancelled(int id) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT Account.account_id, Account.username, Orders.order_id, OrderStatus.status_id, OrderStatus.status_name, Orders.shipping_fee, Orders.order_note, Orders.order_date, Vouchers.voucher_id, Orders.payment_status, Orders.payment_method, Orders.deliver_to\n"
                + "                              FROM     Account INNER JOIN\n"
                + "                              Orders ON Account.account_id = Orders.account_id INNER JOIN\n"
                + "                              OrderStatus ON Orders.status_id = OrderStatus.status_id LEFT JOIN\n"
                + "                          Vouchers ON Orders.voucher_id = Vouchers.voucher_id where Orders.account_id = ? AND status_name IN ('Cancelled')\n"
                + "               	   order by Orders.last_updated desc ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("order_id"),
                        rs.getString("username"),
                        rs.getInt("account_id"),
                        rs.getTimestamp("order_date"),
                        rs.getInt("status_id"),
                        rs.getString("status_name"),
                        rs.getString("deliver_to"),
                        rs.getInt("voucher_id"),
                        rs.getBoolean("payment_status"),
                        rs.getString("payment_method"),
                        rs.getDouble("shipping_fee"),
                        rs.getString("order_note"));
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Order> getAllOrderPending(int id) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT Account.account_id, Account.username, Orders.order_id, OrderStatus.status_id, OrderStatus.status_name, Orders.shipping_fee, Orders.order_note, Orders.order_date, Vouchers.voucher_id, Orders.payment_status, Orders.payment_method, Orders.deliver_to\n"
                + "                              FROM     Account INNER JOIN\n"
                + "                              Orders ON Account.account_id = Orders.account_id INNER JOIN\n"
                + "                              OrderStatus ON Orders.status_id = OrderStatus.status_id LEFT JOIN\n"
                + "                          Vouchers ON Orders.voucher_id = Vouchers.voucher_id where Orders.account_id = ? AND status_name NOT IN ('Cancelled', 'Delivered', 'Received')\n"
                + "               	   order by Orders.last_updated desc ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("order_id"),
                        rs.getString("username"),
                        rs.getInt("account_id"),
                        rs.getTimestamp("order_date"),
                        rs.getInt("status_id"),
                        rs.getString("status_name"),
                        rs.getString("deliver_to"),
                        rs.getInt("voucher_id"),
                        rs.getBoolean("payment_status"),
                        rs.getString("payment_method"),
                        rs.getDouble("shipping_fee"),
                        rs.getString("order_note"));
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Order> getAllOrderDelivered(int id) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT Account.account_id, Account.username, Orders.order_id, OrderStatus.status_id, OrderStatus.status_name, Orders.shipping_fee, Orders.order_note, Orders.order_date, Vouchers.voucher_id, Orders.payment_status, Orders.payment_method, Orders.deliver_to\n"
                + "                              FROM     Account INNER JOIN\n"
                + "                              Orders ON Account.account_id = Orders.account_id INNER JOIN\n"
                + "                              OrderStatus ON Orders.status_id = OrderStatus.status_id LEFT JOIN\n"
                + "                          Vouchers ON Orders.voucher_id = Vouchers.voucher_id where Orders.account_id = ? AND status_name IN ('Delivered')\n"
                + "               	   order by Orders.last_updated desc ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("order_id"),
                        rs.getString("username"),
                        rs.getInt("account_id"),
                        rs.getTimestamp("order_date"),
                        rs.getInt("status_id"),
                        rs.getString("status_name"),
                        rs.getString("deliver_to"),
                        rs.getInt("voucher_id"),
                        rs.getBoolean("payment_status"),
                        rs.getString("payment_method"),
                        rs.getDouble("shipping_fee"),
                        rs.getString("order_note")
                );
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Order getOrderbyOrderId(int id) {
        Order o = new Order();
        try {
            String sql = "SELECT Account.account_id, Account.username, Orders.order_id, OrderStatus.status_id, OrderStatus.status_name, Vouchers.voucher_id, Orders.order_note, Orders.shipping_fee, Orders.order_date, \n"
                    + "                  Orders.payment_status, Orders.payment_method, Orders.deliver_to\n"
                    + "FROM     Account INNER JOIN\n"
                    + "                  Orders ON Account.account_id = Orders.account_id INNER JOIN\n"
                    + "                  OrderStatus ON Orders.status_id = OrderStatus.status_id LEFT JOIN\n"
                    + "                  Vouchers ON Orders.voucher_id = Vouchers.voucher_id where Orders.order_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                o.setOrderId(rs.getInt("order_id"));
                o.setUsername(rs.getString("username"));
                o.setAccountId(rs.getInt("account_id"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setStatusId(rs.getInt("status_id"));
                o.setStatusName(rs.getString("status_name"));
                o.setDeliveryAddress(rs.getString("deliver_to"));
                o.setVoucherId(rs.getInt("voucher_id"));
                o.setPaymentStatus(rs.getBoolean("payment_status"));
                o.setPaymentMethod(rs.getString("payment_method"));
                o.setShippingFee(rs.getDouble("shipping_fee"));
                o.setOrderNote(rs.getString("order_note"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public List<Order> getAllOrderReceived(int id) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT Account.account_id, Account.username, Orders.order_id, OrderStatus.status_id, OrderStatus.status_name, Orders.shipping_fee, Orders.order_note, Orders.order_date, Vouchers.voucher_id, Orders.payment_status, Orders.payment_method, Orders.deliver_to\n"
                + "                              FROM     Account INNER JOIN\n"
                + "                              Orders ON Account.account_id = Orders.account_id INNER JOIN\n"
                + "                              OrderStatus ON Orders.status_id = OrderStatus.status_id LEFT JOIN\n"
                + "                          Vouchers ON Orders.voucher_id = Vouchers.voucher_id where Orders.account_id = ? AND status_name IN ('Received')\n"
                + "               	   order by Orders.last_updated desc ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("order_id"),
                        rs.getString("username"),
                        rs.getInt("account_id"),
                        rs.getTimestamp("order_date"),
                        rs.getInt("status_id"),
                        rs.getString("status_name"),
                        rs.getString("deliver_to"),
                        rs.getInt("voucher_id"),
                        rs.getBoolean("payment_status"),
                        rs.getString("payment_method"),
                        rs.getDouble("shipping_fee"),
                        rs.getString("order_note")
                );
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
