package com.datnt.dormitorymanagement.ApiResultModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("startedSchoolYear")
    @Expose
    private Integer startedSchoolYear;
    @SerializedName("term")
    @Expose
    private Integer term;
    @SerializedName("studentCardNumber")
    @Expose
    private String studentCardNumber;
    @SerializedName("gender")
    @Expose
    private Boolean gender;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("birthDay")
    @Expose
    private String birthDay;
    @SerializedName("identityNumber")
    @Expose
    private String identityNumber;
    @SerializedName("identityCardImageIrl")
    @Expose
    private String identityCardImageIrl;
    @SerializedName("priorityType")
    @Expose
    private PriorityType priorityType;
    @SerializedName("room")
    @Expose
    private Room room;
    @SerializedName("isRoomLeader")
    @Expose
    private Boolean isRoomLeader;
    @SerializedName("evaluationScore")
    @Expose
    private Integer evaluationScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStartedSchoolYear() {
        return startedSchoolYear;
    }

    public void setStartedSchoolYear(Integer startedSchoolYear) {
        this.startedSchoolYear = startedSchoolYear;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getStudentCardNumber() {
        return studentCardNumber;
    }

    public void setStudentCardNumber(String studentCardNumber) {
        this.studentCardNumber = studentCardNumber;
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

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getIdentityCardImageIrl() {
        return identityCardImageIrl;
    }

    public void setIdentityCardImageIrl(String identityCardImageIrl) {
        this.identityCardImageIrl = identityCardImageIrl;
    }

    public PriorityType getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(PriorityType priorityType) {
        this.priorityType = priorityType;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean getIsRoomLeader() {
        return isRoomLeader;
    }

    public void setIsRoomLeader(Boolean isRoomLeader) {
        this.isRoomLeader = isRoomLeader;
    }

    public Integer getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(Integer evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

}