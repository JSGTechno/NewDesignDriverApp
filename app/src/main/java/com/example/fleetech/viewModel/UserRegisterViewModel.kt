package com.example.fleetech.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RegistrationModel
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.response.RegistrationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRegisterViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<RegistrationResponse>()
    val loading = MutableLiveData<Boolean>()
    val requestFriendList = ObservableField<RegistrationModel>()
    private val progressbarObservable: MutableLiveData<Boolean>? = null


    fun getUserRegistrationData(mobileNo: String, deviceId: String, modelNo: String, s3: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
         println("MObile No $mobileNo" )
        loading.value = true
        val call = userAPI.userRegistration(RegistrationModel(deviceId,"fcm",mobileNo,modelNo))
         call.enqueue(object : Callback<RegistrationResponse> {
             override fun onResponse(
                 call: Call<RegistrationResponse>,
                 response: Response<RegistrationResponse>
             ) {
                 if (response.code() == 200) {
                     movieList.postValue(response.body())
                     loading.value = false
                     println("data check ${movieList}" + response.body() )


                 }
             }

             override fun onFailure(
                 call: Call<RegistrationResponse>,
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
