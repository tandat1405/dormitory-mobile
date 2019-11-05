package com.datnt.dormitorymanagement.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.RoomBookingClient;
import com.datnt.dormitorymanagement.Api.StudentClient;
import com.datnt.dormitorymanagement.ApiResultModel.BookRoomResult;
import com.datnt.dormitorymanagement.ApiResultModel.RoomBookingDetailResult;
import com.datnt.dormitorymanagement.ApiResultModel.RoomInfoResult;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.SendModel.EditRoomBooking;
import com.datnt.dormitorymanagement.SendModel.RoomBooking;
import com.datnt.dormitorymanagement.Utility.LoadImageInternet;
import com.datnt.dormitorymanagement.Utility.MyLoadingDialog;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;
import com.datnt.dormitorymanagement.Utility.ReduceBitmapSize;
import com.datnt.dormitorymanagement.Utility.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
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
    private LinearLayout ll_Priority, ll_Edit;
    private MySharedPreference mySharedPreference;
    private RadioGroup rg_RoomType, rg_Month;
    private RadioButton rb_4, rb_8, rb_12, rb_Standard, rb_Service;
    private int roomType = 11, month = 4;
    private Button btn_Book;
    private MyLoadingDialog myLoadingDialog;
    private boolean isCreate = false;
    private String token;
    private int userId;
    private TextView tv_DayIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        //get Intent
        Intent intent = this.getIntent();
        String state = intent.getStringExtra("state");

        if (state != null && state.equals("create")) {
            isCreate = true;
        }
        //setting support action bar
        if (isCreate) {
            getSupportActionBar().setTitle("Đăng ký phòng");
        }
        if (!isCreate) {
            getSupportActionBar().setTitle("Chỉnh sửa thông tin đăng kí");
        }
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
        btn_Book = findViewById(R.id.btn_book_room);
        ll_Edit = findViewById(R.id.ll_edit);
        myLoadingDialog = new MyLoadingDialog(this);
        userId = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        roomBookingDetail = new RoomBookingDetailResult();
        tv_DayIn = findViewById(R.id.tv_dayIn);
        //get Date and Information
        getInformation();
        //
        if (!isCreate) {
            btn_Book.setVisibility(View.GONE);
            ll_Edit.setVisibility(View.VISIBLE);
            loadBookingDetail();
        }
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
                switch (checkedId) {
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

    private Calendar c = Calendar.getInstance();
    int dateIn, monthIn, yearIn;

    private void getInformation() {
        myLoadingDialog.showLoading();
        StudentClient studentClient = ApiUtil.studentClient(token);
        Call<ResponseBody> call = studentClient.getSystemDate();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String date = "";
                    try {
                        date = response.body().string();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date systemDate = sdf.parse(date);
                        c.setTime(systemDate);
                        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                        if (dayOfWeek == Calendar.THURSDAY || dayOfWeek == Calendar.TUESDAY || dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.FRIDAY) {
                            //dateIn = c.get(Calendar.DAY_OF_MONTH) + 6;
                            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + 6);
                        }
                        if (dayOfWeek == Calendar.SATURDAY) {
                            //dateIn = c.get(Calendar.DAY_OF_MONTH) + 5;
                            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + 5);
                        }
                        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.MONDAY) {
                            //dateIn = c.get(Calendar.DAY_OF_MONTH) + 4;
                            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + 4);
                        }
                        tv_DayIn.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR));
                        StudentClient studentClient1 = ApiUtil.studentClient(token);
                        Call<List<RoomInfoResult>> listCall = studentClient1.getRoomTypeInfo();
                        listCall.enqueue(new Callback<List<RoomInfoResult>>() {
                            @Override
                            public void onResponse(Call<List<RoomInfoResult>> call, Response<List<RoomInfoResult>> response) {
                                if (response.isSuccessful()) {
                                    for (int i = 0; i<response.body().size(); i++){
                                        if (response.body().get(i).getRoomTypeId() == 11)
                                        {
                                            rb_Standard.setText("Phòng thường ("+response.body().get(i).getRoomTypeVacancy()+" chổ trống)");
                                        }
                                        if (response.body().get(i).getRoomTypeId() == 12)
                                        {
                                            rb_Service.setText("Phòng thường ("+response.body().get(i).getRoomTypeVacancy()+" chổ trống)");
                                        }
                                        myLoadingDialog.dismissLoading();
                                    }
                                }
                                else {
                                    Toast.makeText(BookRoomActivity.this, R.string.err_try_again, Toast.LENGTH_SHORT).show();
                                    myLoadingDialog.dismissLoading();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<RoomInfoResult>> call, Throwable t) {
                                Toast.makeText(BookRoomActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                                myLoadingDialog.dismissLoading();
                                finish();
                            }
                        });

                    }
                    //catch (IOException e) {
                    //    e.printStackTrace();
                    //    new SweetAlertDialog(BookRoomActivity.this, SweetAlertDialog.ERROR_TYPE)
                    //            .setTitleText("Lỗi")
                    //            .setContentText(String.valueOf(R.string.err_try_again))
                    //            .show();
                    //    finish();
                    //}
                    catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(BookRoomActivity.this, R.string.err_try_again, Toast.LENGTH_SHORT).show();
                        myLoadingDialog.dismissLoading();
                        finish();
                    }

                } else {
                    Toast.makeText(BookRoomActivity.this, R.string.err_try_again, Toast.LENGTH_SHORT).show();
                    myLoadingDialog.dismissLoading();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(BookRoomActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                myLoadingDialog.dismissLoading();
                finish();
            }
        });

    }

    private RoomBookingDetailResult roomBookingDetail;

    private void loadBookingDetail() {
        myLoadingDialog.showLoading();
        RoomBookingClient roomBookingClient = ApiUtil.roomBooking(token);
        Call<RoomBookingDetailResult> call = roomBookingClient.getRoomBookingDetail(36);
        call.enqueue(new Callback<RoomBookingDetailResult>() {
            @Override
            public void onResponse(Call<RoomBookingDetailResult> call, Response<RoomBookingDetailResult> response) {
                if (response.isSuccessful()) {
                    roomBookingDetail = response.body();
                    if (roomBookingDetail.getRoomTypeId() == 12) {
                        rb_Service.setChecked(true);
                    } else {
                        rb_Standard.setChecked(true);
                    }
                    switch (roomBookingDetail.getMonth()) {
                        case 4:
                            rb_4.setChecked(true);
                            break;
                        case 8:
                            rb_8.setChecked(true);
                            break;
                        case 12:
                            rb_12.setChecked(true);
                            break;
                    }
                    String identityUrl = roomBookingDetail.getIdentityCardImageUrl();
                    if (identityUrl.trim().length() > 0) {
                        LoadImageInternet load = new LoadImageInternet(iv_Identity);
                        load.execute(identityUrl);
                        iv_Identity.setVisibility(View.VISIBLE);
                        iv_IdentityAdding.setVisibility(View.GONE);
                    }
                    String studentIdentityUrl = roomBookingDetail.getStudentCardImageUrl();
                    if (identityUrl.trim().length() > 0) {
                        LoadImageInternet load = new LoadImageInternet(iv_StudentIdentity);
                        load.execute(studentIdentityUrl);
                        iv_StudentIdentity.setVisibility(View.VISIBLE);
                        iv_StudentIdentityAdding.setVisibility(View.GONE);
                    }
                    if (roomBookingDetail.getPriorityTypeId() != 3) {
                        LoadImageInternet load = new LoadImageInternet(iv_Priority);
                        load.execute(roomBookingDetail.getPriorityImageUrl());
                        ll_Priority.setVisibility(View.VISIBLE);
                        iv_PriorityAdding.setVisibility(View.GONE);
                        iv_Priority.setVisibility(View.VISIBLE);
                    }
                    switch (roomBookingDetail.getPriorityTypeId()) {
                        case 0:
                            cb_PriorityType1.setChecked(true);
                            break;
                        case 1:
                            cb_PriorityType2.setChecked(true);
                            break;
                        case 2:
                            cb_PriorityType1.setChecked(true);
                            cb_PriorityType2.setChecked(true);
                            break;
                    }
                    myLoadingDialog.dismissLoading();
                } else {
                    myLoadingDialog.dismissLoading();
                    Toast.makeText(BookRoomActivity.this, R.string.err_try_again, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RoomBookingDetailResult> call, Throwable t) {
                myLoadingDialog.dismissLoading();
                Toast.makeText(BookRoomActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                finish();
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
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_priority_hint);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button okButton = dialog.findViewById(R.id.btn_priority_OK);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
        //Intent intent = new Intent();
        ////intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //intent.setType("image/*");
        //
        //startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, requestCode);
    }

    private List<Uri> imageUriList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PICK_IDENTITY && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePathIdentity = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathIdentity);
                ReduceBitmapSize reduceBitmapSize = new ReduceBitmapSize();
                bitmap = reduceBitmapSize.getResizedBitmap(bitmap, 500);
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
                ReduceBitmapSize reduceBitmapSize = new ReduceBitmapSize();
                bitmap = reduceBitmapSize.getResizedBitmap(bitmap, 500);
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
                ReduceBitmapSize reduceBitmapSize = new ReduceBitmapSize();
                bitmap = reduceBitmapSize.getResizedBitmap(bitmap, 500);
                iv_Priority.setVisibility(View.VISIBLE);
                iv_PriorityAdding.setVisibility(View.GONE);
                iv_Priority.setImageBitmap(bitmap);
                isPriorityImageSelected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String uploadedIdentityUri = "", uploadedStudentUri = "", uploadedPriorityUri = "";
    private String urlIdentity = "", urlStudentIdentity = "", urlPriority = "";
    private boolean isUploadIdentity = false, isUploadStudentIdentity = false, isUploadPriority = false;
    private List<String> imageUrlList;
    private int uploadCount = 0;
    private int i;
    private String[] folderArr = {"identity", "studentCard", "priority"};

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
        myLoadingDialog.showLoading();
        imageUriList = new ArrayList<>();
        imageUriList.add(filePathIdentity);
        imageUriList.add(filePathStudentIdentity);
        if (filePathPriority != null) {
            imageUriList.add(filePathPriority);
        }
        imageUrlList = new ArrayList<>();
        uploadCount = 0;
        for (i = 0; i < imageUriList.size(); i++) {
            final StorageReference ref = mStorageRef.child(folderArr[i] + "/" + UUID.randomUUID().toString());
            ref.putFile(imageUriList.get(i))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrlList.add(uri.toString());
                                    uploadCount++;
                                    if (uploadCount == imageUriList.size()) {
                                        bookRoom();
                                    }
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
                                            myLoadingDialog.dismissLoading();
                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
                            myLoadingDialog.dismissLoading();
                        }
                    });
        }

        //TODO:
        //refIdentity.putFile(filePathIdentity)
        //        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        //            @Override
        //            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        //                refIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        //                    @Override
        //                    public void onSuccess(Uri uri) {
        //                        urlIdentity = uri.toString();
        //                        final StorageReference refStudentIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
        //                        refStudentIdentity.putFile(filePathStudentIdentity)
        //                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        //                                    @Override
        //                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        //                                        refStudentIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        //                                            @Override
        //                                            public void onSuccess(Uri uri) {
        //                                                urlStudentIdentity = uri.toString();
        //                                                if(isPriorityImageSelected){
        //                                                    final StorageReference refPriority = mStorageRef.child("images/" + UUID.randomUUID().toString());
        //                                                    refPriority.putFile(filePathPriority)
        //                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        //                                                                @Override
        //                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        //                                                                    refPriority.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        //                                                                        @Override
        //                                                                        public void onSuccess(Uri uri) {
        //                                                                            urlPriority = uri.toString();
        //                                                                            //bookRoom();
        //                                                                        }
        //                                                                    });
        //                                                                }
        //                                                            })
        //                                                            .addOnFailureListener(new OnFailureListener() {
        //                                                                @Override
        //                                                                public void onFailure(@NonNull Exception e) {
        //                                                                    myLoadingDialog.dismissLoading();
        //                                                                    Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
        //                                                                }
        //                                                            });
        //                                                }
        //                                                else {
        //                                                    //bookRoom();
        //                                                }
        //                                            }
        //                                        });
        //                                    }
        //                                })
        //                                .addOnFailureListener(new OnFailureListener() {
        //                                    @Override
        //                                    public void onFailure(@NonNull Exception e) {
        //                                        myLoadingDialog.dismissLoading();
        //                                        Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
        //                                    }
        //                                });
        //                    }
        //                });
        //            }
        //        })
        //        .addOnFailureListener(new OnFailureListener() {
        //            @Override
        //            public void onFailure(@NonNull Exception e) {
        //                myLoadingDialog.dismissLoading();
        //                Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
        //            }
        //        });
//TODO
        //
        //myLoadingDialog.showLoading();
        ////upload image to firebase
        //if (isIdentityImageSelected) {
        //    if(!isUploadIdentity){
        //        final StorageReference refIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
        //        refIdentity.putFile(filePathIdentity)
        //                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        //                    @Override
        //                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        //                        refIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        //                            @Override
        //                            public void onSuccess(Uri uri) {
        //                                urlIdentity = uri.toString();
        //                                isUploadIdentity = true;
        //                                bookRoom();
        //                            }
        //                        });
//
        //                    }
        //                })
        //                .addOnFailureListener(new OnFailureListener() {
        //                    @Override
        //                    public void onFailure(@NonNull Exception e) {
        //                        myLoadingDialog.dismissLoading();
        //                        Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
        //                    }
        //                });
        //    }
//
//
        //}
        //if (isStudentIdentityImageSelected) {
        //    if(!isUploadStudentIdentity){
        //        final StorageReference refStudentIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
        //        refStudentIdentity.putFile(filePathStudentIdentity)
        //                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        //                    @Override
        //                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        //                        refStudentIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        //                            @Override
        //                            public void onSuccess(Uri uri) {
        //                                urlStudentIdentity = uri.toString();
        //                                isUploadStudentIdentity = true;
        //                                bookRoom();
        //                            }
        //                        });
//
        //                    }
        //                })
        //                .addOnFailureListener(new OnFailureListener() {
        //                    @Override
        //                    public void onFailure(@NonNull Exception e) {
        //                        myLoadingDialog.dismissLoading();
        //                        Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
        //                    }
        //                });
        //    }
        //}
        //if (isPriorityImageSelected) {
        //    if(!isUploadPriority){
        //        final StorageReference refPriority = mStorageRef.child("images/" + UUID.randomUUID().toString());
        //        refPriority.putFile(filePathPriority)
        //                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        //                    @Override
        //                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        //                        refPriority.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        //                            @Override
        //                            public void onSuccess(Uri uri) {
        //                                urlPriority = uri.toString();
        //                                isUploadPriority = true;
        //                                bookRoom();
        //                            }
        //                        });
//
        //                    }
        //                })
        //                .addOnFailureListener(new OnFailureListener() {
        //                    @Override
        //                    public void onFailure(@NonNull Exception e) {
        //                        myLoadingDialog.dismissLoading();
        //                        Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
        //                    }
        //                });
        //    }
//
        //} else {
        //    bookRoom();
        //}

    }

    public void bookRoom() {
        int priorityType = 3;
        if (type1) {
            priorityType = 0;
        }
        if (type2) {
            priorityType = 1;
        }
        if (type1 && type2) {
            priorityType = 2;
        }
        for (int i = 0; i < imageUrlList.size(); i++) {
            if (imageUrlList.get(i).contains(folderArr[0])) {
                urlIdentity = imageUrlList.get(i);
            }
            if (imageUrlList.get(i).contains(folderArr[1])) {
                urlStudentIdentity = imageUrlList.get(i);
            }
            if (imageUrlList.get(i).contains(folderArr[2])) {
                urlPriority = imageUrlList.get(i);
            }
        }
        //urlIdentity = imageUrlList.get(0);
        //urlStudentIdentity = imageUrlList.get(1);
        //if (imageUrlList.size()>2){
        //    urlPriority = imageUrlList.get(2);
        //}
        RoomBooking roomBooking = new RoomBooking(userId, month, roomType, urlIdentity, urlStudentIdentity, priorityType, urlPriority);
        RoomBookingClient roomBookingClient = ApiUtil.roomBooking(token);
        Call<BookRoomResult> call = roomBookingClient.bookRoom(roomBooking);
        call.enqueue(new Callback<BookRoomResult>() {
            @Override
            public void onResponse(Call<BookRoomResult> call, Response<BookRoomResult> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(BookRoomActivity.this, "Đăng kí phòng thành công.", Toast.LENGTH_SHORT).show();
                    myLoadingDialog.dismissLoading();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {

                    myLoadingDialog.dismissLoading();
                    Toast.makeText(BookRoomActivity.this, "Đăng kí phòng thất bại. Xin vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    //finish();
                }
            }

            @Override
            public void onFailure(Call<BookRoomResult> call, Throwable t) {
                myLoadingDialog.dismissLoading();
                Toast.makeText(BookRoomActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

    }

    public void clickToDeleteBookRoom(View view) {

    }

    private String editedIdentityImageUrl = null, editedStudentImageUrl = null, editedPriorityImageUrl = null;

    public void clickToEditBookRoom(View view) {
        if (!isPriorityImageSelected && (type2 || type1)) {
            Toast.makeText(this, "Vui lòng tải ảnh xác minh đối tượng ưu tiên.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isIdentityImageSelected) {
            final StorageReference refIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
            refIdentity.putFile(filePathIdentity)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            refIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    editedIdentityImageUrl = uri.toString();
                                    if (isStudentIdentityImageSelected) {
                                        final StorageReference refIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
                                        refIdentity.putFile(filePathStudentIdentity)
                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        refIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {
                                                                editedStudentImageUrl = uri.toString();
                                                                if (isPriorityImageSelected && (type1 || type2)) {
                                                                    final StorageReference refStudentIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
                                                                    refStudentIdentity.putFile(filePathStudentIdentity)
                                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                    refStudentIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                        @Override
                                                                                        public void onSuccess(Uri uri) {
                                                                                            editedPriorityImageUrl = uri.toString();
                                                                                            editRoomBooking();
                                                                                        }
                                                                                    });

                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    myLoadingDialog.dismissLoading();
                                                                                    Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                } else {
                                                                    editRoomBooking();
                                                                }
                                                            }
                                                        });

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        myLoadingDialog.dismissLoading();
                                                        Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        if (isPriorityImageSelected && (type1 || type2)) {
                                            final StorageReference refStudentIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
                                            refStudentIdentity.putFile(filePathPriority)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            refStudentIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    editedPriorityImageUrl = uri.toString();
                                                                    editRoomBooking();
                                                                }
                                                            });

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            myLoadingDialog.dismissLoading();
                                                            Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {
                                            editRoomBooking();
                                        }

                                    }
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            myLoadingDialog.dismissLoading();
                            Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            if (isStudentIdentityImageSelected) {
                final StorageReference refIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
                refIdentity.putFile(filePathStudentIdentity)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                refIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        editedStudentImageUrl = uri.toString();
                                        if (isPriorityImageSelected && (type1 || type2)) {
                                            final StorageReference refStudentIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
                                            refStudentIdentity.putFile(filePathPriority)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            refStudentIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    editedPriorityImageUrl = uri.toString();
                                                                    editRoomBooking();
                                                                }
                                                            });

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            myLoadingDialog.dismissLoading();
                                                            Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {
                                            editRoomBooking();
                                        }
                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                myLoadingDialog.dismissLoading();
                                Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                if (isPriorityImageSelected && (type1 || type2)) {
                    final StorageReference refStudentIdentity = mStorageRef.child("images/" + UUID.randomUUID().toString());
                    refStudentIdentity.putFile(filePathPriority)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    refStudentIdentity.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            editedPriorityImageUrl = uri.toString();
                                            editRoomBooking();
                                        }
                                    });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    myLoadingDialog.dismissLoading();
                                    Toast.makeText(BookRoomActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    editRoomBooking();
                }
            }
        }

    }

    private void editRoomBooking() {
        int priorityType = 3;
        if (type1) {
            priorityType = 0;
        }
        if (type2) {
            priorityType = 1;
        }
        if (type1 && type2) {
            priorityType = 2;
        }
        int bookingId = roomBookingDetail.getRoomBookingId();
        EditRoomBooking editRoomBooking = new EditRoomBooking(bookingId, userId, month, roomType, editedIdentityImageUrl, editedStudentImageUrl, priorityType, editedPriorityImageUrl);
        RoomBookingClient roomBookingClient = ApiUtil.roomBooking(token);
        Call<ResponseBody> call = roomBookingClient.edtiRoomBooking(editRoomBooking);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookRoomActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookRoomActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(BookRoomActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clickToShowRoomTypeHint(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_room_type_hint);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button okButton = dialog.findViewById(R.id.btn_room_type_OK);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void clickToShowDayInHint(View view) {
        //TooltipCompat.setTooltipText(view,"Hi");
    }
}
