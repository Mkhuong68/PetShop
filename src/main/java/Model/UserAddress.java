/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author tvhun
 */

public class UserAddress {

    private int addressId;
    private int accountId;
    private String address;
    private boolean isDefault;
    private Date createdDate;
    private double latitude;  // Thêm cột latitude
    private double longitude; // Thêm cột longitude

    // Constructor không tham số
    public UserAddress() {
        // Constructor mặc định, có thể để trống hoặc gán giá trị mặc định
    }

    // Getter và Setter cho các thuộc tính mới
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Các getter/setter còn lại cho các thuộc tính khác
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    // Constructor với tham số
    public UserAddress(int addressId, int accountId, String address, boolean isDefault, Date createdDate,
                       double latitude, double longitude) {
        this.addressId = addressId;
        this.accountId = accountId;
        this.address = address;
        this.isDefault = isDefault;
        this.createdDate = createdDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
