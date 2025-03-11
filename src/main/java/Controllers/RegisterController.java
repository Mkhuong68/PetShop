/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.RegisterDAO;
import Service.EmailService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "RegisterController", urlPatterns = {"/Register"})
public class RegisterController extends HttpServlet {

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
            out.println("<title>Servlet RegisterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("registerandlogin.jsp").forward(request, response);
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
        String username = request.getParameter("username");
        String password_hash = request.getParameter("password_hash");
        String email = request.getParameter("email");
        RegisterDAO registerDAO = new RegisterDAO();

        if (registerDAO.isUserExists(username)) {
            request.setAttribute("msg", "Tên người dùng đã tồn tại, vui lòng thử lại.");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("registerandlogin.jsp").forward(request, response);
            return;
        } else if (registerDAO.isEmailExists(email)) {
            request.setAttribute("msg", "Email đã tồn tại, vui lòng thử lại.");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("registerandlogin.jsp").forward(request, response);
            return;
        }

        int verificationCode = (int) (Math.random() * 900000) + 100000;
        String verificationCodeStr = String.valueOf(verificationCode);
        EmailService emailService = new EmailService();
        emailService.sendVerificationEmail(email, verificationCodeStr);
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("password_hash", password_hash);
        request.getSession().setAttribute("email", email);
        request.getSession().setAttribute("verificationCode", verificationCodeStr);

        response.sendRedirect("verifyEmail");
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
