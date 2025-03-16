/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author tvhun
 */
public class CartItem {
    private int cartItemId;
    private int accountId;
    private int productId;
    private int quantity;
    private BigDecimal finalPrice;
    private BigDecimal originalPrice;
    private Date createdDate;
    private Date lastUpdated;
    private String productName;
    private String productImage;
    
    // Getters and setters
    public int getCartItemId() {
        return cartItemId;
    }
  
    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }
   
    public int getAccountId() {
        return accountId;
    }
   
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
   
    public int getProductId() {
        return productId;
    }
  
    public void setProductId(int productId) {
        this.productId = productId;
    }
 
    public int getQuantity() {
        return quantity;
    }
 
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }
    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }
    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}