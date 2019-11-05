package com.datnt.dormitorymanagement.SendModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditRoomBooking {

    @SerializedName("roomBookingRequestFormId")
    @Expose
    private Integer roomBookingRequestFormId;
    @SerializedName("studentId")
    @Expose
    private Integer studentId;
    @SerializedName("month")
    @Expose
    private Integer month;
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

    public EditRoomBooking(Integer roomBookingRequestFormId, Integer studentId, Integer month, Integer targetRoomType, String identityCardImageUrl, String studentCardImageUrl, Integer priorityType, String priorityImageUrl) {
        this.roomBookingRequestFormId = roomBookingRequestFormId;
        this.studentId = studentId;
        this.month = month;
        this.targetRoomType = targetRoomType;
        this.identityCardImageUrl = identityCardImageUrl;
        this.studentCardImageUrl = studentCardImageUrl;
        this.priorityType = priorityType;
        this.priorityImageUrl = priorityImageUrl;
    }

    public Integer getRoomBookingRequestFormId() {
        return roomBookingRequestFormId;
    }

    public void setRoomBookingRequestFormId(Integer roomBookingRequestFormId) {
        this.roomBookingRequestFormId = roomBookingRequestFormId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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
