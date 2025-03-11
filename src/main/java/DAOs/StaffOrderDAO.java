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
        String sql = "SELECT Orders.order_id, Account.account_id, Account.username, Orders.order_date, OrderStatus.status_id, OrderStatus.status_name, Orders.deliver_to\n"
                + "FROM     Account INNER JOIN\n"
                + "                  Orders ON Account.account_id = Orders.account_id INNER JOIN\n"
                + "                  OrderStatus ON Orders.status_id = OrderStatus.status_id";
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
                        rs.getString("deliver_to"));
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Order getOrderbyId(int id) {
        Order o = new Order();
        try {
            String sql = "SELECT Orders.order_id, Account.account_id, Account.username, Orders.order_date, OrderStatus.status_id, OrderStatus.status_name, Orders.deliver_to\n"
                    + "FROM     Account INNER JOIN\n"
                    + "                  Orders ON Account.account_id = Orders.account_id INNER JOIN\n"
                    + "                  OrderStatus ON Orders.status_id = OrderStatus.status_id where order_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                o.setOrderId(rs.getInt("order_id"));
                o.setUsername(rs.getString("username"));
                o.setAccountId(rs.getInt("account_id"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setStatusId(rs.getInt("status_id"));
                o.setStatusName(rs.getString("status_name"));
                o.setDeliveryAddress(rs.getString("deliver_to"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public void changeStatus(int statusId) {
        Order o = new Order();

    }
}
