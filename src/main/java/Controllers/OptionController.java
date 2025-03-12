/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.OptionDAO;
import Model.Option;
import Model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author tvhun
 */
public class OptionController extends HttpServlet {

    private OptionDAO optionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        optionDAO = new OptionDAO();
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
                    listOptions(request, response);
                    break;
                case "/create":
                    if ("POST".equals(request.getMethod())) {
                        addOption(request, response);
                    } else {
                        showAddForm(request, response);
                    }
                    break;
                case "/edit":
                    if ("POST".equals(request.getMethod())) {
                        updateOption(request, response);
                    } else {
                        showEditForm(request, response);
                    }
                    break;
                case "/toggle":
                    toggleOptionStatus(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/ManageOption/list");
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/ManageOption/list").forward(request, response);
        }
    }

    private void listOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Option> options = optionDAO.getAllOptions();
        request.setAttribute("options", options);
        request.getRequestDispatcher("/listOption.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = optionDAO.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/createOption.jsp").forward(request, response);
    }

    private void addOption(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Option option = new Option();
            option.setOptionName(request.getParameter("optionName"));
            option.setOptionDescription(request.getParameter("optionDescription"));
            option.setOptionPrice(Double.parseDouble(request.getParameter("optionPrice")));
            option.setProductId(Integer.parseInt(request.getParameter("productId")));
            option.setHidden(false);

            if (optionDAO.insertOption(option)) {
                request.getSession().setAttribute("successMessage", "Option added successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to add option!");
            }
            response.sendRedirect(request.getContextPath() + "/ManageOption/list");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid data!");
            showAddForm(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int optionId = Integer.parseInt(request.getParameter("id"));
            Option option = optionDAO.getOptionById(optionId);
            List<Product> products = optionDAO.getAllProducts();
            
            if (option != null) {
                request.setAttribute("option", option);
                request.setAttribute("products", products);
                request.getRequestDispatcher("/editOption.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("errorMessage", "Option not found!");
                response.sendRedirect(request.getContextPath() + "/ManageOption/list");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid ID!");
            response.sendRedirect(request.getContextPath() + "/ManageOption/list");
        }
    }

    private void updateOption(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Option option = new Option();
            option.setOptionId(Integer.parseInt(request.getParameter("optionId")));
            option.setOptionName(request.getParameter("optionName"));
            option.setOptionDescription(request.getParameter("optionDescription"));
            option.setOptionPrice(Double.parseDouble(request.getParameter("optionPrice")));
            option.setProductId(Integer.parseInt(request.getParameter("productId")));
            option.setHidden(Boolean.parseBoolean(request.getParameter("isHidden")));

            if (optionDAO.updateOption(option)) {
                request.getSession().setAttribute("successMessage", "Option updated successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update option!");
            }
            response.sendRedirect(request.getContextPath() + "/ManageOption/list");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid data!");
            showEditForm(request, response);
        }
    }

    private void toggleOptionStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int optionId = Integer.parseInt(request.getParameter("id"));
            boolean currentStatus = Boolean.parseBoolean(request.getParameter("status"));
            
            if (optionDAO.toggleOptionStatus(optionId, !currentStatus)) {
                request.getSession().setAttribute("successMessage", 
                    "Option " + (!currentStatus ? "hidden" : "shown") + " successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to change status!");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid ID!");
        }
        response.sendRedirect(request.getContextPath() + "/ManageOption/list");
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
        return "Short description";
    }// </editor-fold>

}
