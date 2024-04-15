package com.example.fleetech.retrofit

import android.app.Activity
import android.app.Dialog
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ProgressBar
import androidx.databinding.library.BuildConfig
import androidx.lifecycle.MutableLiveData
import com.example.fleetech.R

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {
    private lateinit var apiInterface: Api
    init {
        apiInterface = getApiInterface()!!

    }

    private fun getApiInterface(): Api? {

        if (apiInterface == null) {
            val client = getOkHttpClient() ?: return null
            val gson = GsonBuilder().setLenient().create()
            val builder = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
            builder.baseUrl(Apiuitils.testUrl.toString())
            return builder.build().create(Api::class.java)
        } else {
            return apiInterface
        }
    }

    private fun getOkHttpClient(): OkHttpClient? {
        try {
            val okClientBuilder = OkHttpClient.Builder()

            okClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            okClientBuilder.readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            okClientBuilder.writeTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okClientBuilder.addInterceptor(httpLoggingInterceptor)
            }
            okClientBuilder.networkInterceptors().add(Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
//                if (Prefs.get().authToken != null && Prefs.get().authToken!!.isNotEmpty())
//                    requestBuilder.header("Authorization", "bearer " + Prefs.get().authToken)
                chain.proceed(requestBuilder.build())
            })

//            val logging = HttpLoggingInterceptor()
//            logging.level = HttpLoggingInterceptor.Level.BODY
//            okClientBuilder.addInterceptor(logging)
            return okClientBuilder.build()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}