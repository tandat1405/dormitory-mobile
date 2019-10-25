package com.datnt.dormitorymanagement.Utility;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyLoadingDialog {
    private SweetAlertDialog sweetAlertDialog;

    public MyLoadingDialog(Context context) {
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#448aff"));
        sweetAlertDialog.setTitleText("Đang tải...");
    }
    public void showLoading(){
        sweetAlertDialog.show();
    }
    public void dismissLoading(){
        sweetAlertDialog.dismiss();
    }

}
