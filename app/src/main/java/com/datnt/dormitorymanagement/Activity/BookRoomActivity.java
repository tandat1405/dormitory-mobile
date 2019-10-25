package com.datnt.dormitorymanagement.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.RoomBookingClient;
import com.datnt.dormitorymanagement.ApiResultModel.BookRoomResult;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.SendModel.RoomBooking;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;
import com.datnt.dormitorymanagement.Utility.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRoomActivity extends AppCompatActivity {
    private final static int RC_PICK_IDENTITY = 101, RC_PICK_STUDENT_IDENTITY = 102, RC_PICK_PRIORITY = 103;
    private Uri filePathIdentity, filePathStudentIdentity, filePathPriority;
    private ImageView iv_Identity, iv_StudentIdentity, iv_Priority, iv_IdentityAdding, iv_StudentIdentityAdding, iv_PriorityAdding;
    private StorageReference mStorageRef;
    private CheckBox cb_PriorityType1, cb_PriorityType2;
    private boolean type1 = false, type2 = false, isIdentityImageSelected = false, isStudentIdentityImageSelected = false, isPriorityImageSelected = false;
    private LinearLayout ll_Priority;
    private MySharedPreference mySharedPreference;
    private RadioGroup rg_RoomType, rg_Month;
    private RadioButton rb_4, rb_8, rb_12, rb_Standard, rb_Service;
    private int roomType = 11, month = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        //setting support action bar
        getSupportActionBar().setTitle("Đăng ký phòng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
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
        //room type
        rg_RoomType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_booking_standard_type:
                        roomType = 11;
                        break;
                    case R.id.rb_booking_service_type:
                        roomType = 12;
                        break;

                }
            }
        });
        rg_Month.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_booking_4:
                        month = 4;
                        break;
                    case R.id.rb_booking_8:
                        month = 8;
                        break;
                    case R.id.rb_booking_12:
                        month = 12;
                        break;
                }
            }
        });
        //
        cb_PriorityType1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    type1 = true;
                } else {
                    type1 = false;
                }
                checkPriority();
            }
        });
        cb_PriorityType2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    type2 = true;
                } else {
                    type2 = false;
                }
                checkPriority();
            }
        });
    }

    private void checkPriority() {
        if (type1 == true || type2 == true) {
            ll_Priority.setVisibility(View.VISIBLE);
        } else {
            ll_Priority.setVisibility(View.GONE);
        }
    }

    public void clickToShowPriorityHint(View view) {

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

    public void clickToChooseIdentityImage(View view) {
        chooseImage(RC_PICK_IDENTITY);
    }

    public void clickToChooseStudentIdentity(View view) {
        chooseImage(RC_PICK_STUDENT_IDENTITY);
    }

    public void clickToChoosePriorityImage(View view) {
        chooseImage(RC_PICK_PRIORITY);
    }

    private void chooseImage(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PICK_IDENTITY && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePathIdentity = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathIdentity);
                iv_Identity.setVisibility(View.VISIBLE);
                iv_IdentityAdding.setVisibility(View.GONE);
                iv_Identity.setImageBitmap(bitmap);
                isIdentityImageSelected = true;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        if (requestCode == RC_PICK_STUDENT_IDENTITY && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePathStudentIdentity = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathStudentIdentity);
                iv_StudentIdentity.setVisibility(View.VISIBLE);
                iv_StudentIdentityAdding.setVisibility(View.GONE);
                iv_StudentIdentity.setImageBitmap(bitmap);
                isStudentIdentityImageSelected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == RC_PICK_PRIORITY && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePathPriority = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathPriority);
                iv_Priority.setVisibility(View.VISIBLE);
                iv_PriorityAdding.setVisibility(View.GONE);
                iv_Priority.setImageBitmap(bitmap);
                isPriorityImageSelected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String urlIdentity = "", urlStudentIdentity = "", urlPriority = "";
    private boolean isUploadIdentity = false, isUploadStudentIdentity = false, isUploadPriority = false;

    public void clickToCreateRequestRoomBooking(View view) {
        //validate
        if (!isIdentityImageSelected) {
            Toast.makeText(this, "Xin vui lòng chọn ảnh CMND/ Thẻ căn cước.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isStudentIdentityImageSelected) {
            Toast.makeText(this, "Xin vui lòng chọn ảnh Thẻ sinh viên.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isPriorityImageSelected && (type1 || type2)) {
            Toast.makeText(this, "Xin vui lòng chọn ảnh xác nhận Đối tượng ưu tiên.", Toast.LENGTH_SHORT).show();
            return;
        }
        //upload image to firebase
        if (isIdentityImageSelected) {
            final StorageReference refIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
            refIdentity.putFile(filePathIdentity)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            refIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    urlIdentity = uri.toString();
                                    isUploadIdentity = true;
                                    bookRoom();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }
        if (isStudentIdentityImageSelected) {
            final StorageReference refStudentIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
            refStudentIdentity.putFile(filePathStudentIdentity)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            refStudentIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    urlStudentIdentity = uri.toString();
                                    isUploadStudentIdentity = true;
                                    bookRoom();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }
        if (isPriorityImageSelected) {
            final StorageReference refPriority = mStorageRef.child("images/" + UUID.randomUUID().toString());
            refPriority.putFile(filePathPriority)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            refPriority.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    urlPriority = uri.toString();
                                    isUploadPriority = true;
                                    bookRoom();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        else {
            bookRoom();
        }

    }
    public void bookRoom (){
        if (!isUploadIdentity) {
            return;
        }
        if (!isUploadStudentIdentity) {
            return;
        }
        if (!isUploadPriority && (type1 || type2)) {
            return;
        }
        int priorityType = 3;
        if(type1){
            priorityType = 0;
        }
        if(type2){
            priorityType = 1;
        }
        if (type1 && type2){
            priorityType = 2;
        }
        int userId = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        String token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        RoomBooking roomBooking = new RoomBooking(userId,month,roomType,urlIdentity,urlStudentIdentity,priorityType,urlPriority);
        RoomBookingClient roomBookingClient = ApiUtil.roomBooking(token);
        Call<BookRoomResult> call = roomBookingClient.bookRoom(roomBooking);
        call.enqueue(new Callback<BookRoomResult>() {
            @Override
            public void onResponse(Call<BookRoomResult> call, Response<BookRoomResult> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookRoomActivity.this, "Đăng kí phòng thành công.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(BookRoomActivity.this, "Đăng kí phòng thất bại. Xin vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookRoomResult> call, Throwable t) {
                Toast.makeText(BookRoomActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
