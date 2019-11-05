package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.datnt.dormitorymanagement.Adapter.IssueTicketAdapter;
import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.IssueTicketClient;
import com.datnt.dormitorymanagement.ApiResultModel.IssueTicketResult;
import com.datnt.dormitorymanagement.ApiResultModel.ResultList;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.MyLoadingDialog;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;
import com.datnt.dormitorymanagement.Utility.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HandleIssueTicketActivity extends AppCompatActivity {
    private MyLoadingDialog myLoadingDialog;
    private MySharedPreference mySharedPreference;
    private String token;
    private int userId;
    private ListView lv_Ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_issue_ticket);
        //setting support action bar
        getSupportActionBar().setTitle("Yêu cầu của sinh viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        myLoadingDialog = new MyLoadingDialog(this);
        mySharedPreference = new MySharedPreference(this);
        token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        userId = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        lv_Ticket = findViewById(R.id.lv_issue_ticket);
        //get list issue ticket
        getIssueTicketList();
        lv_Ticket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ticketId = resultLists.get(position).getIssueTicketId();
                Intent intent = new Intent(HandleIssueTicketActivity.this, HandleIssueTicketDetailActivity.class);
                intent.putExtra("id", ticketId);
                startActivity(intent);

            }
        });

    }
    private List<ResultList> resultLists;
    private void getIssueTicketList() {
        myLoadingDialog.showLoading();
        IssueTicketClient issueTicketClient = ApiUtil.issueTicketClient(token);
        Call<IssueTicketResult> call = issueTicketClient.getListIssueTicket();
        call.enqueue(new Callback<IssueTicketResult>() {
            @Override
            public void onResponse(Call<IssueTicketResult> call, Response<IssueTicketResult> response) {
                if (response.isSuccessful()) {
                    resultLists = response.body().getResultList();
                    IssueTicketAdapter ticketAdapter = new IssueTicketAdapter(HandleIssueTicketActivity.this, resultLists);
                    lv_Ticket.setAdapter(ticketAdapter);
                    ticketAdapter.notifyDataSetChanged();
                    myLoadingDialog.dismissLoading();
                }
                else {

                    Toast.makeText(HandleIssueTicketActivity.this, R.string.err_try_again, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<IssueTicketResult> call, Throwable t) {
                Toast.makeText(HandleIssueTicketActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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
