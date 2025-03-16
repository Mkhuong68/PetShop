/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAOs.ProductPromotionDAO;
import Model.ProductPromotion;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.*;


/**
 *
 * @author THANH THAO
 */
/**
 * Handles requests related to ProductPromotion.
 */

public class ProductPromotionController extends HttpServlet {

    private ProductPromotionDAO productPromotionDAO;

    public void init() {
        productPromotionDAO = new ProductPromotionDAO();
    }

    // Xử lý GET để lấy danh sách các Promotion và xóa một ProductPromotion
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // Nếu yêu cầu là xóa một ProductPromotion
        if (action != null && action.equals("delete")) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int promotionId = Integer.parseInt(request.getParameter("promotionId"));

            // Gọi DAO để xóa
            if (productPromotionDAO.deleteProductPromotion(productId, promotionId)) {
                response.sendRedirect("ProductPromotionController");
            } else {
                response.getWriter().println("Failed to delete ProductPromotion");
            }
        } else {
            // Lấy danh sách các ProductPromotions
            List<ProductPromotion> productPromotions = productPromotionDAO.getAllProductPromotions();
            request.setAttribute("productPromotions", productPromotions);
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewProductPromotions.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Xử lý POST khi thêm một promotion mới
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productIdStr = request.getParameter("productId");
        String promotionIdStr = request.getParameter("promotionId");

        int productId = Integer.parseInt(productIdStr);
        int promotionId = Integer.parseInt(promotionIdStr);

        // Lấy giá gốc và giá giảm từ form
        double originalPrice = Double.parseDouble(request.getParameter("originalPrice"));
        double discountedPrice = Double.parseDouble(request.getParameter("discountedPrice"));

        // Tạo đối tượng ProductPromotion mới và thêm vào cơ sở dữ liệu
        ProductPromotion productPromotion = new ProductPromotion(productId, promotionId, originalPrice, discountedPrice);

        if (productPromotionDAO.addProductPromotion(productPromotion)) {
            response.sendRedirect("ProductPromotionController");
        } else {
            response.getWriter().println("Failed to add ProductPromotion");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet to handle operations on ProductPromotion";
    }
}