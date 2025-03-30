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
public class ProductPromotion {

    private int productId;
    private String productName;
    private int promotionId;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private String productImage;
    private Date promotionValidFrom;
    private Date promotionValidTo;
    private String promotionName;
    private int discountPercentage;

    // Constructor
    public ProductPromotion() {
    }

    public ProductPromotion(int productId, String productName, int promotionId, BigDecimal originalPrice,
            BigDecimal discountedPrice, String productImage, Date promotionValidFrom,
            Date promotionValidTo, String promotionName, int discountPercentage) {
        this.productId = productId;
        this.productName = productName;
        this.promotionId = promotionId;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.productImage = productImage;
        this.promotionValidFrom = promotionValidFrom;
        this.promotionValidTo = promotionValidTo;
        this.promotionName = promotionName;
        this.discountPercentage = discountPercentage;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Date getPromotionValidFrom() {
        return promotionValidFrom;
    }

    public void setPromotionValidFrom(Date promotionValidFrom) {
        this.promotionValidFrom = promotionValidFrom;
    }

    public Date getPromotionValidTo() {
        return promotionValidTo;
    }

    public void setPromotionValidTo(Date promotionValidTo) {
        this.promotionValidTo = promotionValidTo;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
