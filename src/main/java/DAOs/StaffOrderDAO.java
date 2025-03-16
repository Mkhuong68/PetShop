    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.Account;
import Model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class StaffOrderDAO {

    Connection conn = DBConnection.getConnection();

    public List<Order> getAllOrder() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT Orders.order_id, Account.account_id, Account.username, OrderStatus.status_id, Orders.shipping_fee, Orders.order_note, OrderStatus.status_name, Vouchers.voucher_id, Orders.order_date, Orders.payment_status, Orders.payment_method, \n"
                + "                  Orders.deliver_to\n"
                + "FROM     Account INNER JOIN\n"
                + "                  Orders ON Account.account_id = Orders.account_id INNER JOIN\n"
                + "                  OrderStatus ON Orders.status_id = OrderStatus.status_id LEFT JOIN\n"
                + "                  Vouchers ON Orders.voucher_id = Vouchers.voucher_id order bY order_date DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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

    public Order getOrderbyId(int id) {
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
}
