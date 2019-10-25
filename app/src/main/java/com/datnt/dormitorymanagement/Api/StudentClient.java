package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.ApiResultModel.LoginResult;
import com.datnt.dormitorymanagement.ApiResultModel.ProfileResult;
import com.datnt.dormitorymanagement.ApiResultModel.Room;
import com.datnt.dormitorymanagement.SendModel.SocialUser;
import com.datnt.dormitorymanagement.SendModel.UpdateProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudentClient {
    @PUT("/api/Student")
    Call<UpdateProfile> updateProfile(@Body UpdateProfile updateProfile);

    @GET("/api/Student/GetProfile/{id}")
    Call<ProfileResult> getProfile(@Path("id") int id);
}
