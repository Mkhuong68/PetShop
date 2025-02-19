/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.RegisterDAO;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.JsonFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "GoogleLoginController", urlPatterns = {"/GoogleLogin"})
public class GoogleLoginController extends HttpServlet {

    private static final String CLIENT_ID = "383132407851-hra9ugqnm56ap016fjceqo5i4bbus9o9.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/GoogleLogin";
    private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

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
            out.println("<title>Servlet GoogleLoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GoogleLoginController at " + request.getContextPath() + "</h1>");
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
        String code = request.getParameter("code");

        if (code != null) {
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    jsonFactory,
                    "https://oauth2.googleapis.com/token",
                    CLIENT_ID,
                    CLIENT_SECRET,
                    code,
                    REDIRECT_URI
            ).execute();
            GoogleIdToken idToken = tokenResponse.parseIdToken();
            GoogleIdToken.Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            String name = (String) payload.get("name");
            if (name == null || name.trim().isEmpty()) {
                // Nếu không có tên, sử dụng phần trước dấu @ của email làm tên đăng nhập
                name = email.split("@")[0];
            }
            RegisterDAO registerDAO = new RegisterDAO();

            request.getSession().invalidate();
            request.getSession(true);
            if (registerDAO.isEmailExists(email)) {
                request.getSession().setAttribute("email", email);
                response.sendRedirect("hompage.jsp");
            } else {
                registerDAO.registerUser(name, "", email);
                request.getSession().setAttribute("email", email);
                response.sendRedirect("hompage.jsp");
            }
        } else {
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(),
                    jsonFactory,
                    CLIENT_ID,
                    CLIENT_SECRET,
                    java.util.Collections.singletonList("email")
            ).setAccessType("offline")
                    .setApprovalPrompt("force") // Thêm dòng này
                    .build();
            String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
            response.sendRedirect(authorizationUrl);
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
