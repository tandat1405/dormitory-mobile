package com.datnt.dormitorymanagement.ApiResultModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EquipmentResult {

    @SerializedName("equipmentId")
    @Expose
    private Integer equipmentId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("equipmentTypeId")
    @Expose
    private Integer equipmentTypeId;
    @SerializedName("equipmentTypeName")
    @Expose
    private String equipmentTypeName;
    @SerializedName("roomId")
    @Expose
    private Integer roomId;
    @SerializedName("roomName")
    @Expose
    private String roomName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(Integer equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}