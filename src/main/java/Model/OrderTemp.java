/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author NgocNNCE181950
 */
public class OrderTemp {

    private int accountId;
    private int optionId;
    private int productId;
    private int orderDetailId;
    private int statusId;
    private int orderId;
    private int userVoucherId;
    private int voucherId;
    private String deliveryAddress;
    private String paymentMethod;
    private Timestamp orderDate;
    private double shippingFee;
    private String voucherCode;
    private String productName;
    private double productPrice;
    private String statusName;
    private String optionName;
    private String orderNote;
    private double finalPrice;
    private int quantity;
    private double purchasePrice;
    private Integer appliedPromotionId;

    public OrderTemp(int accountId, int optionId, int productId, int orderDetailId, int statusId, int orderId,
            int userVoucherId, int voucherId, String deliveryAddress, String paymentMethod,
            Timestamp orderDate, double shippingFee, String voucherCode, String productName,
            double productPrice, String statusName, String optionName, String orderNote,
            double finalPrice, int quantity, double purchasePrice, Integer appliedPromotionId) {
        this.accountId = accountId;
        this.optionId = optionId;
        this.productId = productId;
        this.orderDetailId = orderDetailId;
        this.statusId = statusId;
        this.orderId = orderId;
        this.userVoucherId = userVoucherId;
        this.voucherId = voucherId;
        this.deliveryAddress = deliveryAddress;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.shippingFee = shippingFee;
        this.voucherCode = voucherCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.statusName = statusName;
        this.optionName = optionName;
        this.orderNote = orderNote;
        this.finalPrice = finalPrice;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.appliedPromotionId = appliedPromotionId;
    }

    public OrderTemp(int orderId, Timestamp orderDate, double shippingFee, String productName, String statusName, double finalPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.shippingFee = shippingFee;
        this.productName = productName;
        this.statusName = statusName;
        this.finalPrice = finalPrice;
    }
    
    

    // Getters và Setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserVoucherId() {
        return userVoucherId;
    }

    public void setUserVoucherId(int userVoucherId) {
        this.userVoucherId = userVoucherId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getAppliedPromotionId() {
        return appliedPromotionId;
    }

    public void setAppliedPromotionId(Integer appliedPromotionId) {
        this.appliedPromotionId = appliedPromotionId;
    }
}
