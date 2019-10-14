package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.Utility;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }

    @Override
    public void onBackPressed() {
        Utility.hideSoftKeyboard(this);
        super.onBackPressed();
    }
}
