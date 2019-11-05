package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.SendModel.TransferInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RoomTransferClient {

    @POST("/api/RoomTransfer")
    Call<ResponseBody> requestTransfer(@Body TransferInfo transferInfo);
}
