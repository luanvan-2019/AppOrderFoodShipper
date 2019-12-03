package com.hcmunre.apporderfoodshipper.views.activities;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtilsShipper {
    public PreferenceUtilsShipper(){

    }
    public  static  void saveEmail(String email, Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Email",email);
        editor.apply();
    }
    public static String getEmail(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        return sharedPreferences.getString("Email",null);
    }
    public static void savePassword(String password,Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Password",password);
        editor.apply();
    }
    public static String getPassword(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        return sharedPreferences.getString("Password",null);
    }
    public static void savePhone(String phone,Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Phone",phone);
        editor.apply();
    }
    public static String getPhone(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        return sharedPreferences.getString("Phone",null);
    }
    public static void saveName(String name,Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Name",name);
        editor.apply();
    }
    public static String getName(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        return sharedPreferences.getString("Name",null);
    }
    public static void saveAddress(String address,Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Address",address);
        editor.apply();
    }
    public static String getAddress(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        return sharedPreferences.getString("Address",null);
    }
    public static void saveUserId(int userId,Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("UserId",userId);
        editor.apply();
    }
    public static int getUserId(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("SaveShipper",context.MODE_PRIVATE);
        return sharedPreferences.getInt("UserId",0);
    }
}
