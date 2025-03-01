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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class CustomerPostController extends HttpServlet {

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
            out.println("<title>Servlet CustomerPostController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerPostController at " + request.getContextPath() + "</h1>");
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
        CustomerPostDAO c = new CustomerPostDAO();
        List<Post> list = c.getAllPost();
        if (list != null) {
            request.setAttribute("list", list);
            request.getRequestDispatcher("/cpost.jsp").forward(request, response);
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
        CustomerPostDAO c = new CustomerPostDAO();
        
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
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            request.setAttribute("msg", "No user");
            request.getRequestDispatcher("cpost.jsp").forward(request, response);
            return;
        }
        int account_id = c.getAccountId(loggedInUser);
        if (account_id == -1) {
            request.setAttribute("msg", "No id");
            request.getRequestDispatcher("cpost.jsp").forward(request, response);
            return;
        }
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/CustomerPostController");
            return;
        }
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        int status_id = 3;
        Post p = new Post(0, account_id, title, content, status_id, createdDate);
        c.addPost(p);
        response.sendRedirect(request.getContextPath() + "/CustomerPostController");

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
