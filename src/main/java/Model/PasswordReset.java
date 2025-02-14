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
public class PasswordReset {
    private int resetId;
    private int accountId;
    private String resetToken;
    private Date expirationDate;

    public int getResetId() {
        return resetId;
    }

    public void setResetId(int resetId) {
        this.resetId = resetId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public PasswordReset() {}

    public PasswordReset(int resetId, int accountId, String resetToken, Date expirationDate) {
        this.resetId = resetId;
        this.accountId = accountId;
        this.resetToken = resetToken;
        this.expirationDate = expirationDate;
    }
}
