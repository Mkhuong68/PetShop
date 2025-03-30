/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.StaffPostDAO;
import Model.Post;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author THANH THAO
 */
public class StaffPostController extends HttpServlet {

    private StaffPostDAO staffPostDAO;

    @Override
    public void init() throws ServletException {
        staffPostDAO = new StaffPostDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Post> posts = staffPostDAO.getAllCustomerPosts();
        request.setAttribute("posts", posts);
        request.getRequestDispatcher("staffPostList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int postId = Integer.parseInt(request.getParameter("postId"));

        if ("accept".equals(action)) {
            staffPostDAO.acceptPost(postId);
        } else if ("delete".equals(action)) {
            staffPostDAO.deletePost(postId);
        }
        response.sendRedirect(request.getContextPath() + "/StaffPostController");
    }

    @Override
    public String getServletInfo() {
        return "Staff Post Management Controller";
    }
}
