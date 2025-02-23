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
public class Account {

    private int accountId;
    private String username;
    private String passwordHash;
    private String email;
    private String bannedReason;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean Active) {
        this.isActive = Active;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Phương thức kiểm tra tài khoản Google
    public String getBannedReason() {
        return bannedReason;
    }

    public boolean isGoogleAccount() {
        return this.passwordHash == null || this.passwordHash.isEmpty();
    }
    private String phoneNumber;
    private int roleId;
    private Date createdDate;
    private Date lastLogin;
    private boolean isActive;
    private String profileImage;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;

    public Account() {
    }

    public Account(int accountId, String username, String passwordHash, String email, String phoneNumber, int roleId,
            Date createdDate, Date lastLogin, boolean isActive, String profileImage, String firstName, String lastName,
            Date dateOfBirth, String gender, String bannedReason) {
        this.accountId = accountId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
        this.isActive = isActive;
        this.profileImage = profileImage;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bannedReason = bannedReason;
    }

}
