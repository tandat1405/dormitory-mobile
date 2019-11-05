package com.datnt.dormitorymanagement.ApiResultModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentStatusResult {

    @SerializedName("numberOfUnseenNotification")
    @Expose
    private Integer numberOfUnseenNotification;
    @SerializedName("isHaveRoom")
    @Expose
    private Boolean isHaveRoom;
    @SerializedName("isHaveRequestBooking")
    @Expose
    private Boolean isHaveRequestBooking;
    @SerializedName("isHaveRequestTransfer")
    @Expose
    private Boolean isHaveRequestTransfer;
    @SerializedName("isHaveRequestCancel")
    @Expose
    private Boolean isHaveRequestCancel;
    @SerializedName("isHaveRequestRenew")
    @Expose
    private Boolean isHaveRequestRenew;
    @SerializedName("isHavePayment")
    @Expose
    private Boolean isHavePayment;

    public Integer getNumberOfUnseenNotification() {
        return numberOfUnseenNotification;
    }

    public void setNumberOfUnseenNotification(Integer numberOfUnseenNotification) {
        this.numberOfUnseenNotification = numberOfUnseenNotification;
    }

    public Boolean getIsHaveRoom() {
        return isHaveRoom;
    }

    public void setIsHaveRoom(Boolean isHaveRoom) {
        this.isHaveRoom = isHaveRoom;
    }

    public Boolean getIsHaveRequestBooking() {
        return isHaveRequestBooking;
    }

    public void setIsHaveRequestBooking(Boolean isHaveRequestBooking) {
        this.isHaveRequestBooking = isHaveRequestBooking;
    }

    public Boolean getIsHaveRequestTransfer() {
        return isHaveRequestTransfer;
    }

    public void setIsHaveRequestTransfer(Boolean isHaveRequestTransfer) {
        this.isHaveRequestTransfer = isHaveRequestTransfer;
    }

    public Boolean getIsHaveRequestCancel() {
        return isHaveRequestCancel;
    }

    public void setIsHaveRequestCancel(Boolean isHaveRequestCancel) {
        this.isHaveRequestCancel = isHaveRequestCancel;
    }

    public Boolean getIsHaveRequestRenew() {
        return isHaveRequestRenew;
    }

    public void setIsHaveRequestRenew(Boolean isHaveRequestRenew) {
        this.isHaveRequestRenew = isHaveRequestRenew;
    }

    public Boolean getIsHavePayment() {
        return isHavePayment;
    }

    public void setIsHavePayment(Boolean isHavePayment) {
        this.isHavePayment = isHavePayment;
    }

}
