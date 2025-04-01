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
            List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
            // Debug: Check the number of products retrieved
            System.out.println("Number of products retrieved: " + (products != null ? products.size() : 0));
            if (products == null || products.isEmpty()) {
                request.setAttribute("errorMessage", "No products available to display in the dropdown. Please check the Products table.");
            }
            request.setAttribute("products", products);
            RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
            dispatcher.forward(request, response);
        } else if ("edit".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int promotionId = Integer.parseInt(request.getParameter("promotionId"));
            ProductPromotion promotion = productPromotionDAO.getPromotionById(productId, promotionId);
            if (promotion != null) {
                request.setAttribute("promotion", promotion);
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editPromotion.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Promotion not found.");
                List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
                request.setAttribute("productPromotions", productPromotions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
                dispatcher.forward(request, response);
            }
        } else if ("delete".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int promotionId = Integer.parseInt(request.getParameter("promotionId"));
            ProductPromotion promotion = productPromotionDAO.getPromotionById(productId, promotionId);
            if (promotion != null) {
                request.setAttribute("promotion", promotion);
                RequestDispatcher dispatcher = request.getRequestDispatcher("deletePromotion.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Promotion not found.");
                List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
                request.setAttribute("productPromotions", productPromotions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
                dispatcher.forward(request, response);
            }
        } else if ("list".equals(action)) {
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
            String productIdStr = request.getParameter("productId");
            String discountPercentageStr = request.getParameter("discountPercentage");
            String validFromStr = request.getParameter("validFrom");
            String validToStr = request.getParameter("validTo");

            // Validate input data
            if (productIdStr == null || productIdStr.isEmpty() || discountPercentageStr == null || discountPercentageStr.isEmpty()
                    || validFromStr == null || validFromStr.isEmpty() || validToStr == null || validToStr.isEmpty()) {
                request.setAttribute("errorMessage", "Please fill in all required fields: Product name, discount percentage, start date, and end date.");
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            int productId;
            int discountPercentage;
            try {
                productId = Integer.parseInt(productIdStr);
                discountPercentage = Integer.parseInt(discountPercentageStr);
                if (discountPercentage < 0 || discountPercentage > 100) {
                    throw new NumberFormatException("Discount percentage must be between 0 and 100.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid data: " + e.getMessage());
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Convert date strings to Date objects
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Sửa định dạng ngày
            Date validFrom, validTo;
            try {
                validFrom = dateFormat.parse(validFromStr);
                validTo = dateFormat.parse(validToStr);
                if (validTo.before(validFrom)) {
                    throw new Exception("End date must be after start date.");
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Invalid date: " + e.getMessage());
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Get the original price of the product
            ProductPromotion product = productPromotionDAO.getProductById(productId);
            if (product == null) {
                request.setAttribute("errorMessage", "Product not found: " + productId);
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Create a record in the Promotions table
            int createdBy = 1; // Assume admin (account_id = 1). You can modify to get from session if needed.
            int promotionId = productPromotionDAO.createPromotion(discountPercentage, validFrom, validTo, createdBy);
            if (promotionId == -1) {
                request.setAttribute("errorMessage", "Failed to create promotion. Check the logs for details.");
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Add to the ProductPromotions table
            ProductPromotion productPromotion = new ProductPromotion();
            productPromotion.setProductId(productId);
            productPromotion.setPromotionId(promotionId);
            productPromotion.setDiscountPercentage(discountPercentage);
            productPromotion.setPromotionValidFrom(validFrom);
            productPromotion.setPromotionValidTo(validTo);

            if (productPromotionDAO.addPromotion(productPromotion)) {
                request.setAttribute("successMessage", "Promotion added successfully!");
                List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
                request.setAttribute("productPromotions", productPromotions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to add promotion. Check the logs for details. product_id=" + productId + ", promotion_id=" + promotionId);
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("addPromotion.jsp");
                dispatcher.forward(request, response);
            }
        } else if ("update".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int promotionId = Integer.parseInt(request.getParameter("promotionId"));
            String discountPercentageStr = request.getParameter("discountPercentage");

            // Validate input data
            if (discountPercentageStr == null || discountPercentageStr.isEmpty()) {
                request.setAttribute("errorMessage", "Discount percentage cannot be empty.");
                ProductPromotion promotion = productPromotionDAO.getPromotionById(productId, promotionId);
                request.setAttribute("promotion", promotion);
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            int discountPercentage;
            try {
                discountPercentage = Integer.parseInt(discountPercentageStr);
                if (discountPercentage < 0 || discountPercentage > 100) {
                    throw new NumberFormatException("Discount percentage must be between 0 and 100.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid discount percentage: " + discountPercentageStr);
                ProductPromotion promotion = productPromotionDAO.getPromotionById(productId, promotionId);
                request.setAttribute("promotion", promotion);
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editPromotion.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Get the current promotion details
            ProductPromotion existingPromotion = productPromotionDAO.getPromotionById(productId, promotionId);
            if (existingPromotion == null) {
                request.setAttribute("errorMessage", "Promotion not found.");
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
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
                request.setAttribute("successMessage", "Promotion updated successfully!");
                List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
                request.setAttribute("productPromotions", productPromotions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to update promotion.");
                ProductPromotion updatedPromotion = productPromotionDAO.getPromotionById(productId, promotionId);
                request.setAttribute("promotion", updatedPromotion);
                List<ProductPromotion> products = productPromotionDAO.getProductsWithPrice();
                request.setAttribute("products", products);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editPromotion.jsp");
                dispatcher.forward(request, response);
            }
        } else if ("delete".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int promotionId = Integer.parseInt(request.getParameter("promotionId"));
            if (productPromotionDAO.deletePromotion(productId, promotionId)) {
                request.setAttribute("successMessage", "Promotion deleted successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to delete promotion.");
            }
            List<ProductPromotion> productPromotions = productPromotionDAO.getAllPromotions();
            request.setAttribute("productPromotions", productPromotions);
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewPromotion.jsp");
            dispatcher.forward(request, response);
        }
    }
}
