package com.site7x24learn.internshipfrontend.data.datasources.remote


import com.site7x24learn.internshipfrontend.data.datasources.local.PreferencesManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val preferencesManager: PreferencesManager
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = preferencesManager.getAuthToken()

        return if (!token.isNullOrEmpty()) {
            val requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
            chain.proceed(requestBuilder.build())
        } else {
            chain.proceed(originalRequest)
        }
    }
}