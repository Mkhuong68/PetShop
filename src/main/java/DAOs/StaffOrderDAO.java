/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.Account;
import Model.Order;
import Model.OrderTemp;
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

    public List<OrderTemp> getAllOrders() {
        List<OrderTemp> orders = new ArrayList<>();
        String sql = "SELECT \n"
                + "    O.order_id,\n"
                + "    A.account_id,\n"
                + "    O.order_date,\n"
                + "    O.shipping_fee,\n"
                + "    OS.status_name,\n"
                + "    O.deliver_to,\n"
                + "    O.payment_method,\n"
                + "    O.order_note,\n"
                + "    V.voucher_code,\n"
                + "    UV.user_voucher_id,\n"
                + "    V.voucher_id,\n"
                + "    MAX(OD.final_price) AS final_price, \n"
                + "    MAX(OD.applied_promotion_id) AS applied_promotion_id,\n"
                + "    MAX(OP.option_id) AS option_id,\n"
                + "    MAX(P.product_id) AS product_id,\n"
                + "    MAX(OD.order_detail_id) AS order_detail_id,\n"
                + "    MAX(OS.status_id) AS status_id,\n"
                + "    MAX(P.product_price) AS product_price,\n"
                + "    MAX(OD.quantity) AS quantity,\n"
                + "    MAX(OD.purchase_price) AS purchase_price,\n"
                + "    STRING_AGG(\n"
                + "        CONCAT(P.product_name, ' (x', OD.quantity, ') - ', FORMAT(OD.purchase_price, '#,##0'), ' VND'),\n"
                + "        CHAR(10) \n"
                + "    ) AS product_name,\n"
                + "    STRING_AGG(OP.option_name, ', ') AS option_name\n"
                + "FROM Orders O\n"
                + "LEFT JOIN Account A ON O.account_id = A.account_id\n"
                + "LEFT JOIN OrderDetails OD ON O.order_id = OD.order_id\n"
                + "LEFT JOIN Products P ON OD.product_id = P.product_id\n"
                + "LEFT JOIN Options OP ON P.product_id = OP.product_id\n"
                + "LEFT JOIN OrderStatus OS ON O.status_id = OS.status_id\n"
                + "LEFT JOIN UserVouchers UV ON O.user_voucher_id = UV.user_voucher_id\n"
                + "LEFT JOIN Vouchers V ON UV.voucher_id = V.voucher_id\n"
                + "GROUP BY O.order_id, A.account_id, O.order_date, O.shipping_fee, OS.status_name, \n"
                + "         O.deliver_to, O.payment_method, O.order_note, V.voucher_code, \n"
                + "         UV.user_voucher_id, V.voucher_id\n"
                + "ORDER BY O.order_date DESC;";

        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrderTemp order = new OrderTemp(
                        rs.getInt("account_id"),
                        rs.getInt("option_id"),
                        rs.getInt("product_id"),
                        rs.getInt("order_detail_id"),
                        rs.getInt("status_id"),
                        rs.getInt("order_id"),
                        rs.getInt("user_voucher_id"),
                        rs.getInt("voucher_id"),
                        rs.getString("deliver_to"),
                        rs.getString("payment_method"),
                        rs.getTimestamp("order_date"),
                        rs.getDouble("shipping_fee"),
                        rs.getString("voucher_code"),
                        rs.getString("product_name"),
                        rs.getDouble("product_price"),
                        rs.getString("status_name"),
                        rs.getString("option_name"),
                        rs.getString("order_note"),
                        rs.getDouble("final_price"),
                        rs.getInt("quantity"),
                        rs.getDouble("purchase_price"),
                        rs.getObject("applied_promotion_id") != null ? rs.getInt("applied_promotion_id") : null
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public OrderTemp getOrderByOrderId(int orderId) {
        String sql = "SELECT Account.account_id, Options.option_id, Products.product_id, OrderDetails.order_detail_id, "
                + "OrderStatus.status_id, Orders.order_id, UserVouchers.user_voucher_id, Vouchers.voucher_id, "
                + "Orders.deliver_to, Orders.payment_method, Orders.order_date, Orders.shipping_fee, Vouchers.voucher_code, "
                + "Products.product_name, Products.product_price, OrderStatus.status_name, Options.option_name, "
                + "Orders.order_note, OrderDetails.final_price, OrderDetails.quantity, OrderDetails.purchase_price, "
                + "OrderDetails.applied_promotion_id "
                + "FROM Account "
                + "LEFT JOIN Orders ON Account.account_id = Orders.account_id "
                + "LEFT JOIN OrderDetails ON Orders.order_id = OrderDetails.order_id "
                + "LEFT JOIN OrderStatus ON Orders.status_id = OrderStatus.status_id "
                + "LEFT JOIN Products ON OrderDetails.product_id = Products.product_id "
                + "LEFT JOIN Options ON Products.product_id = Options.product_id "
                + "LEFT JOIN UserVouchers ON Account.account_id = UserVouchers.account_id AND Orders.user_voucher_id = UserVouchers.user_voucher_id "
                + "LEFT JOIN Vouchers ON UserVouchers.voucher_id = Vouchers.voucher_id "
                + "WHERE Orders.order_id = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new OrderTemp(
                            rs.getInt("account_id"),
                            rs.getInt("option_id"),
                            rs.getInt("product_id"),
                            rs.getInt("order_detail_id"),
                            rs.getInt("status_id"),
                            rs.getInt("order_id"),
                            rs.getInt("user_voucher_id"),
                            rs.getInt("voucher_id"),
                            rs.getString("deliver_to"),
                            rs.getString("payment_method"),
                            rs.getTimestamp("order_date"),
                            rs.getDouble("shipping_fee"),
                            rs.getString("voucher_code"),
                            rs.getString("product_name"),
                            rs.getDouble("product_price"),
                            rs.getString("status_name"),
                            rs.getString("option_name"),
                            rs.getString("order_note"),
                            rs.getDouble("final_price"),
                            rs.getInt("quantity"),
                            rs.getDouble("purchase_price"),
                            rs.getObject("applied_promotion_id") != null ? rs.getInt("applied_promotion_id") : null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OrderTemp> getOrderDetailByOrderId(int orderId) {
        List<OrderTemp> orders = new ArrayList<>();
        String sql = "			  SELECT Account.account_id, Options.option_id, Products.product_id, OrderDetails.order_detail_id, OrderStatus.status_id, Orders.order_id, UserVouchers.user_voucher_id, Vouchers.voucher_id, Orders.deliver_to, Orders.payment_method, \n"
                + "                      Orders.order_date, Orders.shipping_fee, Vouchers.voucher_code, Products.product_name, Products.product_price, OrderStatus.status_name, Options.option_name, Orders.order_note, OrderDetails.final_price, OrderDetails.quantity, \n"
                + "                        OrderDetails.purchase_price, OrderDetails.applied_promotion_id\n"
                + "          FROM     Account  LEFT JOIN\n"
                + "                         Orders ON Account.account_id = Orders.account_id LEFT JOIN\n"
                + "                          OrderDetails ON Orders.order_id = OrderDetails.order_id LEFT JOIN\n"
                + "                          OrderStatus ON Orders.status_id = OrderStatus.status_id  LEFT JOIN\n"
                + "                         Products ON OrderDetails.product_id = Products.product_id LEFT JOIN\n"
                + "                         Options ON Products.product_id = Options.product_id  LEFT JOIN\n"
                + "                       UserVouchers ON Account.account_id = UserVouchers.account_id AND Orders.user_voucher_id = UserVouchers.user_voucher_id  LEFT JOIN\n"
                + "                  Vouchers ON UserVouchers.voucher_id = Vouchers.voucher_id where Orders.order_id = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderTemp order = new OrderTemp(
                            rs.getInt("account_id"),
                            rs.getInt("option_id"),
                            rs.getInt("product_id"),
                            rs.getInt("order_detail_id"),
                            rs.getInt("status_id"),
                            rs.getInt("order_id"),
                            rs.getInt("user_voucher_id"),
                            rs.getInt("voucher_id"),
                            rs.getString("deliver_to"),
                            rs.getString("payment_method"),
                            rs.getTimestamp("order_date"),
                            rs.getDouble("shipping_fee"),
                            rs.getString("voucher_code"),
                            rs.getString("product_name"),
                            rs.getDouble("product_price"),
                            rs.getString("status_name"),
                            rs.getString("option_name"),
                            rs.getString("order_note"),
                            rs.getDouble("final_price"),
                            rs.getInt("quantity"),
                            rs.getDouble("purchase_price"),
                            rs.getObject("applied_promotion_id") != null ? rs.getInt("applied_promotion_id") : null
                    );
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
