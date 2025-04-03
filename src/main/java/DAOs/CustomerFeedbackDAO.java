package DAOs;

import DB.DBConnection;
import Model.ProductFeedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerFeedbackDAO {
    
    // Thêm đánh giá mới
    public int addFeedback(ProductFeedback feedback) {
        String sql = "INSERT INTO ProductFeedback (order_detail_id, rating, comment, created_date, is_hidden) "
                + "VALUES (?, ?, ?, GETDATE(), 0); SELECT SCOPE_IDENTITY();";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, feedback.getOrderDetailId());
            ps.setInt(2, feedback.getRating());
            ps.setString(3, feedback.getComment());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Cập nhật đánh giá
    public boolean updateFeedback(ProductFeedback feedback) {
        String sql = "UPDATE ProductFeedback SET rating = ?, comment = ?, last_updated = GETDATE() "
                + "WHERE feedback_id = ? AND order_detail_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, feedback.getRating());
            ps.setString(2, feedback.getComment());
            ps.setInt(3, feedback.getFeedbackId());
            ps.setInt(4, feedback.getOrderDetailId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Xóa đánh giá
    public boolean deleteFeedback(int feedbackId, int accountId) {
        String sql = "DELETE FROM ProductFeedback "
                + "WHERE feedback_id = ? AND order_detail_id IN ("
                + "SELECT od.order_detail_id FROM OrderDetails od "
                + "JOIN Orders o ON od.order_id = o.order_id "
                + "WHERE o.account_id = ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, feedbackId);
            ps.setInt(2, accountId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Ẩn/hiện đánh giá
    public boolean toggleFeedbackVisibility(int feedbackId, boolean isHidden) {
        String sql = "UPDATE ProductFeedback SET is_hidden = ?, last_updated = GETDATE() "
                + "WHERE feedback_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBoolean(1, isHidden);
            ps.setInt(2, feedbackId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Lấy đánh giá của người dùng
    public List<Map<String, Object>> getUserFeedbacks(int accountId) {
        List<Map<String, Object>> feedbacks = new ArrayList<>();
        try {
            System.out.println("CustomerFeedbackDAO.getUserFeedbacks: Đang tìm feedback cho accountId: " + accountId);
            
            String sql = "SELECT f.feedback_id, f.order_detail_id, f.comment, f.rating, f.created_date, f.last_updated, " +
                     "f.staff_reply, p.product_name, p.product_image, p.product_price, p.product_id, od.quantity, od.final_price, " +
                     "o.order_id, o.order_date, c.category_name, c.category_id " +
                     "FROM ProductFeedback f " +
                     "JOIN OrderDetails od ON f.order_detail_id = od.order_detail_id " +
                     "JOIN Orders o ON od.order_id = o.order_id " +
                     "JOIN Products p ON od.product_id = p.product_id " +
                     "JOIN Categories c ON p.category_id = c.category_id " +
                     "WHERE o.account_id = ? " +
                     "ORDER BY f.created_date DESC";
            
            System.out.println("SQL Query: " + sql);
            
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, accountId);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    System.out.println("Đang duyệt qua kết quả...");
                    int count = 0;
                    
                    while (rs.next()) {
                        count++;
                        Map<String, Object> feedback = new HashMap<>();
                        
                        // Feedback details
                        feedback.put("feedbackId", rs.getInt("feedback_id"));
                        feedback.put("orderDetailId", rs.getInt("order_detail_id"));
                        feedback.put("comment", rs.getString("comment"));
                        feedback.put("rating", rs.getInt("rating"));
                        feedback.put("createdDate", rs.getTimestamp("created_date"));
                        feedback.put("lastUpdated", rs.getTimestamp("last_updated"));
                        feedback.put("staffReply", rs.getString("staff_reply"));
                        
                        // Product details
                        feedback.put("productName", rs.getString("product_name"));
                        feedback.put("productImage", rs.getString("product_image"));
                        feedback.put("productId", rs.getInt("product_id"));
                        feedback.put("originalPrice", rs.getBigDecimal("product_price"));
                        feedback.put("categoryName", rs.getString("category_name"));
                        feedback.put("categoryId", rs.getInt("category_id"));
                        
                        // Order details
                        feedback.put("orderId", rs.getInt("order_id"));
                        feedback.put("orderDate", rs.getTimestamp("order_date"));
                        feedback.put("quantity", rs.getInt("quantity"));
                        feedback.put("finalPrice", rs.getBigDecimal("final_price"));
                        
                        // Tính tổng giá trị đơn hàng từ final_price
                        feedback.put("totalAmount", rs.getBigDecimal("final_price"));
                        
                        System.out.println("Tìm thấy feedback: " + feedback.get("feedbackId") + " - " + feedback.get("productName"));
                        
                        feedbacks.add(feedback);
                    }
                    
                    System.out.println("Tổng số feedback tìm thấy: " + count);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi trong CustomerFeedbackDAO.getUserFeedbacks: " + e.getMessage());
            e.printStackTrace();
        }
        return feedbacks;
    }
    
    // Lấy chi tiết đánh giá theo ID
    public Map<String, Object> getFeedbackDetail(int feedbackId, int accountId) {
        Map<String, Object> feedbackDetail = new HashMap<>();
        String query = "SELECT pf.*, p.product_name, p.product_image, p.product_id, p.product_price, c.category_name, c.category_id, "
                + "od.order_id, od.quantity, od.final_price "
                + "FROM ProductFeedback pf "
                + "JOIN OrderDetails od ON pf.order_detail_id = od.order_detail_id " +
                "JOIN Products p ON od.product_id = p.product_id " +
                "JOIN Categories c ON p.category_id = c.category_id " +
                "JOIN Orders o ON od.order_id = o.order_id " +
                "WHERE pf.feedback_id = ? AND o.account_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, feedbackId);
            ps.setInt(2, accountId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Feedback fields
                    feedbackDetail.put("feedbackId", rs.getInt("feedback_id"));
                    feedbackDetail.put("orderDetailId", rs.getInt("order_detail_id"));
                    feedbackDetail.put("rating", rs.getInt("rating"));
                    feedbackDetail.put("comment", rs.getString("comment"));
                    feedbackDetail.put("staffReply", rs.getString("staff_reply"));
                    feedbackDetail.put("createdDate", rs.getTimestamp("created_date"));
                    feedbackDetail.put("lastUpdated", rs.getTimestamp("last_updated"));
                    feedbackDetail.put("isHidden", rs.getBoolean("is_hidden"));

                    // Product details
                    feedbackDetail.put("productId", rs.getInt("product_id"));
                    feedbackDetail.put("productName", rs.getString("product_name"));
                    feedbackDetail.put("productImage", rs.getString("product_image"));
                    feedbackDetail.put("originalPrice", rs.getBigDecimal("product_price"));
                    feedbackDetail.put("categoryId", rs.getInt("category_id"));
                    feedbackDetail.put("categoryName", rs.getString("category_name"));
                    
                    // Order details
                    feedbackDetail.put("orderId", rs.getInt("order_id"));
                    feedbackDetail.put("quantity", rs.getInt("quantity"));
                    feedbackDetail.put("finalPrice", rs.getBigDecimal("final_price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackDetail;
    }
    
    // Kiểm tra người dùng đã đánh giá cho order_detail này chưa
    public boolean hasUserReviewedOrderDetail(int orderDetailId) {
        String query = "SELECT COUNT(*) FROM ProductFeedback WHERE order_detail_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, orderDetailId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Kiểm tra sở hữu đánh giá
    public boolean isFeedbackOwner(int feedbackId, int accountId) {
        String query = "SELECT COUNT(*) FROM ProductFeedback pf "
                + "JOIN OrderDetails od ON pf.order_detail_id = od.order_detail_id "
                + "JOIN Orders o ON od.order_id = o.order_id "
                + "WHERE pf.feedback_id = ? AND o.account_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, feedbackId);
            ps.setInt(2, accountId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Lấy đánh giá của sản phẩm
    public List<Map<String, Object>> getProductFeedbacks(int productId, int page, int pageSize) {
        List<Map<String, Object>> feedbackList = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        
        String query = "SELECT pf.*, a.username, a.profile_image "
                + "FROM ProductFeedback pf "
                + "JOIN OrderDetails od ON pf.order_detail_id = od.order_detail_id " +
                "JOIN Orders o ON od.order_id = o.order_id " +
                "JOIN Account a ON o.account_id = a.account_id " +
                "WHERE od.product_id = ? AND pf.is_hidden = 0 " +
                "ORDER BY pf.created_date DESC " +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, productId);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> feedbackDetails = new HashMap<>();

                    // Feedback fields
                    feedbackDetails.put("feedbackId", rs.getInt("feedback_id"));
                    feedbackDetails.put("rating", rs.getInt("rating"));
                    feedbackDetails.put("comment", rs.getString("comment"));
                    feedbackDetails.put("staffReply", rs.getString("staff_reply"));
                    feedbackDetails.put("createdDate", rs.getTimestamp("created_date"));

                    // User details
                    feedbackDetails.put("username", rs.getString("username"));
                    feedbackDetails.put("profileImage", rs.getString("profile_image"));

                    feedbackList.add(feedbackDetails);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackList;
    }
    
    // Đếm số lượng đánh giá của sản phẩm
    public int countProductFeedbacks(int productId) {
        String query = "SELECT COUNT(*) FROM ProductFeedback pf "
                + "JOIN OrderDetails od ON pf.order_detail_id = od.order_detail_id "
                + "WHERE od.product_id = ? AND pf.is_hidden = 0";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, productId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Tính điểm đánh giá trung bình của sản phẩm
    public double getAverageProductRating(int productId) {
        String query = "SELECT AVG(CAST(rating AS FLOAT)) FROM ProductFeedback pf "
                + "JOIN OrderDetails od ON pf.order_detail_id = od.order_detail_id "
                + "WHERE od.product_id = ? AND pf.is_hidden = 0";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, productId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Lấy thông tin order detail để đánh giá
    public Map<String, Object> getOrderDetailForFeedback(int orderDetailId, int accountId) {
        Map<String, Object> orderDetail = new HashMap<>();
        String query = "SELECT od.*, p.product_name, p.product_image, p.product_id, p.product_price, " +
                "c.category_name, c.category_id, o.order_id, o.order_date, " +
                "o.shipping_fee, o.discount_amount, pm.payment_method_name, os.status_name, " +
                "a.first_name, a.last_name, a.phone, a.email, " +
                "sa.address_line, sa.city, sa.district, sa.ward " +
                "FROM OrderDetails od " +
                "JOIN Products p ON od.product_id = p.product_id " +
                "JOIN Categories c ON p.category_id = c.category_id " +
                "JOIN Orders o ON od.order_id = o.order_id " +
                "JOIN PaymentMethod pm ON o.payment_method_id = pm.payment_method_id " +
                "JOIN OrderStatus os ON o.status_id = os.status_id " +
                "JOIN Account a ON o.account_id = a.account_id " +
                "LEFT JOIN ShippingAddress sa ON o.shipping_address_id = sa.address_id " +
                "WHERE od.order_detail_id = ? AND o.account_id = ? AND o.status_id = 4"; // status_id = 4: Delivered
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, orderDetailId);
            ps.setInt(2, accountId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Chi tiết sản phẩm
                    orderDetail.put("orderDetailId", rs.getInt("order_detail_id"));
                    orderDetail.put("productId", rs.getInt("product_id"));
                    orderDetail.put("productName", rs.getString("product_name"));
                    orderDetail.put("productImage", rs.getString("product_image"));
                    orderDetail.put("originalPrice", rs.getBigDecimal("product_price"));
                    orderDetail.put("categoryId", rs.getInt("category_id"));
                    orderDetail.put("categoryName", rs.getString("category_name"));
                    orderDetail.put("quantity", rs.getInt("quantity"));
                    orderDetail.put("finalPrice", rs.getBigDecimal("final_price"));
                    
                    // Chi tiết đơn hàng
                    orderDetail.put("orderId", rs.getInt("order_id"));
                    orderDetail.put("orderDate", rs.getTimestamp("order_date"));
                    // Tính tổng giá trị đơn hàng từ final_price
                    orderDetail.put("totalAmount", rs.getBigDecimal("final_price"));
                    orderDetail.put("shippingFee", rs.getBigDecimal("shipping_fee"));
                    orderDetail.put("discountAmount", rs.getBigDecimal("discount_amount"));
                    orderDetail.put("paymentMethod", rs.getString("payment_method_name"));
                    orderDetail.put("orderStatus", rs.getString("status_name"));
                    
                    // Thông tin người đặt
                    orderDetail.put("firstName", rs.getString("first_name"));
                    orderDetail.put("lastName", rs.getString("last_name"));
                    orderDetail.put("phone", rs.getString("phone"));
                    orderDetail.put("email", rs.getString("email"));
                    
                    // Địa chỉ giao hàng
                    orderDetail.put("addressLine", rs.getString("address_line"));
                    orderDetail.put("city", rs.getString("city"));
                    orderDetail.put("district", rs.getString("district"));
                    orderDetail.put("ward", rs.getString("ward"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy thông tin chi tiết đơn hàng: " + e.getMessage());
            e.printStackTrace();
        }
        
        return orderDetail;
    }
    
    // Kiểm tra đơn hàng đã được đánh giá hay chưa
    public boolean hasOrderBeenReviewed(int orderId, int accountId) {
        String query = "SELECT COUNT(*) FROM ProductFeedback pf " +
                "JOIN OrderDetails od ON pf.order_detail_id = od.order_detail_id " +
                "JOIN Orders o ON od.order_id = o.order_id " +
                "WHERE o.order_id = ? AND o.account_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, orderId);
            ps.setInt(2, accountId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking if order has been reviewed: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Lấy feedback ID của một đơn hàng nếu đã được đánh giá
    public int getFeedbackIdForOrder(int orderId, int accountId) {
        String query = "SELECT pf.feedback_id FROM ProductFeedback pf " +
                "JOIN OrderDetails od ON pf.order_detail_id = od.order_detail_id " +
                "JOIN Orders o ON od.order_id = o.order_id " +
                "WHERE o.order_id = ? AND o.account_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, orderId);
            ps.setInt(2, accountId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("feedback_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting feedback ID for order: " + e.getMessage());
            e.printStackTrace();
        }
        
        return -1;
    }
    
    // Lấy thông tin đơn hàng để đánh giá từ orderId
    public List<Map<String, Object>> getOrderDetailsForFeedback(int orderId, int accountId) {
        List<Map<String, Object>> orderDetails = new ArrayList<>();
        String query = "SELECT od.*, p.product_name, p.product_image, p.product_id, p.product_price, " +
                "c.category_name, c.category_id, o.order_id, o.order_date, " +
                "o.shipping_fee, o.payment_method, os.status_name, " +
                "a.first_name, a.last_name, a.phone_number as phone, a.email, " +
                "po.option_name " +
                "FROM Orders o " +
                "JOIN OrderDetails od ON o.order_id = od.order_id " +
                "JOIN Products p ON od.product_id = p.product_id " +
                "JOIN Categories c ON p.category_id = c.category_id " +
                "JOIN OrderStatus os ON o.status_id = os.status_id " +
                "JOIN Account a ON o.account_id = a.account_id " +
                "LEFT JOIN Options po ON od.option_id = po.option_id " +
                "WHERE o.order_id = ? AND o.account_id = ? AND o.status_id = 4"; // status_id = 4: Delivered
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, orderId);
            ps.setInt(2, accountId);
            
            try (ResultSet rs = ps.executeQuery()) {
                // Lưu trữ thông tin chung về đơn hàng
                Map<String, Object> commonOrderInfo = new HashMap<>();
                boolean hasSetCommonInfo = false;
                
                while (rs.next()) {
                    Map<String, Object> orderDetail = new HashMap<>();
                    
                    // Chi tiết sản phẩm
                    orderDetail.put("orderDetailId", rs.getInt("order_detail_id"));
                    orderDetail.put("productId", rs.getInt("product_id"));
                    orderDetail.put("productName", rs.getString("product_name"));
                    orderDetail.put("productImage", rs.getString("product_image"));
                    orderDetail.put("originalPrice", rs.getBigDecimal("product_price"));
                    orderDetail.put("categoryId", rs.getInt("category_id"));
                    orderDetail.put("categoryName", rs.getString("category_name"));
                    orderDetail.put("quantity", rs.getInt("quantity"));
                    orderDetail.put("finalPrice", rs.getBigDecimal("final_price"));
                    orderDetail.put("optionName", rs.getString("option_name"));
                    
                    // Chi tiết đơn hàng - thông tin chung, chỉ lưu trữ một lần
                    if (!hasSetCommonInfo) {
                        commonOrderInfo.put("orderId", rs.getInt("order_id"));
                        commonOrderInfo.put("orderDate", rs.getTimestamp("order_date"));
                        commonOrderInfo.put("shippingFee", rs.getBigDecimal("shipping_fee"));
                        
                        // Sử dụng payment_method trực tiếp từ bảng Orders thay vì từ bảng PaymentMethod
                        commonOrderInfo.put("paymentMethod", rs.getString("payment_method"));
                        commonOrderInfo.put("orderStatus", rs.getString("status_name"));
                        
                        // Thông tin người đặt
                        commonOrderInfo.put("firstName", rs.getString("first_name"));
                        commonOrderInfo.put("lastName", rs.getString("last_name"));
                        commonOrderInfo.put("phone", rs.getString("phone"));
                        commonOrderInfo.put("email", rs.getString("email"));
                        
                        // Bỏ thông tin địa chỉ giao hàng vì không có bảng ShippingAddress
                        hasSetCommonInfo = true;
                    }
                    
                    // Thêm thông tin chung vào từng sản phẩm
                    orderDetail.putAll(commonOrderInfo);
                    orderDetails.add(orderDetail);
                    
                    System.out.println("From DB - Product: " + orderDetail.get("productName") + 
                            ", Order Detail ID: " + orderDetail.get("orderDetailId") + 
                            ", Option Name: '" + rs.getString("option_name") + "'");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy thông tin đơn hàng từ orderId: " + e.getMessage());
            e.printStackTrace();
        }
        
        return orderDetails;
    }
    
    // Lấy orderDetailId từ orderId
    public int getOrderDetailIdFromOrderId(int orderId, int accountId) {
        String query = "SELECT od.order_detail_id FROM OrderDetails od " +
                "JOIN Orders o ON od.order_id = o.order_id " +
                "WHERE o.order_id = ? AND o.account_id = ? " +
                "AND o.status_id = 4"; // status_id = 4: Delivered
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, orderId);
            ps.setInt(2, accountId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("order_detail_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy orderDetailId từ orderId: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
} 