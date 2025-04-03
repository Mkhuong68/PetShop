/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.DeliveryInformation;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tvhun
 */
public class DeliveryInformationDAO {

    Connection con = DBConnection.getConnection();

    public List<Map<String, Object>> getDeliveryOrderDisplayList() {
        List<Map<String, Object>> list = new ArrayList<>();
        // Sửa câu query để phù hợp với cấu trúc database hiện tại và thêm payment_status
        String sql = "SELECT d.delivery_id, "
                + "       ds.status_name, "
                + "       (a.first_name + ' ' + a.last_name) AS recipientName, "
                + "       o.deliver_to, "
                + "       o.payment_status " // Thêm payment_status
                + "FROM DeliveryInformation d "
                + "JOIN Orders o ON d.order_id = o.order_id "
                + "JOIN DeliveryStatus ds ON d.status_id = ds.status_id "
                + "JOIN Account a ON o.account_id = a.account_id "
                + "ORDER BY d.delivery_id DESC";

        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("deliveryId", rs.getInt("delivery_id"));
                row.put("statusName", rs.getString("status_name"));
                row.put("recipientName", rs.getString("recipientName"));
                row.put("deliverTo", rs.getString("deliver_to"));
                row.put("paymentStatus", rs.getInt("payment_status")); // Thêm payment_status
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

        // Thêm debug log
        System.out.println("Đang lấy chi tiết cho delivery ID: " + deliveryId);

        // Sửa câu truy vấn SQL để phù hợp với database
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
                double totalPrice = quantity * finalPrice;
                item.put("totalPrice", totalPrice);
                totalAmount += totalPrice;
                orderDetailsList.add(item);
            }

            if (orderDetailsList.isEmpty()) {
                System.out.println("Không tìm thấy chi tiết đơn hàng cho delivery ID: " + deliveryId);
                return null;
            }

            result.put("orderDetails", orderDetailsList);
            result.put("orderTotal", totalAmount);
            result.put("customerName", customerFirstName + " " + customerLastName);
            result.put("customerPhone", phone);
            result.put("customerAddress", address);

            System.out.println("Tìm thấy " + orderDetailsList.size() + " sản phẩm trong đơn hàng");

        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy chi tiết đơn hàng: " + e.getMessage());
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

    public Map<String, String> updateDeliveryStatus(int deliveryId, int newStatusId) {
        Map<String, String> result = new HashMap<>();
        result.put("success", "false");

        // Check current status of the delivery order
        String checkSql = "SELECT d.status_id, ds.status_name FROM DeliveryInformation d "
                + "JOIN DeliveryStatus ds ON d.status_id = ds.status_id "
                + "WHERE d.delivery_id = ?";

        try ( Connection con = DBConnection.getConnection()) {
            // First check if the order is already in a final state
            try ( PreparedStatement checkPs = con.prepareStatement(checkSql)) {
                checkPs.setInt(1, deliveryId);
                ResultSet rs = checkPs.executeQuery();

                if (rs.next()) {
                    int currentStatusId = rs.getInt("status_id");
                    String currentStatusName = rs.getString("status_name");

                    // If order is already Delivered or Cancelled, don't allow status update
                    if ("Delivered".equals(currentStatusName)) {
                        result.put("error", "Cannot update status: Order has already been delivered.");
                        return result;
                    }

                    if ("Cancelled".equals(currentStatusName)) {
                        result.put("error", "Cannot update status: Order has already been cancelled.");
                        return result;
                    }

                    // Check if trying to downgrade the status
                    if (newStatusId < currentStatusId) {
                        result.put("error", "Cannot downgrade order status from "
                                + currentStatusName + " to a previous status.");
                        return result;
                    }
                } else {
                    // Order not found
                    result.put("error", "Delivery order not found. Please check the order ID.");
                    return result;
                }
            }

            // If all checks passed, proceed with the update
            String updateSql = "UPDATE DeliveryInformation SET status_id = ? WHERE delivery_id = ?";
            try ( PreparedStatement ps = con.prepareStatement(updateSql)) {
                ps.setInt(1, newStatusId);
                ps.setInt(2, deliveryId);

                int affectedRows = ps.executeUpdate();

                // If update was successful, update timestamp and possibly order status
                if (affectedRows > 0) {
                    // Update last_updated timestamp
                    String updateTimeSql = "UPDATE DeliveryInformation SET last_updated = GETDATE() WHERE delivery_id = ?";
                    try ( PreparedStatement timePs = con.prepareStatement(updateTimeSql)) {
                        timePs.setInt(1, deliveryId);
                        timePs.executeUpdate();
                    }

                    // If changing to Delivered status, update the corresponding order status too
                    if (newStatusId == 5) { // Assuming 5 is the ID for Delivered status
                        int orderId = getOrderIdByDeliveryId(deliveryId);
                        if (orderId != -1) {
                            updateOrderStatus(orderId, 4); // Assuming 4 is the ID for Delivered status in Orders table
                            
                            // Sử dụng phương thức sendCustomerNotification
                            sendCustomerNotification(orderId, "Đơn hàng của bạn đã được giao thành công!");
                        }
                    }

                    // Get the new status name for the success message
                    String getStatusSql = "SELECT status_name FROM DeliveryStatus WHERE status_id = ?";
                    try ( PreparedStatement statusPs = con.prepareStatement(getStatusSql)) {
                        statusPs.setInt(1, newStatusId);
                        ResultSet statusRs = statusPs.executeQuery();
                        if (statusRs.next()) {
                            String newStatusName = statusRs.getString("status_name");
                            result.put("success", "true");
                            result.put("message", "Order status successfully updated to " + newStatusName);
                        } else {
                            result.put("success", "true");
                            result.put("message", "Order status successfully updated");
                        }
                    }

                    return result;
                } else {
                    result.put("error", "Failed to update order status. No changes were made.");
                    return result;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.put("error", "Database error: " + e.getMessage());
            return result;
        }
    }

    public int getCurrentStatusId(int deliveryId) {
        String sql = "SELECT status_id FROM DeliveryInformation WHERE delivery_id = ?";

        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, deliveryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("status_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Trả về -1 nếu không tìm thấy
    }

    public int getOrderIdByDeliveryId(int deliveryId) {
        String sql = "SELECT order_id FROM DeliveryInformation WHERE delivery_id = ?";

        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, deliveryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("order_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // Update order status
    public boolean updateOrderStatus(int orderId, int statusId) {
        String sql = "UPDATE Orders SET status_id = ?, last_updated = GETDATE() WHERE order_id = ?";

        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, statusId);
            ps.setInt(2, orderId);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    // Tạo route mới và kết nối với delivery
    public int createNewRoute(int deliveryId) {
        int routeId = -1;

        // Tạo route mới
        String createRouteSql = "INSERT INTO Routes (route_name, created_date) VALUES (?, GETDATE()); SELECT SCOPE_IDENTITY();";
        String updateDeliverySql = "UPDATE DeliveryInformation SET route_id = ? WHERE delivery_id = ?";

        try ( Connection con = DBConnection.getConnection();  PreparedStatement createRoutePs = con.prepareStatement(createRouteSql)) {

            // Tạo tên route sử dụng deliveryId
            createRoutePs.setString(1, "Delivery #" + deliveryId);
            ResultSet rs = createRoutePs.executeQuery();

            if (rs.next()) {
                routeId = rs.getInt(1);

                // Cập nhật route_id trong DeliveryInformation
                try ( PreparedStatement updatePs = con.prepareStatement(updateDeliverySql)) {
                    updatePs.setInt(1, routeId);
                    updatePs.setInt(2, deliveryId);
                    updatePs.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return routeId;
    }

    // Thêm route point (điểm trên tuyến đường)
    public boolean addRoutePoint(int routeId, double latitude, double longitude) {
        String sql = "INSERT INTO RoutePoints (route_id, point_order, latitude, longitude, recorded_time) "
                + "VALUES (?, (SELECT ISNULL(MAX(point_order), 0) + 1 FROM RoutePoints WHERE route_id = ?), ?, ?, GETDATE())";

        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, routeId);
            ps.setInt(2, routeId);
            ps.setDouble(3, latitude);
            ps.setDouble(4, longitude);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, Object>> getRoutePoints(int routeId) {
        List<Map<String, Object>> points = new ArrayList<>();

        String sql = "SELECT route_point_id, point_order, latitude, longitude, recorded_time "
                + "FROM RoutePoints "
                + "WHERE route_id = ? "
                + "ORDER BY point_order ASC";

        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> point = new HashMap<>();
                point.put("routePointId", rs.getInt("route_point_id"));
                point.put("pointOrder", rs.getInt("point_order"));
                point.put("latitude", rs.getDouble("latitude"));
                point.put("longitude", rs.getDouble("longitude"));
                point.put("recordedTime", rs.getTimestamp("recorded_time"));

                points.add(point);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return points;
    }

    // Lấy thông tin cho trang tracking với đầy đủ chi tiết
    public Map<String, Object> getDeliveryTrackingInfo(int deliveryId) {
        Map<String, Object> result = new HashMap<>();

        String sql = "SELECT d.delivery_id, d.order_id, d.staff_id, d.status_id, "
                + "d.current_latitude, d.current_longitude, d.route_id, "
                + "ds.status_name, "
                + "o.deliver_to, o.order_date, o.payment_status, o.payment_method, "
                + "(a_customer.first_name + ' ' + a_customer.last_name) AS customer_name, "
                + "a_customer.phone_number, "
                + "(a_staff.first_name + ' ' + a_staff.last_name) AS staff_name "
                + "FROM DeliveryInformation d "
                + "JOIN Orders o ON d.order_id = o.order_id "
                + "JOIN Account a_customer ON o.account_id = a_customer.account_id "
                + "JOIN Account a_staff ON d.staff_id = a_staff.account_id "
                + "JOIN DeliveryStatus ds ON d.status_id = ds.status_id "
                + "WHERE d.delivery_id = ?";

        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, deliveryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result.put("deliveryId", rs.getInt("delivery_id"));
                result.put("orderId", rs.getInt("order_id"));
                result.put("staffId", rs.getInt("staff_id"));
                result.put("statusId", rs.getInt("status_id"));
                result.put("statusName", rs.getString("status_name"));
                result.put("customerName", rs.getString("customer_name"));
                result.put("customerPhone", rs.getString("phone_number"));
                result.put("customerAddress", rs.getString("deliver_to"));
                result.put("staffName", rs.getString("staff_name"));
                result.put("orderDate", rs.getTimestamp("order_date"));
                result.put("currentLatitude", rs.getDouble("current_latitude"));
                result.put("currentLongitude", rs.getDouble("current_longitude"));
                result.put("routeId", rs.getInt("route_id"));
                result.put("paymentStatus", rs.getInt("payment_status"));
                result.put("paymentMethod", rs.getString("payment_method"));

                // Nếu không có tọa độ hiện tại, sử dụng tọa độ mặc định Hà Nội
                if (rs.getObject("current_latitude") == null) {
                    result.put("currentLatitude", 21.02800);
                    result.put("currentLongitude", 105.83991);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    // Lấy tất cả các điểm trên tuyến đường gần đây nhất
    public List<Map<String, Object>> getRecentRoutePoints(int routeId, int limit) {
        List<Map<String, Object>> points = new ArrayList<>();

        String sql = "SELECT TOP ? route_point_id, point_order, latitude, longitude, recorded_time "
                + "FROM RoutePoints "
                + "WHERE route_id = ? "
                + "ORDER BY recorded_time DESC";

        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, routeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> point = new HashMap<>();
                point.put("routePointId", rs.getInt("route_point_id"));
                point.put("pointOrder", rs.getInt("point_order"));
                point.put("latitude", rs.getDouble("latitude"));
                point.put("longitude", rs.getDouble("longitude"));
                point.put("recordedTime", rs.getTimestamp("recorded_time"));

                points.add(point);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return points;
    }
    
    // Cập nhật vị trí hiện tại của người giao hàng
    public boolean updateCurrentLocation(int deliveryId, double latitude, double longitude) {
        String sql = "UPDATE DeliveryInformation SET current_latitude = ?, current_longitude = ? " +
                     "WHERE delivery_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDouble(1, latitude);
            ps.setDouble(2, longitude);
            ps.setInt(3, deliveryId);
            
            int rowsAffected = ps.executeUpdate();
            
            // Nếu cập nhật thành công, thêm điểm vào tuyến đường
            if (rowsAffected > 0) {
                // Lấy route_id từ đơn giao hàng
                int routeId = 0;
                String routeSql = "SELECT route_id FROM DeliveryInformation WHERE delivery_id = ?";
                
                try (PreparedStatement routePs = con.prepareStatement(routeSql)) {
                    routePs.setInt(1, deliveryId);
                    ResultSet rs = routePs.executeQuery();
                    
                    if (rs.next()) {
                        routeId = rs.getInt("route_id");
                        
                        // Nếu chưa có route, tạo mới route
                        if (routeId == 0) {
                            routeId = createNewRoute(deliveryId);
                        }
                        
                        // Thêm điểm mới vào tuyến đường
                        if (routeId > 0) {
                            addRoutePoint(routeId, latitude, longitude);
                        }
                    }
                }
            }
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
 * Lấy route_id từ delivery_id
 * @param deliveryId ID của đơn giao hàng
 * @return ID của tuyến đường, hoặc -1 nếu không tìm thấy
 */
public int getRouteIdByDeliveryId(int deliveryId) {
    String sql = "SELECT route_id FROM DeliveryInformation WHERE delivery_id = ?";
    
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, deliveryId);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("route_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return -1;  // Trả về -1 nếu không tìm thấy hoặc có lỗi
}

    // Phương thức lưu tọa độ đích của điểm giao hàng để cache lại
    public boolean cacheDestinationCoordinates(int deliveryId, double latitude, double longitude) {
        try {
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            
            try {
                con = DBConnection.getConnection();
                con.setAutoCommit(false); // Bắt đầu transaction
                
                // 1. Lấy route_id từ deliveryId
                String getRouteSql = "SELECT route_id FROM DeliveryInformation WHERE delivery_id = ?";
                ps = con.prepareStatement(getRouteSql);
                ps.setInt(1, deliveryId);
                rs = ps.executeQuery();
                
                int routeId = -1;
                if (rs.next()) {
                    routeId = rs.getInt("route_id");
                }
                
                // Nếu chưa có route, tạo route mới
                if (routeId <= 0) {
                    // Tạo route mới
                    ps.close();
                    String createRouteSql = "INSERT INTO Routes (route_name, created_date) VALUES (?, GETDATE()); SELECT SCOPE_IDENTITY();";
                    ps = con.prepareStatement(createRouteSql);
                    ps.setString(1, "Delivery #" + deliveryId);
                    rs = ps.executeQuery();
                    
                    if (rs.next()) {
                        routeId = rs.getInt(1);
                        
                        // Cập nhật route_id trong DeliveryInformation
                        ps.close();
                        String updateDeliverySql = "UPDATE DeliveryInformation SET route_id = ? WHERE delivery_id = ?";
                        ps = con.prepareStatement(updateDeliverySql);
                        ps.setInt(1, routeId);
                        ps.setInt(2, deliveryId);
                        ps.executeUpdate();
                    } else {
                        con.rollback();
                        return false;
                    }
                }
                
                // 2. Kiểm tra xem điểm đích (point_order = 0) đã tồn tại chưa
                ps.close();
                String checkSql = "SELECT route_point_id FROM RoutePoints WHERE route_id = ? AND point_order = 0";
                ps = con.prepareStatement(checkSql);
                ps.setInt(1, routeId);
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    // Nếu đã tồn tại, cập nhật
                    int routePointId = rs.getInt("route_point_id");
                    ps.close();
                    String updateSql = "UPDATE RoutePoints SET latitude = ?, longitude = ?, recorded_time = GETDATE() WHERE route_point_id = ?";
                    ps = con.prepareStatement(updateSql);
                    ps.setDouble(1, latitude);
                    ps.setDouble(2, longitude);
                    ps.setInt(3, routePointId);
                    
                    int affectedRows = ps.executeUpdate();
                    con.commit();
                    return affectedRows > 0;
                } else {
                    // Nếu chưa tồn tại, thêm mới
                    ps.close();
                    String insertSql = "INSERT INTO RoutePoints (route_id, point_order, latitude, longitude, recorded_time) VALUES (?, 0, ?, ?, GETDATE())";
                    ps = con.prepareStatement(insertSql);
                    ps.setInt(1, routeId);
                    ps.setDouble(2, latitude);
                    ps.setDouble(3, longitude);
                    
                    int affectedRows = ps.executeUpdate();
                    con.commit();
                    return affectedRows > 0;
                }
                
            } catch (SQLException e) {
                if (con != null) {
                    try {
                        con.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                System.err.println("Lỗi khi lưu tọa độ điểm đến: " + e.getMessage());
                e.printStackTrace();
                return false;
            } finally {
                if (rs != null) {
                    try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                }
                if (ps != null) {
                    try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                }
                if (con != null) {
                    try { 
                        con.setAutoCommit(true);
                        con.close(); 
                    } catch (SQLException e) { 
                        e.printStackTrace(); 
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi không mong đợi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức để lấy tọa độ đã cache nếu có
    public Map<String, Double> getCachedDestinationCoordinates(int deliveryId) {
        // 1. Lấy route_id từ deliveryId
        int routeId = getRouteIdByDeliveryId(deliveryId);
        if (routeId == -1) {
            return null; // Không tìm thấy route
        }
        
        // 2. Lấy tọa độ điểm đến từ RoutePoints (point_order = 0)
        String sql = "SELECT latitude, longitude FROM RoutePoints WHERE route_id = ? AND point_order = 0";
        Map<String, Double> coordinates = new HashMap<>();
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                coordinates.put("latitude", rs.getDouble("latitude"));
                coordinates.put("longitude", rs.getDouble("longitude"));
                return coordinates;
            }
            
            return null; // Trả về null nếu không tìm thấy tọa độ đã cache
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy tọa độ điểm đến: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy thông tin giới hạn tốc độ từ Goong API dựa trên tọa độ
     * @param latitude Vĩ độ hiện tại
     * @param longitude Kinh độ hiện tại
     * @param apiKey Khóa API Goong
     * @return Map chứa road_name và speed_limit, hoặc null nếu không thể lấy thông tin
     */
    public Map<String, Object> getSpeedLimit(double latitude, double longitude, String apiKey) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Tạo URL cho API Speed Limit của Goong
            String speedLimitUrl = String.format(
                "https://speed-api.goong.io/roadinfo?lat=%f&lon=%f&api_key=%s",
                latitude, longitude, apiKey
            );
            
            // Mở kết nối HTTP
            java.net.URL url = new java.net.URL(speedLimitUrl);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // Kiểm tra mã phản hồi
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // Đọc phản hồi
                try (java.io.BufferedReader reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    
                    // Phân tích phản hồi JSON
                    // Sử dụng thư viện JSON đơn giản để tránh phụ thuộc vào thư viện phức tạp
                    String jsonStr = response.toString();
                    
                    // Trích xuất thông tin từ JSON đơn giản
                    // Tìm road_name
                    int roadNameStart = jsonStr.indexOf("\"road_name\":") + 13;
                    int roadNameEnd = jsonStr.indexOf("\"", roadNameStart);
                    if (roadNameStart > 13 && roadNameEnd > roadNameStart) {
                        String roadName = jsonStr.substring(roadNameStart, roadNameEnd);
                        result.put("road_name", roadName);
                    }
                    
                    // Tìm speed limit
                    int speedStart = jsonStr.indexOf("\"speed\":") + 8;
                    int speedEnd = jsonStr.indexOf("}", speedStart);
                    if (speedStart > 8 && speedEnd > speedStart) {
                        try {
                            int speedLimit = Integer.parseInt(jsonStr.substring(speedStart, speedEnd).trim());
                            result.put("speed", speedLimit);
                        } catch (NumberFormatException e) {
                            System.err.println("Lỗi khi parse speed limit: " + e.getMessage());
                        }
                    }
                    
                    if (result.isEmpty()) {
                        return null;
                    }
                    
                    return result;
                }
            } else {
                System.err.println("API Speed Limit trả về mã lỗi: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API Speed Limit: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Lấy thông tin tuyến đường từ Goong Directions API
     * @param startLat Vĩ độ điểm bắt đầu
     * @param startLng Kinh độ điểm bắt đầu
     * @param endLat Vĩ độ điểm kết thúc
     * @param endLng Kinh độ điểm kết thúc
     * @param apiKey Khóa API Goong
     * @return Map chứa thông tin tuyến đường, hoặc null nếu không thể lấy thông tin
     */
    public Map<String, Object> getDirections(double startLat, double startLng, double endLat, double endLng, String apiKey) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Tạo URL cho API Directions của Goong
            String directionsUrl = String.format(
                "https://rsapi.goong.io/Direction?origin=%f,%f&destination=%f,%f&vehicle=car&api_key=%s",
                startLat, startLng, endLat, endLng, apiKey
            );
            
            // Mở kết nối HTTP
            java.net.URL url = new java.net.URL(directionsUrl);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // Kiểm tra mã phản hồi
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // Đọc phản hồi
                try (java.io.BufferedReader reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    
                    // Lưu phản hồi JSON nguyên bản để xử lý tại client
                    result.put("json", response.toString());
                    
                    // Có thể thêm logic để phân tích JSON và trích xuất thông tin cụ thể ở đây
                    // nếu cần thiết, nhưng với ứng dụng này ta nên để client JavaScript xử lý
                    
                    return result;
                }
            } else {
                System.err.println("API Directions trả về mã lỗi: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API Directions: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Lấy thông tin khoảng cách và thời gian di chuyển từ Goong DistanceMatrix API
     * @param startLat Vĩ độ điểm bắt đầu
     * @param startLng Kinh độ điểm bắt đầu
     * @param endLat Vĩ độ điểm kết thúc
     * @param endLng Kinh độ điểm kết thúc
     * @param apiKey Khóa API Goong
     * @return Map chứa distance và duration, hoặc null nếu không thể lấy thông tin
     */
    public Map<String, Object> getDistanceMatrix(double startLat, double startLng, double endLat, double endLng, String apiKey) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Tạo URL cho API DistanceMatrix của Goong
            String distanceMatrixUrl = String.format(
                "https://rsapi.goong.io/DistanceMatrix?origins=%f,%f&destinations=%f,%f&vehicle=car&api_key=%s",
                startLat, startLng, endLat, endLng, apiKey
            );
            
            // Mở kết nối HTTP
            java.net.URL url = new java.net.URL(distanceMatrixUrl);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // Kiểm tra mã phản hồi
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // Đọc phản hồi
                try (java.io.BufferedReader reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    
                    // Lưu phản hồi JSON nguyên bản
                    result.put("json", response.toString());
                    
                    // Phân tích phản hồi JSON đơn giản để trích xuất thông tin chính
                    String jsonStr = response.toString();
                    
                    // Kiểm tra nếu response có status OK
                    if (jsonStr.contains("\"status\":\"OK\"")) {
                        // Tìm thông tin khoảng cách
                        int distanceTextStart = jsonStr.indexOf("\"text\":\"", jsonStr.indexOf("\"distance\":")) + 8;
                        int distanceTextEnd = jsonStr.indexOf("\"", distanceTextStart);
                        if (distanceTextStart > 8 && distanceTextEnd > distanceTextStart) {
                            String distanceText = jsonStr.substring(distanceTextStart, distanceTextEnd);
                            result.put("distance_text", distanceText);
                        }
                        
                        int distanceValueStart = jsonStr.indexOf("\"value\":", jsonStr.indexOf("\"distance\":")) + 8;
                        int distanceValueEnd = jsonStr.indexOf("}", distanceValueStart);
                        if (distanceValueStart > 8 && distanceValueEnd > distanceValueStart) {
                            try {
                                int distanceValue = Integer.parseInt(jsonStr.substring(distanceValueStart, distanceValueEnd).trim());
                                result.put("distance_value", distanceValue);
                            } catch (NumberFormatException e) {
                                System.err.println("Lỗi khi parse distance value: " + e.getMessage());
                            }
                        }
                        
                        // Tìm thông tin thời gian
                        int durationTextStart = jsonStr.indexOf("\"text\":\"", jsonStr.indexOf("\"duration\":")) + 8;
                        int durationTextEnd = jsonStr.indexOf("\"", durationTextStart);
                        if (durationTextStart > 8 && durationTextEnd > durationTextStart) {
                            String durationText = jsonStr.substring(durationTextStart, durationTextEnd);
                            result.put("duration_text", durationText);
                        }
                        
                        int durationValueStart = jsonStr.indexOf("\"value\":", jsonStr.indexOf("\"duration\":")) + 8;
                        int durationValueEnd = jsonStr.indexOf("}", durationValueStart);
                        if (durationValueStart > 8 && durationValueEnd > durationValueStart) {
                            try {
                                int durationValue = Integer.parseInt(jsonStr.substring(durationValueStart, durationValueEnd).trim());
                                result.put("duration_value", durationValue);
                            } catch (NumberFormatException e) {
                                System.err.println("Lỗi khi parse duration value: " + e.getMessage());
                            }
                        }
                    }
                    
                    return result;
                }
            } else {
                System.err.println("API DistanceMatrix trả về mã lỗi: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API DistanceMatrix: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Geocode địa chỉ để lấy tọa độ sử dụng Goong Geocoding API
     * @param address Địa chỉ cần geocoding
     * @param apiKey Khóa API Goong
     * @return Map chứa lat, lng và formatted_address, hoặc null nếu không thể geocode
     */
    public Map<String, Object> geocodeAddress(String address, String apiKey) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Mã hóa địa chỉ
            String encodedAddress = java.net.URLEncoder.encode(address, "UTF-8");
            
            // Tạo URL cho API Geocoding của Goong
            String geocodingUrl = String.format(
                "https://rsapi.goong.io/geocode?address=%s&api_key=%s",
                encodedAddress, apiKey
            );
            
            // Mở kết nối HTTP
            java.net.URL url = new java.net.URL(geocodingUrl);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // Kiểm tra mã phản hồi
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // Đọc phản hồi
                try (java.io.BufferedReader reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    
                    // Phân tích phản hồi JSON
                    String jsonStr = response.toString();
                    
                    // Kiểm tra nếu response có status OK và có kết quả
                    if (jsonStr.contains("\"status\":\"OK\"") && jsonStr.contains("\"results\":[")) {
                        // Tìm tọa độ lat/lng
                        int latStart = jsonStr.indexOf("\"lat\":", jsonStr.indexOf("\"location\":")) + 6;
                        int latEnd = jsonStr.indexOf(",", latStart);
                        if (latStart > 6 && latEnd > latStart) {
                            try {
                                double lat = Double.parseDouble(jsonStr.substring(latStart, latEnd).trim());
                                result.put("lat", lat);
                            } catch (NumberFormatException e) {
                                System.err.println("Lỗi khi parse latitude: " + e.getMessage());
                            }
                        }
                        
                        int lngStart = jsonStr.indexOf("\"lng\":", jsonStr.indexOf("\"location\":")) + 6;
                        int lngEnd = jsonStr.indexOf("}", lngStart);
                        if (lngStart > 6 && lngEnd > lngStart) {
                            try {
                                double lng = Double.parseDouble(jsonStr.substring(lngStart, lngEnd).trim());
                                result.put("lng", lng);
                            } catch (NumberFormatException e) {
                                System.err.println("Lỗi khi parse longitude: " + e.getMessage());
                            }
                        }
                        
                        // Tìm địa chỉ định dạng
                        int addressStart = jsonStr.indexOf("\"formatted_address\":\"") + 21;
                        int addressEnd = jsonStr.indexOf("\"", addressStart);
                        if (addressStart > 21 && addressEnd > addressStart) {
                            String formattedAddress = jsonStr.substring(addressStart, addressEnd);
                            result.put("formatted_address", formattedAddress);
                        }
                        
                        if (result.containsKey("lat") && result.containsKey("lng")) {
                            return result;
                        }
                    }
                }
            } else {
                System.err.println("API Geocoding trả về mã lỗi: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API Geocoding: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Gửi thông báo cho khách hàng liên quan đến đơn hàng
     * @param orderId ID của đơn hàng
     * @param notificationContent Nội dung thông báo
     * @return true nếu gửi thành công, false nếu không gửi được
     */
    public boolean sendCustomerNotification(int orderId, String notificationContent) {
        try {
            // Giới hạn độ dài thông báo xuống còn 100 ký tự (dựa trên schema)
            if (notificationContent.length() > 100) {
                notificationContent = notificationContent.substring(0, 97) + "...";
            }
            
            String sql = "INSERT INTO Notifications (notification_content, account_id, notification_date, is_read) " +
                         "SELECT ?, o.account_id, GETDATE(), 0 " +
                         "FROM Orders o WHERE o.order_id = ?";
            
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                
                ps.setString(1, notificationContent);
                ps.setInt(2, orderId);
                
                int result = ps.executeUpdate();
                return result > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi gửi thông báo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}