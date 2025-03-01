/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.ProductDAO;
import DAOs.CategoryDAO;
import Model.Product;
import Model.Category;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *
 * @author tvhun
 */
public class ProductListController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductListController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy các tham số lọc và sắp xếp từ request
        String[] categoryIds = request.getParameterValues("category");
        String[] priceRanges = request.getParameterValues("priceRange");
        String sort = request.getParameter("sort");

        List<Product> productList;
        // Nếu có tham số lọc hoặc sắp xếp, sử dụng hàm getFilteredProducts để lấy sản phẩm theo điều kiện
        if ((categoryIds != null && categoryIds.length > 0)
                || (priceRanges != null && priceRanges.length > 0)
                || (sort != null && !sort.isEmpty())) {
            productList = productDAO.getFilteredProducts(categoryIds, priceRanges, sort);
        } else {
            // Nếu không có tham số lọc, lấy toàn bộ sản phẩm và áp dụng sắp xếp nếu có
            productList = productDAO.getAllProducts(sort);
        }
        request.setAttribute("productList", productList);

        // Lấy danh sách danh mục để hiển thị trong sidebar
        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("categories", categories);

        // Forward tới productlist.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("productlist.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển hướng doPost sang doGet để xử lý các tham số lọc và sắp xếp
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet ProductListController tích hợp lọc sản phẩm theo danh mục, khoảng giá và sắp xếp.";
    }// </editor-fold>

}
