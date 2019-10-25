package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.NewsClient;
import com.datnt.dormitorymanagement.ApiResultModel.NewsDetailResult;
import com.datnt.dormitorymanagement.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends AppCompatActivity {
    private TextView tv_Title, tv_Author, tv_Content;
    private ImageView iv_Image;
    private boolean isImageFullScreen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        //setting support action bar
        getSupportActionBar().setTitle("Chi tiết");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        tv_Title = findViewById(R.id.tv_news_detail_title);
        tv_Author = findViewById(R.id.tv_news_detail_author);
        tv_Content = findViewById(R.id.tv_news_detail_content);
        iv_Image = findViewById(R.id.iv_news_detail_image);
        iv_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(isImageFullScreen){
                //    isImageFullScreen=false;
                //    iv_Image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
                //    iv_Image.setAdjustViewBounds(true);
                //}
                //else{
                //    isImageFullScreen=true;
                //    iv_Image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                //    iv_Image.setScaleType(ImageView.ScaleType.FIT_XY);
                //}
            }
        });
        //get intent
        Intent intent = this.getIntent();
        int newsId = intent.getIntExtra("newsId",-1);
        String token = intent.getStringExtra("token");
        NewsClient newsClient = ApiUtil.newsClient(token);
        Call<NewsDetailResult> call = newsClient.getNewsDetail(newsId);
        call.enqueue(new Callback<NewsDetailResult>() {
            @Override
            public void onResponse(Call<NewsDetailResult> call, Response<NewsDetailResult> response) {
                if (response.isSuccessful()) {
                    tv_Title.setText(response.body().getTitle());
                    tv_Content.setText(response.body().getContent());

                    tv_Author.setText("Đăng bởi: "+response.body().getAuthor().getName()+" "+ response.body().getCreatedDate());
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<NewsDetailResult> call, Throwable t) {

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
