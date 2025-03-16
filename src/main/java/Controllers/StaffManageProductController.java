package Controllers;

import DAOs.StaffManageProductDAO;
import Model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "StaffManageProductController", urlPatterns = {"/manageProduct"}) // Đổi URL mapping để tránh lỗi 404
public class StaffManageProductController extends HttpServlet {

    private final StaffManageProductDAO productDAO = new StaffManageProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
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
                int editId = Integer.parseInt(request.getParameter("id"));
                Product product = productDAO.getProductById(editId);
                request.setAttribute("product", product);
                request.getRequestDispatcher("editProduct.jsp").forward(request, response);
                break;
            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                productDAO.deleteProduct(deleteId);
                response.sendRedirect("manageProduct");
                break;
        }
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
                int category = Integer.parseInt(request.getParameter("category"));
                int stock = Integer.parseInt(request.getParameter("stock"));
// Kiểm tra tham số hợp lệ
                if (name == null || name.isEmpty() || price.compareTo(BigDecimal.ZERO) <= 0 || stock < 0 || category <= 0) {
                    // Trả về lỗi nếu dữ liệu không hợp lệ
                    request.setAttribute("error", "Invalid input data.");
                    request.getRequestDispatcher("addProduct.jsp").forward(request, response);
                    return;
                }
                Product newProduct = new Product(0, name, description, price, image, category,
                        new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), false, stock, 0, 0);
                productDAO.addProduct(newProduct);
                response.sendRedirect("manageProduct");
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid number format.");
                request.getRequestDispatcher("addProduct.jsp").forward(request, response);
            }
            break;

            case "update":
try {
                int id = Integer.parseInt(request.getParameter("id"));
                String newName = request.getParameter("name");
                String newDescription = request.getParameter("description");
                BigDecimal newPrice = new BigDecimal(request.getParameter("price"));
                String newImage = request.getParameter("image");
                int newCategory = Integer.parseInt(request.getParameter("category"));
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
