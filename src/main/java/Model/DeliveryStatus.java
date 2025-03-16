/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author tvhun
 */
public class DeliveryStatus {
    private int statusId;
    private String statusName;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public DeliveryStatus() {}

    public DeliveryStatus(int statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }
}
