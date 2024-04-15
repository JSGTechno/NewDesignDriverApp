package com.example.fleetech.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.OTPModel
import com.example.fleetech.retrofit.response.ProfileResponse
import com.example.fleetech.retrofit.response.ResendOtpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val profileList = MutableLiveData<ProfileResponse>()

    val resendOtpList = MutableLiveData<ResendOtpResponse>()

    val loading = MutableLiveData<Boolean>()
    val requestFriendList = ObservableField<OTPModel>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null

    fun getDriverProfile(token: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("MObile No $token")
        loading.value = true
        val call = userAPI.driverProfile("Bearer " + token)
        call.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                if (response.code() == 200) {
                    profileList.postValue(response.body())
                    loading.value = false
                    println("data check otp ${profileList}" + response.body())
                }
            }

            override fun onFailure(
                call: Call<ProfileResponse>,
                t: Throwable
            ) {
                loading.value = false

            }
        })
    }


}