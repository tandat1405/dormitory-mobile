package com.datnt.dormitorymanagement.SendModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueTicket {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("ownerId")
    @Expose
    private Integer ownerId;
    @SerializedName("equipmentId")
    @Expose
    private Integer equipmentId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public IssueTicket(Integer type, Integer ownerId, Integer equipmentId, String description, String imageUrl) {
        this.type = type;
        this.ownerId = ownerId;
        this.equipmentId = equipmentId;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}