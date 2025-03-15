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

public class Post {

    private int postId;
    private int accountId;
    private String title;
    private String content;
    private int statusId;  // Giữ lại statusId
    private PostStatus status;  // Thêm PostStatus để lấy tên trạng thái
    private Timestamp createdDate;
    private String rejectReason;

    // Getter và Setter cho statusId
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    // Getter và Setter cho status (PostStatus)
    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    // Các getter và setter khác
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    // Constructor
    public Post() {}

    public Post(int postId, int accountId, String title, String content, int statusId, Timestamp createdDate) {
        this.postId = postId;
        this.accountId = accountId;
        this.title = title;
        this.content = content;
        this.statusId = statusId;
        this.createdDate = createdDate;
    }

    // Phương thức kiểm tra đối tượng Post có hợp lệ không
    public static boolean isEmpty(Post p) {
        return p.getPostId() < 0 && p.getAccountId() < 0;
    }
}
