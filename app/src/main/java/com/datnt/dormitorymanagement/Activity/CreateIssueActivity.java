package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.Utility;

public class CreateIssueActivity extends AppCompatActivity {
    private LinearLayout ll_Equipment;
    private TextView tv_Equipment;
    private Spinner spn_IssueType, spn_EquipmentType;
    String[] spnArrRequestType = {"Phản ánh","Trang thiết bị"};
    String[] spnArrEquipmentType = {"Giường","Bàn ghế","Tủ quần áo","Quạt","Máy lạnh","Wifi"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_issue);
        //setting support action bar
        getSupportActionBar().setTitle("Tạo yêu cầu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        ll_Equipment = findViewById(R.id.ll_equipment_type);
        tv_Equipment = findViewById(R.id.tv_equipment_type);
        spn_IssueType = findViewById(R.id.spn_issue_type);
        spn_EquipmentType = findViewById(R.id.spn_equipment_type);
        //spinner
        ArrayAdapter spnIssueAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,spnArrRequestType);
        spn_IssueType.setAdapter(spnIssueAdapter);
        spn_IssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    tv_Equipment.setVisibility(View.VISIBLE);
                    ll_Equipment.setVisibility(View.VISIBLE);
                }
                else {
                    tv_Equipment.setVisibility(View.GONE);
                    ll_Equipment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter spnEquipmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,spnArrEquipmentType);
        spn_EquipmentType.setAdapter(spnEquipmentAdapter);
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
