/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author tvhun
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
    private Integer parentCategoryId;
    private boolean isHidden;
    private Date createdDate;
    private Date lastUpdated;
    private String categoryImage;

    public Category() {
    }

    public Category(int categoryId, String categoryName, String categoryDescription, Integer parentCategoryId, String categoryImage, boolean isHidden, Timestamp createdTimestamp, Timestamp lastUpdatedTimestamp) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.parentCategoryId = parentCategoryId;
        this.categoryImage = categoryImage;
        this.isHidden = isHidden;
        this.createdDate = createdTimestamp != null ? new Date(createdTimestamp.getTime()) : null;
        this.lastUpdated = lastUpdatedTimestamp != null ? new Date(lastUpdatedTimestamp.getTime()) : null;
    }


    public Category(int i, String name, String description, Integer parentId, String image, boolean hidden, Object object, Object object0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public boolean isIsHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        this.isHidden = hidden;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCategoryImage() {
        return ""; // Trả về một giá trị mặc định hoặc có thể sửa thành categoryImage nếu bạn thêm thuộc tính này
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public boolean isHidden() {
        return isHidden;
    }

}
