package com.example.fleetech.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.fleetech.activities.MainActivity;
import com.example.fleetech.activities.ui.home.MainPODActivity;

import java.util.HashMap;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_DEVICEID = "deviceid";        //user
    public static final String KEY_MOBILENO = "mobileno";  // user
    public static final String KEY_DRIVERID = "driverid";  // user
    public static final String KEY_PASSWORD = "password";  // user
    public static final String KEY_LANGUAGE = "language";  // user language
    public static final String KEY_FCMTOKENID="fcmTokenID";
    public static final String KEY_ADMINEFLAG="admin";
    public static final String KEY_ADVANCE = "requestAdvance";
    public static final String KEY_VIDEODATE = "videoDate";
    public static final String KEY_PODTYPE = "podType";

    public static final String KEY_LRTYPE = "lrType";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createPasswordSession(String password) {
        editor.putString(KEY_PASSWORD, password);
        // Storing email in pref
        // commit changes
        editor.commit();
    }
    public void saveVideoDate(String videoDate){
            editor.putString(KEY_VIDEODATE, videoDate);
            editor.commit();
    }

    public String getVideoDate() {
        return pref.getString(KEY_VIDEODATE, null);
    }


    public HashMap<String, String> getUserPassword(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name and id
        user.put(KEY_PASSWORD,pref.getString(KEY_PASSWORD,null));
        // return user
        return user;
    }

    public void createLanguageSession(String languageToLoad){
        editor.putString(KEY_LANGUAGE, languageToLoad);
        // Storing email in pref
        // commit changes
        editor.commit();

    }

   public void createRequestAdvanceSession(String requestAdvance){
        editor.putString(KEY_ADVANCE,requestAdvance);
        editor.commit();
   }

    public void createAdminflag(String admin) {
        editor.putString(KEY_ADMINEFLAG, admin);
        // Storing admiflag in pref
        // commit changes
        editor.commit();

    }

    public HashMap<String, String> getAdminFlag(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name and id
        user.put(KEY_ADMINEFLAG,pref.getString(KEY_ADMINEFLAG,null));
        // return user
        return user;
    }


    public HashMap<String, String> getUserLanguage(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name and id
        user.put(KEY_LANGUAGE,pref.getString(KEY_LANGUAGE,null));
        // return user
        return user;
    }

    /**
     * Create login session
     * */

    public void createLoginSession(String mobileno, String deviceid, String driverid){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name and id in pref
        editor.putString(KEY_MOBILENO,mobileno);
        editor.putString(KEY_DEVICEID, deviceid);
        editor.putString(KEY_DRIVERID, driverid);
       //editor.putString(KEY_PASSWORD, driverid);

        // Storing email in pref
        // commit changes
        editor.commit();
    }



    public void createfcmtokenidSession(String fcmTokenID) {
        editor.putString(KEY_FCMTOKENID, fcmTokenID);
        // Storing email in pref
        // commit changes
        editor.commit();
    }
    public HashMap<String, String> getUserfCMTOEKNID(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name and id
        user.put(KEY_FCMTOKENID,pref.getString(KEY_FCMTOKENID,null));
        // return user
        return user;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainPODActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name and id
        user.put(KEY_MOBILENO,pref.getString(KEY_MOBILENO,null));
        user.put(KEY_DEVICEID, pref.getString(KEY_DEVICEID, null));
        user.put(KEY_DRIVERID, pref.getString(KEY_DRIVERID, null));
        user.put(KEY_FCMTOKENID, pref.getString(KEY_FCMTOKENID, null));
       // user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);

    }



}
