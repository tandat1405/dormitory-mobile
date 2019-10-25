package com.datnt.dormitorymanagement.ApiResultModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookRoomResult {

    @SerializedName("roomBookingRequestFormId")
    @Expose
    private Integer roomBookingRequestFormId;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("studentId")
    @Expose
    private Integer studentId;
    @SerializedName("targetRoomType")
    @Expose
    private Integer targetRoomType;
    @SerializedName("identityCardImageUrl")
    @Expose
    private String identityCardImageUrl;
    @SerializedName("studentCardImageUrl")
    @Expose
    private String studentCardImageUrl;
    @SerializedName("priorityType")
    @Expose
    private Integer priorityType;
    @SerializedName("priorityImageUrl")
    @Expose
    private String priorityImageUrl;

    public Integer getRoomBookingRequestFormId() {
        return roomBookingRequestFormId;
    }

    public void setRoomBookingRequestFormId(Integer roomBookingRequestFormId) {
        this.roomBookingRequestFormId = roomBookingRequestFormId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTargetRoomType() {
        return targetRoomType;
    }

    public void setTargetRoomType(Integer targetRoomType) {
        this.targetRoomType = targetRoomType;
    }

    public String getIdentityCardImageUrl() {
        return identityCardImageUrl;
    }

    public void setIdentityCardImageUrl(String identityCardImageUrl) {
        this.identityCardImageUrl = identityCardImageUrl;
    }

    public String getStudentCardImageUrl() {
        return studentCardImageUrl;
    }

    public void setStudentCardImageUrl(String studentCardImageUrl) {
        this.studentCardImageUrl = studentCardImageUrl;
    }

    public Integer getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(Integer priorityType) {
        this.priorityType = priorityType;
    }

    public String getPriorityImageUrl() {
        return priorityImageUrl;
    }

    public void setPriorityImageUrl(String priorityImageUrl) {
        this.priorityImageUrl = priorityImageUrl;
    }

}
