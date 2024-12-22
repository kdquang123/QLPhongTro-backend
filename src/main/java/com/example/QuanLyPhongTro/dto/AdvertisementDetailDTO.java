package com.example.QuanLyPhongTro.dto;

import com.example.QuanLyPhongTro.models.Advertisements;

public class AdvertisementDetailDTO extends  AdvertisementDTO{
    private double latitude;
    private double longitude;
    private String email;
    public AdvertisementDetailDTO(Advertisements advertisements) {
        super(advertisements);
        latitude = advertisements.getLatitude();
        longitude = advertisements.getLongitude();
        email=advertisements.getUser().getEmail();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
