package com.example.fleetech.viewModel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.OTPModel
import com.example.fleetech.retrofit.response.DispatchOrderListResponse
import com.example.fleetech.retrofit.response.DriverDocuments
import com.example.fleetech.retrofit.response.ProfileResponse
import com.example.fleetech.retrofit.response.ResendOtpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DispatchOrderListViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val docList = MutableLiveData<DispatchOrderListResponse>()

    val resendOtpList = MutableLiveData<ResendOtpResponse>()

    val loading = MutableLiveData<Boolean>()
    val requestFriendList = ObservableField<OTPModel>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null

    fun getDispatchOrderList(token: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("MObile No $token")
        loading.value = true
        val call = userAPI.dispatchOrderListResponse("Bearer " + token)
        call.enqueue(object : Callback<DispatchOrderListResponse> {
            override fun onResponse(
                call: Call<DispatchOrderListResponse>,
                response: Response<DispatchOrderListResponse>
            ) {
                if (response.code() == 200) {
                    docList.postValue(response.body())
                    Log.i("TAG","doc_data" + response.body());
                    loading.value = false
                }
            }

            override fun onFailure(
                call: Call<DispatchOrderListResponse>,
                t: Throwable
            ) {
                loading.value = false

            }
        })
    }


}