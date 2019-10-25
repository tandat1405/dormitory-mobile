package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.ApiResultModel.BookRoomResult;
import com.datnt.dormitorymanagement.SendModel.RoomBooking;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RoomBookingClient {

    @POST("/api/RoomBookings")
    Call<BookRoomResult> bookRoom(@Body RoomBooking roomBooking);
}
