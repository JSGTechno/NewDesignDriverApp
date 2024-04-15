package com.example.fleetech.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.OTPModel
import com.example.fleetech.retrofit.response.PaymentDetailResponse
import com.example.fleetech.retrofit.response.ProfileResponse
import com.example.fleetech.retrofit.response.ResendOtpResponse
import com.example.fleetech.retrofit.response.TripHistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FuelViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val paymentList = MutableLiveData<PaymentDetailResponse>()

    val loading = MutableLiveData<Boolean>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null

    fun getTripHistoryDetail(token: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("Payment Token:- $token")
        loading.value = true
        val call = userAPI.paymentDetail("Bearer " + token)
        call.enqueue(object : Callback<PaymentDetailResponse> {
            override fun onResponse(
                call: Call<PaymentDetailResponse>,
                response: Response<PaymentDetailResponse>
            ) {
                if (response.code() == 200) {
                    paymentList.postValue(response.body())
                    loading.value = false
                    println("data check Payment ${paymentList}" + response.body())
                }
            }

            override fun onFailure(
                call: Call<PaymentDetailResponse>,
                t: Throwable
            ) {
                loading.value = false

            }
        })
    }


}