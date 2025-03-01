/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author thanhthao
 */

public class Promotion {

    private int promotionId;
    private String promotionName;
    private String promotionImage;
    private String description;
    private int discountRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int priority;
    private int createdBy;
    private Boolean isHidden;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;

    // Constructor mặc định
    public Promotion() {
    }

    // Constructor đầy đủ
    public Promotion(int promotionId, String promotionName, String promotionImage, String description,
            int discountRate, LocalDate startDate, LocalDate endDate, int priority,
            int createdBy, Boolean isHidden, LocalDateTime createdDate, LocalDateTime lastUpdated) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.promotionImage = promotionImage;
        this.description = description;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.createdBy = createdBy;
        this.isHidden = isHidden;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }

    // Getters và Setters
    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionImage() {
        return promotionImage;
    }

    public void setPromotionImage(String promotionImage) {
        this.promotionImage = promotionImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
