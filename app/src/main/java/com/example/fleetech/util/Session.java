package com.example.fleetech.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.fleetech.activities.LoginActivity;
import com.example.fleetech.activities.RegisterActivity;

import java.util.HashMap;

public class Session {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_MOBILE = "mobileNo";
    public static final String KEY_TOKEN = "token";


    // Email address (make variable public to access from outside)
    public static final String KEY_USER_TYPE = "jUserType";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TRIP_STATUS = "tripstatus";
    public static final String KEY_WALLET_BALANCE = "balance";
    public static final String KEY_DRIVER_ADDRESS = "driver_address";
    public static final String KEY_VEHICLE_NO = "vehicle_no";
    public static final String KEY_LATTITUDE = "lattitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_ORDER_ID = "orderid";
    public static final String KEY_DRIVER_NAME = "driverName";
    public static final String KEY_PODTYPE = "podType";
    public static final String KEY_IN_TIME = "CheckInDate";
    public static final String KEY_LOCATION = "Location";

    public static final String KEY_LRTYPE = "lrType";
    public static final String KEY_GALLERY_IMAGE = "GalleryImage";


    // Constructor
    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String jMobileNumber, String jPwd, String jUserType) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, jMobileNumber);

        // Storing email in pref
        editor.putString(KEY_EMAIL, jPwd);

        // Storing email in pref
        editor.putString(KEY_USER_TYPE, jUserType);
        Log.d("createLoginSession", KEY_USER_TYPE + ": " + jUserType);

        // commit changes
        editor.commit();
    }

    public String getUserType() {
        return pref.getString(KEY_USER_TYPE, "");
    }

    public void setUserType(String userType) {
        pref.edit().putString(KEY_USER_TYPE, userType).apply();
    }

    public String getMobile() {
        return pref.getString(KEY_MOBILE, "");
    }

    public void setMobile(String last_name) {
        pref.edit().putString(KEY_MOBILE, last_name).apply();
    }

    public String getKeyTripStatus() {
        return pref.getString(KEY_TRIP_STATUS, "");
    }

    public void setKeyTripStatus(String last_name) {
        pref.edit().putString(KEY_TRIP_STATUS, last_name).apply();
    }

    public String getKeyToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public void setKeyVehicleNo(String last_name) {
        pref.edit().putString(KEY_VEHICLE_NO, last_name).apply();
    }

    public String getKeyDriverName() {
        return pref.getString(KEY_DRIVER_NAME, "");
    }

    public void setKeyDriverName(String last_name) {
        pref.edit().putString(KEY_DRIVER_NAME, last_name).apply();
    }

    public String getKeyGalleryImage() {
        return pref.getString(KEY_GALLERY_IMAGE, "");
    }

    public void setKeyGalleryImage(String last_name) {
        pref.edit().putString(KEY_GALLERY_IMAGE, last_name).apply();
    }

    public String getKeyPodtype() {
        return pref.getString(KEY_PODTYPE, "");
    }

    public void setKeyPodtype(String last_name) {
        pref.edit().putString(KEY_PODTYPE, last_name).apply();
    }


    public String getKeyVehicleNo() {
        return pref.getString(KEY_VEHICLE_NO, "");
    }

    public void setKeyDriverAddress(String last_name) {
        pref.edit().putString(KEY_DRIVER_ADDRESS, last_name).apply();
    }

    public String getKeyDriverAddress() {
        return pref.getString(KEY_DRIVER_ADDRESS, "");
    }

    public void setKeyToken(String last_name) {
        pref.edit().putString(KEY_TOKEN, last_name).apply();
    }

    public String getKeyWalletBalance() {
        return pref.getString(KEY_WALLET_BALANCE, "");
    }

    public void setKeyWalletBalance(String last_name) {
        pref.edit().putString(KEY_WALLET_BALANCE, last_name).apply();
    }

    public String getKeyLattitude() {
        return pref.getString(KEY_LATTITUDE, "");
    }


    public void setKeyOrderId(String last_name) {
        pref.edit().putString(KEY_ORDER_ID, last_name).apply();
    }

    public String getKeyOrderId() {
        return pref.getString(KEY_ORDER_ID, "");
    }


    public void setLattitude(String last_name) {
        pref.edit().putString(KEY_LATTITUDE, last_name).apply();
    }

    public String getKeyLongitude() {
        return pref.getString(KEY_LONGITUDE, "");
    }

    public void setKeyLongitude(String last_name) {
        pref.edit().putString(KEY_LONGITUDE, last_name).apply();
    }

    public String getKeyInTime() {
        return pref.getString(KEY_IN_TIME, "");
    }

    public void setKeyInTime(String last_name) {
        pref.edit().putString(KEY_IN_TIME, last_name).apply();
    }

    public String getKeyLocation() {
        return pref.getString(KEY_LOCATION, "");
    }

    public void setKeyLocation(String last_name) {
        pref.edit().putString(KEY_LOCATION, last_name).apply();
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, RegisterActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        } else {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, RegisterActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        _context.startActivity(i);

    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}
