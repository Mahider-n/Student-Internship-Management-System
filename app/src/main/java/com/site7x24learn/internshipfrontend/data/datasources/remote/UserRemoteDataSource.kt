package com.site7x24learn.internshipfrontend.data.datasources.remote

import com.site7x24learn.internshipfrontend.data.datasources.models.request.UpdateProfileRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.ProfileDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.UserDto
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getProfile(): ProfileDto {
        val response = apiService.getProfile()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty profile response")
        } else {
            throw Exception("Failed to load profile: ${response.code()}")
        }
    }

    suspend fun updateProfile(id: Int, request: UpdateProfileRequestDto): Response<UserDto> {
        return apiService.updateProfile(id, request)
    }

    suspend fun deleteProfile(userId: Int) {
        apiService.deleteProfile(userId)
    }
}