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
import com.datnt.dormitorymanagement.Api.RoomTransferClient;
import com.datnt.dormitorymanagement.Api.StudentClient;
import com.datnt.dormitorymanagement.ApiResultModel.RenewContractStateResult;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.SendModel.TransferInfo;
import com.datnt.dormitorymanagement.Utility.MyLoadingDialog;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;
import com.datnt.dormitorymanagement.Utility.Utility;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferRoomActivity extends AppCompatActivity {
    private RadioGroup rg_RoomType;
    private RadioButton rb_Standard, rb_Service;
    private EditText et_Reason;
    private int roomType = 11;
    private String token;
    private int userId;
    private MySharedPreference mySharedPreference;
    private MyLoadingDialog myLoadingDialog;
    private RenewContractStateResult studentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_room);
        //setting support action bar
        getSupportActionBar().setTitle("Đăng kí chuyển phòng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //
        myLoadingDialog = new MyLoadingDialog(this);
        mySharedPreference = new MySharedPreference(this);
        token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        userId = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        et_Reason = findViewById(R.id.et_transfer_reason);
        rg_RoomType = findViewById(R.id.rg_transfer_room_type);
        rg_RoomType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_transfer_standard:
                        roomType = 11;
                        break;
                    case R.id.rb_transfer_service:
                        roomType = 12;
                        break;

                }
            }
        });
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
                    Toast.makeText(TransferRoomActivity.this, R.string.err_try_again, Toast.LENGTH_SHORT).show();
                    myLoadingDialog.dismissLoading();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RenewContractStateResult> call, Throwable t) {
                Toast.makeText(TransferRoomActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                myLoadingDialog.dismissLoading();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    public void clickToSendTransferRequest(View view) {
        if (et_Reason.getText().toString().length()<3){
            Toast.makeText(this, "Xin vui lòng nhập đầy đủ lí do bạn muốn chuyển phòng.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!studentState.getContractIsActiveNextMonth()){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Không đủ điều kiện chuyển phòng")
                    .setContentText("Bạn không thể đăng kí chuyển phòng vì tháng sau bạn đã hết hạn hợp đồng.")
                    .show();
            return;
        }
        if(studentState.getNumberOfRoomTransferRequest()>=2){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Không đủ điều kiện chuyển phòng")
                    .setContentText("Bạn đã chuyển phòng "+studentState.getNumberOfRoomTransferRequest()+" lần. Số lần chuyển phòng tối đa là 2.")
                    .show();
            return;
        }


        RoomTransferClient roomTransferClient = ApiUtil.roomTransferClient(token);
        TransferInfo transferInfo = new TransferInfo(userId, roomType, et_Reason.getText().toString());
        Call<ResponseBody> call = roomTransferClient.requestTransfer(transferInfo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    new SweetAlertDialog(TransferRoomActivity.this, SweetAlertDialog.SUCCESS_TYPE)
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
                    Toast.makeText(TransferRoomActivity.this, R.string.err_try_again, Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TransferRoomActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}
