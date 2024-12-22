package com.example.QuanLyPhongTro.payload;

public class InvoiceRequest {
    private Double total; // Tổng tiền
    private String message; // Thông báo
    private Integer roomId; // ID phòng
    private String email; // Địa chỉ email để gửi thông báo

    // Getters và Setters
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}