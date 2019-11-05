package com.datnt.dormitorymanagement.SendModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RenewContract {

    @SerializedName("studentId")
    @Expose
    private Integer studentId;
    @SerializedName("month")
    @Expose
    private Integer month;

    public RenewContract(Integer studentId, Integer month) {
        this.studentId = studentId;
        this.month = month;
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

}
