package com.datnt.dormitorymanagement.SendModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferInfo {

    @SerializedName("studentId")
    @Expose
    private Integer studentId;
    @SerializedName("targetRoomType")
    @Expose
    private Integer targetRoomType;
    @SerializedName("description")
    @Expose
    private String description;

    public TransferInfo(Integer studentId, Integer targetRoomType, String description) {
        this.studentId = studentId;
        this.targetRoomType = targetRoomType;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
