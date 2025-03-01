/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.CustomerPostDAO;
import Model.Post;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

/**
 *
 * @author NgocNNCE181950
 */
public class CustomerUpdatePostController extends HttpServlet {

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
            out.println("<title>Servlet CustomerUpdatePostController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerUpdatePostController at " + request.getContextPath() + "</h1>");
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
        CustomerPostDAO cd = new CustomerPostDAO();
        int postId = Integer.parseInt(pId);
        Post p = cd.getPostbyId(postId);
        if (!Post.isEmpty(p)) {
            request.setAttribute("data", p);
            request.getRequestDispatcher("upost.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/CustomerPostController");
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
        String title = request.getParameter("title");
        String content = request.getParameter("content");
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
        Post post = cd.getPostbyId(postId);
        int accountId = cd.getAccountId(loggedInUser);
        if (accountId < 0) {
            request.setAttribute("msg", "You are not logged in yet");
            request.setAttribute("data", post);
            request.getRequestDispatcher("upost.jsp").forward(request, response);
            return;
        }
        if (post == null) {
            request.setAttribute("msg", "The post does not exist.");
            request.setAttribute("data", post);
            request.getRequestDispatcher("upost.jsp").forward(request, response);
            return;
        }
        if (!cd.isPostOwner(postId, accountId)) {
            request.setAttribute("msg", "You do not have the right to update this post!");
            request.setAttribute("data", post);
            request.getRequestDispatcher("upost.jsp").forward(request, response);
            return;
        }
        if (title.isEmpty() || content.isEmpty()) {
            request.setAttribute("msg", "Use blank title or post content!");
            request.setAttribute("data", post);
            request.getRequestDispatcher("upost.jsp").forward(request, response);
        } else {
            cd.UpdatePost(new Post(postId, accountId, title, content, post.getStatusId(), post.getCreatedDate()));
            response.sendRedirect(request.getContextPath() + "/CustomerPostController");
        }
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
