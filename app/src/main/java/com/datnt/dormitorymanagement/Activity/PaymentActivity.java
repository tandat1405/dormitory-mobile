package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.datnt.dormitorymanagement.Adapter.PaymentHistoryAdapter;
import com.datnt.dormitorymanagement.InAppModel.Payment;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        //setting support action bar
        getSupportActionBar().setTitle("Thanh toán");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

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

    @Override
    public void onBackPressed() {
        Utility.hideSoftKeyboard(this);
        super.onBackPressed();
    }

    public void clickToViewPaymentHistory(View view) {
        startActivity(new Intent(this, PaymentHistoryActivity.class));
    }
}
