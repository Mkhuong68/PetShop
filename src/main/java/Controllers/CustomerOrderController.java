/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.CartDAO;
import DAOs.CustomerOrderDAO;
import DAOs.OrderDetailDAO;
import DAOs.ProductDAO;
import DAOs.VoucherDAO;
import Model.Account;
import Model.CartItem;
import Model.Order;
import Model.OrderDetail;
import Model.Product;
import Model.Voucher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NgocNNCE181950
 */
public class CustomerOrderController extends HttpServlet {

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
            out.println("<title>Servlet CustomerOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerOrderController at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        BigDecimal totalValue = BigDecimal.valueOf(-1);
        double shippingFee = -1;
        HttpSession session = request.getSession();

        // Neu order duoc thuc hien o trang detail
        if ("orderFromDetail".equalsIgnoreCase(action)) {
            String pId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");
            if (pId != null && quantity != null) {
                ProductDAO productDAO = new ProductDAO();
                int productId = Integer.parseInt(pId);
                int qty = Integer.parseInt(quantity);
                Product product = productDAO.getProductById(productId);

                if (product != null && qty > 0) {
                    CartItem cartItem = new CartItem();
                    cartItem.setProductId(product.getProductId());
                    cartItem.setProductName(product.getProductName());
                    BigDecimal productPriceRounded = product.getProductPrice().setScale(0, RoundingMode.HALF_UP);
                    cartItem.setOriginalPrice(productPriceRounded);
                    cartItem.setQuantity(qty);
                    session.setAttribute("dataProduct", cartItem);
                    request.setAttribute("product", cartItem);

                    totalValue = productPriceRounded.multiply(BigDecimal.valueOf(qty)).setScale(0, RoundingMode.HALF_UP);

                    // Kiem tra tong gia tri don hang de tinh phi ship
                    if (totalValue.doubleValue() >= 300000) {
                        shippingFee = 5000;
                    } else if (totalValue.doubleValue() >= 150000) {
                        shippingFee = 10000;
                    } else {
                        shippingFee = 15000;
                    }
                }

            } else {
                request.setAttribute("msg", "No product");
                request.getRequestDispatcher("viewOrderCustomer.jsp").forward(request, response);
                return;
            }

            // Neu order duoc thu hien o trang cart
        } else if ("orderFromCart".equalsIgnoreCase(action)) {
            List<CartItem> listItems = new ArrayList<>();
            String total = request.getParameter("totalAmount");
            if (total != null && !total.isEmpty()) {
                totalValue = new BigDecimal(total).setScale(0, RoundingMode.HALF_UP);
                // Kiem tra tong gia tri don hang de tinh phi ship
                if (totalValue.doubleValue() >= 300000) {
                    shippingFee = 5000;
                } else if (totalValue.doubleValue() >= 150000) {
                    shippingFee = 10000;
                } else {
                    shippingFee = 15000;
                }
            }

            // Kiem tra cac san pham da chon trong cart
            String[] selectedItems = request.getParameterValues("selectedItem");
            if (selectedItems == null || selectedItems.length == 0) {
                response.sendRedirect(request.getContextPath() + "/Cart");
                return;
            } else {
                CustomerOrderDAO c = new CustomerOrderDAO();

                // Lap qua danh sach da chon trong cart
                for (String cartItem : selectedItems) {
                    int cartItemId = Integer.parseInt(cartItem);
                    CartItem product = c.getProductInCart(cartItemId);
                    if (product != null) {
                        listItems.add(product);
                    }
                }
            }
            session.setAttribute("dataCart", listItems);
            request.setAttribute("selectedItems", listItems);
        }

        String loggedInUser = null;
        CustomerOrderDAO c = new CustomerOrderDAO();
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
                request.getRequestDispatcher("viewOrderCustomer.jsp").forward(request, response);
                return;
            }
        }
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            request.setAttribute("msg", "No user");
            request.getRequestDispatcher("viewOrderCustomer.jsp").forward(request, response);
            return;
        }

        int accountId = c.getAccountId(loggedInUser);

        VoucherDAO v = new VoucherDAO();
        List<Voucher> voucherList = v.getAllVoucher(accountId);
        if (voucherList != null && !voucherList.isEmpty()) {
            request.setAttribute("voucherList", voucherList);
        }

        session.setAttribute("shippingFee", shippingFee);
        request.setAttribute("shippingFee", shippingFee);
        request.setAttribute("totalAmount", totalValue);
        request.setAttribute("voucherList", voucherList);
        request.getRequestDispatcher("/viewOrderCustomer.jsp").forward(request, response);
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
        Product product = new Product();
        CartDAO cart = new CartDAO();
        OrderDetailDAO odDAO = new OrderDetailDAO();
        int productId = -1;
        int quantity = -1;

        // Kiem tra co dang nhap hay khong
        HttpSession session = request.getSession();
        CustomerOrderDAO c = new CustomerOrderDAO();
        String loggedInUser = null;
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
                request.getRequestDispatcher("viewOrderCustomer.jsp").forward(request, response);
                return;
            }
        }
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            request.setAttribute("msg", "No user");
            request.getRequestDispatcher("viewOrderCustomer.jsp").forward(request, response);
            return;
        }
        int accountId = c.getAccountId(loggedInUser);

        String deliveryAddress = request.getParameter("deliveryAddress");
        String orderNote = request.getParameter("orderNote");
        String paymentMethod = request.getParameter("paymentMethod");
        String voucherCode = request.getParameter("applyVoucher");


        // Kiem tra chon phuong thuc thanh toan nao
        boolean paymentStatus = true;
        if (paymentMethod.equalsIgnoreCase("COD")) {
            paymentStatus = false;
        }

        // Lay voucher da chon
        VoucherDAO v = new VoucherDAO();
        int voucherId = v.getVoucherIdByAccountId(voucherCode);

        // Kiem tra phi ship
        double shippingFee = -1;
        if (session.getAttribute("shippingFee") != null) {
            shippingFee = (Double) session.getAttribute("shippingFee");
        }
        // Kiem tra ghi chu
        if (orderNote.isEmpty()) {
            orderNote = "";
        }

        BigDecimal finalPrice = BigDecimal.valueOf(-1);
        BigDecimal purchasePrice = BigDecimal.valueOf(-1);
        int lastId = -1;

        // Kiem tra co san pham da chon trong cart hay khong
        if (session.getAttribute("dataCart") != null) {
            List<CartItem> selectedItems = (List<CartItem>) request.getSession().getAttribute("dataCart");

            // Them du lieu vao Order
            Order newOrder = new Order(0, loggedInUser, accountId, new Timestamp(System.currentTimeMillis()), 1, "Received", deliveryAddress, voucherId, paymentStatus, paymentMethod, shippingFee, orderNote);
            c.addOrder(newOrder);
            lastId = c.getLastInsertedOrderId(accountId);

            // Them du lieu vao order detail;
            for (CartItem item : selectedItems) {
                int cartItemId = item.getCartItemId();
                productId = item.getProductId();
                quantity = item.getQuantity();
                purchasePrice = item.getFinalPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                finalPrice = purchasePrice.add(BigDecimal.valueOf(shippingFee));
                OrderDetail detail = new OrderDetail(0, lastId, productId, quantity, finalPrice.doubleValue(), purchasePrice.doubleValue());
                boolean isAdded = odDAO.insertOrderDetail(detail);
                if (isAdded) {
                    cart.deleteCartItem(cartItemId);
                }
            }

            // Kiem tra co san pham da chon hay khong
        } else if (session.getAttribute("dataProduct") != null) {
            CartItem cartItem = (CartItem) session.getAttribute("dataProduct");
            quantity = cartItem.getQuantity();
            productId = cartItem.getProductId();
            purchasePrice = cartItem.getOriginalPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            finalPrice = purchasePrice.add(BigDecimal.valueOf(shippingFee));
            ProductDAO pDao = new ProductDAO();
            product = pDao.getProductById(productId);
            if (product == null) {
                request.setAttribute("msg", "No product");
                request.getRequestDispatcher("viewOrderCustomer.jsp").forward(request, response);
                return;
            } else {

                // Them du lieu vao Order
                Order newOrder = new Order(0, loggedInUser, accountId, new Timestamp(System.currentTimeMillis()), 1, "Received", deliveryAddress, voucherId, paymentStatus, paymentMethod, shippingFee, orderNote);
                c.addOrder(newOrder);
                lastId = c.getLastInsertedOrderId(accountId);

                // Them du lieu vao order detail;
                OrderDetail detail = new OrderDetail(0, lastId, productId, quantity, finalPrice.doubleValue(), purchasePrice.doubleValue());
                odDAO.insertOrderDetail(detail);

            }
        }
        response.sendRedirect(request.getContextPath() + "/CustomerOrderDetailController?orderId=" + lastId);

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
