package com.example.QuanLyPhongTro.dto;

import com.example.QuanLyPhongTro.models.Advertisements;
import com.example.QuanLyPhongTro.models.SupportRequests;
import com.example.QuanLyPhongTro.models.Users;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

public class SupportRequestDTO {

    private Integer id;

    @JsonProperty("create_at")
    private Date createdAt;

    private Integer status; // 0: Pending, 1: Replied (admin đã trả lời)

    private String content; // Nội dung yêu cầu từ user

    @JsonProperty("admin_rely")
    private String adminReply; // Nội dung trả lời từ admin

    private String username;
    private String phone_number;

    public SupportRequestDTO(SupportRequests supportRequests) {
        this.id = supportRequests.getId();
        this.status = supportRequests.getStatus();
        this.createdAt = supportRequests.getCreatedAt();
        this.content = supportRequests.getContent();
        this.adminReply = supportRequests.getAdminReply();
        this.username=supportRequests.getUser().getUsername();
        this.phone_number = supportRequests.getUser().getPhoneNumber();
    }

    // Getters và Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdminReply() {
        return adminReply;
    }

    public void setAdminReply(String adminReply) {
        this.adminReply = adminReply;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
