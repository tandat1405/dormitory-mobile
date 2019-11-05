package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.ApiResultModel.IssueTicketResult;
import com.datnt.dormitorymanagement.ApiResultModel.ResultList;
import com.datnt.dormitorymanagement.ApiResultModel.TicketDetailResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IssueTicketClient {
    @GET("/api/IssueTickets/AdvancedGet")
    Call<IssueTicketResult> getListIssueTicket();

    @GET("/api/IssueTickets/{id}")
    Call<TicketDetailResult> getTicketDetail(@Path("id") int ticketId);
}
