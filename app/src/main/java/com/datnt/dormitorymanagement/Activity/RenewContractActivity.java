package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.Utility;

public class RenewContractActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_contract);
        //setting support action bar
        getSupportActionBar().setTitle("Gia hạn hợp đồng");
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
}
