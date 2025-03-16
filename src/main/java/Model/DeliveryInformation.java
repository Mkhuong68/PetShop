/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author tvhun
 */
public class DeliveryInformation {
    private int deliveryId;
    private int orderId;
    private int statusId;
    private String trackingNumber;
    private String shippingCompany;

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public DeliveryInformation() {}

    public DeliveryInformation(int deliveryId, int orderId, int statusId, String trackingNumber, String shippingCompany) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.statusId = statusId;
        this.trackingNumber = trackingNumber;
        this.shippingCompany = shippingCompany;
    }
    
}
