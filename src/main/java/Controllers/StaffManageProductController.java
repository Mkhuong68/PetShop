package Controllers;

import DAOs.CategoryDAO;
import DAOs.StaffManageProductDAO;
import DB.DBConnection;
import Model.Category;
import Model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "StaffManageProductController", urlPatterns = {"/manageProduct"}) // Đổi URL mapping để tránh lỗi 404
public class StaffManageProductController extends HttpServlet {

    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            // Sử dụng phương thức getConnection từ lớp DBConnection
            conn = DBConnection.getConnection();
            if (conn != null) {
                CategoryDAO categoryDAO = new CategoryDAO(conn);
                System.out.println("Database connected successfully!");
            } else {
                throw new ServletException("Failed to establish database connection.");
            }
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            throw new ServletException(e);
        }
    }

    private final StaffManageProductDAO productDAO = new StaffManageProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        CategoryDAO categoryDAO = new CategoryDAO();

        List<Category> categories = categoryDAO.getAllCategories();
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "list":
                List<Product> products = productDAO.getAllProducts();
                request.setAttribute("products", products);
                request.getRequestDispatcher("manageProduct.jsp").forward(request, response);
                break;
            case "edit":
                int editId = Integer.parseInt(request.getParameter("name"));
                Product product = productDAO.getProductById(editId);
                request.setAttribute("product", product);
                request.getRequestDispatcher("editProduct.jsp").forward(request, response);
                break;
            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("name"));
                productDAO.deleteProduct(deleteId);
                response.sendRedirect("manageProduct");
                break;
        }
        request.setAttribute("categories", categories);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("manageProduct");
            return;
        }

        switch (action) {
            case "add":
            try {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                BigDecimal price = new BigDecimal(request.getParameter("price"));
                String image = request.getParameter("image");
                String categoryName = request.getParameter("category");
                int stock = Integer.parseInt(request.getParameter("stock"));

                // Kiểm tra tham số hợp lệ
                if (name == null || name.isEmpty() || price.compareTo(BigDecimal.ZERO) <= 0 || stock < 0 || categoryName == null || categoryName.isEmpty()) {
                    // Trả về lỗi nếu dữ liệu không hợp lệ
                    request.setAttribute("error", "Invalid input data.");
                    request.getRequestDispatcher("addProduct.jsp").forward(request, response);
                    return;
                }

                // Lấy category_id từ category_name
                CategoryDAO categoryDAO = new CategoryDAO();
                int categoryId = categoryDAO.getCategoryIdByName(categoryName);

                // Kiểm tra nếu không tìm thấy category_id
                if (categoryId == -1) {
                    request.setAttribute("error", "Category not found.");
                    request.getRequestDispatcher("addProduct.jsp").forward(request, response);
                    return;
                }
                boolean haveProduct = false;
                List<Product> productList = new ArrayList<>();
                productList = productDAO.getAllProducts();
                for (Product product : productList) {
                    if (name.equals(product.getProductName())) {
                        haveProduct = true;
                        break;
                    }
                }
                if (haveProduct == true) {
                    productDAO.addQuantityProductByName(name, stock);
                    response.sendRedirect("manageProduct");
                    return;
                } else {
                    Product newProduct = new Product(0, name, description, price, image, categoryId,
                            new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), false, stock, 0, 0);
                    productDAO.addProduct(newProduct);
                    response.sendRedirect("manageProduct");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid number format.");
                request.getRequestDispatcher("addProduct.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(StaffManageProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;

            case "update":
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String newName = request.getParameter("name");
                String newDescription = request.getParameter("description");
                BigDecimal newPrice = new BigDecimal(request.getParameter("price"));
                String newImage = request.getParameter("image");
                int newCategory = Integer.parseInt(request.getParameter("categoryId"));
                int newStock = Integer.parseInt(request.getParameter("stock"));
                // Kiểm tra tham số hợp lệ
                if (newName == null || newName.isEmpty() || newPrice.compareTo(BigDecimal.ZERO) <= 0 || newStock < 0 || newCategory <= 0) {
                    // Trả về lỗi nếu dữ liệu không hợp lệ
                    request.setAttribute("error", "Invalid input data.");
                    request.getRequestDispatcher("editProduct.jsp").forward(request, response);
                    return;
                }
                Product updatedProduct = new Product(id, newName, newDescription, newPrice, newImage, newCategory,
                        new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), false, newStock, 0, 0);
                productDAO.updateProduct(updatedProduct);
                response.sendRedirect("manageProduct");
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid number format.");
                request.getRequestDispatcher("editProduct.jsp").forward(request, response);
            }
            break;
        }
    }

}
