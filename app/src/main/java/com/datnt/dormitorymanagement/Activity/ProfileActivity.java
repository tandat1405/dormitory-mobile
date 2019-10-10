package com.datnt.dormitorymanagement.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.datnt.dormitorymanagement.GoogleClient.GoogleClient;
import com.datnt.dormitorymanagement.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //setting support action bar
        getSupportActionBar().setTitle("Thông tin cá nhân");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //
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

    public void clickToSignOut(View view) {
        GoogleClient googleClient = new GoogleClient();
        googleClient.signOut(this);
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
