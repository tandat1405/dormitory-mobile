package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.datnt.dormitorymanagement.Adapter.NewsAdapter;
import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.NewsClient;
import com.datnt.dormitorymanagement.ApiResultModel.NewsResult;
import com.datnt.dormitorymanagement.InAppModel.News;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    private ListView lv_News;
    private MySharedPreference mySharedPreference;
    private String token;
    private List<NewsResult> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //setting support action bar
        getSupportActionBar().setTitle("Thông báo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        lv_News = findViewById(R.id.lv_news);
        newsList = new ArrayList<>();
        //get token
        mySharedPreference = new MySharedPreference(this);
        token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        //getNews
        NewsClient newsClient = ApiUtil.newsClient(token);
        Call<List<NewsResult>> call = newsClient.getNews();
        call.enqueue(new Callback<List<NewsResult>>() {
            @Override
            public void onResponse(Call<List<NewsResult>> call, Response<List<NewsResult>> response) {
                if (response.isSuccessful()) {
                    newsList = response.body();
                    NewsAdapter na = new NewsAdapter(NewsActivity.this, newsList);
                    lv_News.setAdapter(na);
                    na.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(NewsActivity.this, "Có lỗi xảy ra xin vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NewsResult>> call, Throwable t) {
                Toast.makeText(NewsActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
            }
        });
        lv_News.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsResult news = newsList.get(position);
                Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                intent.putExtra("newsId",news.getNewsId());
                intent.putExtra("token", token);
                startActivity(intent);
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
