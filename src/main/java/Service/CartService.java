/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import DAOs.CartDAO;
import Model.Account;
import Model.CartItem;

/**
 *
 * @author tvhun
 */
public class CartService {

    public static int getCartCount(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            return 0;
        }
        CartDAO cartDAO = new CartDAO();
        List<CartItem> items = cartDAO.getCartItems(account.getAccountId());
        int count = 0;
        for (CartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }

    // Nếu bạn muốn tính theo số lượng sản phẩm duy nhất (mỗi sản phẩm chỉ tính 1)
    public static int getUniqueCartCount(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            return 0;
        }
        CartDAO cartDAO = new CartDAO();
        // Giả sử bạn có thêm phương thức getUniqueCartCount trong CartDAO (xem mục 2)
        return cartDAO.getUniqueCartCount(account.getAccountId());
    }
}
