/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.StaffPostDAO;
import Model.Post;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author THANH THAO
 */

@WebServlet("/StaffManagePosts")
public class StaffManagePostsController extends HttpServlet {

    private final StaffPostDAO staffPostDAO = new StaffPostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("signinandlogin.jsp");
            return;
        }

        // Lấy danh sách bài viết từ DAO
        List<Post> posts = staffPostDAO.getAllPosts();
        request.setAttribute("posts", posts);

        // Hiển thị thông báo nếu có
        String success = request.getParameter("success");
        if (success != null) {
            request.setAttribute("message", success.equals("accept") ? "Post accepted successfully." : "Post rejected successfully.");
        }
        
        String error = request.getParameter("error");
        if (error != null) {
            request.setAttribute("message", error.equals("accept_failed") ? "Failed to accept the post." : "Failed to reject the post.");
        }

        request.getRequestDispatcher("staff-manage-posts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int postId = Integer.parseInt(request.getParameter("id"));
        boolean success = false;

        if ("accept".equals(action)) {
            success = staffPostDAO.acceptPost(postId);  // Duyệt bài viết
        } else if ("reject".equals(action)) {
            String rejectReason = request.getParameter("rejectReason");
            success = staffPostDAO.rejectPost(postId, rejectReason);  // Từ chối bài viết
        }

        if (success) {
            response.sendRedirect("StaffManagePosts?success=" + action);
        } else {
            response.sendRedirect("StaffManagePosts?error=" + action + "_failed");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles requests related to managing posts for Staff users.";
    }
}
