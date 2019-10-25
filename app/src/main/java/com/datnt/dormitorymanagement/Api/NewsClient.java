package com.datnt.dormitorymanagement.Api;

import com.datnt.dormitorymanagement.ApiResultModel.NewsDetailResult;
import com.datnt.dormitorymanagement.ApiResultModel.NewsResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsClient {
    @GET("/api/News/Headlines")
    Call<List<NewsResult>> getNews();


    @GET("/api/News/{id}")
    Call<NewsDetailResult> getNewsDetail(@Path("id") int newsId);
}
