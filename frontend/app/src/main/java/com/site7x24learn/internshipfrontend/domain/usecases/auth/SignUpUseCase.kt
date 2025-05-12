package com.site7x24learn.internshipfrontend.domain.usecases.auth

import com.site7x24learn.internshipfrontend.domain.models.auth.AuthUser
import com.site7x24learn.internshipfrontend.domain.models.auth.SignUpRequest
import com.site7x24learn.internshipfrontend.domain.repositories.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        gender: String,
        dob: String,
        phone: String,
        address: String
    ): Result<AuthUser> {
        return repository.signUp(
            SignUpRequest(name, email, password, gender, dob, phone, address)
        )
    }
}