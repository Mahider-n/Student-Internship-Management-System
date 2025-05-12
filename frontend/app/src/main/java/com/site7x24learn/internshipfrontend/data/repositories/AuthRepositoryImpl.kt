package com.site7x24learn.internshipfrontend.data.repositories


import com.site7x24learn.internshipfrontend.data.datasources.local.PreferencesManager
import com.site7x24learn.internshipfrontend.data.datasources.models.request.LoginRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.request.SignUpRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.AuthResponseDto
import com.site7x24learn.internshipfrontend.data.datasources.remote.ApiService
import com.site7x24learn.internshipfrontend.domain.models.auth.AuthUser
import com.site7x24learn.internshipfrontend.domain.models.auth.LoginRequest
import com.site7x24learn.internshipfrontend.domain.models.auth.SignUpRequest
import com.site7x24learn.internshipfrontend.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject
import java.lang.Exception

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) : AuthRepository {

    override suspend fun login(request: LoginRequest): Result<AuthUser> {
        return try {
            val response = apiService.login(request.toDto())
            handleLoginResponse(response)
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.message}"))
        }
    }

    override suspend fun signUp(request: SignUpRequest): Result<AuthUser> {
        return try {
            val response = apiService.signUp(request.toDto())
            handleSignUpResponse(response, request)
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.message}"))
        }
    }

    private fun handleLoginResponse(response: Response<AuthResponseDto>): Result<AuthUser> {
        return if (response.isSuccessful) {
            response.body()?.let { dto ->
                if (dto.user != null && dto.token != null) {
                    // Normalize role and create AuthUser
                    val authUser = AuthUser(
                        id = dto.user.id,
                        name = dto.user.name ?: "",
                        email = dto.user.email,
                        gender = dto.user.gender ?: "",
                        birthDate = dto.user.birthDate ?: "",
                        phone = dto.user.phone ?: "",
                        address = dto.user.address ?: "",
                        role = dto.user.role?.trim()?.lowercase() ?: "student",
                        token = dto.token
                    )

                    // Save token and return user
                    preferencesManager.saveAuthToken(authUser.token)
                    println("DEBUG: Login successful - Role: ${authUser.role}")
                    Result.success(authUser)
                } else {
                    Result.failure(Exception("User data missing in response"))
                }
            } ?: Result.failure(Exception("Empty response body"))
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            Result.failure(Exception("Login failed: ${response.code()} - $errorBody"))
        }
    }

    private fun handleSignUpResponse(
        response: Response<AuthResponseDto>,
        request: SignUpRequest
    ): Result<AuthUser> {
        return if (response.isSuccessful) {
            response.body()?.let { dto ->
                val authUser = AuthUser(
                    name = request.name,
                    email = request.email,
                    gender = request.gender,
                    birthDate = request.dob,
                    phone = request.phone,
                    address = request.address,
                    role = dto.user?.role?.trim()?.lowercase() ?: "student",
                    token = dto.token ?: ""
                )
                dto.token?.let { token -> preferencesManager.saveAuthToken(token) }
                Result.success(authUser)
            } ?: Result.failure(Exception("Registration successful but no user data returned"))
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            Result.failure(Exception("Registration failed: ${response.code()} - $errorBody"))
        }
    }

    override suspend fun logout() {
        preferencesManager.clearToken()
    }

    // Extension functions for DTO conversion
    private fun LoginRequest.toDto() = LoginRequestDto(
        email = email,
        password = password
    )

    private fun SignUpRequest.toDto() = SignUpRequestDto(
        name = name,
        email = email,
        password = password,
        confirmPassword = password,
        gender = gender,
        birthDate = dob,
        phone = phone,
        address = address
    )
}