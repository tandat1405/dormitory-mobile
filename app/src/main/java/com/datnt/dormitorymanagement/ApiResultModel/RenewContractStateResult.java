package com.datnt.dormitorymanagement.ApiResultModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RenewContractStateResult {

    @SerializedName("hasInValidTrainingPoint")
    @Expose
    private Boolean hasInValidTrainingPoint;
    @SerializedName("hasStayedMoreThanPermittedYear")
    @Expose
    private Boolean hasStayedMoreThanPermittedYear;
    @SerializedName("contractIsActiveNextMonth")
    @Expose
    private Boolean contractIsActiveNextMonth;
    @SerializedName("numberOfRoomTransferRequest")
    @Expose
    private Integer numberOfRoomTransferRequest;

    public Boolean getHasInValidTrainingPoint() {
        return hasInValidTrainingPoint;
    }

    public void setHasInValidTrainingPoint(Boolean hasInValidTrainingPoint) {
        this.hasInValidTrainingPoint = hasInValidTrainingPoint;
    }

    public Boolean getHasStayedMoreThanPermittedYear() {
        return hasStayedMoreThanPermittedYear;
    }

    public void setHasStayedMoreThanPermittedYear(Boolean hasStayedMoreThanPermittedYear) {
        this.hasStayedMoreThanPermittedYear = hasStayedMoreThanPermittedYear;
    }

    public Boolean getContractIsActiveNextMonth() {
        return contractIsActiveNextMonth;
    }

    public void setContractIsActiveNextMonth(Boolean contractIsActiveNextMonth) {
        this.contractIsActiveNextMonth = contractIsActiveNextMonth;
    }

    public Integer getNumberOfRoomTransferRequest() {
        return numberOfRoomTransferRequest;
    }

    public void setNumberOfRoomTransferRequest(Integer numberOfRoomTransferRequest) {
        this.numberOfRoomTransferRequest = numberOfRoomTransferRequest;
    }

}
