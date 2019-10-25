package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.SendModel.RoomBooking;

public class ApiUtil {

    public static String BASE_URL = "https://dormywebservice.azurewebsites.net";
    public static UserClient userClient() {
        return RetrofitClient.getClient(BASE_URL).create(UserClient.class);
    }
    public static StudentClient studentClient(String token) {
        return RetrofitClient.createClientWithHeaderToken(BASE_URL, token).create(StudentClient.class);
    }
    public static NewsClient newsClient(String token) {
        return RetrofitClient.createClientWithHeaderToken(BASE_URL, token).create(NewsClient.class);
    }
    public static RoomBookingClient roomBooking(String token) {
        return RetrofitClient.createClientWithHeaderToken(BASE_URL, token).create(RoomBookingClient.class);
    }
    //public static GroupClient groupClient() {
    //    return RetrofitClient.getClient(BASE_URL).create(GroupClient.class);
    //}
    //public static UserExpenseClient userExpenseClient() {
    //    return RetrofitClient.getClient(BASE_URL).create(UserExpenseClient.class);
    //}
    //public static InvitationClient invitationClient() {
    //    return RetrofitClient.getClient(BASE_URL).create(InvitationClient.class);
    //}
}
