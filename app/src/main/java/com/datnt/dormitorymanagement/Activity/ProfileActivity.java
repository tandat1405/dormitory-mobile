package com.datnt.dormitorymanagement.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.StudentClient;
import com.datnt.dormitorymanagement.ApiResultModel.ProfileResult;
import com.datnt.dormitorymanagement.GoogleClient.GoogleClient;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.MyLoadingDialog;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;
import com.datnt.dormitorymanagement.Utility.Utility;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private int RC_UP = 101;
    private MySharedPreference mySharedPreference;
    private MyLoadingDialog myLoadingDialog;
    private TextView tv_Name, tv_Gender, tv_PhoneNumber, tv_Address, tv_Birthday, tv_CMND, tv_StartYear, tv_Term, tv_Priority, tv_Email, tv_TrainingPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //setting support action bar
        getSupportActionBar().setTitle("Thông tin cá nhân");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        mySharedPreference = new MySharedPreference(this);
        tv_Name = findViewById(R.id.tv_profile_name);
        tv_Gender = findViewById(R.id.tv_profile_gender);
        tv_PhoneNumber = findViewById(R.id.tv_profile_phone_number);
        tv_Address = findViewById(R.id.tv_profile_address);
        tv_Birthday = findViewById(R.id.tv_profile_birthday);
        tv_CMND = findViewById(R.id.tv_profile_cmnd);
        tv_StartYear = findViewById(R.id.tv_profile_year_start);
        tv_Term = findViewById(R.id.tv_profile_term);
        tv_Priority = findViewById(R.id.tv_profile_priority);
        tv_Email = findViewById(R.id.tv_profile_email);
        tv_TrainingPoint = findViewById(R.id.tv_profile_training_point);
        myLoadingDialog = new MyLoadingDialog(this);
        //get Profile
        getProfile();

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
                    tv_Name.setText(profile.getName());
                    tv_Address.setText(profile.getAddress());
                    tv_Email.setText(profile.getEmail());
                    tv_TrainingPoint.setText("Điểm :" + profile.getEvaluationScore());
                    if(profile.getGender() == true){
                        tv_Gender.setText("Nam");
                    }
                    else {
                        tv_Gender.setText("Nữ");
                    }
                    tv_PhoneNumber.setText(profile.getPhoneNumber());
                    tv_Address.setText(profile.getAddress());
                    tv_Birthday.setText(profile.getBirthDay());
                    tv_CMND.setText(profile.getIdentityNumber());
                    tv_StartYear.setText(profile.getStartedSchoolYear().toString());
                    tv_Term.setText(profile.getTerm().toString());
                    tv_Priority.setText(profile.getPriorityType().getName());

                    myLoadingDialog.dismissLoading();
                }
                else {
                    myLoadingDialog.dismissLoading();
                    Toast.makeText(ProfileActivity.this, "Không thể tải thông tin cá nhân.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResult> call, Throwable t) {
                myLoadingDialog.dismissLoading();
                Toast.makeText(ProfileActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
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

    public void clickToSignOut(View view) {
        createConfirmLogoutDialog(ProfileActivity.this,"Đăng xuất","Bạn có muốn đăng xuất không?");
    }
    public void createConfirmLogoutDialog(Context mContext, String title, String mes){
        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(mes)
                .setCancelText("Không")
                .setConfirmText("Có")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        GoogleClient googleClient = new GoogleClient();
                        googleClient.signOut(ProfileActivity.this);
                        finish();
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();

        //final Dialog dialog = new Dialog(mContext);
        //dialog.setContentView(R.layout.dialog_confirm);
        //Window window = dialog.getWindow();
        //window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //TextView tv_title = dialog.findViewById(R.id.tv_confirm_title);
        //TextView tv_message = dialog.findViewById(R.id.tv_confirm_message);
        //Button yesButton = dialog.findViewById(R.id.btn_confirm_yes);
        //Button noButton = dialog.findViewById(R.id.btn_confirm_no);
        //tv_title.setText(title);
        //tv_message.setText(mes);
        //noButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        dialog.dismiss();
        //    }
        //});
        //yesButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //
        //    }
        //});
        //dialog.show();
    }

    public void clickToUpdateProfile(View view) {
        startActivityForResult(new Intent(this, UpdateProfileActivity.class), RC_UP);
    }

    public void clickToViewRoomDetail(View view) {
        startActivity(new Intent(this, RoomDetailActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_UP){
            if (resultCode == Activity.RESULT_OK){
                getProfile();
            }
        }
    }
}
