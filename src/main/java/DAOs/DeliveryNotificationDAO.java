/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import DB.DBConnection;
import Model.Notification;
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
public class DeliveryNotificationDAO {
    
    // Lấy tất cả thông báo delivery của người dùng
    public List<Notification> getDeliveryNotificationsByUserId(int accountId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT n.notification_id, n.notification_content, n.is_read, n.notification_date, n.account_id "
                + "FROM Notifications n "
                + "WHERE n.account_id = ? AND n.notification_content LIKE 'ORDER_STATUS:%' "
                + "ORDER BY n.notification_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification notification = new Notification();
                    notification.setNotificationId(rs.getInt("notification_id"));
                    notification.setAccountId(rs.getInt("account_id"));
                    notification.setMessage(rs.getString("notification_content"));
                    notification.setCreatedDate(rs.getDate("notification_date"));
                    notification.setIsRead(rs.getBoolean("is_read"));
                    notifications.add(notification);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy thông báo giao hàng: " + e.getMessage());
        }
        return notifications;
    }
    
    // Lấy số lượng thông báo giao hàng chưa đọc
    public int getUnreadDeliveryNotificationCount(int accountId) {
        int count = 0;
        String sql = "SELECT COUNT(*) as count FROM Notifications " +
                     "WHERE account_id = ? AND is_read = 0 AND notification_content LIKE 'ORDER_STATUS:%'";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi đếm thông báo giao hàng chưa đọc: " + e.getMessage());
        }
        return count;
    }
    
    // Định dạng nội dung thông báo giao hàng
public String formatDeliveryNotification(String message) {
    if (message == null) return "";
    
    // Sửa các ký tự tiếng Việt bị lỗi thường gặp
    return message
        .replace("B?n", "Bạn")
        .replace("du?c", "được") 
        .replace("m?i", "mới")
        .replace("c?n", "cần")
        .replace("?", "ớ");  // Có thể điều chỉnh theo lỗi cụ thể
}
    
    // Lấy chi tiết đơn hàng từ thông báo
    public int getOrderIdFromNotification(String content) {
        if (content.startsWith("ORDER_STATUS:")) {
            String[] parts = content.split(":");
            if (parts.length > 1) {
                try {
                    return Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
        }
        return -1;
    }
    // Đánh dấu một thông báo đã đọc
public boolean markNotificationAsRead(int notificationId) {
    String sql = "UPDATE Notifications SET is_read = 1 WHERE notification_id = ?";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, notificationId);
        int rowsAffected = ps.executeUpdate();
        
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.out.println("Lỗi khi đánh dấu thông báo đã đọc: " + e.getMessage());
        return false;
    }
}

// Đánh dấu tất cả thông báo giao hàng của người dùng đã đọc
public boolean markAllDeliveryNotificationsAsRead(int accountId) {
    String sql = "UPDATE Notifications SET is_read = 1 " +
                 "WHERE account_id = ? AND notification_content LIKE 'ORDER_STATUS:%'";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, accountId);
        int rowsAffected = ps.executeUpdate();
        
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.out.println("Lỗi khi đánh dấu tất cả thông báo đã đọc: " + e.getMessage());
        return false;
    }
}
}
