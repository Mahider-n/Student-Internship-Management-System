package com.site7x24learn.internshipfrontend.domain.usecases.profile


import com.site7x24learn.internshipfrontend.domain.repositories.UserRepository
import javax.inject.Inject

class DeleteProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: Int) {
        repository.deleteProfile(userId)
    }
}