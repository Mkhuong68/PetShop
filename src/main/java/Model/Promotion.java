/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.sql.Timestamp;
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
    private String promotionDescription;
    private int promotionDiscount;
    private Timestamp promotionValidFrom;
    private Timestamp promotionValidTo;
    private int priority;
    private int createdBy;
    private boolean isHidden;
    private Timestamp createdDate;
    private Date lastUpdated;

    // Constructor
    public Promotion() {}

    public Promotion(int promotionId, String promotionName, String promotionImage, String promotionDescription, int promotionDiscount, Timestamp promotionValidFrom, Timestamp promotionValidTo, int priority, int createdBy, boolean isHidden, Timestamp createdDate, Date lastUpdated) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.promotionImage = promotionImage;
        this.promotionDescription = promotionDescription;
        this.promotionDiscount = promotionDiscount;
        this.promotionValidFrom = promotionValidFrom;
        this.promotionValidTo = promotionValidTo;
        this.priority = priority;
        this.createdBy = createdBy;
        this.isHidden = isHidden;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }



    // Getters and Setters
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

    public String getPromotionDescription() {
        return promotionDescription;
    }

    public void setPromotionDescription(String promotionDescription) {
        this.promotionDescription = promotionDescription;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public void setPromotionDiscount(int promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    public Timestamp getPromotionValidFrom() {
        return promotionValidFrom;
    }

    public void setPromotionValidFrom(Timestamp promotionValidFrom) {
        this.promotionValidFrom = promotionValidFrom;
    }

    public Timestamp getPromotionValidTo() {
        return promotionValidTo;
    }

    public void setPromotionValidTo(Timestamp promotionValidTo) {
        this.promotionValidTo = promotionValidTo;
    }

    public boolean isIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
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

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
