package com.example.fleetech.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.OTPModel
import com.example.fleetech.retrofit.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChallanViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val mychallanList = MutableLiveData<MyChallanResponse>()

    val loading = MutableLiveData<Boolean>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null

    fun getMyChallan(token: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("MObile No $token")
        loading.value = true
        val call = userAPI.mychallanList("Bearer " + token)
        call.enqueue(object : Callback<MyChallanResponse> {
            override fun onResponse(
                call: Call<MyChallanResponse>,
                response: Response<MyChallanResponse>
            ) {
                if (response.code() == 200) {
                    mychallanList.postValue(response.body())
                    loading.value = false
                    println("data challan ${mychallanList}" + response.body())
                }
            }

            override fun onFailure(
                call: Call<MyChallanResponse>,
                t: Throwable
            ) {
                loading.value = false

            }
        })
    }


}