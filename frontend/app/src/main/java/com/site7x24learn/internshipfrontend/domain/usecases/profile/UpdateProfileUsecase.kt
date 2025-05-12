package com.site7x24learn.internshipfrontend.domain.usecases.profile

import com.site7x24learn.internshipfrontend.domain.models.user.UserProfile
import com.site7x24learn.internshipfrontend.domain.repositories.UserRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(profile: UserProfile): UserProfile {
        return repository.updateProfile(profile)
    }
}