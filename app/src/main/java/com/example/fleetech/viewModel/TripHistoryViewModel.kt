package com.example.fleetech.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.OTPModel
import com.example.fleetech.retrofit.response.ProfileResponse
import com.example.fleetech.retrofit.response.ResendOtpResponse
import com.example.fleetech.retrofit.response.TripHistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TripHistoryViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val tripHistoryList = MutableLiveData<TripHistoryResponse>()

    val loading = MutableLiveData<Boolean>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null

    fun getTripHistoryDetail(token: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("MObile No $token")
        loading.value = true
        val call = userAPI.tripHistoryDetails("Bearer " + token)
        call.enqueue(object : Callback<TripHistoryResponse> {
            override fun onResponse(
                call: Call<TripHistoryResponse>,
                response: Response<TripHistoryResponse>
            ) {
                if (response.code() == 200) {
                    tripHistoryList.postValue(response.body())
                    loading.value = false
                    println("data check otp ${tripHistoryList}" + response.body())
                }
            }

            override fun onFailure(
                call: Call<TripHistoryResponse>,
                t: Throwable
            ) {
                loading.value = false

            }
        })
    }


}