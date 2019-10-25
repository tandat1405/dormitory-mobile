package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.datnt.dormitorymanagement.Adapter.RoomFeesAdapter;
import com.datnt.dormitorymanagement.GoogleClient.GoogleClient;
import com.datnt.dormitorymanagement.InAppModel.RoomFees;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class SubmitMonthlyFeeActivity extends AppCompatActivity {
    private ListView lv_Fees;
    private Button submitButton, cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_monthly_fee);
        //setting support action bar
        getSupportActionBar().setTitle("Ghi tiền điện nước");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        lv_Fees = findViewById(R.id.lv_list_room);

        //fake data
        RoomFees roomFees1 = new RoomFees(1,123,89,"A101");
        RoomFees roomFees2 = new RoomFees(2,145,102,"A103");
        RoomFees roomFees3 = new RoomFees(3,154,73,"A104");
        RoomFees roomFees4 = new RoomFees(4,179,96,"A107");
        List<RoomFees> roomFeesList = new ArrayList<>();
        roomFeesList.add(roomFees1);
        roomFeesList.add(roomFees2);
        roomFeesList.add(roomFees3);
        roomFeesList.add(roomFees4);

        lv_Fees.setAdapter(new RoomFeesAdapter(this,roomFeesList));
        lv_Fees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(SubmitMonthlyFeeActivity.this);
                dialog.setContentView(R.layout.dialog_submit_monthly_fees);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                submitButton = dialog.findViewById(R.id.btn_monthly_fees_submit);
                cancelButton = dialog.findViewById(R.id.btn_monthly_fees_cancel);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                createConfirmLogoutDialog(SubmitMonthlyFeeActivity.this,"Logout", "Bạn có muốn đăng xuất không?");

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
    public void createConfirmLogoutDialog(Context mContext, String title, String mes){
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_confirm);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv_title = dialog.findViewById(R.id.tv_confirm_title);
        TextView tv_message = dialog.findViewById(R.id.tv_confirm_message);
        Button yesButton = dialog.findViewById(R.id.btn_confirm_yes);
        Button noButton = dialog.findViewById(R.id.btn_confirm_no);
        tv_title.setText(title);
        tv_message.setText(mes);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleClient googleClient = new GoogleClient();
                googleClient.signOut(SubmitMonthlyFeeActivity.this);
                finish();
                startActivity(new Intent(SubmitMonthlyFeeActivity.this, MainActivity.class));
            }
        });
        dialog.show();
    }

}
