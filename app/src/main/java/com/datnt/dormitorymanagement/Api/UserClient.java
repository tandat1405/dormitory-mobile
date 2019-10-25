package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.ApiResultModel.LoginResult;
import com.datnt.dormitorymanagement.SendModel.SocialUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserClient {
    @POST("/api/users/login")
    Call<LoginResult> login(@Body SocialUser socialUser);
}
