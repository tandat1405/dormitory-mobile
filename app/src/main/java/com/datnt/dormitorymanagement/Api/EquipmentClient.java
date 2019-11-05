package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.ApiResultModel.EquipmentResult;
import com.datnt.dormitorymanagement.SendModel.IssueTicket;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EquipmentClient {
    @GET("/api/Equipments/GetByStudent/{studentId}")
    Call<List<EquipmentResult>> getEquipment(@Path("studentId") int studentId);

    @POST("/api/IssueTickets")
    Call<ResponseBody> sendIssueTicket(@Body IssueTicket issueTicket);
}
