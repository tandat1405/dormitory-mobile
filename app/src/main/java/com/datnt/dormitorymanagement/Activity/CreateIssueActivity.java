package com.datnt.dormitorymanagement.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datnt.dormitorymanagement.Api.ApiUtil;
import com.datnt.dormitorymanagement.Api.EquipmentClient;
import com.datnt.dormitorymanagement.ApiResultModel.EquipmentResult;
import com.datnt.dormitorymanagement.R;
import com.datnt.dormitorymanagement.SendModel.IssueTicket;
import com.datnt.dormitorymanagement.Utility.MyLoadingDialog;
import com.datnt.dormitorymanagement.Utility.MySharedPreference;
import com.datnt.dormitorymanagement.Utility.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateIssueActivity extends AppCompatActivity {
    private LinearLayout ll_Equipment;
    private TextView tv_Equipment;
    private Spinner spn_IssueType, spn_EquipmentType;
    String[] spnArrRequestType = {"Phản ánh","Trang thiết bị"};
    String[] spnArrEquipmentType;
    private MySharedPreference mySharedPreference;
    private String token;
    private int userId, issueType, equipmentId;
    private final static int RC_ISSUE_TICKET = 101;
    private ImageView iv_AddingImage, iv_IssueImage;
    private RelativeLayout rl_Issue;
    private EditText et_Description;
    private List<EquipmentResult> listEquipment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_issue);
        //setting support action bar
        getSupportActionBar().setTitle("Tạo yêu cầu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        //declare
        ll_Equipment = findViewById(R.id.ll_equipment_type);
        tv_Equipment = findViewById(R.id.tv_equipment_type);
        spn_IssueType = findViewById(R.id.spn_issue_type);
        spn_EquipmentType = findViewById(R.id.spn_equipment_type);
        iv_AddingImage = findViewById(R.id.iv_issue_adding);
        iv_IssueImage = findViewById(R.id.iv_issue);
        et_Description = findViewById(R.id.et_issue_description);
        rl_Issue = findViewById(R.id.rl_issue);
        mySharedPreference = new MySharedPreference(this);
        ref = FirebaseStorage.getInstance().getReference();
        listEquipment = new ArrayList<>();
        myLoadingDialog = new MyLoadingDialog(this);
        //get token and userId
        token = mySharedPreference.getStringSharedPreference(MySharedPreference.accessToken);
        userId = mySharedPreference.getIntSharedPreference(MySharedPreference.userId);
        //get list equipment
        EquipmentClient equipmentClient = ApiUtil.equipmentClient(token);
        Call<List<EquipmentResult>> call = equipmentClient.getEquipment(userId);
        call.enqueue(new Callback<List<EquipmentResult>>() {
            @Override
            public void onResponse(Call<List<EquipmentResult>> call, Response<List<EquipmentResult>> response) {
                if (response.isSuccessful()) {
                    listEquipment = response.body();
                    spnArrEquipmentType =  new String[response.body().size()];
                    for(int i = 0; i<response.body().size();i++){
                        spnArrEquipmentType[i] = response.body().get(i).getCode();
                    }
                    ArrayAdapter spnEquipmentAdapter = new ArrayAdapter(CreateIssueActivity.this, android.R.layout.simple_spinner_dropdown_item,spnArrEquipmentType);
                    spn_EquipmentType.setAdapter(spnEquipmentAdapter);
                }
                else {
                    Toast.makeText(CreateIssueActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EquipmentResult>> call, Throwable t) {
                Toast.makeText(CreateIssueActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        //spinner Issu Type
        ArrayAdapter spnIssueAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,spnArrRequestType);
        spn_IssueType.setAdapter(spnIssueAdapter);
        spn_IssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    tv_Equipment.setVisibility(View.VISIBLE);
                    ll_Equipment.setVisibility(View.VISIBLE);
                    issueType = 17;
                }
                else {
                    tv_Equipment.setVisibility(View.GONE);
                    ll_Equipment.setVisibility(View.GONE);
                    issueType = 18;
                    equipmentId = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                issueType = 18;
            }
        });
        //spinner Equipment
        spn_EquipmentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                equipmentId = listEquipment.get(position).getEquipmentId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                equipmentId = -1;
            }
        });
        //set Error and validate
        et_Description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = et_Description.getText().toString().trim().length();
                if(length<1){
                    et_Description.setError("Xin vui lòng nhập nội dung chi tiết.");
                }

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
    private StorageReference ref;
    private String imageUrl;
    private MyLoadingDialog myLoadingDialog;
    public void clickToSendIssueTicket(View view) {
        if(et_Description.getError()!=null){
            Toast.makeText(this, et_Description.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        myLoadingDialog.showLoading();
        if (isHadImage){
            ref = ref.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePathIssueTicket)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrl = uri.toString();
                                    sendIssueTicket();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateIssueActivity.this, R.string.err_can_not_upload_image_to_firebase, Toast.LENGTH_SHORT).show();
                            myLoadingDialog.dismissLoading();
                        }
                    });
        }
        else {
            sendIssueTicket();
        }


    }

    private void sendIssueTicket() {
        EquipmentClient equipmentClient = ApiUtil.equipmentClient(token);
        IssueTicket issueTicket = new IssueTicket(issueType,userId, equipmentId, et_Description.getText().toString(), imageUrl);
        Call<ResponseBody> call = equipmentClient.sendIssueTicket(issueTicket);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    myLoadingDialog.dismissLoading();
                    finish();
                    startActivity(new Intent(CreateIssueActivity.this,IssueHistoryAcitivity.class));
                    Toast.makeText(CreateIssueActivity.this, "Tạo báo cáo thành công.", Toast.LENGTH_SHORT).show();
                }
                else {
                    myLoadingDialog.dismissLoading();
                    Toast.makeText(CreateIssueActivity.this, "Tạo báo cáo thất bại, xin vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                myLoadingDialog.dismissLoading();
                Toast.makeText(CreateIssueActivity.this, R.string.err_lost_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clickToChooseIssueImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RC_ISSUE_TICKET);
    }
    private Uri filePathIssueTicket;
    private boolean isHadImage = false;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_ISSUE_TICKET && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePathIssueTicket = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathIssueTicket);
                rl_Issue.setVisibility(View.VISIBLE);
                iv_AddingImage.setVisibility(View.GONE);
                iv_IssueImage.setImageBitmap(bitmap);
                isHadImage = true;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void clickToDeleteImage(View view) {
        filePathIssueTicket = null;
        isHadImage = false;
        rl_Issue.setVisibility(View.GONE);
        iv_AddingImage.setVisibility(View.VISIBLE);
    }
}
