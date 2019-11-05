package com.datnt.dormitorymanagement.ApiResultModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketDetailResult {

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
    @SerializedName("ownerEmail")
    @Expose
    private String ownerEmail;
    @SerializedName("equipmentId")
    @Expose
    private Integer equipmentId;
    @SerializedName("targetStudentId")
    @Expose
    private Integer targetStudentId;
    @SerializedName("targetStudentName")
    @Expose
    private String targetStudentName;
    @SerializedName("targetStudentEmail")
    @Expose
    private String targetStudentEmail;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
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

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getTargetStudentId() {
        return targetStudentId;
    }

    public void setTargetStudentId(Integer targetStudentId) {
        this.targetStudentId = targetStudentId;
    }

    public String getTargetStudentName() {
        return targetStudentName;
    }

    public void setTargetStudentName(String targetStudentName) {
        this.targetStudentName = targetStudentName;
    }

    public String getTargetStudentEmail() {
        return targetStudentEmail;
    }

    public void setTargetStudentEmail(String targetStudentEmail) {
        this.targetStudentEmail = targetStudentEmail;
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