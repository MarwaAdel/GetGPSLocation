package com.example.getgpslocation.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Eman on 11/28/2016.
 */

public class SessionManager {


    public static final String LOGIN_PREF = "user_login_pref";

    public static final String USER_NAME_PREF = "user_name_pref";
    public static final String USER_PASSWORD_PREF = "user_password_pref";
    public static final String USER_TOKEN_PREF = "user_token_pref";
    public static final String USER_TOKEN_EXPIRE_PREF = "user_token_expire_pref";
    public static final String USER_ROLE_PREF = "user_role_pref";
    public static final String USER_ID_PREF = "user_id_pref";



    public void setPreferences(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }


    public  String getPreferences(Context context, String key) {

        Log.e("prefs" , "get prefc");

        SharedPreferences prefs = context.getSharedPreferences(LOGIN_PREF,Context.MODE_PRIVATE);

        Log.e("init" , "ok");

        String position = prefs.getString(key, "");

        Log.e("posotion" , position);
        return position;
    }


    public void setUserNamePref(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_NAME_PREF, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public  String UserNamePref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(USER_NAME_PREF,Context.MODE_PRIVATE);
        String userName = prefs.getString(key, "");
        Log.e("userName" , userName);
        return userName;
    }

    public void setUserIdPref(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_ID_PREF, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public  int UserIdPref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(USER_ID_PREF,Context.MODE_PRIVATE);
        int userId = prefs.getInt(key, 0);
        Log.e("userName" , userId +" ");
        return userId;
    }


    public void setUserTokenPref(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_TOKEN_PREF, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public  String UserTokenPref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(USER_TOKEN_PREF,Context.MODE_PRIVATE);
        String userFirstName = prefs.getString(key, "");
        Log.e("userFirstName" , userFirstName);
        return userFirstName;
    }

    public void setUserTokenExpirePref(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_TOKEN_EXPIRE_PREF, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public  String UserTokenExpirePref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(USER_TOKEN_EXPIRE_PREF,Context.MODE_PRIVATE);
        String userLastName = prefs.getString(key, "");
        Log.e("userLastName" , userLastName);
        return userLastName;
    }

    public void setUserRolePref(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_ROLE_PREF, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public  String UserRolePref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(USER_ROLE_PREF,Context.MODE_PRIVATE);
        String userPhoneName = prefs.getString(key, "");
        Log.e("userPhoneName" , userPhoneName);
        return userPhoneName;
    }



    public void setUserPasswordPref(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PASSWORD_PREF, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public  String UserPasswordPref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(USER_PASSWORD_PREF,Context.MODE_PRIVATE);
        String userPassword = prefs.getString(key, "");
        Log.e("userPassword" , userPassword);
        return userPassword;
    }



    public boolean isLogin(Context contxt){
        SessionManager manager =new SessionManager();
        String status = manager.getPreferences(contxt, "status");
        Log.d("status", status);
        if (status.equals("1")) {
            return true;
        } else {
            return false;
        }
    }


}
