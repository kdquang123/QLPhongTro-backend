package com.example.QuanLyPhongTro.payload;

import javax.validation.constraints.NotBlank;

public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String fullName;

    private String email;

    private String phoneNumber;

    private int idService;

    private Double price;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFullName() {
        return fullName;
    }

    public int getIdService() {
        return idService;
    }

    public Double getPrice() {
        return price;
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



    // Getter và Setter
}