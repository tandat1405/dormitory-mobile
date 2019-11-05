package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.IssueTicketClient;
import com.datnt.dormitorymanagement.ApiResultModel.ResultList;
import com.datnt.dormitorymanagement.ApiResultModel.TicketDetailResult;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.LoadImageInternet;
import com.datnt.dormitorymanagement.Utility.MyLoadingDialog;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HandleIssueTicketDetailActivity extends AppCompatActivity {
    private int ticketId, userId;
    private String token;
    private MySharedPreference mySharedPreference;
    private MyLoadingDialog myLoadingDialog;
    private TextView tv_OwnerName, tv_OwnerEmail, tv_RoomName, tv_TicketType, tv_EquipmentType, tv_Description;
    private LinearLayout ll_Image;
    private RelativeLayout rl_Equipment;
    private ImageView iv_Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_issue_ticket_detail);
        //setting support action bar
        getSupportActionBar().setTitle("Chi tiết yêu cầu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        tv_OwnerName = findViewById(R.id.tv_ticket_detail_owner_name);
        tv_OwnerEmail = findViewById(R.id.tv_ticket_detail_email);
        tv_RoomName = findViewById(R.id.tv_ticket_detail_room_name);
        tv_TicketType = findViewById(R.id.tv_ticket_detail_ticket_type);
        tv_EquipmentType = findViewById(R.id.tv_ticket_detail_ticket_equipment_name);
        tv_Description = findViewById(R.id.tv_ticket_detail_description);
        ll_Image = findViewById(R.id.ll_ticket_detail_image);
        rl_Equipment = findViewById(R.id.rl_ticket_detail_equipment);
        iv_Image = findViewById(R.id.iv_ticket_detail_image);
        //
        myLoadingDialog = new MyLoadingDialog(this);
        mySharedPreference = new MySharedPreference(this);
        Intent intent = getIntent();
        ticketId = intent.getIntExtra("id",0);
        userId = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        //
        getTicketDetail();
    }

    private void getTicketDetail() {
        myLoadingDialog.showLoading();
        IssueTicketClient issueTicketClient = ApiUtil.issueTicketClient(token);
        Call<TicketDetailResult> call = issueTicketClient.getTicketDetail(ticketId);
        call.enqueue(new Callback<TicketDetailResult>() {
            @Override
            public void onResponse(Call<TicketDetailResult> call, Response<TicketDetailResult> response) {
                if (response.isSuccessful()) {
                    tv_OwnerName.setText(response.body().getOwnerName());
                    tv_OwnerEmail.setText(response.body().getOwnerEmail());
                    //tv_RoomName.setText(response.body().get);
                    tv_TicketType.setText(response.body().getTypeName());
                    if(response.body().getEquipmentId()!=null){
                        rl_Equipment.setVisibility(View.VISIBLE);
                        //TODO: add equipment name
                        tv_EquipmentType.setText(response.body().getEquipmentId().toString()+"");
                    }
                    tv_Description.setText(response.body().getDescription());
                    if(response.body().getImageUrl()!=null && response.body().getImageUrl().length()>0){
                        ll_Image.setVisibility(View.VISIBLE);
                        LoadImageInternet loadImageInternet = new LoadImageInternet(iv_Image);
                        loadImageInternet.execute(response.body().getImageUrl());
                    }
                    myLoadingDialog.dismissLoading();

                }
                else {
                    Toast.makeText(HandleIssueTicketDetailActivity.this, "", Toast.LENGTH_SHORT).show();
                    myLoadingDialog.dismissLoading();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TicketDetailResult> call, Throwable t) {
                Toast.makeText(HandleIssueTicketDetailActivity.this, "", Toast.LENGTH_SHORT).show();
                myLoadingDialog.dismissLoading();
                finish();
            }
        });
    }
}
