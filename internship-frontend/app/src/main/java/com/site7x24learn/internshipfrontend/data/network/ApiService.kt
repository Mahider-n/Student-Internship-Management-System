package com.site7x24learn.internshipfrontend.data.network

// ApiService.kt
import com.site7x24learn.internshipfrontend.data.model.LoginRequest
import com.site7x24learn.internshipfrontend.data.model.LoginResponse
import com.site7x24learn.internshipfrontend.data.model.SignupRequest
import com.site7x24learn.internshipfrontend.data.model.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login") // Adjust path according to your backend route
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

//    signup
    @POST("api/auth/signup")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>
}
