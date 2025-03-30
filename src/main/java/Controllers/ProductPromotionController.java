/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
/**
 *
 * @author THANH THAO
 */
package Controllers;

import DAOs.ProductPromotionDAO;
import Model.ProductPromotion;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/ProductPromotionController")
public class ProductPromotionController extends HttpServlet {

    private ProductPromotionDAO productPromotionDAO;

    @Override
    public void init() {
        productPromotionDAO = new ProductPromotionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || "view".equals(action)) {
            List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
            request.setAttribute("productPromotions", productPromotions);
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
            dispatcher.forward(request, response);
        } else if ("add".equals(action)) {
            List<String> productNames = productPromotionDAO.getProductNames();
            List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
            request.setAttribute("productNames", productNames);
            request.setAttribute("products", products);
            RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
            dispatcher.forward(request, response);
        } else if ("edit".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int promotionId = Integer.parseInt(request.getParameter("promotionId"));
            ProductPromotion promotion = productPromotionDAO.getPromotionById(productId, promotionId);
            if (promotion != null) {
                request.setAttribute("promotion", promotion);
                List<String> productNames = productPromotionDAO.getProductNames();
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("productNames", productNames);
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editPromotion.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Khuyến mãi không tồn tại.");
                List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
                request.setAttribute("productPromotions", productPromotions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
                dispatcher.forward(request, response);
            }
        } else if ("delete".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int promotionId = Integer.parseInt(request.getParameter("promotionId"));
            if (productPromotionDAO.deletePromotion(productId, promotionId)) {
                request.setAttribute("successMessage", "Xóa khuyến mãi thành công!");
                List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
                request.setAttribute("productPromotions", productPromotions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Xóa khuyến mãi thất bại.");
                List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
                request.setAttribute("productPromotions", productPromotions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
                dispatcher.forward(request, response);
            }
        } else if ("list".equals(action)) {
            // Hiển thị danh sách khuyến mãi để chọn chỉnh sửa (cho Update Promotion)
            List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
            request.setAttribute("promotions", productPromotions);
            RequestDispatcher dispatcher = request.getRequestDispatcher("updatePromotion.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String productName = request.getParameter("productName");
            String discountPercentageStr = request.getParameter("discountPercentage");
            String validFromStr = request.getParameter("validFrom");
            String validToStr = request.getParameter("validTo");

            // Kiểm tra dữ liệu đầu vào
            if (productName == null || productName.isEmpty() || discountPercentageStr == null || discountPercentageStr.isEmpty()
                    || validFromStr == null || validFromStr.isEmpty() || validToStr == null || validToStr.isEmpty()) {
                request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin: Tên sản phẩm, phần trăm giảm giá, ngày bắt đầu, ngày kết thúc.");
                List<String> productNames = productPromotionDAO.getProductNames();
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("productNames", productNames);
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            int discountPercentage;
            try {
                discountPercentage = Integer.parseInt(discountPercentageStr);
                if (discountPercentage < 0 || discountPercentage > 100) {
                    throw new NumberFormatException("Phần trăm khuyến mãi phải từ 0 đến 100.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Phần trăm khuyến mãi không hợp lệ: " + discountPercentageStr);
                List<String> productNames = productPromotionDAO.getProductNames();
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("productNames", productNames);
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Chuyển đổi ngày từ String sang Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date validFrom, validTo;
            try {
                validFrom = dateFormat.parse(validFromStr);
                validTo = dateFormat.parse(validToStr);
                if (validTo.before(validFrom)) {
                    throw new Exception("Ngày kết thúc phải sau ngày bắt đầu.");
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Ngày không hợp lệ: " + e.getMessage());
                List<String> productNames = productPromotionDAO.getProductNames();
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("productNames", productNames);
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Kiểm tra productId từ productName
            int productId = productPromotionDAO.getProductIdByName(productName);
            if (productId == -1) {
                request.setAttribute("errorMessage", "Sản phẩm không tồn tại: " + productName);
                List<String> productNames = productPromotionDAO.getProductNames();
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("productNames", productNames);
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Tạo bản ghi trong bảng Promotions
            int createdBy = 1; // Giả sử admin (account_id = 1)
            int promotionId = productPromotionDAO.createPromotion(discountPercentage, createdBy);
            if (promotionId == -1) {
                request.setAttribute("errorMessage", "Tạo khuyến mãi thất bại. Kiểm tra log để biết chi tiết.");
                List<String> productNames = productPromotionDAO.getProductNames();
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("productNames", productNames);
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Thêm vào bảng ProductPromotions
            ProductPromotion productPromotion = new ProductPromotion();
            productPromotion.setProductId(productId);
            productPromotion.setPromotionId(promotionId);

            if (productPromotionDAO.addPromotion(productPromotion)) {
                request.setAttribute("successMessage", "Thêm khuyến mãi thành công!");
                List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
                request.setAttribute("productPromotions", productPromotions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Thêm khuyến mãi thất bại. Kiểm tra log để biết chi tiết. product_id=" + productId + ", promotion_id=" + promotionId);
                List<String> productNames = productPromotionDAO.getProductNames();
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("productNames", productNames);
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
            }
        } else if ("update".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int promotionId = Integer.parseInt(request.getParameter("promotionId"));
            String discountPercentageStr = request.getParameter("discountPercentage");

            // Kiểm tra dữ liệu đầu vào
            if (discountPercentageStr == null || discountPercentageStr.isEmpty()) {
                request.setAttribute("errorMessage", "Phần trăm khuyến mãi không được để trống.");
                ProductPromotion promotion = productPromotionDAO.getPromotionById(productId, promotionId);
                request.setAttribute("promotion", promotion);
                List<String> productNames = productPromotionDAO.getProductNames();
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("productNames", productNames);
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            int discountPercentage;
            try {
                discountPercentage = Integer.parseInt(discountPercentageStr);
                if (discountPercentage < 0 || discountPercentage > 100) {
                    throw new NumberFormatException("Phần trăm khuyến mãi phải từ 0 đến 100.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Phần trăm khuyến mãi không hợp lệ: " + discountPercentageStr);
                ProductPromotion promotion = productPromotionDAO.getPromotionById(productId, promotionId);
                request.setAttribute("promotion", promotion);
                List<String> productNames = productPromotionDAO.getProductNames();
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("productNames", productNames);
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            ProductPromotion promotion = new ProductPromotion();
            promotion.setProductId(productId);
            promotion.setPromotionId(promotionId);
            promotion.setDiscountPercentage(discountPercentage);

            if (productPromotionDAO.updatePromotion(promotion)) {
                request.setAttribute("successMessage", "Cập nhật khuyến mãi thành công!");
                List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
                request.setAttribute("productPromotions", productPromotions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Cập nhật khuyến mãi thất bại.");
                ProductPromotion updatedPromotion = productPromotionDAO.getPromotionById(productId, promotionId);
                request.setAttribute("promotion", updatedPromotion);
                List<String> productNames = productPromotionDAO.getProductNames();
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("productNames", productNames);
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editPromotion.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}
