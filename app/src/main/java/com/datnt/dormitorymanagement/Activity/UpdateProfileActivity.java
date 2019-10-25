package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.StudentClient;
import com.datnt.dormitorymanagement.ApiResultModel.ProfileResult;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.SendModel.UpdateProfile;
import com.datnt.dormitorymanagement.Utility.MyLoadingDialog;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;
import com.datnt.dormitorymanagement.Utility.StaticData;
import com.datnt.dormitorymanagement.Utility.Utility;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText et_FullName, et_PhoneNumber, et_Birthday,et_CMND,et_NamNhapHoc,et_Khoa, et_Address, et_MSSV;
    private MySharedPreference mySharedPreference;
    private MyLoadingDialog myLoadingDialog;
    private RadioGroup rg_Gender;
    private RadioButton rb_Nam, rb_Nu;
    private boolean gender = true;
    private int mYear, mMonth, mDay;
    private boolean isHadBirthday = false;
    Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        //setting support action bar
        getSupportActionBar().setTitle("Cập nhật thông tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        et_FullName = findViewById(R.id.edt_update_profile_full_name);
        et_PhoneNumber = findViewById(R.id.edt_update_profile_phone_number);
        et_Birthday = findViewById(R.id.edt_update_profile_birthday);
        et_CMND = findViewById(R.id.edt_update_profile_cmnd);
        et_NamNhapHoc = findViewById(R.id.edt_update_profile_nam_hoc);
        et_Khoa = findViewById(R.id.edt_update_profile_khoa);
        et_Address = findViewById(R.id.edt_update_profile_address);
        et_MSSV = findViewById(R.id.edt_update_profile_mssv);
        rg_Gender = findViewById(R.id.rg_gender);
        rb_Nam = findViewById(R.id.rb_male);
        rb_Nu = findViewById(R.id.rb_female);
        myLoadingDialog = new MyLoadingDialog(this);
        mySharedPreference = new MySharedPreference(this);
        //get profile
        getProfile();
        //get calendar


        et_Birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String monthString, dayString;
                        month = month +1;
                        if(month<10){
                            monthString = "0"+month;
                        }
                        else {
                            monthString = month+"";
                        }
                        if(dayOfMonth<10){
                            dayString = "0"+dayOfMonth;
                        }
                        else {
                            dayString = dayOfMonth +"";
                        }
                        et_Birthday.setText(dayString+"/"+monthString+"/"+year);
                        mDay = dayOfMonth;
                        mMonth = month -1;
                        mYear= year;

                    }
                }, mYear,mMonth,mDay);
                Calendar now = Calendar.getInstance();
                int limitDay = 31;
                int limitMonth = 11;
                int nowYear = c.get(Calendar.YEAR);
                int limitYear = nowYear - 18;
                now.set(limitYear, limitMonth, limitDay);
                Date limit = now.getTime();
                datePickerDialog.getDatePicker().setMaxDate(limit.getTime());
                datePickerDialog.show();
            }
        });
        //validate
        validate();


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
                    et_FullName.setText(profile.getName());
                    et_Address.setText(profile.getAddress());
                    if(profile.getGender() == true){
                        rb_Nam.setChecked(true);
                    }
                    else {
                        rb_Nu.setChecked(true);
                    }
                    et_PhoneNumber.setText(profile.getPhoneNumber());
                    et_Birthday.setText(profile.getBirthDay());
                    if(profile.getBirthDay().length()>0){
                        String[] birthdayArr = profile.getBirthDay().split("/");
                        mDay = Integer.parseInt(birthdayArr[0]);
                        mMonth = Integer.parseInt(birthdayArr[1])-1;
                        mYear = Integer.parseInt(birthdayArr[2]);
                        isHadBirthday = true;
                    }
                    else {
                        isHadBirthday = false;
                    }
                    et_CMND.setText(profile.getIdentityNumber());
                    et_NamNhapHoc.setText(profile.getStartedSchoolYear().toString());
                    et_Khoa.setText(profile.getTerm().toString());
                    et_MSSV.setText(profile.getStudentCardNumber());
                    myLoadingDialog.dismissLoading();
                }
                else {
                    myLoadingDialog.dismissLoading();
                    isHadBirthday = false;
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }
            }

            @Override
            public void onFailure(Call<ProfileResult> call, Throwable t) {
                myLoadingDialog.dismissLoading();
                Toast.makeText(UpdateProfileActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validate() {
        et_FullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int nameLength = et_FullName.getText().toString().trim().length();
                if(nameLength<1){
                    et_FullName.setError("Họ và tên không được để trống");
                }
                else if(nameLength<3 || nameLength>50){
                    et_FullName.setError("Họ tên phải dài hơn 3 kí tự và bé hơn 50 kí tự.");
                }
            }
        });
        et_PhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int phoneLength = et_PhoneNumber.getText().toString().trim().length();
                if(phoneLength<1){
                    et_PhoneNumber.setError("Số điện thoại không được để trống.");
                }
                else if(phoneLength<8 || phoneLength>12){
                    et_PhoneNumber.setError("Số điện thoại phải dài hơn 8 kí tự và bé hơn 12 kí tự.");
                }
            }
        });
        et_Birthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(et_Birthday.getText().toString().trim().length()<1){
                    et_Birthday.setError("Ngày sinh không được để trống");
                }
            }
        });
        et_CMND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int cmndLength = et_CMND.getText().toString().trim().length();
                if(cmndLength<1){
                    et_CMND.setError("Số CMND không được để trống.");
                }
                else if(cmndLength!=9 && cmndLength!=12){
                    et_CMND.setError("Số CMND phải dài 9 hoặc 12 kí tự.");
                }
            }
        });
        et_NamNhapHoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int length = et_NamNhapHoc.getText().toString().trim().length();
                if(length<1){
                    et_NamNhapHoc.setError("Số CMND không được để trống.");
                }
                else if(length!=4){
                    et_NamNhapHoc.setError("Năm nhập học phải dài 4 kí tự.");
                }
            }
        });
        et_Khoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int length = et_Khoa.getText().toString().trim().length();
                if(length<1){
                    et_Khoa.setError("Khóa học không được để trống.");
                }
            }
        });

        et_MSSV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int length = et_MSSV.getText().toString().trim().length();
                if(length<1){
                    et_MSSV.setError("Mã số sinh viên không được để trống.");
                }
            }
        });
        et_Address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int length = et_Address.getText().toString().trim().length();
                if(length<1){
                    et_Address.setError("Địa chỉ không được để trống.");
                }
                else if(length<3 || length>100){
                    et_Address.setError("Địa chỉ dài từ 3-100 kí tự.");
                }
            }
        });
        //et_FullName.setError("asdasd");
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
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();

    }

    public void clickToUpdateProfile(View view) {
        if(et_FullName.getError()!=null){
            Toast.makeText(this, et_FullName.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_PhoneNumber.getError()!=null){
            Toast.makeText(this, et_PhoneNumber.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_Birthday.getError()!=null){
            Toast.makeText(this, et_Birthday.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_CMND.getError()!=null){
            Toast.makeText(this, et_CMND.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_NamNhapHoc.getError()!=null){
            Toast.makeText(this, et_NamNhapHoc.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_Khoa.getError()!=null){
            Toast.makeText(this, et_Khoa.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_Address.getError()!=null){
            Toast.makeText(this, et_Address.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_MSSV.getError()!=null){
            Toast.makeText(this, et_MSSV.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        String name = et_FullName.getText().toString();
        int startedSchoolYear = Integer.parseInt(et_NamNhapHoc.getText().toString());
        String cmnd = et_CMND.getText().toString();
        String mssv = et_MSSV.getText().toString();
        int term = Integer.parseInt(et_Khoa.getText().toString());
        String address = et_Address.getText().toString();
        String phoneNumber = et_PhoneNumber.getText().toString();
        String birthday = et_Birthday.getText().toString();
        if(rb_Nam.isChecked()){
            gender = true;
        }
        if (rb_Nu.isChecked()){
            gender = false;
        }

        UpdateProfile profile = new UpdateProfile(userId, name,startedSchoolYear, cmnd,mssv, birthday, term,gender,address,phoneNumber);
        String token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        StudentClient studentClient = ApiUtil.studentClient(token);
        Call<UpdateProfile> call = studentClient.updateProfile(profile);
        call.enqueue(new Callback<UpdateProfile>() {
            @Override
            public void onResponse(Call<UpdateProfile> call, Response<UpdateProfile> response) {
                if (response.isSuccessful()) {
                    setResult(Activity.RESULT_OK);
                    Toast.makeText(UpdateProfileActivity.this, "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(UpdateProfileActivity.this, "Cập nhật thất bại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfile> call, Throwable t) {
                Toast.makeText(UpdateProfileActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
