package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.ContractClient;
import com.datnt.dormitorymanagement.Api.StudentClient;
import com.datnt.dormitorymanagement.ApiResultModel.RenewContractStateResult;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.SendModel.RenewContract;
import com.datnt.dormitorymanagement.Utility.MyLoadingDialog;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;
import com.datnt.dormitorymanagement.Utility.Utility;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenewContractActivity extends AppCompatActivity {
    private RadioGroup rg_Month;
    private int month = 4;
    private MyLoadingDialog myLoadingDialog;
    private MySharedPreference mySharedPreference;
    private String token;
    private int userId;
    private RenewContractStateResult studentState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_contract);
        //setting support action bar
        getSupportActionBar().setTitle("Gia hạn hợp đồng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        myLoadingDialog = new MyLoadingDialog(this);
        mySharedPreference = new MySharedPreference(this);
        token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        userId = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        rg_Month = findViewById(R.id.rg_renew_month);
        rg_Month.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_renew_4:
                        month = 4;
                        break;
                    case R.id.rb_renew_8:
                        month = 8;
                        break;
                    case R.id.rb_renew_12:
                        month = 12;
                        break;
                }
            }
        });

        //
        getStudentState();


    }

    private void getStudentState() {
        myLoadingDialog.showLoading();
        StudentClient studentClient = ApiUtil.studentClient(token);
        Call<RenewContractStateResult> call = studentClient.getStudentRenewState(userId);
        call.enqueue(new Callback<RenewContractStateResult>() {
            @Override
            public void onResponse(Call<RenewContractStateResult> call, Response<RenewContractStateResult> response) {
                if (response.isSuccessful()){
                    studentState = response.body();
                    myLoadingDialog.dismissLoading();
                }
                else {
                    Toast.makeText(RenewContractActivity.this, R.string.err_try_again, Toast.LENGTH_SHORT).show();
                    myLoadingDialog.dismissLoading();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RenewContractStateResult> call, Throwable t) {
                Toast.makeText(RenewContractActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                myLoadingDialog.dismissLoading();
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

    public void clickToRenewContract(View view) {
        if (studentState.getHasInValidTrainingPoint()){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Không đủ điều kiện gia hạn")
                    .setContentText("Điểm rèn luyện của bạn không đủ để gia hạn hợp đồng. Vui lòng liên hệ văn phòng ban quản lý ký túc xá.")
                    .show();
            return;
        }
        if (studentState.getHasStayedMoreThanPermittedYear()){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Không đủ điều kiện gia hạn")
                    .setContentText("Bạn không đủ điều kiện gia hạn hợp đồng vì đã học hơn 5 năm. Vui lòng liên hệ văn phòng ban quản lý ký túc xá.")
                    .show();
            return;
        }
        myLoadingDialog.showLoading();
        RenewContract renewContract = new RenewContract(userId,month);
        ContractClient contractClient = ApiUtil.contractClient(token);
        Call<ResponseBody> call = contractClient.renewContract(renewContract);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    myLoadingDialog.dismissLoading();
                    new SweetAlertDialog(RenewContractActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Yêu cầu gia hạn thành công!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    finish();
                                }
                            })
                            .show();
                }
                else {
                    Toast.makeText(RenewContractActivity.this, R.string.err_try_again, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RenewContractActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
