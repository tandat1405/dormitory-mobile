package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.datnt.dormitorymanagement.GoogleClient.GoogleClient;
import com.datnt.dormitorymanagement.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeForStaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_for_staff);
        getSupportActionBar().hide();
    }

    public void clickToRecordMonthlyBills(View view) {
    }

    public void clickToHandleIssueTikcet(View view) {
        startActivity(new Intent(this, HandleIssueTicketActivity.class));
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Đăng xuất")
                .setContentText("Bạn có muốn đăng xuất không?")
                .setCancelText("Không")
                .setConfirmText("Có")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        GoogleClient googleClient = new GoogleClient();
                        googleClient.signOut(HomeForStaffActivity.this);
                        finish();
                        HomeForStaffActivity.super.onBackPressed();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }
}
