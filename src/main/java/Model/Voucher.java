/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author tvhun
 */
public class Voucher {

    private int voucherId;
    private String voucherCode;
    private String voucherDescription;
    private int voucherDiscount;
    private Date voucherValidFrom;
    private Date voucherValidTo;
    private boolean voucherStatus;
    private boolean isHidden;
    private String voucherType;
    private boolean isUsed;

    public Voucher() {
    }

    public Voucher(int voucherId, String voucherCode, String voucherDescription, int voucherDiscount, Date voucherValidFrom, Date voucherValidTo, boolean voucherStatus,boolean isUsed,String voucherType,boolean isHidden) {
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.voucherDescription = voucherDescription;
        this.voucherDiscount = voucherDiscount;
        this.voucherValidFrom = voucherValidFrom;
        this.voucherValidTo = voucherValidTo;
        this.voucherStatus = voucherStatus;
        this.isUsed = isUsed;
        this.voucherType = voucherType;
        this.isHidden = isHidden;        
    }


    // Getters và setters
    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getVoucherDescription() {
        return voucherDescription;
    }

    public void setVoucherDescription(String voucherDescription) {
        this.voucherDescription = voucherDescription;
    }

    public int getVoucherDiscount() {
        return voucherDiscount;
    }

    public void setVoucherDiscount(int voucherDiscount) {
        this.voucherDiscount = voucherDiscount;
    }

    public Date getVoucherValidFrom() {
        return voucherValidFrom;
    }

    public void setVoucherValidFrom(Date voucherValidFrom) {
        this.voucherValidFrom = voucherValidFrom;
    }

    public Date getVoucherValidTo() {
        return voucherValidTo;
    }

    public void setVoucherValidTo(Date voucherValidTo) {
        this.voucherValidTo = voucherValidTo;
    }

    public boolean isVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(boolean voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
