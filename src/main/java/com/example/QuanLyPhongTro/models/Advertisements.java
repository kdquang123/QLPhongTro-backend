package com.example.QuanLyPhongTro.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Advertisements")
public class Advertisements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(columnDefinition = "LONGTEXT")
    private String description;

    private Integer status;

    private String address;

    private Double cost;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private Integer maxOccupants;

    private String title;

    private Integer area;

    private Double latitude;

    private Double longitude;

	private Integer type;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users user;

	@OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<AdvertisementImages> advertisementImages;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getMaxOccupants() {
		return maxOccupants;
	}

	public void setMaxOccupants(Integer maxOccupants) {
		this.maxOccupants = maxOccupants;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Set<AdvertisementImages> getAdvertisementImages() {
		return advertisementImages;
	}

	public void setAdvertisementImages(Set<AdvertisementImages> advertisementImages) {
		this.advertisementImages = advertisementImages;
	}

	// Getters and Setters

    
}

