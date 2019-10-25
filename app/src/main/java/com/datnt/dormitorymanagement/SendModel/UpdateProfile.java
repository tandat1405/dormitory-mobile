package com.datnt.dormitorymanagement.SendModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfile {

    @SerializedName("studentId")
    @Expose
    private Integer studentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("startedSchoolYear")
    @Expose
    private Integer startedSchoolYear;
    @SerializedName("identityNumber")
    @Expose
    private String identityNumber;
    @SerializedName("studentCardNumber")
    @Expose
    private String studentCardNumber;
    @SerializedName("birthDay")
    @Expose
    private String birthDay;
    @SerializedName("term")
    @Expose
    private Integer term;
    @SerializedName("gender")
    @Expose
    private Boolean gender;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    public UpdateProfile(Integer studentId, String name, Integer startedSchoolYear, String identityNumber, String studentCardNumber, String birthDay, Integer term, Boolean gender, String address, String phoneNumber) {
        this.studentId = studentId;
        this.name = name;
        this.startedSchoolYear = startedSchoolYear;
        this.identityNumber = identityNumber;
        this.studentCardNumber = studentCardNumber;
        this.birthDay = birthDay;
        this.term = term;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

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

    public Integer getStartedSchoolYear() {
        return startedSchoolYear;
    }

    public void setStartedSchoolYear(Integer startedSchoolYear) {
        this.startedSchoolYear = startedSchoolYear;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getStudentCardNumber() {
        return studentCardNumber;
    }

    public void setStudentCardNumber(String studentCardNumber) {
        this.studentCardNumber = studentCardNumber;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}