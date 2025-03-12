/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.ProductFeedbackDAO;
import Model.ProductFeedback;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.util.Map;

/**
 *
 * @author tvhun
 */
public class FeedbackController extends HttpServlet {

    private ProductFeedbackDAO feedbackDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        feedbackDAO = new ProductFeedbackDAO();
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
        request.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/list";
        }

        try {
            switch (pathInfo) {
                case "/list":
                    viewFeedbackList(request, response);
                    break;
                case "/details":
                    viewFeedbackDetail(request, response);
                    break;
                case "/reply":
                    if ("POST".equals(request.getMethod())) {
                        replyFeedback(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/ManageFeedback/list");
                    }
                    break;
                default:
                    if (pathInfo.startsWith("/details/")) {
                        String[] splits = pathInfo.split("/");
                        if (splits.length >= 3) {
                            try {
                                int feedbackId = Integer.parseInt(splits[2]);
                                viewFeedbackDetailById(request, response, feedbackId);
                            } catch (NumberFormatException e) {
                                response.sendRedirect(request.getContextPath() + "/ManageFeedback/list");
                            }
                        } else {
                            response.sendRedirect(request.getContextPath() + "/ManageFeedback/list");
                        }
                    } else {
                        response.sendRedirect(request.getContextPath() + "/ManageFeedback/list");
                    }
                    break;
            }
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/ManageFeedback/list");
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
        return "Feedback management controller";
    }// </editor-fold>

    // Display feedback list
    private void viewFeedbackList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String, Object>> feedbackList = feedbackDAO.getAllFeedbackWithDetails();
        request.setAttribute("feedbackList", feedbackList);
        request.getRequestDispatcher("/feedbackList.jsp").forward(request, response);
    }

    // Display feedback detail by ID parameter
    private void viewFeedbackDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int feedbackId = Integer.parseInt(request.getParameter("id"));
            ProductFeedback feedback = feedbackDAO.getFeedbackById(feedbackId);

            if (feedback != null) {
                request.setAttribute("feedback", feedback);
                request.getRequestDispatcher("/feedbackDetail.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("errorMessage", "Feedback not found!");
                response.sendRedirect(request.getContextPath() + "/ManageFeedback/list");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid feedback ID!");
            response.sendRedirect(request.getContextPath() + "/ManageFeedback/list");
        }
    }

    // Display feedback detail by URL path
    private void viewFeedbackDetailById(HttpServletRequest request, HttpServletResponse response, int feedbackId)
            throws ServletException, IOException {
        Map<String, Object> feedback = feedbackDAO.getFeedbackByIdWithDetails(feedbackId);

        if (feedback != null && !feedback.isEmpty()) {
            request.setAttribute("feedback", feedback);
            request.getRequestDispatcher("/feedbackDetail.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("errorMessage", "Feedback not found!");
            response.sendRedirect(request.getContextPath() + "/ManageFeedback/list");
        }
    }

    // Reply to feedback
    private void replyFeedback(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
            String reply = request.getParameter("reply");

            if (reply == null || reply.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Reply content cannot be empty!");
                response.sendRedirect(request.getContextPath() + "/ManageFeedback/details/" + feedbackId);
                return;
            }

            boolean success = feedbackDAO.replyFeedback(feedbackId, reply);

            if (success) {
                request.getSession().setAttribute("successMessage", "Reply sent successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to send reply!");
            }

            response.sendRedirect(request.getContextPath() + "/ManageFeedback/details/" + feedbackId);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid feedback ID!");
            response.sendRedirect(request.getContextPath() + "/ManageFeedback/list");
        }
    }
}
