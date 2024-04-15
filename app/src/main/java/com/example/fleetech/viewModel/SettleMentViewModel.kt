package com.example.fleetech.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.OTPModel
import com.example.fleetech.retrofit.response.MySettleMentResponse
import com.example.fleetech.retrofit.response.ProfileResponse
import com.example.fleetech.retrofit.response.ResendOtpResponse
import com.example.fleetech.retrofit.response.TripHistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettleMentViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val settlementList = MutableLiveData<MySettleMentResponse>()

    val loading = MutableLiveData<Boolean>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null

    fun getMySettlementDetail(token: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("MObile No $token")
        loading.value = true
        val call = userAPI.settlementList("Bearer " + token)
        call.enqueue(object : Callback<MySettleMentResponse> {
            override fun onResponse(
                call: Call<MySettleMentResponse>,
                response: Response<MySettleMentResponse>
            ) {
                if (response.code() == 200) {
                    settlementList.postValue(response.body())
                    loading.value = false
                    println("data Settlement ${settlementList}" + response.body())
                }
            }

            override fun onFailure(
                call: Call<MySettleMentResponse>,
                t: Throwable
            ) {
                loading.value = false

            }
        })
    }


}