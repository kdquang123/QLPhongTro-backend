package com.example.QuanLyPhongTro.models;
import jakarta.persistence.*;

@Entity
@Table(name = "Tenants")
public class Tenants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;

    private String phoneNumber;

    private String email;

    private Integer isRepresentative;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Rooms room;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIsRepresentative() {
		return isRepresentative;
	}

	public void setIsRepresentative(Integer isRepresentative) {
		this.isRepresentative = isRepresentative;
	}

	public Rooms getRoom() {
		return room;
	}

	public void setRoom(Rooms room) {
		this.room = room;
	}

    // Getters and Setters
    
}

