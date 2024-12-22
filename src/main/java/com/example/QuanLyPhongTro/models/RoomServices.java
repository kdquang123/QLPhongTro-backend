package com.example.QuanLyPhongTro.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Room_Services")
public class RoomServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double cost;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private Integer unit;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Rooms room;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Rooms getRoom() {
		return room;
	}

	public void setRoom(Rooms room) {
		this.room = room;
	}

    // Getters and Setters
    
}