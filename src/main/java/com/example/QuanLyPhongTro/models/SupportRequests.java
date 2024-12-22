package com.example.QuanLyPhongTro.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Support_Requests")
public class SupportRequests {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date createdAt;

	private Integer status; // 0: Pending, 1: Replied (admin đã trả lời)

	private String content; // Nội dung yêu cầu từ user

	private String adminReply; // Nội dung trả lời từ admin

	@ManyToOne
	@JoinColumn(name = "id_user")
	private Users user;

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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}