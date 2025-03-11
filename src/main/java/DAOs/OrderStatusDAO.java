/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.OrderStatus;

/**
 *
 * @author NgocNNCE181950
 */
public class OrderStatusDAO {

    Connection conn = DBConnection.getConnection();

    public List<OrderStatus> getAllOrderStatus() {
        List<OrderStatus> list = new ArrayList<>();
        String sql = "select * from OrderStatus";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderStatus o = new OrderStatus(rs.getInt("status_id"), rs.getString("status_name"));
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void changeStatus(int statusId, int orderId) {
        String sql = "UPDATE Orders SET status_id = ?, last_updated = CURRENT_TIMESTAMP WHERE order_id = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, statusId);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
