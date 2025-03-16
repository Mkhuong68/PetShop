/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DB.DBConnection;
import Model.OrderDetail;
import Model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class OrderDetailDAO {

    Connection conn = DBConnection.getConnection();

    public boolean insertOrderDetail(OrderDetail o) {
        String sql = "INSERT INTO OrderDetails (order_id, product_id, quantity, final_price, purchase_price, applied_promotion_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o.getOrderId());
            ps.setInt(2, o.getProductId());
            ps.setInt(3, o.getQuantity());
            ps.setDouble(4, o.getFinalPrice());
            ps.setDouble(5, o.getPurchasePrice());
            ps.setNull(6, java.sql.Types.INTEGER);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT OrderDetails.order_detail_id, Orders.order_id, Products.product_id, OrderDetails.final_price, OrderDetails.quantity, OrderDetails.purchase_price\n"
                + "FROM     OrderDetails INNER JOIN\n"
                + "                  Orders ON OrderDetails.order_id = Orders.order_id INNER JOIN\n"
                + "                  Products ON OrderDetails.product_id = Products.product_id where Orders.order_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDetailId(rs.getInt("order_detail_id"));
                orderDetail.setOrderId(rs.getInt("order_id"));
                orderDetail.setProductId(rs.getInt("product_id"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setFinalPrice(rs.getDouble("final_price"));
                orderDetail.setPurchasePrice(rs.getDouble("purchase_price"));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

}
