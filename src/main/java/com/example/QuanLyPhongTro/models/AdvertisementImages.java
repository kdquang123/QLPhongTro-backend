package com.example.QuanLyPhongTro.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Advertisement_Images")
public class AdvertisementImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@JsonProperty("image_path")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "id_ad")
	@JsonIgnore
    private Advertisements advertisement;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Advertisements getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisements advertisement) {
		this.advertisement = advertisement;
	}

    // Getters and Setters
    
}

