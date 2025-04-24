package com.site7x24learn.internshipfrontend.data.network

// RetrofitClient.kt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //    private const val BASE_URL="http://localhost:3000"
    private const val BASE_URL = "http://10.0.2.2:3000/" // Use 10.0.2.2 for Android emulator

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
