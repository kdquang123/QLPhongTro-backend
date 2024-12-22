package com.example.QuanLyPhongTro.dto;

import com.example.QuanLyPhongTro.models.AdvertisementImages;
import com.example.QuanLyPhongTro.models.Advertisements;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

public class AdvertisementDTO {
    private Integer id;

    private String description;

    private Integer status;

    private String address;

    private Double cost;

    @JsonProperty("create_at")
    private Date created_at;

    private String title;

    private Integer area;

    private Integer type;

    @JsonProperty("full_name")
    private String full_name;

    @JsonProperty("phone_number")
    private String phone_number;

    private Set<AdvertisementImages> images;

    public AdvertisementDTO(Advertisements advertisement) {
        this.id = advertisement.getId();
        this.description = advertisement.getDescription();
        this.status = advertisement.getStatus();
        this.address = advertisement.getAddress();
        this.cost = advertisement.getCost();
        this.created_at = advertisement.getCreatedAt();
        this.title = advertisement.getTitle();
        this.area = advertisement.getArea();
        this.type = advertisement.getType();
        this.full_name=advertisement.getUser().getFullName();
        this.phone_number = advertisement.getUser().getPhoneNumber();
        this.images = advertisement.getAdvertisementImages();
    }


    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public Double getCost() {
        return cost;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public String getTitle() {
        return title;
    }

    public Integer getArea() {
        return area;
    }

    public Integer getType() {
        return type;
    }

    public String getFullName() {
        return full_name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public Set<AdvertisementImages> getImages() {
        return images;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setCreatedAt(Date createdAt) {
        this.created_at = createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setFullName(String fullName) {
        this.full_name = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
    }

    public void setImages(Set<AdvertisementImages> images) {
        this.images = images;
    }
}
