package com.datnt.dormitorymanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.RoomBookingClient;
import com.datnt.dormitorymanagement.ApiResultModel.RoomBookingDetailResult;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.Utility.LoadImageInternet;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;
import com.datnt.dormitorymanagement.Utility.Utility;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBookingRequestActivity extends AppCompatActivity {
    private final static int RC_PICK_IDENTITY = 101, RC_PICK_STUDENT_IDENTITY = 102, RC_PICK_PRIORITY = 103;
    private Uri filePathIdentity, filePathStudentIdentity, filePathPriority;
    private ImageView iv_Identity, iv_StudentIdentity, iv_Priority, iv_IdentityAdding, iv_StudentIdentityAdding, iv_PriorityAdding;
    private StorageReference mStorageRef;
    private CheckBox cb_PriorityType1, cb_PriorityType2;
    private boolean type1 = false, type2 = false, isIdentityImageSelected = false, isStudentIdentityImageSelected = false, isPriorityImageSelected = false;
    private LinearLayout ll_Priority, ll_Edit;
    private MySharedPreference mySharedPreference;
    private RadioGroup rg_RoomType, rg_Month;
    private RadioButton rb_4, rb_8, rb_12, rb_Standard, rb_Service;
    private int roomType = 11, month = 4;
    private Button btn_Book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        //declare
        iv_Identity = findViewById(R.id.iv_booking_identity);
        iv_StudentIdentity = findViewById(R.id.iv_booking_student_identity);
        iv_Priority = findViewById(R.id.iv_booking_priority);
        iv_IdentityAdding = findViewById(R.id.iv_booking_identity_adding);
        iv_StudentIdentityAdding = findViewById(R.id.iv_booking_student_identity_adding);
        iv_PriorityAdding = findViewById(R.id.iv_booking_priority_adding);
        cb_PriorityType1 = findViewById(R.id.cb_type1);
        cb_PriorityType2 = findViewById(R.id.cb_type2);
        ll_Priority = findViewById(R.id.ll_priority);
        rg_RoomType = findViewById(R.id.rg_booking_room_type);
        rg_Month = findViewById(R.id.rg_booking_month);
        rb_4 = findViewById(R.id.rb_booking_4);
        rb_8 = findViewById(R.id.rb_booking_8);
        rb_12 = findViewById(R.id.rb_booking_12);
        rb_Standard = findViewById(R.id.rb_booking_standard_type);
        rb_Service = findViewById(R.id.rb_booking_service_type);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mySharedPreference = new MySharedPreference(this);
        btn_Book = findViewById(R.id.btn_book_room);
        ll_Edit = findViewById(R.id.ll_edit);
        btn_Book.setVisibility(View.GONE);
        ll_Edit.setVisibility(View.VISIBLE);
        int userId = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        String token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        //setting support action bar
        getSupportActionBar().setTitle("Chỉnh sửa thông tin đăng kí");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

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
}
