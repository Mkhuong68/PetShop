/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.PostDAO;
import Model.Account;
import Model.Post;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;


/**
 *
 * @author tvhun
 */
public class CommunityController extends HttpServlet {

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
        
        // Lấy danh sách bài viết
        PostDAO postDAO = new PostDAO();
        
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        List<Post> posts;
        boolean isLoggedIn = (account != null);
        
        if (isLoggedIn) {
            // Nếu đã đăng nhập, lấy danh sách post với thông tin reaction của người dùng
            posts = postDAO.getAllPosts(account.getAccountId());
        } else {
            // Nếu chưa đăng nhập, vẫn lấy danh sách post nhưng không có thông tin reaction
            posts = postDAO.getAllPostsForGuest();
        }
        
        // Đánh dấu người dùng đã đăng nhập hay chưa để JSP hiển thị UI phù hợp
        request.setAttribute("isLoggedIn", isLoggedIn);
        request.setAttribute("posts", posts);
        request.getRequestDispatcher("community.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
