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
        response.sendRedirect(request.getContextPath() + "/CustomerPostController");
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
        String content = request.getParameter("comment");
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        CustomerPostDAO cd = new CustomerPostDAO();
        String loggedInUser = null;
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
        int accountId = cd.getAccountId(loggedInUser);
        if (accountId < 0) {
            request.setAttribute("msg", "You are not logged in");
            request.getRequestDispatcher("cpost.jsp").forward(request, response);
            return;
        }

        // Tạo comment mới với Model đã cập nhật
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setAccountId(accountId);
        comment.setContent(content);
        comment.setCreatedDate(createdDate);

        cd.addComment(comment);
        response.sendRedirect(request.getContextPath() + "/CustomerPostController?postId="+ postId +"&show=true");
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
