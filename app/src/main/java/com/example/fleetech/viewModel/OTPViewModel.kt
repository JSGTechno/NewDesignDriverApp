package com.example.fleetech.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RegistrationModel
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.OTPModel
import com.example.fleetech.retrofit.response.OTPResponse
import com.example.fleetech.retrofit.response.RegistrationResponse
import com.example.fleetech.retrofit.response.ResendOtpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OTPViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val otpList = MutableLiveData<OTPResponse>()
    val resendOtpList = MutableLiveData<ResendOtpResponse>()

    val loading = MutableLiveData<Boolean>()
    val requestFriendList = ObservableField<OTPModel>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null


    fun getUserOTPDATA(mobileNo: String, otp: String, s2: String, s3: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("MObile No $mobileNo")
        loading.value = true
        val call = userAPI.checkOTP(OTPModel("device id", mobileNo, otp))
        call.enqueue(object : Callback<OTPResponse> {
            override fun onResponse(
                call: Call<OTPResponse>,
                response: Response<OTPResponse>
            ) {
                if (response.code() == 200) {
                    otpList.postValue(response.body())
                    loading.value = false
                    println("data check otp ${otpList}" + response.body())


                }
            }

            override fun onFailure(
                call: Call<OTPResponse>,
                t: Throwable
            ) {
                loading.value = false

            }
        })
    }


    fun getUserResendOTPDATA(mobileNo: String, otp: String, s2: String, s3: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("MObile No $mobileNo")
        loading.value = true
        val call = userAPI.resendOTP(OTPModel("device id", mobileNo, otp))
        call.enqueue(object : Callback<ResendOtpResponse> {
            override fun onResponse(
                call: Call<ResendOtpResponse>,
                response: Response<ResendOtpResponse>
            ) {
                if (response.code() == 200) {
                    resendOtpList.postValue(response.body())
                    loading.value = false
                    println("data check ${otpList}" + response.body())


                }
            }

            override fun onFailure(
                call: Call<ResendOtpResponse>,
                t: Throwable
            ) {
                loading.value = false

            }
        })
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }

}