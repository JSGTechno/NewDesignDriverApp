package com.example.fleetech.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.LRModel
import com.example.fleetech.retrofit.model.OTPModel
import com.example.fleetech.retrofit.model.UpdateModel
import com.example.fleetech.retrofit.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val tripList = MutableLiveData<TripDetailResponse>()
    val uploadLRDoc = MutableLiveData<LRCOCSResponse>()

    val accountBalanceData = MutableLiveData<AccountBalanceResponse>()

    val resendOtpList = MutableLiveData<ResendOtpResponse>()

    val loading = MutableLiveData<Boolean>()
    val requestFriendList = ObservableField<OTPModel>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null

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
//    fun uploadLRIMAGE(token: String) {
//        RetrofitClient.retrofit = null
//        val userAPI = Apiuitils.testUrl
//        println("MObile No $token")
//        loading.value = true
//        val call = userAPI.uploadLRImage("Bearer " + token, LRModel("jpg","ggdg","7034988"))
//        call.enqueue(object : Callback<LRCOCSResponse> {
//            override fun onResponse(
//                call: Call<LRCOCSResponse>,
//                response: Response<LRCOCSResponse>
//            ) {
//                if (response.code() == 200) {
//                    uploadLRDoc.postValue(response.body())
//                    loading.value = false
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

}