package com.purplecommerce.interpriseapp.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "LoginPrefrences";
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String USER_ID = "user_id";
    public static final String USER_FIRST_NAME = "user_first_name";
    public static final String USER_LAST_NAME = "user_last_name";
    public static final String USER_PHONE = "user_phone_number";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_ONLINE_OFFLINE = "online_offline_status";
    public static final String USER_LOGIN_STATUS = "registered_status";
    public static final String USER_DEVICE_ID = "device_id";


    public static final String FACEBOOK_ID = "facebook_id";
    public static final String FACEBOOK_MAIL = "facebook_mail";
    public static final String FACEBOOK_IMAGE = "facebook_image";
    public static final String FACEBOOK_FIRSTNAME = "facebook_firstnme";
    public static final String FACEBOOK_LASTNAME = "facebook_lastname";

    public static final String GOOGLE_ID = "google_id";
    public static final String GOOGLE_NAME = "google_name";
    public static final String GOOGLE_MAIL = "googlr_mail";
    public static final String GOOGLE_IMAGE = "google_image";

    public static final String LOGIN_TYPE = "logintype";

    public static final String LOGIN_NORAL= "normal";
    public static final String LOGIN_GOOGLE = "google";
    public static final String LOGIN_FACEBOOK = "facebook";


    public static final String URL = "user_url";
    public static final String KEY = "user_key";




    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setDeviceId(String device_id){
        editor.putString(USER_DEVICE_ID, device_id);
        editor.commit();
    }

//    public void createLoginSession(String user_id, String user_name,String user_last_name ,  String user_email, String user_phone, String password ,String online_offline , String device_id,
//                                   String facebook_id , String facebook_mail , String facebook_image , String facebook_firstname , String facebook_lastname,
//                                   String google_id , String google_name , String google_mail  , String google_image , String login_type) {
//
//
//        Log.d("SESSION MANAGER  , CREATING SESSION " , ""+user_id+" "+user_name+" "+user_last_name+" "+user_email+" "+user_phone+" "+password+" "+online_offline+" "+device_id+" "+facebook_id+" "+facebook_mail+" "+facebook_image+" "+facebook_firstname+" "+facebook_lastname+" "+google_id+" "+google_image+" "+google_mail+" "+google_name+" ");
//
//
//        editor.putBoolean(IS_LOGIN, true);
//        editor.putString(USER_ID, user_id);
//        editor.putString(USER_FIRST_NAME, user_name);
//        editor.putString(USER_LAST_NAME, user_last_name);
//        editor.putString(USER_EMAIL, user_email);
//        editor.putString(USER_PHONE, user_phone);
//        editor.putString(USER_PASSWORD, password);
//        editor.putString(USER_ONLINE_OFFLINE, online_offline);
//        editor.putString(USER_DEVICE_ID, device_id);
//
//        editor.putString(FACEBOOK_ID, facebook_id);
//        editor.putString(FACEBOOK_MAIL, facebook_mail);
//        editor.putString(FACEBOOK_IMAGE, facebook_image);
//        editor.putString(FACEBOOK_FIRSTNAME, facebook_firstname);
//        editor.putString(FACEBOOK_LASTNAME, facebook_lastname);
//
//
//        editor.putString(GOOGLE_ID, google_id);
//        editor.putString(GOOGLE_NAME, google_name);
//        editor.putString(GOOGLE_MAIL, google_mail);
//        editor.putString(GOOGLE_IMAGE, google_image);
//        editor.putString(LOGIN_TYPE, login_type);
//
//        editor.commit();
//    }

    public void CreateUrlCredentials(String url ,  String key) {


      //  Log.d("SESSION MANAGER  , CREATING SESSION " , ""+user_id+" "+user_name+" "+user_last_name+" "+user_email+" "+user_phone+" "+password+" "+online_offline+" "+device_id+" "+facebook_id+" "+facebook_mail+" "+facebook_image+" "+facebook_firstname+" "+facebook_lastname+" "+google_id+" "+google_image+" "+google_mail+" "+google_name+" ");


        editor.putBoolean(IS_LOGIN, true);

        editor.putString(URL, url);
        editor.putString(KEY, key);

        editor.commit();
    }



//    public HashMap<String, String> getUserDetails() {
//        HashMap<String, String> user = new HashMap<>();
//        user.put(USER_ID, pref.getString(USER_ID, ""));
//        user.put(USER_FIRST_NAME, pref.getString(USER_FIRST_NAME, ""));
//        user.put(USER_LAST_NAME, pref.getString(USER_LAST_NAME, ""));
//        user.put(USER_EMAIL, pref.getString(USER_EMAIL, "default@gmail.com"));
//        user.put(USER_PHONE, pref.getString(USER_PHONE, ""));
//        user.put(USER_PASSWORD, pref.getString(USER_PASSWORD, ""));
//        user.put(USER_ONLINE_OFFLINE, pref.getString(USER_ONLINE_OFFLINE, ""));
//        user.put(USER_DEVICE_ID, pref.getString(USER_DEVICE_ID, ""));
//
//
//
//        user.put(FACEBOOK_ID, pref.getString(FACEBOOK_ID, ""));
//        user.put(FACEBOOK_MAIL, pref.getString(FACEBOOK_MAIL, ""));
//        user.put(FACEBOOK_IMAGE, pref.getString(FACEBOOK_IMAGE, ""));
//        user.put(FACEBOOK_FIRSTNAME, pref.getString(FACEBOOK_FIRSTNAME, ""));
//        user.put(FACEBOOK_LASTNAME, pref.getString(FACEBOOK_LASTNAME, ""));
//
//
//
//        user.put(GOOGLE_ID, pref.getString(GOOGLE_ID, ""));
//        user.put(GOOGLE_NAME, pref.getString(GOOGLE_NAME, ""));
//        user.put(GOOGLE_MAIL, pref.getString(GOOGLE_MAIL, ""));
//        user.put(GOOGLE_IMAGE, pref.getString(GOOGLE_IMAGE, ""));
//        user.put(LOGIN_TYPE, pref.getString(LOGIN_TYPE, ""));
//
//        return user;
//    }


    public HashMap<String, String> getUrlDetails() {
        HashMap<String, String> Keyurls = new HashMap<>();

        Keyurls.put(URL, pref.getString(URL, ""));
        Keyurls.put(KEY , pref.getString(KEY , ""));

        return Keyurls ;
    }



    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }
}
