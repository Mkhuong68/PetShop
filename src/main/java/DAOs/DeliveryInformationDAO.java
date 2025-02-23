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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tvhun
 */
public class DeliveryInformationDAO {

    public List<Map<String, Object>> getDeliveryOrderDisplayList() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT d.delivery_id, ds.status_name, "
                + "       (a.first_name + ' ' + a.last_name) AS recipientName, "
                + "       o.deliver_to "
                + "FROM DeliveryInformation d "
                + "JOIN Orders o ON d.order_id = o.order_id "
                + "JOIN DeliveryStatus ds ON d.status_id = ds.status_id "
                + "JOIN Account a ON o.account_id = a.account_id";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("deliveryId", rs.getInt("delivery_id"));
                row.put("statusName", rs.getString("status_name"));
                row.put("recipientName", rs.getString("recipientName"));
                row.put("deliverTo", rs.getString("deliver_to"));
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, Object> getDeliveryOrderDetails(int deliveryId) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> orderDetailsList = new ArrayList<>();
        double totalAmount = 0;

        // Query to fetch order details along with customer info
        String sql = "SELECT o.order_id, "
                + "       a.first_name, a.last_name, a.phone_number, o.deliver_to, "
                + "       od.quantity, od.final_price, p.product_name "
                + "FROM DeliveryInformation d "
                + "JOIN Orders o ON d.order_id = o.order_id "
                + "JOIN Account a ON o.account_id = a.account_id "
                + "JOIN OrderDetails od ON o.order_id = od.order_id "
                + "JOIN Products p ON od.product_id = p.product_id "
                + "WHERE d.delivery_id = ?";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, deliveryId);
            ResultSet rs = ps.executeQuery();
            String customerFirstName = "";
            String customerLastName = "";
            String phone = "";
            String address = "";
            while (rs.next()) {
                if (customerFirstName.isEmpty()) {
                    customerFirstName = rs.getString("first_name");
                    customerLastName = rs.getString("last_name");
                    phone = rs.getString("phone_number");
                    address = rs.getString("deliver_to");
                }
                Map<String, Object> item = new HashMap<>();
                item.put("productName", rs.getString("product_name"));
                int quantity = rs.getInt("quantity");
                item.put("quantity", quantity);
                double finalPrice = rs.getDouble("final_price");
                // Tính tổng giá cho sản phẩm = quantity * final_price (giả sử final_price là đơn giá)
                double totalPrice = quantity * finalPrice;
                item.put("totalPrice", totalPrice);
                totalAmount += totalPrice;
                orderDetailsList.add(item);
            }
            if (orderDetailsList.isEmpty()) {
                return null;
            }
            result.put("orderDetails", orderDetailsList);
            result.put("orderTotal", totalAmount);
            result.put("customerName", customerFirstName + " " + customerLastName);
            result.put("customerPhone", phone);
            result.put("customerAddress", address);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    // New method: Delete a delivery order if its status is "Cancelled"
    public boolean deleteDeliveryOrder(int deliveryId) {
        // Kiểm tra trạng thái của đơn hàng trước khi xóa
        String checkSql = "SELECT ds.status_name "
                + "FROM DeliveryInformation d "
                + "JOIN DeliveryStatus ds ON d.status_id = ds.status_id "
                + "WHERE d.delivery_id = ?";
        try ( Connection con = DBConnection.getConnection();  PreparedStatement checkPs = con.prepareStatement(checkSql)) {
            checkPs.setInt(1, deliveryId);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                String statusName = rs.getString("status_name");
                if (!"Cancelled".equalsIgnoreCase(statusName)) {
                    return false; // Chỉ cho phép xóa khi trạng thái là "Cancelled"
                }
            } else {
                return false; // Không tìm thấy đơn hàng
            }

            // Xóa bản ghi trong DeliveryInformation
            String deleteSql = "DELETE FROM DeliveryInformation WHERE delivery_id = ?";
            try ( PreparedStatement deletePs = con.prepareStatement(deleteSql)) {
                deletePs.setInt(1, deliveryId);
                int affectedRows = deletePs.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
