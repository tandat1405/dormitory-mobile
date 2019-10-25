package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.datnt.dormitorymanagement.Adapter.PaymentHistoryAdapter;
import com.datnt.dormitorymanagement.InAppModel.Payment;
import com.datnt.dormitorymanagement.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryActivity extends AppCompatActivity {
    private ListView lv_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        //setting support action bar
        getSupportActionBar().setTitle("Lịch sử thanh toán");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        lv_payment = findViewById(R.id.lv_payment_history);
        //fake data
        Payment payment1 = new Payment(1,"1/9/2019","Tiền phòng","350.000 VND");
        Payment payment2 = new Payment(2,"1/9/2019","Điện nước","72.000 VND");
        Payment payment3 = new Payment(3,"1/10/2019","Tiền phòng","350.000 VND");
        Payment payment4 = new Payment(4,"1/10/2019","Tiền phòng","68.000 VND");
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment1);
        paymentList.add(payment2);
        paymentList.add(payment3);
        paymentList.add(payment4);

        lv_payment.setAdapter(new PaymentHistoryAdapter(this,paymentList));
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
