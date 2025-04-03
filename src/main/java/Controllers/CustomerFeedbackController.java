package Controllers;

import DAOs.CustomerFeedbackDAO;
import Model.Account;
import Model.ProductFeedback;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class CustomerFeedbackController extends HttpServlet {
    
    private CustomerFeedbackDAO customerFeedbackDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        customerFeedbackDAO = new CustomerFeedbackDAO();
    }

    // Helper method to forward request to a JSP view
    private void forward(HttpServletRequest request, HttpServletResponse response, String page) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin tài khoản đã đăng nhập
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        // Kiểm tra đăng nhập
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Kiểm tra role_id
        if (account.getRoleId() != 3) {
            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        }
        
        // Xác định action từ URL path
        String pathInfo = request.getPathInfo();
        String action = "list";
        
        if (pathInfo != null && pathInfo.length() > 1) {
            action = pathInfo.substring(1);
        }
        
        try {
            switch (action) {
                case "list":
                    listUserFeedbacks(request, response, account);
                    break;
                case "add":
                    if ("POST".equals(request.getMethod())) {
                        addFeedback(request, response, account);
                    } else {
                        showAddFeedbackForm(request, response, account);
                    }
                    break;
                case "edit":
                    if ("POST".equals(request.getMethod())) {
                        updateFeedback(request, response, account);
                    } else {
                        showEditFeedbackForm(request, response, account);
                    }
                    break;
                case "delete":
                    deleteFeedback(request, response, account);
                    break;
                default:
                    listUserFeedbacks(request, response, account);
            }
        } catch (Exception e) {
            System.out.println("Error in CustomerFeedbackController: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/feedback/list");
        }
    }
    
    // Hiển thị danh sách đánh giá của người dùng
    private void listUserFeedbacks(HttpServletRequest request, HttpServletResponse response, Account account) 
            throws ServletException, IOException {
        List<Map<String, Object>> userFeedbacks = customerFeedbackDAO.getUserFeedbacks(account.getAccountId());
        request.setAttribute("feedbacks", userFeedbacks);
        forward(request, response, "/userFeedbacks.jsp");
    }
    
    // Hiển thị form thêm đánh giá
    private void showAddFeedbackForm(HttpServletRequest request, HttpServletResponse response, Account account) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Lấy tham số orderId từ URL
        String orderIdParam = request.getParameter("orderId");
        
        if (orderIdParam == null || orderIdParam.isEmpty()) {
            session.setAttribute("errorMessage", "Order information not found!");
            response.sendRedirect(request.getContextPath() + "/CustomerOrderHistoryController");
            return;
        }
        
        try {
            int orderId = Integer.parseInt(orderIdParam);
            
            // Lấy thông tin chi tiết đơn hàng (danh sách sản phẩm)
            List<Map<String, Object>> orderDetails = customerFeedbackDAO.getOrderDetailsForFeedback(orderId, account.getAccountId());
            
            if (orderDetails == null || orderDetails.isEmpty()) {
                session.setAttribute("errorMessage", "Order information not found or you don't have permission to access it!");
                response.sendRedirect(request.getContextPath() + "/CustomerOrderHistoryController");
                return;
            }
            
            // Gán thông tin đơn hàng vào request
            request.setAttribute("orderDetails", orderDetails);
            request.setAttribute("orderId", orderId);
            
            // Gán thông tin chung của đơn hàng (lấy từ sản phẩm đầu tiên)
            request.setAttribute("orderInfo", orderDetails.get(0));
            
            // Chuyển đến trang thêm đánh giá
            forward(request, response, "/addFeedback.jsp");
            
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid order ID!");
            response.sendRedirect(request.getContextPath() + "/CustomerOrderHistoryController");
        }
    }
    
    // Xử lý thêm đánh giá
    private void addFeedback(HttpServletRequest request, HttpServletResponse response, Account account) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String content = request.getParameter("content");
        
        // Kiểm tra rating và đặt giá trị mặc định nếu không hợp lệ
        int rating = 5; // Default 5 stars
        String ratingParam = request.getParameter("rating");
        if (ratingParam != null && !ratingParam.isEmpty()) {
            try {
                rating = Integer.parseInt(ratingParam);
            } catch (NumberFormatException e) {
                // Keep default value
            }
        }
        
        // Lấy orderDetailId
        String orderDetailIdParam = request.getParameter("orderDetailId");
        String orderIdParam = request.getParameter("orderId");
        int orderDetailId = 0;
        
        // Kiểm tra xem orderDetailId có hợp lệ không
        if (orderDetailIdParam != null && !orderDetailIdParam.isEmpty()) {
            try {
                orderDetailId = Integer.parseInt(orderDetailIdParam);
            } catch (NumberFormatException e) {
                // Nếu không hợp lệ, thử lấy từ orderId
                if (orderIdParam != null && !orderIdParam.isEmpty()) {
                    try {
                        int orderId = Integer.parseInt(orderIdParam);
                        // Lấy orderDetailId từ orderId
                        orderDetailId = customerFeedbackDAO.getOrderDetailIdFromOrderId(orderId, account.getAccountId());
                    } catch (NumberFormatException ex) {
                        session.setAttribute("errorMessage", "Invalid order ID!");
                        response.sendRedirect(request.getContextPath() + "/CustomerOrderHistoryController");
                        return;
                    }
                }
            }
        } else if (orderIdParam != null && !orderIdParam.isEmpty()) {
            // Nếu không có orderDetailId nhưng có orderId
            try {
                int orderId = Integer.parseInt(orderIdParam);
                // Lấy orderDetailId từ orderId
                orderDetailId = customerFeedbackDAO.getOrderDetailIdFromOrderId(orderId, account.getAccountId());
            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Invalid order ID!");
                response.sendRedirect(request.getContextPath() + "/CustomerOrderHistoryController");
                return;
            }
        }
        
        // Kiểm tra xem đã có orderDetailId chưa
        if (orderDetailId <= 0) {
            session.setAttribute("errorMessage", "Order detail information not found!");
            response.sendRedirect(request.getContextPath() + "/CustomerOrderHistoryController");
            return;
        }
        
        ProductFeedback feedback = new ProductFeedback();
        feedback.setOrderDetailId(orderDetailId);
        feedback.setRating(rating);
        feedback.setComment(content);
        feedback.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        
        int feedbackId = customerFeedbackDAO.addFeedback(feedback);
        if (feedbackId > 0) {
            session.setAttribute("successMessage", "Feedback added successfully!");
        } else {
            session.setAttribute("errorMessage", "Could not add feedback. Please try again later!");
        }
        
        response.sendRedirect(request.getContextPath() + "/feedback/list");
    }
    
    // Hiển thị form chỉnh sửa đánh giá
    private void showEditFeedbackForm(HttpServletRequest request, HttpServletResponse response, Account account) 
            throws ServletException, IOException {
        int feedbackId = Integer.parseInt(request.getParameter("id"));
        Map<String, Object> feedbackDetail = customerFeedbackDAO.getFeedbackDetail(feedbackId, account.getAccountId());
        
        if (feedbackDetail != null && !feedbackDetail.isEmpty()) {
            request.setAttribute("feedback", feedbackDetail);
            forward(request, response, "/updateFeedback.jsp");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Feedback not found or you don't have permission to edit it!");
            response.sendRedirect(request.getContextPath() + "/feedback/list");
        }
    }
    
    // Xử lý cập nhật đánh giá
    private void updateFeedback(HttpServletRequest request, HttpServletResponse response, Account account) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
        String content = request.getParameter("content");
        int rating = Integer.parseInt(request.getParameter("rating"));
        
        Map<String, Object> feedbackDetail = customerFeedbackDAO.getFeedbackDetail(feedbackId, account.getAccountId());
        if (feedbackDetail != null && !feedbackDetail.isEmpty()) {
            ProductFeedback feedback = new ProductFeedback();
            feedback.setFeedbackId(feedbackId);
            feedback.setOrderDetailId((Integer) feedbackDetail.get("orderDetailId"));
            feedback.setRating(rating);
            feedback.setComment(content);
            feedback.setLastUpdated(new Timestamp(System.currentTimeMillis()));
            
            boolean success = customerFeedbackDAO.updateFeedback(feedback);
            if (success) {
                session.setAttribute("successMessage", "Feedback updated successfully!");
            } else {
                session.setAttribute("errorMessage", "Could not update feedback. Please try again later!");
            }
        } else {
            session.setAttribute("errorMessage", "You don't have permission to update this feedback!");
        }
        
        response.sendRedirect(request.getContextPath() + "/feedback/list");
    }
    
    // Xử lý xóa đánh giá
    private void deleteFeedback(HttpServletRequest request, HttpServletResponse response, Account account) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int feedbackId = Integer.parseInt(request.getParameter("id"));
        
        if (customerFeedbackDAO.isFeedbackOwner(feedbackId, account.getAccountId())) {
            boolean success = customerFeedbackDAO.deleteFeedback(feedbackId, account.getAccountId());
            if (success) {
                session.setAttribute("successMessage", "Feedback deleted successfully!");
            } else {
                session.setAttribute("errorMessage", "Could not delete feedback. Please try again later!");
            }
        } else {
            session.setAttribute("errorMessage", "You don't have permission to delete this feedback!");
        }
        
        response.sendRedirect(request.getContextPath() + "/feedback/list");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Customer Feedback Controller";
    }
} 