/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAOs.CategoryDAO;
import Model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author admin
 */
@WebServlet(name = "CategoryController", urlPatterns = {"/categories"})
public class CategoryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categories = categoryDAO.getAllCategories();

        // Gửi danh sách danh mục đến trang JSP để hiển thị
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("manageCategories.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Đảm bảo tiếng Việt không bị lỗi
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("CategoryController");
            return;
        }

        CategoryDAO categoryDAO = new CategoryDAO();

        switch (action) {
            case "add":
                addCategory(request, response, categoryDAO);
                break;
            case "update":
                updateCategory(request, response, categoryDAO);
                break;
            case "delete":
                deleteCategory(request, response, categoryDAO);
                break;
            default:
                response.sendRedirect("CategoryController");
                break;
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO)
            throws ServletException, IOException {
        String name = request.getParameter("category_name");
        String description = request.getParameter("category_description");
        String parentIdStr = request.getParameter("parent_category_id");
        String image = request.getParameter("category_image");
        boolean isHidden = request.getParameter("is_hidden") != null;

        Integer parentId = (parentIdStr != null && !parentIdStr.isEmpty()) ? Integer.parseInt(parentIdStr) : null;

        Category newCategory = new Category(0, name, description, parentId, image, isHidden, null, null);

        if (categoryDAO.insertCategory(newCategory)) {
            response.sendRedirect("CategoryController");
        } else {
            request.setAttribute("errorMessage", "Thêm danh mục thất bại.");
            processRequest(request, response);
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("category_id"));
        String name = request.getParameter("category_name");
        String description = request.getParameter("category_description");
        String parentIdStr = request.getParameter("parent_category_id");
        String image = request.getParameter("category_image");
        boolean isHidden = request.getParameter("is_hidden") != null;

        Integer parentId = (parentIdStr != null && !parentIdStr.isEmpty()) ? Integer.parseInt(parentIdStr) : null;

        Category updatedCategory = new Category(id, name, description, parentId, image, isHidden, null, null);

        if (categoryDAO.updateCategory(updatedCategory)) {
            response.sendRedirect("CategoryController");
        } else {
            request.setAttribute("errorMessage", "Cập nhật danh mục thất bại.");
            processRequest(request, response);
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("category_id"));

        if (categoryDAO.deleteCategory(id)) {
            response.sendRedirect("CategoryController");
        } else {
            request.setAttribute("errorMessage", "Xóa danh mục thất bại.");
            processRequest(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Category Controller";
    }
}
