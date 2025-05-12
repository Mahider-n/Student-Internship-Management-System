package com.site7x24learn.internshipfrontend.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.site7x24learn.internshipfrontend.domain.models.auth.AuthUser
import com.site7x24learn.internshipfrontend.domain.repositories.AuthRepository
import com.site7x24learn.internshipfrontend.domain.usecases.auth.LoginUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val user: AuthUser) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Please fill in all fields")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            loginUseCase(email, password)
                .onSuccess { user ->
                    // Normalize role and validate
                    val normalizedUser = user.copy(
                        role = user.role?.trim()?.lowercase() ?: "student" // Default role
                    )
                    _authState.value = AuthState.Success(normalizedUser)
                }
                .onFailure { e ->
                    _authState.value = AuthState.Error(
                        e.message ?: "Login failed. Please try again."
                    )
                }
        }
    }


    fun validateAndSignUp(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        gender: String,
        dob: String,
        phone: String,
        address: String
    ) {
        // Field validations
        when {
            name.isBlank() -> _authState.value = AuthState.Error("Name cannot be empty")
            email.isBlank() -> _authState.value = AuthState.Error("Email cannot be empty")
            password.isBlank() -> _authState.value = AuthState.Error("Password cannot be empty")
            password != confirmPassword -> _authState.value = AuthState.Error("Passwords don't match")
            !isPasswordValid(password) -> _authState.value = AuthState.Error(
                "Password must contain 8+ chars with uppercase, lowercase and number"
            )
            gender.lowercase() !in listOf("male", "female") -> {
                _authState.value = AuthState.Error("Gender must be 'male' or 'female'")
            }
            !isValidDate(dob) -> _authState.value = AuthState.Error("Invalid date format. Use YYYY-MM-DD")
            phone.isBlank() -> _authState.value = AuthState.Error("Phone cannot be empty")
            address.isBlank() -> _authState.value = AuthState.Error("Address cannot be empty")
            else -> signUp(name, email, password, gender, dob, phone, address)
        }
    }

    private fun signUp(
        name: String,
        email: String,
        password: String,
        gender: String,
        dob: String,
        phone: String,
        address: String
    ) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            signUpUseCase(
                name = name,
                email = email,
                password = password,
                gender = gender,
                dob = dob,
                phone = phone,
                address = address
            ).onSuccess { user ->
                _authState.value = AuthState.Success(user)
            }.onFailure { e ->
                _authState.value = AuthState.Error(
                    e.message ?: "Registration failed. Please try again."
                )
            }
        }
    }

    private fun isValidDate(dateString: String): Boolean {
        return try {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
                isLenient = false
            }.parse(dateString)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$".toRegex()
        return password.matches(passwordPattern)
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _authState.value = AuthState.Idle
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
    fun clearState() {
        _authState.value = AuthState.Idle
    }
}