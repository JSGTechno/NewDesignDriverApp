package com.example.fleetech.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val CACHE_SIZE = 20 * 1024 * 1024 // 10 MB
//    private val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
//        val newRequest = chain.request().newBuilder()
////            .addHeader(
////                "Authorization",
////                "Bearer " + Prefs.get().loginDataNew!!.token
////            )
//            .build()
//        chain.proceed(newRequest)
//    }.build()

    private val client: OkHttpClient =  OkHttpClient.Builder()
        .connectTimeout(200, TimeUnit.SECONDS)
        .readTimeout(200, TimeUnit.SECONDS)
        .build()
    var retrofit: Retrofit? = null
    fun getClient(baseUrl: String?): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}