/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.AccountDAO;
import DAOs.CartDAO;
import DAOs.CustomerOrderDAO;
import DAOs.OrderDetailDAO;
import DAOs.ProductDAO;
import DAOs.UserVoucherDAO;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        String momoStatus = request.getParameter("momoStatus");
        BigDecimal totalValue = BigDecimal.valueOf(-1);
        double shippingFee = -1;

        // Kiểm tra người dùng đăng nhập
        HttpSession session = request.getSession();
        CustomerOrderDAO c = new CustomerOrderDAO();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        } else {
            System.out.println("Account in session: " + account);
        }
        AccountDAO a = new AccountDAO();

        int accountId = c.getAccountId(account.getUsername());
        Account acc = a.getAccountById(accountId);
        String phoneNumber = acc.getPhoneNumber();
        String firstName = acc.getFirstName();
        String lastName = acc.getLastName();

        try {

            if ("orderFromDetail".equalsIgnoreCase(action)) {
                // Lấy thông tin sản phẩm từ request
                String pId = request.getParameter("productId");
                String quantity = request.getParameter("quantity");

                if (pId == null || quantity == null) {
                    request.setAttribute("msg", "No product selected.");
                } else {
                    ProductDAO productDAO = new ProductDAO();
                    int productId = Integer.parseInt(pId);
                    int qty = Integer.parseInt(quantity);

                    Product product = productDAO.getProductById(productId);

                    if (product == null || qty <= 0) {
                        request.setAttribute("msg", "Invalid product or quantity.");
                    } else {
                        // Tạo CartItem
                        CartItem cartItem = new CartItem();
                        cartItem.setProductId(product.getProductId());
                        cartItem.setProductName(product.getProductName());
                        BigDecimal productPriceRounded = product.getProductPrice().setScale(0, RoundingMode.HALF_UP);
                        cartItem.setOriginalPrice(productPriceRounded);
                        cartItem.setQuantity(qty);

                        // Lưu vào session và request
                        session.setAttribute("dataProduct", cartItem);
                        request.setAttribute("product", cartItem);

                        // Tính tổng tiền
                        totalValue = productPriceRounded.multiply(BigDecimal.valueOf(qty)).setScale(0, RoundingMode.HALF_UP);
                    }
                }

            } else if ("orderFromCart".equalsIgnoreCase(action)) {
                List<CartItem> listItems = new ArrayList<>();
                String total = request.getParameter("totalAmount");
                System.out.println("Total received in servlet: " + total);

                if (total != null && !total.isEmpty()) {
                    totalValue = new BigDecimal(total).setScale(0, RoundingMode.HALF_UP);
                }

                // Lấy danh sách sản phẩm đã chọn trong cart
                String[] selectedItems = request.getParameterValues("selectedItem");

                if (selectedItems == null || selectedItems.length == 0) {
                    response.sendRedirect(request.getContextPath() + "/Cart");
                    return;
                } else {
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

            // Tính phí ship
            if (totalValue.doubleValue() >= 300000) {
                shippingFee = 5000;
            } else if (totalValue.doubleValue() >= 150000) {
                shippingFee = 10000;
            } else {
                shippingFee = 15000;
            }

            // Lấy danh sách voucher
            UserVoucherDAO u = new UserVoucherDAO();
            VoucherDAO v = new VoucherDAO();
            List<Voucher> voucherList = new ArrayList<>();

            List<Integer> voucherIds = u.getVoucherIdByAccId(accountId);
            if (voucherIds != null && !voucherIds.isEmpty()) {
                for (Integer voucherId : voucherIds) {
                    voucherList.addAll(v.getAllVoucherAcc(voucherId));
                }
            }
            session.setAttribute("voucherList", voucherList);
            request.setAttribute("voucherList", voucherList);

            if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                request.setAttribute("phoneNumber", phoneNumber);
            } else {
                request.setAttribute("phoneNumber", "");
            }

            if (firstName != null && !firstName.trim().isEmpty()) {
                request.setAttribute("firstName", firstName);
            } else {
                request.setAttribute("firstName", "");
            }

            if (lastName != null && !lastName.trim().isEmpty()) {
                request.setAttribute("lastName", phoneNumber);
            } else {
                request.setAttribute("lastName", "");
            }
        } catch (Exception e) {
            request.setAttribute("msg", "An error occurred: " + e.getMessage());
        }

        request.setAttribute("phoneNumber", phoneNumber);
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);

        session.setAttribute("shippingFee", shippingFee);
        request.setAttribute("shippingFee", shippingFee);

        session.setAttribute("totalAmount", totalValue);
        request.setAttribute("totalAmount", totalValue);

        request.setAttribute("account", account);
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
        ProductDAO p = new ProductDAO();
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

        }
        int accountId = c.getAccountId(loggedInUser);

        String deliveryAddress = request.getParameter("deliveryAddress");
        String orderNote = request.getParameter("orderNote");
        String paymentMethod = request.getParameter("paymentMethod");
        String voucherCode = request.getParameter("applyVoucher");
        String momoStatus = request.getParameter("resultCode");
        String name = request.getParameter("name");

        // Kiem tra phi ship
        double shippingFee = -1;
        if (session.getAttribute("shippingFee") != null) {
            shippingFee = (Double) session.getAttribute("shippingFee");
        }

        BigDecimal totalAmount = (BigDecimal) session.getAttribute("totalAmount");
        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        Integer userVoucherId = null;
        BigDecimal discountAmount = BigDecimal.ZERO;
        VoucherDAO v = new VoucherDAO();

        if (voucherCode != null && !voucherCode.trim().isEmpty()) {
            userVoucherId = v.getVoucherIdByAccId(voucherCode);
            Integer voucherId = v.getVoucherIdByAccId(voucherCode);
            boolean isUsed = true;
            v.updateVoucherIsUsed(isUsed, voucherId);
            if (userVoucherId != null) {
                double voucherDiscount = v.getVoucherDiscount(userVoucherId);

                if (voucherDiscount >= 0 && voucherDiscount <= 100) {
                    discountAmount = totalAmount.multiply(BigDecimal.valueOf(voucherDiscount / 100));
                } else if (voucherDiscount >= 1000) {
                    discountAmount = BigDecimal.valueOf(voucherDiscount);

                    if (discountAmount.compareTo(totalAmount) > 0) {
                        discountAmount = totalAmount;
                    }
                }
            } else {
                userVoucherId = -1;
            }
        }

        BigDecimal finalAmount = totalAmount.subtract(discountAmount);
        finalAmount = finalAmount.add(BigDecimal.valueOf(shippingFee));

        // Kiem tra ghi chu
        if (orderNote.isEmpty()) {
            orderNote = "";
        }

        BigDecimal purchasePrice = BigDecimal.valueOf(-1);
        int lastId = -1;

        String phoneNumber = request.getParameter("phoneNumber");
        String phoneRegex = "^(0[3|5|7|8|9])+([0-9]{8})$";
        if (!phoneNumber.matches(phoneRegex)) {
            List<Voucher> voucherList = (List<Voucher>) session.getAttribute("voucherList");
            request.setAttribute("voucherList", voucherList);
            request.setAttribute("phoneError", "Invalid phone number! Please enter a valid one.");
            request.setAttribute("deliveryAddress", deliveryAddress);
            request.setAttribute("orderNote", orderNote);
            request.setAttribute("paymentMethod", paymentMethod);
            request.setAttribute("voucherCode", voucherCode);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("shippingFee", shippingFee);
            request.setAttribute("finalAmount", finalAmount);

            if (session.getAttribute("dataCart") != null) {
                request.setAttribute("selectedItems", session.getAttribute("dataCart"));
            } else if (session.getAttribute("dataProduct") != null) {
                request.setAttribute("product", session.getAttribute("dataProduct"));
            }

            request.getRequestDispatcher("viewOrderCustomer.jsp").forward(request, response);
            return;
        }
        boolean paymentStatus = false;

        // Kiem tra co san pham da chon trong cart hay khong
        if (session.getAttribute("dataCart") != null) {
            List<CartItem> selectedItems = (List<CartItem>) request.getSession().getAttribute("dataCart");

            // Them du lieu vao Order
            Order newOrder = new Order(0, loggedInUser, accountId, new Timestamp(System.currentTimeMillis()), 1, "Received", deliveryAddress, userVoucherId, paymentStatus, paymentMethod, shippingFee, orderNote);
            c.addOrder(newOrder);
            lastId = c.getLastInsertedOrderId(accountId);
            session.setAttribute("lastId", lastId);

            // Them du lieu vao order detail;
            for (CartItem item : selectedItems) {
                int cartItemId = item.getCartItemId();
                productId = item.getProductId();
                quantity = item.getQuantity();
                int stock = p.getStockQuantity(productId);
                int stockRemain = stock - quantity;
                int stockSold = p.getSoldQuantity(productId) + quantity;
                p.updateSoldQuantity(stockSold, productId);
                p.updateStockQuantity(stockRemain, productId);
                purchasePrice = item.getFinalPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                OrderDetail detail = new OrderDetail(0, lastId, productId, quantity, finalAmount.doubleValue(), purchasePrice.doubleValue());
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
            int stock = p.getStockQuantity(productId);
            int stockRemain = stock - quantity;
            int stockSold = p.getSoldQuantity(productId) + quantity;
            p.updateSoldQuantity(stockSold, productId);
            p.updateStockQuantity(stockRemain, productId);
            purchasePrice = cartItem.getOriginalPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            ProductDAO pDao = new ProductDAO();
            product = pDao.getProductById(productId);
            if (product == null) {
                request.setAttribute("msg", "No product");
                request.getRequestDispatcher("viewOrderCustomer.jsp").forward(request, response);
                return;
            } else {

                // Them du lieu vao Order
                Order newOrder = new Order(0, loggedInUser, accountId, new Timestamp(System.currentTimeMillis()), 1, "Received", deliveryAddress, userVoucherId, paymentStatus, paymentMethod, shippingFee, orderNote);
                c.addOrder(newOrder);
                lastId = c.getLastInsertedOrderId(accountId);

                // Them du lieu vao order detail;
                OrderDetail detail = new OrderDetail(0, lastId, productId, quantity, finalAmount.doubleValue(), purchasePrice.doubleValue());
                odDAO.insertOrderDetail(detail);

            }
        }
        if ("MOMO".equals(paymentMethod)) {
            if (momoStatus == null) {
                session.setAttribute("totalAmount", finalAmount.toPlainString());
                request.getRequestDispatcher("/MoMoPaymentServlet").forward(request, response);
                return;
            }
        }

        String encodedPhone = URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8);
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);

        Cookie phoneCookie = new Cookie("phoneNumber", encodedPhone);
        Cookie nameCookie = new Cookie("name", encodedName);

        phoneCookie.setMaxAge(7 * 24 * 60 * 60);
        nameCookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(phoneCookie);
        response.addCookie(nameCookie);
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
