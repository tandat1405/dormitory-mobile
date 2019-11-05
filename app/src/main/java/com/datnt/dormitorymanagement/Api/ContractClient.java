package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.SendModel.RenewContract;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ContractClient {
    @POST("/api/ContractRenewal")
    Call<ResponseBody> renewContract(@Body RenewContract renewContract);
}
