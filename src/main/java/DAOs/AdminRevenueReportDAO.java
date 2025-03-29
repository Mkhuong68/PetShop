/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.RevenueReport;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
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
public class AdminRevenueReportDAO {

    Connection conn = DBConnection.getConnection();

    public BigDecimal getAverageOrderValue(Date reportDate) {
        String sql = "SELECT \n"
                + "    CASE \n"
                + "        WHEN COUNT(DISTINCT o.order_id) > 0 \n"
                + "        THEN COALESCE(SUM(filtered_orders.final_price), 0) / COUNT(DISTINCT o.order_id) \n"
                + "        ELSE 0 \n"
                + "    END AS average_order_value\n"
                + "FROM Orders o\n"
                + "JOIN (\n"
                + "    SELECT order_id, MIN(final_price) AS final_price\n"
                + "    FROM OrderDetails\n"
                + "    GROUP BY order_id\n"
                + ") AS filtered_orders ON o.order_id = filtered_orders.order_id\n"
                + "WHERE CAST(o.order_date AS DATE) = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, reportDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getTotalRevenue(Date reportDate) {
        String sql = "SELECT \n"
                + "    COALESCE(SUM(d.final_price), 0) AS total_revenue\n"
                + "FROM Orders o\n"
                + "JOIN (\n"
                + "    SELECT order_id, MIN(final_price) AS final_price\n"
                + "    FROM OrderDetails\n"
                + "    GROUP BY order_id\n"
                + ") d ON o.order_id = d.order_id\n"
                + "WHERE CAST(o.order_date AS DATE) = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, reportDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public int getTotalOrdersByDate(Date reportDate) {
        String sql = "SELECT COUNT(order_id) AS total_orders \n"
                + "FROM Orders \n"
                + "WHERE CAST(order_date AS DATE) = ?;";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, reportDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_orders");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean createRevenueReport(RevenueReport r) {
        String sql = "INSERT INTO RevenueReports (report_date, total_revenue, total_orders, average_order_value, created_by, created_date) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, r.getReportDate());
            ps.setBigDecimal(2, r.getTotalRevenue());
            ps.setInt(3, r.getTotalOrders());
            ps.setBigDecimal(4, r.getAverageOrderValue());
            ps.setInt(5, r.getCreatedBy());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<RevenueReport> getAllRevenueReports() {
        List<RevenueReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM RevenueReports ORDER BY report_id DESC";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RevenueReport report = new RevenueReport(
                        rs.getInt("report_id"),
                        rs.getDate("report_date"),
                        rs.getBigDecimal("total_revenue"),
                        rs.getInt("total_orders"),
                        rs.getBigDecimal("average_order_value"),
                        rs.getInt("created_by"),
                        rs.getTimestamp("created_date")
                );
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public boolean deleteRevenueReport(int reportId) {
        String sql = "DELETE FROM RevenueReports WHERE report_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reportId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
