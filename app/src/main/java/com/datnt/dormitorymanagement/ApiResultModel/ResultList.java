package com.datnt.dormitorymanagement.ApiResultModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultList {

    @SerializedName("issueTicketId")
    @Expose
    private Integer issueTicketId;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("typeName")
    @Expose
    private String typeName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ownerId")
    @Expose
    private Integer ownerId;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("equipmentId")
    @Expose
    private Integer equipmentId;
    @SerializedName("equipmentCode")
    @Expose
    private String equipmentCode;
    @SerializedName("equipmentType")
    @Expose
    private Integer equipmentType;
    @SerializedName("equipmentTypeName")
    @Expose
    private String equipmentTypeName;
    @SerializedName("roomId")
    @Expose
    private Integer roomId;
    @SerializedName("roomName")
    @Expose
    private String roomName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageUrl")
    @Expose
    private Object imageUrl;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    public Integer getIssueTicketId() {
        return issueTicketId;
    }

    public void setIssueTicketId(Integer issueTicketId) {
        this.issueTicketId = issueTicketId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
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
