package com.datnt.dormitorymanagement.ApiResultModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomBookingDetailResult {

    @SerializedName("studentId")
    @Expose
    private Integer studentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("identityCardNumber")
    @Expose
    private String identityCardNumber;
    @SerializedName("gender")
    @Expose
    private Boolean gender;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("term")
    @Expose
    private Integer term;
    @SerializedName("priorityTypeId")
    @Expose
    private Integer priorityTypeId;
    @SerializedName("priorityType")
    @Expose
    private String priorityType;
    @SerializedName("roomTypeId")
    @Expose
    private Integer roomTypeId;
    @SerializedName("roomType")
    @Expose
    private String roomType;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("roomBookingId")
    @Expose
    private Integer roomBookingId;
    @SerializedName("month")
    @Expose
    private Integer month;
    @SerializedName("priorityImageUrl")
    @Expose
    private String priorityImageUrl;
    @SerializedName("identityCardImageUrl")
    @Expose
    private String identityCardImageUrl;
    @SerializedName("studentCardImageUrl")
    @Expose
    private String studentCardImageUrl;
    @SerializedName("roomId")
    @Expose
    private Integer roomId;
    @SerializedName("roomName")
    @Expose
    private String roomName;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getPriorityTypeId() {
        return priorityTypeId;
    }

    public void setPriorityTypeId(Integer priorityTypeId) {
        this.priorityTypeId = priorityTypeId;
    }

    public String getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(String priorityType) {
        this.priorityType = priorityType;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
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

    public Integer getRoomBookingId() {
        return roomBookingId;
    }

    public void setRoomBookingId(Integer roomBookingId) {
        this.roomBookingId = roomBookingId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getPriorityImageUrl() {
        return priorityImageUrl;
    }

    public void setPriorityImageUrl(String priorityImageUrl) {
        this.priorityImageUrl = priorityImageUrl;
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

}
