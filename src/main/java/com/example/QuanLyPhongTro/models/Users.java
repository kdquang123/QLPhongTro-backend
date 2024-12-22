package com.example.QuanLyPhongTro.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Tên người dùng không được để trống")
	@Size(min = 3, max = 50, message = "Tên người dùng phải có từ 3 đến 50 ký tự")
	private String username;

	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
	private String password;

	@NotBlank(message = "Email không được để trống")
	@Email(message = "Email không hợp lệ")
	private String email;

	@NotBlank(message = "Số điện thoại không được để trống")
	@Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Số điện thoại không hợp lệ") // Thay đổi regex nếu cần
	private String phoneNumber;

	private String fullName;

	private Integer status;

	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@ManyToOne
	@JoinColumn(name = "id_service")
	private ServicePackages servicePackage;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Advertisements> advertisements;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<SupportRequests> supportRequests;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Houses> houses;

	// Getters and Setters...

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public ServicePackages getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(ServicePackages servicePackage) {
		this.servicePackage = servicePackage;
	}

	public Set<Advertisements> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Set<Advertisements> advertisements) {
		this.advertisements = advertisements;
	}

	public Set<SupportRequests> getSupportRequests() {
		return supportRequests;
	}

	public void setSupportRequests(Set<SupportRequests> supportRequests) {
		this.supportRequests = supportRequests;
	}

	public Set<Houses> getHouses() {
		return houses;
	}

	public void setHouses(Set<Houses> houses) {
		this.houses = houses;
	}
}