package com.datnt.dormitorymanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.datnt.dormitorymanagement.Adapter.GridHomeAdapter;
import com.datnt.dormitorymanagement.InAppModel.Home;
import com.datnt.dormitorymanagement.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private List<Home> homeList;

    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        //
        //gridView = findViewById(R.id.grid_home);
        //
        //createHomeData();
    }

    private void createHomeData() {
        homeList = new ArrayList<>();
        Home news = new Home(1, R.drawable.news,"Tin tức");
        Home book = new Home(2,R.drawable.bookroom,"Đăng kí phòng");
        Home transfer = new Home(3, R.drawable.transfer,"Chuyển phòng");

        homeList.add(news);
        homeList.add(book);
        homeList.add(transfer);
        GridHomeAdapter gha = new GridHomeAdapter(this, homeList);
        gridView.setAdapter(gha);



    }

    public void clickToViewProfile(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void clickToBookRoom(View view) {
        startActivity(new Intent(this, BookRoomActivity.class));
    }

    public void clickToTransferRoom(View view) {
        startActivity(new Intent(this, TransferRoomActivity.class));
    }

    public void clickToRenewContract(View view) {
        startActivity(new Intent(this, RenewContractActivity.class));
    }

    public void clickToCancelContract(View view) {
        startActivity(new Intent(this, CancelContractActivity.class));
    }

    public void clickToCreateIssue(View view) {
        startActivity(new Intent(this, CreateIssueActivity.class));
    }
}
