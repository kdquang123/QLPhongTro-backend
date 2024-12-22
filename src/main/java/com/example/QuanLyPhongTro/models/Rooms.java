package com.example.QuanLyPhongTro.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Rooms")
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double price;

    private Integer roomNumber;

    private String description;

    private Integer occupancyStatus;

    private Integer maxOccupants;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "id_house")
    private Houses house;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOccupancyStatus() {
		return occupancyStatus;
	}

	public void setOccupancyStatus(Integer occupancyStatus) {
		this.occupancyStatus = occupancyStatus;
	}

	public Integer getMaxOccupants() {
		return maxOccupants;
	}

	public void setMaxOccupants(Integer maxOccupants) {
		this.maxOccupants = maxOccupants;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Houses getHouse() {
		return house;
	}

	public void setHouse(Houses house) {
		this.house = house;
	}

    // Getters and Setters
    
}

