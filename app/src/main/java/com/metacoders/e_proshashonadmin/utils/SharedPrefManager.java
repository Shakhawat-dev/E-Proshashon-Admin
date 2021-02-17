package com.metacoders.e_proshashonadmin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.metacoders.e_proshashonadmin.Models.EmpModel;


public class SharedPrefManager {

    private static final String PROFILE_DATA = "profile_data";
    private static final String SHARED_PREF_NAME_LOGIN = "audio_LogInprefs";
    private static final String SHARED_PREF_NAME = "audio_audio_sharedprefs";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String IS_USER_LOGGED_IN = "useremail";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USER_ROLE = "userrole";
    private static final String KEY_USER_TOKEN = "usertoken";
    private static final String KEY_USER_TYPE = "usertype";
    private static final String KEY_USER_LANG_ID = "lang_id";
    private static final String KEY_RUNNING_MUSIC_DETAILS = "running_music";
    private static final String KEY_USER_LANG_NAME = "lang_name";
    private static final String SHARED_PREF_NAME_SETTING = "settings";
    private static final String SHARED_PREF_NAME_START_UP = "start_up_info";
    private static final String SHARED_PREF_START = "start_up_do";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }

        return mInstance;
    }

    public boolean userLogin(String profile_string) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_DATA, profile_string);

        editor.apply();

        return true;
    }

    public String getUserToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(KEY_USER_TOKEN, null);

        return token;
    }


    public EmpModel getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        EmpModel userModel = new EmpModel();

        String data = sharedPreferences.getString(PROFILE_DATA, null);
        Log.d("TAG", "Rerturned User Deatiles :  " + data);
        if (data == null) {
            userModel = null;
        } else {
            // build model from the data
            Gson gson = new Gson();
            userModel = gson.fromJson(data, EmpModel.class);
        }
        return userModel;
    }




    public void logout() {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_EMAIL, "null");
        editor.putString(IS_USER_LOGGED_IN, "no");

        editor.clear();
        editor.apply();

        SharedPreferences sharedPreferences1 = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();

        editor1.clear();
        editor1.apply();


    }

    public boolean isUserLoggedIn() {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_LOGIN, Context.MODE_PRIVATE);
        //    SharedPreferences.Editor editor = sharedPreferences.edit();

        String value = sharedPreferences.getString(IS_USER_LOGGED_IN, "no");
        if (value.equals("yes")) {
            return true;

        } else return false;

    }

    public void saveUser(String email) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(IS_USER_LOGGED_IN, "yes");
        Log.d("TAG", "SaveDataOffline:  User Persistence Saved");
        editor.apply();


    }


    public void saveStartUpInfo(String startUpInfo) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_START, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREF_NAME_START_UP, startUpInfo);
        Log.d("TAG", "SaveDataOffline:  StartUP  Persistence Saved");
        editor.apply();
    }


}
