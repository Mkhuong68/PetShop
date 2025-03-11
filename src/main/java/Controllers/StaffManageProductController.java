package Controllers;

import DAOs.StaffManageProductDAO;
import Model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                String image = request.getParameter("image");
                int category = Integer.parseInt(request.getParameter("category"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                
                Product newProduct = new Product(0, name, description, price, image, category, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), false, stock, 0, 0);
                productDAO.addProduct(newProduct);
                
                response.sendRedirect("manageProduct");
                break;
            case "update":
                int id = Integer.parseInt(request.getParameter("id"));
                String newName = request.getParameter("name");
                String newDescription = request.getParameter("description");
                double newPrice = Double.parseDouble(request.getParameter("price"));
                String newImage = request.getParameter("image");
                int newCategory = Integer.parseInt(request.getParameter("category"));
                int newStock = Integer.parseInt(request.getParameter("stock"));
                
                Product updatedProduct = new Product(id, newName, newDescription, newPrice, newImage, newCategory, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), false, newStock, 0, 0);
                productDAO.updateProduct(updatedProduct);
                
                response.sendRedirect("manageProduct");
                break;
        }
    }
}
