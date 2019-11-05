package com.datnt.dormitorymanagement.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.datnt.dormitorymanagement.Adapter.GridHomeAdapter;
import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.StudentClient;
import com.datnt.dormitorymanagement.ApiResultModel.ProfileResult;
import com.datnt.dormitorymanagement.ApiResultModel.StudentStatusResult;
import com.datnt.dormitorymanagement.GoogleClient.GoogleClient;
import com.datnt.dormitorymanagement.InAppModel.Home;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.MyLoadingDialog;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private List<Home> homeList;
    private MySharedPreference mySharedPreference;
    private MyLoadingDialog myLoadingDialog;
    private TextView tv_Name;
    private boolean isHadRoom = false;
    private boolean isUpdatedProfile = false;
    private static int RC_BR = 101;
    private SweetAlertDialog updateProfileDialog;
    private String token;
    private int userId;
    private StudentClient studentClient;
    private StudentStatusResult studentStatusResult;

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        //
        Intent intent = getIntent();
        String role = intent.getStringExtra("role");
        if (role.toLowerCase().equals("unauthorized")) {
            isUpdatedProfile = false;
        }
        if (role.toLowerCase().equals("student")) {
            isUpdatedProfile = true;
        }
        //declare
        mySharedPreference = new MySharedPreference(this);
        myLoadingDialog = new MyLoadingDialog(this);
        token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        userId = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        tv_Name = findViewById(R.id.tv_home_name);
        studentClient = ApiUtil.studentClient(token);
        studentStatusResult = new StudentStatusResult();
        //getProfile
        //if (isUpdatedProfile = false) {
        //    showDialog("Bạn có muốn cập nhật thông tin cá nhân để sử dụng đầy đủ chức năng của phần mềm này không?");
        //}
        //get student profile
        getProfile();



    }

    private void getStatus() {
        Call<StudentStatusResult> call = studentClient.getStudentStatus(userId);
        call.enqueue(new Callback<StudentStatusResult>() {
            @Override
            public void onResponse(Call<StudentStatusResult> call, Response<StudentStatusResult> response) {
                if (response.isSuccessful()) {
                    studentStatusResult = response.body();
                    myLoadingDialog.dismissLoading();
                } else {
                    myLoadingDialog.dismissLoading();
                    Toast.makeText(HomeActivity.this, "Có lỗi xảy ra xin vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<StudentStatusResult> call, Throwable t) {
                myLoadingDialog.dismissLoading();
                Toast.makeText(HomeActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void showDialog(String content) {
        updateProfileDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        updateProfileDialog.setTitleText("Cập nhật thông tin");
        updateProfileDialog.setContentText(content);
        updateProfileDialog.setCancelText("Không");
        updateProfileDialog.setConfirmText("Có");
        updateProfileDialog.showCancelButton(true);
        updateProfileDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                //startActivityForResult(new Intent(HomeActivity.this, UpdateProfileActivity.class), RC_UP);
            }
        });
        updateProfileDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        updateProfileDialog.show();
    }

    private void getProfile() {
        myLoadingDialog.showLoading();

        Call<ProfileResult> call = studentClient.getProfile(userId);
        call.enqueue(new Callback<ProfileResult>() {
            @Override
            public void onResponse(Call<ProfileResult> call, Response<ProfileResult> response) {
                if (response.isSuccessful()) {
                    ProfileResult profile = response.body();
                    if (profile.getName().length() < 1) {
                        Intent intent = getIntent();
                        tv_Name.setText(intent.getStringExtra("mailName"));
                    } else {
                        tv_Name.setText(profile.getName());
                    }
                    if (profile.getRoom() == null) {
                        isHadRoom = false;
                    } else {
                        isHadRoom = true;
                    }
                    //Get Student Status
                    getStatus();
                    //myLoadingDialog.dismissLoading();
                } else {

                    Toast.makeText(HomeActivity.this, "Có lỗi xảy ra, xin vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ProfileResult> call, Throwable t) {
                myLoadingDialog.dismissLoading();
                Toast.makeText(HomeActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Đăng xuất")
                .setContentText("Bạn có muốn đăng xuất không?")
                .setCancelText("Không")
                .setConfirmText("Có")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        GoogleClient googleClient = new GoogleClient();
                        googleClient.signOut(HomeActivity.this);
                        finish();
                        HomeActivity.super.onBackPressed();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
        //super.onBackPressed();
    }

    public void clickToViewProfile(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void clickToBookRoom(View view) {
        if(studentStatusResult.getIsHaveRoom()){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Bạn không sử dụng được chức năng này vì bạn đã có phòng!")
                    .show();
            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            return;
        }
        if (studentStatusResult.getIsHaveRequestBooking()){
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Bạn đã đăng kí phòng")
                    .setContentText("Bạn có muốn xem lại lịch sử đăng kí?")
                    .setCancelText("Không")
                    .setConfirmText("Có")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                            //TODO: start History Activity
                        }
                    })
                    .show();
            //Toast.makeText(this, "Bạn đã đăng kí phòng, hãy xem lại lịch sử đăng kí.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, BookRoomActivity.class);
        intent.putExtra("state","create");
        startActivityForResult(intent, RC_BR);

    }

    public void clickToTransferRoom(View view) {
        if (studentStatusResult.getIsHaveRoom() == false) {
            Toast.makeText(this, "Bạn chưa có phòng. Hãy đăng kí phòng.", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, TransferRoomActivity.class));
        }

    }

    public void clickToRenewContract(View view) {
        if (studentStatusResult.getIsHaveRoom() == false) {
            Toast.makeText(this, "Bạn chưa có phòng. Hãy đăng kí phòng.", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, RenewContractActivity.class));
        }

    }

    public void clickToCancelContract(View view) {
        if (studentStatusResult.getIsHaveRoom() == false) {
            Toast.makeText(this, "Bạn chưa có phòng. Hãy đăng kí phòng.", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, CancelContractActivity.class));
        }

    }

    public void clickToCreateIssue(View view) {
        if (studentStatusResult.getIsHaveRoom() == false) {
            Toast.makeText(this, "Bạn chưa có phòng. Hãy đăng kí phòng.", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, CreateIssueActivity.class));
        }

    }

    public void clickToViewNews(View view) {
        //startActivity(new Intent(this, NewsActivity.class));
        Intent intent = new Intent(this, BookRoomActivity.class);
        intent.putExtra("state","edit");
        startActivity(intent);
    }

    public void clickToPayment(View view) {
        if (studentStatusResult.getIsHaveRoom() == false) {
            Toast.makeText(this, "Bạn chưa có phòng. Hãy đăng kí phòng.", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, PaymentActivity.class));
        }

    }

    public void clickToViewNotification(View view) {
        startActivity(new Intent(this, NotificationActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_BR) {
            if (resultCode == Activity.RESULT_OK) {
                getStatus();
                //updateProfileDialog.dismiss();
            }
        }
    }
}
