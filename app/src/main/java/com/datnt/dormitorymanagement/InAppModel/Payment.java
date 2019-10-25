package com.datnt.dormitorymanagement.InAppModel;

public class Payment {
    private int paymentId;
    private String paymentDate, paymentType, paymentPrice;

    public Payment(int paymentId, String paymentDate, String paymentType, String paymentPrice) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.paymentType = paymentType;
        this.paymentPrice = paymentPrice;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(String paymentPrice) {
        this.paymentPrice = paymentPrice;
    }
}
