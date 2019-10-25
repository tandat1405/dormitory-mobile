package com.datnt.dormitorymanagement.Utility;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.datnt.dormitorymanagement.Activity.SubmitMonthlyFeeActivity;
import com.datnt.dormitorymanagement.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyDialog {
    public void createErrorDialog(Context mContext, String mes){
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_error);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView message = dialog.findViewById(R.id.tv_message);
        Button okButton = dialog.findViewById(R.id.btn_OK);
        message.setText(mes);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void createConfirmLogoutDialog(Context mContext,String title, String mes){
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_confirm);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv_title = dialog.findViewById(R.id.tv_confirm_title);
        TextView tv_message = dialog.findViewById(R.id.tv_confirm_message);
        Button yesButton = dialog.findViewById(R.id.btn_confirm_yes);
        Button noButton = dialog.findViewById(R.id.btn_confirm_no);
        tv_title.setText(title);
        tv_message.setText(mes);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }
    public void showLoadingDialog(Context context){
        SweetAlertDialog pDialog = new SweetAlertDialog(context);

    }

}
