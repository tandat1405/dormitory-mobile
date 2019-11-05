package com.datnt.dormitorymanagement.ApiResultModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueTicketResult {

    @SerializedName("resultList")
    @Expose
    private List<ResultList> resultList = null;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;
    @SerializedName("totalPage")
    @Expose
    private Integer totalPage;

    public List<ResultList> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultList> resultList) {
        this.resultList = resultList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}
