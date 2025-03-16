/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.ManageIssueAdminDAO;
import Model.ReportedIssue;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet("/ManageIssueAdminController")
public class ManageIssueAdminController extends HttpServlet {

    private ManageIssueAdminDAO issueDAO;

    @Override
    public void init() {
        issueDAO = new ManageIssueAdminDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "edit":
                    int issueId = Integer.parseInt(request.getParameter("id"));
                    ReportedIssue issue = issueDAO.getIssueById(issueId);
                    if (issue != null) {
                        request.setAttribute("issue", issue);
                        request.getRequestDispatcher("updateIssueAdmin.jsp").forward(request, response);
                    } else {
                        request.setAttribute("error", "Issue not found!");
                        request.getRequestDispatcher("manageIssueAdmin.jsp").forward(request, response);
                    }
                    break;

                default:
                    List<ReportedIssue> issues = issueDAO.getAllIssues();
                    if (issues == null || issues.isEmpty()) {
                        System.out.println("No issues found in the database.");
                    } else {
                        System.out.println("Issues loaded successfully. Total: " + issues.size());
                    }
                    request.setAttribute("issueList", issues);
                    request.getRequestDispatcher("manageIssueAdmin.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để debug
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("update".equals(action)) {
                int issueId = Integer.parseInt(request.getParameter("id"));
                boolean isResolved = request.getParameter("isResolved") != null;

                boolean updated = issueDAO.updateIssueStatus(issueId, isResolved);
                if (updated) {
                    response.sendRedirect("ManageIssueAdminController?action=list");
                } else {
                    response.getWriter().println("Error: Failed to update issue.");
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
