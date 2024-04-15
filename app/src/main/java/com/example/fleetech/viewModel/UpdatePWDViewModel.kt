package com.example.fleetech.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.UpdateModel
import com.example.fleetech.retrofit.response.UpdatePWDResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePWDViewModel: ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val updatePWDList = MutableLiveData<UpdatePWDResponse>()
    val loading = MutableLiveData<Boolean>()
    val requestFriendList = ObservableField<UpdateModel>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null


    fun getUserPWDData(mobileNo: String, pwd: String, userType: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        println("MObile No $mobileNo" )
        loading.value = true
        val call = userAPI.userUpdatePWd(UpdateModel(mobileNo,pwd, userType,null,null,null))
        call.enqueue(object : Callback<UpdatePWDResponse> {
            override fun onResponse(
                call: Call<UpdatePWDResponse>,
                response: Response<UpdatePWDResponse>
            ) {
                if (response.code() == 200) {
                    updatePWDList.postValue(response.body())
                    loading.value = false
                    println("data check update data${updatePWDList}" + response.body() )


                }
            }

            override fun onFailure(
                call: Call<UpdatePWDResponse>,
                t: Throwable) {
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