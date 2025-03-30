/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.StaffCommentDAO;
import Model.Comment;
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


public class StaffCommentController extends HttpServlet {

    private StaffCommentDAO staffCommentDAO;

    @Override
    public void init() throws ServletException {
        staffCommentDAO = new StaffCommentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Comment> comments = staffCommentDAO.getAllCustomerComments();
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("staffCommentList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int commentId = Integer.parseInt(request.getParameter("commentId"));

        if ("delete".equals(action)) {
            staffCommentDAO.deleteComment(commentId);
        }
        response.sendRedirect(request.getContextPath() + "/StaffCommentController");
    }

    @Override
    public String getServletInfo() {
        return "Staff Comment Management Controller";
    }
}