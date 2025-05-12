package com.site7x24learn.internshipfrontend.domain.repositories


import com.site7x24learn.internshipfrontend.domain.models.auth.AuthUser
import com.site7x24learn.internshipfrontend.domain.models.auth.LoginRequest
import com.site7x24learn.internshipfrontend.domain.models.auth.SignUpRequest

// domain/repositories/
interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<AuthUser>
    suspend fun signUp(request: SignUpRequest): Result<AuthUser>
    suspend fun logout()
}