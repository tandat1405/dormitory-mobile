package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.ApiResultModel.BookRoomResult;
import com.datnt.dormitorymanagement.ApiResultModel.RoomBookingDetailResult;
import com.datnt.dormitorymanagement.SendModel.EditRoomBooking;
import com.datnt.dormitorymanagement.SendModel.RoomBooking;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RoomBookingClient {

    @POST("/api/RoomBookings")
    Call<BookRoomResult> bookRoom(@Body RoomBooking roomBooking);

    @GET("/api/RoomBookings/GetDetail/{id}")
    Call<RoomBookingDetailResult> getRoomBookingDetail(@Path("id") int bookingId);

    @PUT("/api/RoomBookings")
    Call<ResponseBody> edtiRoomBooking(@Body EditRoomBooking editRoomBooking);
}
