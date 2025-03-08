package DAOs;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import DB.DBConnection;
import Model.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tvhun
 */
public class NotificationDAO {
    public List<Notification> getNotificationsByAccountId(int accountId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT notification_id, account_id, notification_content, notification_date, is_read " +
                     "FROM Notifications WHERE account_id = ? ORDER BY notification_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setAccountId(rs.getInt("account_id"));
                n.setMessage(rs.getString("notification_content"));  
                n.setCreatedDate(rs.getDate("notification_date"));
                n.setIsRead(rs.getBoolean("is_read"));
                notifications.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public void markAsRead(int notificationId) {
        String sql = "UPDATE Notifications SET is_read = 1 WHERE notification_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, notificationId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
