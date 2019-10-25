package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.datnt.dormitorymanagement.Adapter.NotificationAdapter;
import com.datnt.dormitorymanagement.InAppModel.Notification;
import com.datnt.dormitorymanagement.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private ListView lv_Noti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //setting support action bar
        getSupportActionBar().setTitle("Thông tin cá nhân");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        lv_Noti = findViewById(R.id.lv_notification);
        //fake data
        Notification notification1 = new Notification(1,"Bạn đã bị trừ 5 điểm vì đóng tiền điện nước sau ngày 5/10/2019","6/10/2019 7:01:25","Training point");
        Notification notification2 = new Notification(2,"Bạn cần thanh toán điện nước trước 5/10/2019","1/10/2019 17:31:02","Payment");
        Notification notification3 = new Notification(3,"Bạn đã bị trừ 5 điểm vì đóng tiền điện nước sau ngày 5/9/2019","6/9/2019 7:02:10","Training point");
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(notification1);
        notificationList.add(notification2);
        notificationList.add(notification3);

        lv_Noti.setAdapter(new NotificationAdapter(this, notificationList));
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
}
