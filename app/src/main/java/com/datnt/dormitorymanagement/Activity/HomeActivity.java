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
    private static int RC_UP = 101;
    private SweetAlertDialog updateProfileDialog;

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
        tv_Name = findViewById(R.id.tv_home_name);
        //gridView = findViewById(R.id.grid_home);
        //
        //createHomeData();
        //getProfile
        if (isUpdatedProfile = false) {
            showDialog("Bạn có muốn cập nhật thông tin cá nhân để sử dụng đầy đủ chức năng của phần mềm này không?");
        }
        getProfile();

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
                startActivityForResult(new Intent(HomeActivity.this, UpdateProfileActivity.class), RC_UP);
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
        String token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        int id = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        StudentClient studentClient = ApiUtil.studentClient(token);
        Call<ProfileResult> call = studentClient.getProfile(id);
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
                    myLoadingDialog.dismissLoading();
                } else {
                    myLoadingDialog.dismissLoading();
                    Intent intent = getIntent();
                    tv_Name.setText(intent.getStringExtra("mailName"));
                    isHadRoom = false;
                    //Toast.makeText(HomeActivity.this, "Không thể tải thông tin.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResult> call, Throwable t) {
                myLoadingDialog.dismissLoading();
                Toast.makeText(HomeActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
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
            startActivity(new Intent(this, BookRoomActivity.class));

    }

    public void clickToTransferRoom(View view) {
        if (isHadRoom == false) {
            Toast.makeText(this, "Bạn chưa có phòng. Hãy đăng kí phòng.", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, TransferRoomActivity.class));
        }

    }

    public void clickToRenewContract(View view) {
        if (isHadRoom == false) {
            Toast.makeText(this, "Bạn chưa có phòng. Hãy đăng kí phòng.", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, RenewContractActivity.class));
        }

    }

    public void clickToCancelContract(View view) {
        if (isHadRoom == false) {
            Toast.makeText(this, "Bạn chưa có phòng. Hãy đăng kí phòng.", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, CancelContractActivity.class));
        }

    }

    public void clickToCreateIssue(View view) {
        if (isHadRoom == false) {
            Toast.makeText(this, "Bạn chưa có phòng. Hãy đăng kí phòng.", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, CreateIssueActivity.class));
        }

    }

    public void clickToViewNews(View view) {
        startActivity(new Intent(this, NewsActivity.class));
    }

    public void clickToPayment(View view) {
        if (isHadRoom == false) {
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
        if (requestCode == RC_UP) {
            if (resultCode == Activity.RESULT_OK) {
                getProfile();
                updateProfileDialog.dismiss();
            }
        }
    }
}
