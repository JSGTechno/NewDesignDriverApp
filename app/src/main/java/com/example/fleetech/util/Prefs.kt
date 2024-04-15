package com.example.fleetech.util


import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit



/**
 * Kotlin Preference helper class save data in local app storage
 */

class Prefs {

    val PREF_LOGIN_DATA = "PREF_LOGIN_DATA"
    val PREF_LOGIN_DATA_NEW = "PREF_LOGIN_DATA_NEW"
    val PREF_FHIR_KEYS_DATA = "PREF_FHIR_KEYS_DATA"
    val PREF_FHIR_DATA_NEW = "PREF_FHIR_DATA_NEW"

    val PREF_USER_SIGNUP_DATA = "PREF_USER_SIGNUP_DATA"
    var BloodPressureStatus = "BloodPressureStatus"

    val PREF_CRASH_DATA = "PREF_CRASH_DATA"
    val PREF_TOKEN = "PREF_TOKEN"
    var DeviceAddress = "DeviceAddress"
    var DeviceToken = "Token"
    var FcmDeviceToken = "FcmDeviceToken"
    var BlutoothDeviceName = "BleDeviceName"
    var BlutoothDeviceAddress = ""
    var BlutoothDeviceVersion = "BlutoothDeviceVersion"
    var UserId = "UserId"
    var fhirId = "fhirId"
    var userName = "userName"
    var userGender = "Gender"
    var dataSynch = "dataSynch"
    var roleName = "roleName"
    var ProfileImage = "ProfileImage"
    var StepGoal = "StepGoal"
    var DeviceConnect = "DeviceConnect"
    var BloodOxygen = "BloodOxygen"
    var TemperatureHistory = "TemperatureHistory"
    var DynamicHR = "DynamicHR"
    var HRVData = "HRVData"
    var DetailSleepData = "DetailSleepData"
    var TotalActivityData = "TotalActivityData"
    var BloodPressure = "BloodPressure"

    var tempDailyDate = "tempDailyDate"

    private var sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MainApplication.get().getContext())


    init {
        instance = this
    }

//    val gson = Gson()

    companion object {
        private var instance: Prefs? = null
        fun get(): Prefs {
            if (instance == null) {
                instance = Prefs()
            }
            return instance!!
        }
    }
    var address: String?
        get() {
            val str = sharedPreferences.getString(DeviceAddress, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(DeviceAddress, value) }
        }

    var authToken: String?
        get() {
            val str = sharedPreferences.getString(DeviceToken, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(DeviceToken, value) }
        }

    var aspNetUserId: String?
        get() {
            val str = sharedPreferences.getString(UserId, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(UserId, value) }
        }
    var fhirIdPref: String?
        get() {
            val str = sharedPreferences.getString(fhirId, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(fhirId, value) }
        }


    var userNamePref: String?
        get() {
            val str = sharedPreferences.getString(userName, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(userName, value) }
        }
    var userGenderPref: String?
        get() {
            val str = sharedPreferences.getString(userGender, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(userGender, value) }
        }

    var dataSynchPref: String?
        get() {
            val str = sharedPreferences.getString(dataSynch, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(dataSynch, value) }
        }

    var userImage: String?
        get() {
            val str = sharedPreferences.getString(ProfileImage, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(ProfileImage, value) }
        }

    var BloodOxygenPref: String?
        get() {
            val str = sharedPreferences.getString(BloodOxygen, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(BloodOxygen, value) }
        }

    var stepGoalPref: String?
        get() {
            val str = sharedPreferences.getString(StepGoal, "10000") ?: "1"
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(StepGoal, value) }
        }


    var TemperatureHistoryPref: String?
        get() {
            val str = sharedPreferences.getString(TemperatureHistory, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(TemperatureHistory, value) }
        }


    var DynamicHRPref: String?
        get() {
            val str = sharedPreferences.getString(DynamicHR, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(DynamicHR, value) }
        }


    var HRVDataPref: String?
        get() {
            val str = sharedPreferences.getString(HRVData, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(HRVData, value) }
        }


    var DetailSleepDataPref: String?
        get() {
            val str = sharedPreferences.getString(DetailSleepData, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(DetailSleepData, value) }
        }


    var TotalActivityDataPref: String?
        get() {
            val str = sharedPreferences.getString(TotalActivityData, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(TotalActivityData, value) }
        }


//    var crashData: String?
//        get() {
//            val str = sharedPreferences.getString(PREF_CRASH_DATA, "") ?: ""
//            if (!str.isBlank()) return str
//            return null
//        }
//        set(value) {
//            sharedPreferences.edit { putString(PREF_CRASH_DATA, gson.toJson(value)) }
//        }

    var fcmdeviceToken: String?
        get() {
            val str = sharedPreferences.getString(FcmDeviceToken, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(FcmDeviceToken, value) }
        }

    var bleDeviceName: String?
        get() {
            val str = sharedPreferences.getString(BlutoothDeviceName, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(BlutoothDeviceName, value) }
        }

    var bleDeviceAddress: String?
        get() {
            val str = sharedPreferences.getString(BlutoothDeviceAddress, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(BlutoothDeviceAddress, value) }
        }

    var bleDeviceVersion: String?
        get() {
            val str = sharedPreferences.getString(BlutoothDeviceVersion, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(BlutoothDeviceVersion, value) }
        }
    var bloodPressureStatusCheck: String?
        get() {
            val str = sharedPreferences.getString(BloodPressureStatus, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(BloodPressureStatus, value) }
        }
    var temperatureDailyDate: String?
        get() {
            val str = sharedPreferences.getString(tempDailyDate, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(tempDailyDate, value) }
        }
    var roleNamePref: String?
        get() {
            val str = sharedPreferences.getString(roleName, "") ?: ""
            if (!str.isBlank()) return str
            return null
        }
        set(value) {
            sharedPreferences.edit { putString(roleName, value) }
        }


    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun clearData() {
        temperatureDailyDate = ""
        bleDeviceName = ""
        TotalActivityDataPref = ""
//        loginData = null
        address = ""
        authToken = ""
        aspNetUserId = ""
        fhirIdPref = ""
        DetailSleepDataPref = ""
        HRVDataPref = ""
        DynamicHRPref = ""
        TemperatureHistoryPref = ""
        BloodOxygenPref = ""
        userImage = ""
        dataSynchPref = ""
        roleNamePref = ""
        bloodPressureStatusCheck =""


    }


}