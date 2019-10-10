package com.datnt.dormitorymanagement.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.datnt.dormitorymanagement.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
    }

    public void clickToViewProfile(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
    }
}
