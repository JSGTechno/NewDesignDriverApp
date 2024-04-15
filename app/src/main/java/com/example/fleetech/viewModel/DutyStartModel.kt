package com.example.fleetech.viewModel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.LRModel
import com.example.fleetech.retrofit.model.OTPModel
import com.example.fleetech.retrofit.model.UPLOADPODMODEL
import com.example.fleetech.retrofit.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DutyStartModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val tripList = MutableLiveData<TripDetailResponse>()
    val uploadLRDoc = MutableLiveData<LRCOCSResponse>()
    val uploadPODDoc = MutableLiveData<PODResponse>()


    val accountBalanceData = MutableLiveData<AccountBalanceResponse>()
    val reportedfalgData = MutableLiveData<VehicleReporteddataResponse>()

    val resendOtpList = MutableLiveData<ResendOtpResponse>()
    val loading = MutableLiveData<Boolean>()
    val requestFriendList = ObservableField<OTPModel>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null
//    fun uploadLRIMAGE(token: String, lr: String, orderId: String) {
//        RetrofitClient.retrofit = null
//        val userAPI = Apiuitils.testUrl
//        println("MObile No $token")
//        println("LR --$lr")
//        loading.value = true
//        val call = userAPI.uploadLRDOC("Bearer " + token, LRModel("jpg", lr, orderId))
//        call.enqueue(object : Callback<LRCOCSResponse> {
//            override fun onResponse(
//                call: Call<LRCOCSResponse>,
//                response: Response<LRCOCSResponse>
//            ) {
//                if (response.code() == 200) {
//                    uploadLRDoc.postValue(response.body())
//                    loading.value = false
//                    println("CHECK LR ${response.body()}")
//                }
//            }
//
//            override fun onFailure(
//                call: Call<LRCOCSResponse>,
//                t: Throwable
//            ) {
//                loading.value = false
//
//            }
//        })
//
//    }

//    fun uplodPOD(token: String, lr: String, orderId: String, backPOD: String) {
//        RetrofitClient.retrofit = null
//        val userAPI = Apiuitils.testUrl
//        Log.i("POD", "POD" + backPOD)
//        println("MObile No $token")
//        println("LR --$lr")
//        loading.value = true
//        val call =
//            userAPI.uploadPODDOC("Bearer " + token, UPLOADPODMODEL("jpg", "gdhdhd", "hfhhf", "7034988"))
//        call.enqueue(object : Callback<PODResponse> {
//            override fun onResponse(
//                call: Call<PODResponse>,
//                response: Response<PODResponse>
//            ) {
//                if (response.code() == 200) {
//                    uploadPODDoc.postValue(response.body())
//                    loading.value = false
//                    println("CHECK POD ${response.body()}")
//                }
//            }
//
//            override fun onFailure(
//                call: Call<PODResponse>,
//                t: Throwable
//            ) {
//                loading.value = false
//
//            }
//        })
//
//    }

    fun getDriverTripDetails(token: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("MObile No $token")
        loading.value = true
        val call = userAPI.driverTripDetails("Bearer " + token)
        call.enqueue(object : Callback<TripDetailResponse> {
            override fun onResponse(
                call: Call<TripDetailResponse>,
                response: Response<TripDetailResponse>
            ) {
                if (response.code() == 200) {
                    tripList.postValue(response.body())
                    loading.value = false
                    println("data check otp ${tripList}" + response.body())
                }
            }

            override fun onFailure(
                call: Call<TripDetailResponse>,
                t: Throwable
            ) {
                loading.value = false

            }
        })
    }

    fun checkReportedFlag(token: String, keyOrderId: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("MObile No $token ==$keyOrderId")
        loading.value = true
        val call = userAPI.vehicleReporteddata("Bearer $token",VehicleReported(keyOrderId))
        call.enqueue(object : Callback<VehicleReporteddataResponse> {
            override fun onResponse(
                call: Call<VehicleReporteddataResponse>,
                response: Response<VehicleReporteddataResponse>
            ) {

                if (response.code() == 200) {
//                    val jsonobject = JsonObject(response)
                    reportedfalgData.postValue(response.body())
                    println("BBBBBBBBBBBBBBBBB ${response.body()}")
                    loading.value = false
                } else {
                    Log.e("ERRRRRRR", "ERRRRRR" + response.code())
                }
            }

            override fun onFailure(
                call: Call<VehicleReporteddataResponse>,
                t: Throwable
            ) {
                loading.value = false
                Log.e("ERRRRRRR", "ERRRRRR1111" + t.localizedMessage)


            }
        })
    }


}