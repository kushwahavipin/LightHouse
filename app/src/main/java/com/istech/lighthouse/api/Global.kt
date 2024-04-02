package com.istech.lighthouse.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Global {
  // private const val BASE_URL = "http://103.165.78.188:8002/api/v1.0/"//link for api connection
  //   private const val BASE_URL = "http://103.165.78.188:8003/"//SHIkHA
//private const val BASE_URL = "https://api.lighthouseiot.in/api/v1.0/Account/Login"
    private const val BASE_URL = "https://api.lighthouseiot.in:443/api/v1.0/"


    fun initRetrofit(): ApiService {
        // For logging request & response (Optional)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val interceptor = Interceptor { chain ->
            val request: Request = chain.request()
            val newRequest: Request =
                request.newBuilder() //.addHeader("Authorization", Global.ACCESS_TOKEN)
                    .build()
            chain.proceed(newRequest)
        }
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.networkInterceptors().add(interceptor)
        val client: OkHttpClient = builder.addInterceptor(loggingInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}