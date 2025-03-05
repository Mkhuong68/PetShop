/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import DAOs.VoucherDAO;
import DB.DBConnection;
import Model.Voucher;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
/**
 *
 * @author tvhun
 */
public class VoucherAutoAssignService {
    private VoucherDAO voucherDAO = new VoucherDAO();
    
    // Phương thức tự động gán voucher cho tài khoản khi tổng giá trị đơn hàng đạt ngưỡng
    // Lưu ý: Voucher được tạo chung, không gán cho 1 tài khoản cụ thể.
    public void assignVoucherForAccount(int accountId) {
        try (Connection conn = DBConnection.getConnection()) {
            int orderCount = getOrderCount(conn, accountId);
            BigDecimal totalOrderValue = getTotalOrderValue(conn, accountId);
            
            // Nếu tài khoản chưa có đơn hàng nào thì không tạo voucher
            if (orderCount == 0) return;
            
            Voucher voucher = new Voucher();
            // Loại bỏ việc gán accountId vì voucher là chung
            voucher.setVoucherValidFrom(new Date());
            // Voucher có hiệu lực trong 1 tháng kể từ ngày tạo
            voucher.setVoucherValidTo(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000));
            voucher.setVoucherStatus(true);
            
            if (orderCount == 1) {
                // Tài khoản mới (chỉ có đơn hàng đầu tiên)
                voucher.setVoucherType("new");
                voucher.setVoucherCode("NEW_" + System.currentTimeMillis());
                voucher.setVoucherDescription("Voucher dành cho khách hàng mới");
                voucher.setVoucherDiscount(10); // ví dụ giảm 10%
            } else {
                // Với các tài khoản có nhiều đơn hàng, kiểm tra tổng giá trị mua hàng
                if (totalOrderValue.compareTo(new BigDecimal("2000000")) >= 0) {
                    voucher.setVoucherType("high");
                    voucher.setVoucherCode("HIGH_" + System.currentTimeMillis());
                    voucher.setVoucherDescription("Voucher dành cho khách hàng có tổng giá trị mua từ 2 triệu trở lên");
                    voucher.setVoucherDiscount(15); // ví dụ giảm 15%
                } else {
                    voucher.setVoucherType("medium");
                    voucher.setVoucherCode("MEDIUM_" + System.currentTimeMillis());
                    voucher.setVoucherDescription("Voucher dành cho khách hàng có tổng giá trị mua dưới 2 triệu");
                    voucher.setVoucherDiscount(5); // ví dụ giảm 5%
                }
            }
            
            voucherDAO.addVoucher(voucher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int getOrderCount(Connection conn, int accountId) throws Exception {
        String sql = "SELECT COUNT(*) AS OrderCount FROM Orders WHERE account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("OrderCount");
                }
            }
        }
        return 0;
    }
    
    private BigDecimal getTotalOrderValue(Connection conn, int accountId) throws Exception {
        String sql = "SELECT ISNULL(SUM(od.final_price * od.quantity), 0) AS TotalValue " +
                     "FROM Orders o INNER JOIN OrderDetails od ON o.order_id = od.order_id " +
                     "WHERE o.account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("TotalValue");
                }
            }
        }
        return BigDecimal.ZERO;
    }
}