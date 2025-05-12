package com.site7x24learn.internshipfrontend.domain.usecases.auth

import com.site7x24learn.internshipfrontend.domain.models.auth.AuthUser
import com.site7x24learn.internshipfrontend.domain.models.auth.LoginRequest
import com.site7x24learn.internshipfrontend.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthUser> {
        return repository.login(LoginRequest(email, password))
    }
}