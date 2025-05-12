package com.site7x24learn.internshipfrontend.domain.repositories


import com.site7x24learn.internshipfrontend.data.datasources.models.response.ProfileDto
import com.site7x24learn.internshipfrontend.domain.models.user.UserProfile

interface UserRepository {
    suspend fun getProfile(): ProfileDto
    suspend fun updateProfile(profile: UserProfile): UserProfile
    suspend fun deleteProfile(userId: Int)
}
