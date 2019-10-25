package com.datnt.dormitorymanagement.Utility;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {
    private Context mContext;
    public final static String accessToken = "accessToken";
    public final static String userId = "userId";

    public MySharedPreference(Context mContext) {
        this.mContext = mContext;
    }

    public void putStringSharedPreference(String key, String value){
        SharedPreferences sharedpreferences = mContext.getSharedPreferences("Dormitory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void putIntSharedPreference(String key, int value){
        SharedPreferences sharedpreferences = mContext.getSharedPreferences("Dormitory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public String getStringSharedPreference(String key){
        SharedPreferences sharedpreferences = mContext.getSharedPreferences("Dormitory", Context.MODE_PRIVATE);
        String value = sharedpreferences.getString(key,null);
        return value;
    }

    public int getIntSharedPreference(String key){
        SharedPreferences sharedpreferences = mContext.getSharedPreferences("Dormitory", Context.MODE_PRIVATE);
        int value = sharedpreferences.getInt(key,0);
        return value;
    }

}
