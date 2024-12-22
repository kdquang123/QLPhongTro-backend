package com.example.QuanLyPhongTro.payload;

public class PaymentInfo {
    private String transactionId; // ID giao dịch
    private boolean successful; // Trạng thái thanh toán

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    // Getter và setter
    // ...
}