/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.CustomerPostDAO;
import Model.Comment;
import Model.Post;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class CustomerCommentController extends HttpServlet {

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
            out.println("<title>Servlet CustomerCommentController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerCommentController at " + request.getContextPath() + "</h1>");
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
        String pId = request.getParameter("postId");

        int postId = Integer.parseInt(pId);
        CustomerPostDAO cd = new CustomerPostDAO();
        Post post = cd.getPostbyId(postId);
        if (post != null) {
            List<Comment> comments = cd.getCommentsByPostId(postId);
            if (comments.size() < 1) {
                request.setAttribute("msg", "No comments");
            } else {
                request.setAttribute("comments", comments);
            }
            request.setAttribute("post", post);
            request.getRequestDispatcher("comment.jsp").forward(request, response);
        }

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
        String pId = request.getParameter("postId");
        int postId = Integer.parseInt(pId);
        String comment = request.getParameter("comment");
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        CustomerPostDAO cd = new CustomerPostDAO();
        Cookie[] cookies = request.getCookies();
        String loggedInUser = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    loggedInUser = cookie.getValue();
                    break;
                }
            }
        }
        int accountId = cd.getAccountId(loggedInUser);
        if (accountId < 0) {
            request.setAttribute("msg", "You are not logged in");
            request.getRequestDispatcher("comment.jsp").forward(request, response);
            return;
        }
        cd.addComment(new Comment(0, postId, accountId, comment, createdDate));
        response.sendRedirect(request.getContextPath() + "/CustomerCommentController?postId=" + postId);
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
