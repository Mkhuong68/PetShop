package Controllers;

import DAOs.DeliveryIssueDAO;
import Model.Account;
import Model.DeliveryIssue;
import Model.Order;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeliveryIssueController", urlPatterns = {"/manage-issues"})
public class DeliveryIssueController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        // Check if user is logged in and is delivery staff
        if (account == null || account.getRoleId() != 2) {
            response.sendRedirect("/login");
            return;
        }
        
        String action = request.getParameter("action");
        DeliveryIssueDAO issueDAO = new DeliveryIssueDAO();
        
        // View Issue History (default)
        if (action == null || action.equals("view")) {
            List<DeliveryIssue> issueList = issueDAO.getIssuesByReporterId(account.getAccountId());
            request.setAttribute("issueList", issueList);
            request.getRequestDispatcher("/issue.jsp").forward(request, response);
        }
        // Show form to create a new issue
        else if (action.equals("new")) {
            List<Order> deliveredOrders = issueDAO.getDeliveredOrdersByStaffId(account.getAccountId());
            request.setAttribute("deliveredOrders", deliveredOrders);
            request.getRequestDispatcher("/createissue.jsp").forward(request, response);
        }
        // View issue details
        else if (action.equals("detail")) {
            int issueId = Integer.parseInt(request.getParameter("id"));
            DeliveryIssue issue = issueDAO.getIssueById(issueId);
            
            if (issue != null && issue.getReporterId() == account.getAccountId()) {
                // Check if this is an AJAX request for JSON data
                String format = request.getParameter("format");
                if (format != null && format.equals("json")) {
                    // Return issue details as JSON
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    
                    // Create a JSON object with issue details
                    JsonObject jsonIssue = new JsonObject();
                    jsonIssue.addProperty("issueId", issue.getIssueId());
                    jsonIssue.addProperty("issueType", issue.getIssueType());
                    jsonIssue.addProperty("issueDescription", issue.getIssueDescription());
                    jsonIssue.addProperty("status", issue.getStatus());
                    jsonIssue.addProperty("createdDate", issue.getCreatedDate() != null ? issue.getCreatedDate().toString() : "");
                    if (issue.getResolvedDate() != null) {
                        jsonIssue.addProperty("resolvedDate", issue.getResolvedDate().toString());
                    }
                    jsonIssue.addProperty("resolvedBy", issue.getResolvedBy());
                    jsonIssue.addProperty("resolvedByName", issue.getResolvedByName());
                    jsonIssue.addProperty("orderId", issue.getOrderId());
                    
                    // We don't have direct access to order details in the DeliveryIssue class,
                    // so we'll just leave these properties out and handle their absence in the JSP
                    
                    // Write the JSON response
                    response.getWriter().write(jsonIssue.toString());
                    return;
                } else {
                    // Set issue as request attribute for JSP
                    request.setAttribute("issue", issue);
                    request.getRequestDispatcher("/issuedetail.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("/manage-issues");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        // Check if user is logged in and is delivery staff
        if (account == null || account.getRoleId() != 2) {
            response.sendRedirect("/login");
            return;
        }
        
        String action = request.getParameter("action");
        DeliveryIssueDAO issueDAO = new DeliveryIssueDAO();
        
        // Create a new issue
        if (action != null && action.equals("create")) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String issueType = request.getParameter("issueType");
            String description = request.getParameter("description");
            
            DeliveryIssue issue = new DeliveryIssue();
            issue.setReporterId(account.getAccountId());
            issue.setIssueType(issueType);
            issue.setReferenceId(orderId); // Assuming reference_id is the order_id
            issue.setIssueDescription(description);
            
            if (issueDAO.createIssue(issue)) {
                // Success - redirect to issue list with success message
                request.getSession().setAttribute("successMessage", "Issue report sent successfully");
            } else {
                // Error - redirect back to form with error message
                request.getSession().setAttribute("errorMessage", "Failed to send issue report");
            }
            
            response.sendRedirect("/manage-issues");
        }
    }
}