package Controllers;

import DAOs.CustomerPostDAO;
import Model.Account;
import Model.Comment;
import Model.Post;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        String pId = request.getParameter("postId");

        Post post = null;
        List<Comment> comments = new ArrayList<>();

        if (pId != null) {
            try {
                int postId = Integer.parseInt(pId);
                post = c.getPostbyId(postId);
                if (post != null) {
                    comments = c.getCommentsByPostId(postId); 
                }
            } catch (NumberFormatException e) {
                request.setAttribute("msg", "Invalid post ID");
            }
        }

        List<Post> list = c.getAllPost();

        request.setAttribute("post", post);
        request.setAttribute("comments", comments);
        request.setAttribute("list", list);

        request.getRequestDispatcher("/cpost.jsp").forward(request, response);
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
        String loggedInUser = null;
        CustomerPostDAO c = new CustomerPostDAO();

        // Kiem tra co dang nhap hay khong (session hay cookie)
        HttpSession session = request.getSession();
        Account account = (Account) request.getSession().getAttribute("account");
        if (account != null) {
            loggedInUser = account.getUsername();
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        loggedInUser = cookie.getValue();
                        break;
                    }
                }
            } else {
                request.setAttribute("msg", "No cookie");
                request.getRequestDispatcher("cpost.jsp").forward(request, response);
                return;
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

        // Kiem tra du lieu nhap vao, khong duoc de trong
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/CustomerPostController");
            return;
        }
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        int status_id = 3;

        // Sử dụng model đã cập nhật
        Post post = new Post();
        post.setAccountId(account_id);
        post.setTitle(title);
        post.setContent(content);
        post.setStatusId(status_id);
        post.setCreatedDate(createdDate);

        c.addPost(post);
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
