package com.site7x24learn.internshipfrontend.data.repositories

import com.site7x24learn.internshipfrontend.data.datasources.models.request.UpdateProfileRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.ProfileDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.UserDto
import com.site7x24learn.internshipfrontend.data.datasources.remote.UserRemoteDataSource
import com.site7x24learn.internshipfrontend.domain.models.user.UserProfile
import com.site7x24learn.internshipfrontend.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getProfile(): ProfileDto {
        return remoteDataSource.getProfile()
    }

    override suspend fun updateProfile(profile: UserProfile): UserProfile {
        require(profile.email != null) { "Email cannot be null for updates" }
        val requestDto = UpdateProfileRequestDto(
            name = profile.name,

            
            birthDate = profile.birthDate ?: "",
            phone = profile.phone ?: "",
            address = profile.address ?: "",

            )

        val response = try {
            remoteDataSource.updateProfile(profile.id, requestDto)
        } catch (e: Exception) {
            throw Exception("Network error: ${e.message}")
        }

        if (!response.isSuccessful) {  // Now this will work
            val errorBody = response.errorBody()?.string() ?: "No error details"
            throw Exception("Update failed (${response.code()}): $errorBody")
        }

        return response.body()?.toUserProfile(profile.role) ?: throw Exception("Empty response")
    }

    override suspend fun deleteProfile(id: Int) {
        remoteDataSource.deleteProfile(id)
    }

    private fun UserDto.toUserProfile(role: String?): UserProfile {
        return UserProfile(
            id = this.id,
            name = this.name?: "Unknown",
            email = this.email,
            birthDate = this.birthDate,
            phone = this.phone,
            address = this.address,
            role = role
        )
    }
}
