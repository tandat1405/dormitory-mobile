package com.datnt.dormitorymanagement.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.UserClient;
import com.datnt.dormitorymanagement.ApiResultModel.LoginResult;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.SendModel.SocialUser;
import com.datnt.dormitorymanagement.Utility.MyDialog;
import com.datnt.dormitorymanagement.Utility.MyLoadingDialog;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;
import com.datnt.dormitorymanagement.Utility.StaticData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private int RC_SIGN_IN = 100;
    private String token;
    private ApiUtil apiUtil = new ApiUtil();
    private MySharedPreference mySharedPreference;
    private MyLoadingDialog myLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

        //shared
        mySharedPreference = new MySharedPreference(this);
        //dialog
        myLoadingDialog = new MyLoadingDialog(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            } else {
                Toast.makeText(this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
            }
            // The Task returned from this call is always completed, no need to attach
            // a listener.

        }
    }

    private String dump;

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //if(!account.getEmail().contains("@fpt.edu.vn")){
            //    //TODO: dùng cái này để bắt đuôi email
            //    //Toast.makeText(this, "Mail này không có quyền truy cập", Toast.LENGTH_SHORT).show();
            //    //signOut();
            //}
            //else {
            myLoadingDialog.showLoading();
            dump = "";
            dump = account.getIdToken();

            final String displayName = account.getDisplayName();
            UserClient userClient = ApiUtil.userClient();
            SocialUser socialUser = new SocialUser(account.getEmail(), account.getIdToken());
            Call<LoginResult> call = userClient.login(socialUser);
            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("Active")) {
                            if (response.body().getRole().equals("Staff")) {
                                Toast.makeText(MainActivity.this, "Hello " + displayName, Toast.LENGTH_SHORT).show();

                                //store token and user id
                                mySharedPreference.putStringSharedPreference(MySharedPreference.accessToken, response.body().getAccessToken());
                                mySharedPreference.putIntSharedPreference(MySharedPreference.userId, response.body().getId());
                                startActivity(new Intent(MainActivity.this, SubmitMonthlyFeeActivity.class));
                            } else if (response.body().getRole().equals("Admin")) {
                                MyDialog myDialog = new MyDialog();
                                myDialog.createErrorDialog(MainActivity.this, "Admin không được phép sử dụng ứng dụng này, xin vui lòng sử dụng web application.");
                                signOut();
                            } else {
                                //store token and user id
                                mySharedPreference.putStringSharedPreference(MySharedPreference.accessToken, response.body().getAccessToken());
                                mySharedPreference.putIntSharedPreference(MySharedPreference.userId, response.body().getId());
                                //Toast.makeText(MainActivity.this, "Hello " + displayName, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra("mailName", displayName);
                                intent.putExtra("role", response.body().getRole());
                                startActivity(intent);
                            }
                            myLoadingDialog.dismissLoading();

                        } else {
                            myLoadingDialog.dismissLoading();
                            Toast.makeText(MainActivity.this, "Tài khoản của bạn đã bị vô hiệu hóa. Xin liên hệ ban quản lý Ký Túc Xá.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, R.string.err_try_again, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {
                    myLoadingDialog.dismissLoading();
                    Toast.makeText(MainActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                }
            });

            //}

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
