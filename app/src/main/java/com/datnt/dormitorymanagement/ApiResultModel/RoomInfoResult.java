package com.datnt.dormitorymanagement.ApiResultModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomInfoResult {

    @SerializedName("roomTypeId")
    @Expose
    private Integer roomTypeId;
    @SerializedName("roomTypeName")
    @Expose
    private String roomTypeName;
    @SerializedName("roomTypeVacancy")
    @Expose
    private Integer roomTypeVacancy;
    @SerializedName("roomTypePrice")
    @Expose
    private Integer roomTypePrice;

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public Integer getRoomTypeVacancy() {
        return roomTypeVacancy;
    }

    public void setRoomTypeVacancy(Integer roomTypeVacancy) {
        this.roomTypeVacancy = roomTypeVacancy;
    }

    public Integer getRoomTypePrice() {
        return roomTypePrice;
    }

    public void setRoomTypePrice(Integer roomTypePrice) {
        this.roomTypePrice = roomTypePrice;
    }

}
