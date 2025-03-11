/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;
import DAOs.PostDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 *
 * @author THANH THAO
 */
@WebServlet("/AcceptPost")
public class AcceptPostController extends HttpServlet {
    private final PostDAO postDAO = new PostDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("id"));
        boolean success = postDAO.acceptPost(postId);

        if (success) {
            response.sendRedirect("ManagePosts?success=accepted");
        } else {
            response.sendRedirect("ManagePosts?error=accept_failed");
        }
    }
}
