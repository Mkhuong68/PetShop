package Controllers;

import DAOs.ProductDAO;
import Model.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProductController")
public class ProductController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String productName = request.getParameter("productName");
            String productDescription = request.getParameter("productDescription");
            double productPrice = Double.parseDouble(request.getParameter("productPrice"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            Product product = new Product();
            product.setProductName(productName);
            product.setProductDescription(productDescription);
            product.setProductPrice(productPrice);
            product.setCategoryId(categoryId);

            productDAO.createProduct(product);
            response.sendRedirect("productManagement.jsp");

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.deleteProduct(id);
            response.sendRedirect("productManagement.jsp");

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String productName = request.getParameter("productName");
            String productDescription = request.getParameter("productDescription");
            double productPrice = Double.parseDouble(request.getParameter("productPrice"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            Product product = new Product();
            product.setProductId(id);
            product.setProductName(productName);
            product.setProductDescription(productDescription);
            product.setProductPrice(productPrice);
            product.setCategoryId(categoryId);

            productDAO.updateProduct(id, product);
            response.sendRedirect("productManagement.jsp");
        }
    }
}