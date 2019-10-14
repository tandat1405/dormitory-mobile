package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.Utility;

public class CancelContractActivity extends AppCompatActivity {
    private Spinner spn_CancelFromDate;
    String[] spnArr = {"1/10/2019","1/11/2019","1/12/2019"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_contract);
        //setting support action bar
        getSupportActionBar().setTitle("Hủy hợp đồng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        spn_CancelFromDate = findViewById(R.id.spn_cancel_from);
        ArrayAdapter spnAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,spnArr);
        spn_CancelFromDate.setAdapter(spnAdapter);

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
