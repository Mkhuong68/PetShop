/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author tvhun
 */

public class ProductPromotion {
    private int productId;
    private int promotionId;
    private double originalPrice;   // Giá gốc
    private double discountedPrice; // Giá giảm

    // Constructor mặc định
    public ProductPromotion() {}

    // Constructor có tham số (cập nhật để bao gồm giá gốc và giá giảm)
    public ProductPromotion(int productId, int promotionId, double originalPrice, double discountedPrice) {
        this.productId = productId;
        this.promotionId = promotionId;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}
