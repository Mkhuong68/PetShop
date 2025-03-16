package Controllers;

import DAOs.CategoryDAO;
import DB.DBConnection;
import Model.Category;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class CategoryController extends HttpServlet {

    private Connection conn;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(
                    "jdbc:sqlserver://LAPTOP-HKO7018L\\KHUONGTM:1433;databaseName=PetShop;encrypt=false;trustServerCertificate=true",
                    "sa",
                    "123456"
            );

            categoryDAO = new CategoryDAO(conn);
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    RequestDispatcher dispatcherNew = request.getRequestDispatcher("addCategory.jsp");
                    dispatcherNew.forward(request, response);
                    break;
                case "edit":
                    int categoryId = Integer.parseInt(request.getParameter("id"));
                    Category existingCategory = categoryDAO.getCategoryById(categoryId);
                    request.setAttribute("category", existingCategory);
                    RequestDispatcher dispatcherEdit = request.getRequestDispatcher("updateCategory.jsp");
                    dispatcherEdit.forward(request, response);
                    break;

                case "delete":
                    categoryId = Integer.parseInt(request.getParameter("id"));
                    categoryDAO.deleteCategory(categoryId);
                    response.sendRedirect("CategoryController?action=list");
                    break;
                default:
                    List<Category> categories = categoryDAO.getAllCategories();
                    if (categories == null) {
                        categories = new ArrayList<>();
                    }
                    System.out.println("Controller - Categories found: " + categories.size()); // Debug

                    request.setAttribute("categoryList", categories);
                    RequestDispatcher dispatcherList = request.getRequestDispatcher("manageCategory.jsp");
                    dispatcherList.forward(request, response);
                    break;

            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "insert":
                    Category newCategory = new Category();
                    String name = request.getParameter("name");
                    String des = request.getParameter("description");
                    int parentId = Integer.parseInt(request.getParameter("parentId"));
                    
                    boolean isAdded = categoryDAO.addCategory(name, des, parentId);
                    if (isAdded) {
                        response.sendRedirect("CategoryController?action=list");
                    } else {
                        response.sendRedirect("manageStaff.jsp");
                    }
                    break;
                case "update":
                    Category updatedCategory = new Category();
                    updatedCategory.setCategoryId(Integer.parseInt(request.getParameter("id")));
                    updatedCategory.setCategoryName(request.getParameter("name"));
                    updatedCategory.setCategoryDescription(request.getParameter("description"));
                    updatedCategory.setParentCategoryId(request.getParameter("parentId").isEmpty() ? null : Integer.parseInt(request.getParameter("parentId")));
                    updatedCategory.setIsHidden(request.getParameter("hidden") != null);
                    categoryDAO.updateCategory(updatedCategory);
                    response.sendRedirect("CategoryController?action=list");
                    break;
                case "delete":
                    int categoryIdToDelete = Integer.parseInt(request.getParameter("id"));
                    categoryDAO.deleteCategory(categoryIdToDelete);
                    response.sendRedirect("CategoryController?action=list");
                    break;

            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
