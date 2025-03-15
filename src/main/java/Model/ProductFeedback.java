/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author tvhun
 */
public class ProductFeedback {
    private int feedbackId;
    private int orderDetailId;
    private int rating;
    private String comment;
    private String staffReply;
    private Timestamp createdDate;
    private Timestamp lastUpdated;
    private boolean isHidden;

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStaffReply() {
        return staffReply;
    }

    public void setStaffReply(String staffReply) {
        this.staffReply = staffReply;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

 public ProductFeedback() {
    }

    public ProductFeedback(int feedbackId, int orderDetailId, int rating, String comment, String staffReply, Timestamp createdDate, Timestamp lastUpdated, boolean isHidden) {
        this.feedbackId = feedbackId;
        this.orderDetailId = orderDetailId;
        this.rating = rating;
        this.comment = comment;
        this.staffReply = staffReply;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
        this.isHidden = isHidden;
    }
}