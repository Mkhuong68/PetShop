/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.PrintWriter;
import DAOs.CartDAO;
import Model.Account;
import Model.CartItem;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author tvhun
 */
public class CartController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CartDAO cartDAO;

    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAO();
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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CartController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartController at " + request.getContextPath() + "</h1>");
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
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            response.sendRedirect("/login");
            return;
        }
        int accountId = account.getAccountId();
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "list":
                listCart(request, response, accountId);
                break;
            case "delete":
                int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                cartDAO.deleteCartItem(cartItemId);
                sendCartSummary(response, accountId);
                break;
            case "deleteAll":
                cartDAO.deleteAllCartItems(accountId);
                sendCartSummary(response, accountId);
                break;
            case "add":
                int productId = Integer.parseInt(request.getParameter("productId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                BigDecimal price = new BigDecimal("100000");
                CartItem item = new CartItem();
                item.setProductId(productId);
                item.setQuantity(quantity);
                item.setFinalPrice(price);
                item.setOriginalPrice(price);
                cartDAO.addCartItem(accountId, item);
                sendCartSummary(response, accountId);
                return;
            case "getSummary":
                sendCartSummary(response, accountId);
                return;
            default:
                listCart(request, response, accountId);
                break;
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
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            response.sendRedirect("/login");
            return;
        }
        int accountId = account.getAccountId();
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            int newQuantity = Integer.parseInt(request.getParameter("quantity"));
            cartDAO.updateCartItemQuantity(cartItemId, newQuantity);

            // Lấy lại danh sách giỏ hàng sau khi cập nhật
            List<CartItem> items = cartDAO.getCartItems(accountId);
            BigDecimal total = BigDecimal.ZERO;
            CartItem updatedItem = null;

            // Tính tổng giá trị giỏ hàng và tìm mục vừa cập nhật
            for (CartItem ci : items) {
                if (ci.getCartItemId() == cartItemId) {
                    updatedItem = ci;
                }
                total = total.add(ci.getFinalPrice().multiply(new BigDecimal(ci.getQuantity())));
            }

            // Định dạng số tiền theo locale vi_VN
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            String totalFormatted = nf.format(total);

            BigDecimal itemTotal = updatedItem.getFinalPrice().multiply(new BigDecimal(updatedItem.getQuantity()));
            String itemTotalFormatted = nf.format(itemTotal);

            // Nếu muốn đếm số lượng sản phẩm duy nhất thay vì tổng số lượng, bạn có thể dùng items.size()
            int uniqueProductCount = items.size();

            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"cartCount\": " + uniqueProductCount
                    + ", \"totalFormatted\": \"" + totalFormatted
                    + "\", \"itemTotalFormatted\": \"" + itemTotalFormatted + "\"}"
            );
            return;
        } else if ("applyVoucher".equals(action)) {
            String voucherCode = request.getParameter("voucherCode");
            BigDecimal discount = cartDAO.applyVoucher(accountId, voucherCode);
            request.setAttribute("discount", discount);
            listCart(request, response, accountId);
        } else {
            doGet(request, response);
        }
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

    private void listCart(HttpServletRequest request, HttpServletResponse response, int accountId)
            throws ServletException, IOException {
        List<CartItem> cartItems = cartDAO.getCartItems(accountId);
        request.setAttribute("cartItems", cartItems);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            BigDecimal itemTotal = item.getFinalPrice().multiply(new BigDecimal(item.getQuantity()));
            total = total.add(itemTotal);
        }
        request.setAttribute("total", total);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
        dispatcher.forward(request, response);
    }

    private void sendCartSummary(HttpServletResponse response, int accountId) throws IOException {
        List<CartItem> items = cartDAO.getCartItems(accountId);
        int cartCount = 0;
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : items) {
            cartCount += ci.getQuantity();
            total = total.add(ci.getFinalPrice().multiply(new BigDecimal(ci.getQuantity())));
        }
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        String totalFormatted = nf.format(total);
        response.setContentType("application/json");
        response.getWriter().write("{\"cartCount\": " + cartCount + ", \"totalFormatted\": \"" + totalFormatted + "\"}");
    }
}
