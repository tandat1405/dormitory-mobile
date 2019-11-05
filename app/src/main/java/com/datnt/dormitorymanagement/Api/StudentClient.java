package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.ApiResultModel.LoginResult;
import com.datnt.dormitorymanagement.ApiResultModel.ProfileResult;
import com.datnt.dormitorymanagement.ApiResultModel.RenewContractStateResult;
import com.datnt.dormitorymanagement.ApiResultModel.Room;
import com.datnt.dormitorymanagement.ApiResultModel.RoomInfoResult;
import com.datnt.dormitorymanagement.ApiResultModel.StudentStatusResult;
import com.datnt.dormitorymanagement.SendModel.SocialUser;
import com.datnt.dormitorymanagement.SendModel.UpdateProfile;

import java.util.List;

import okhttp3.ResponseBody;
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


    @GET("/api/Home/{id}")
    Call<StudentStatusResult> getStudentStatus(@Path("id") int id);

    @GET("/api/Home/Date")
    Call<ResponseBody> getSystemDate();

    @GET("/api/Student/CheckStudentForRenewContract/{id}")
    Call<RenewContractStateResult> getStudentRenewState(@Path("id") int studentId);

    @GET("/api/Rooms/GetRoomTypeInfo")
    Call<List<RoomInfoResult>> getRoomTypeInfo();
}
