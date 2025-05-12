package com.site7x24learn.internshipfrontend.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.site7x24learn.internshipfrontend.data.datasources.local.PreferencesManager
import com.site7x24learn.internshipfrontend.data.datasources.models.request.UpdateProfileRequestDto
import com.site7x24learn.internshipfrontend.domain.models.user.UserProfile
import com.site7x24learn.internshipfrontend.domain.usecases.*
import com.site7x24learn.internshipfrontend.domain.usecases.profile.DeleteProfileUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.profile.GetProfileUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.profile.UpdateProfileUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetProfileUseCase,
    private val updateUserProfileUseCase: UpdateProfileUseCase,
    private val deleteUserProfileUseCase: DeleteProfileUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

//    private val _updateSuccess = MutableStateFlow(false)
//    val updateSuccess: StateFlow<Boolean> = _updateSuccess.asStateFlow()

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    // Add navigation event channel
    private val _navigationEvent = Channel<Boolean>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    sealed class ProfileUiState {
        object Loading : ProfileUiState()
        data class Success(val profile: UserProfile) : ProfileUiState()
        object Deleted : ProfileUiState()
    }

    fun getProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val profile = getUserProfileUseCase()
                _uiState.value = ProfileUiState.Success(profile)
            } catch (e: Exception) {
                val errorMsg = when {
                    e.message?.contains("404") == true -> "Profile endpoint not found"
                    e.message?.contains("401") == true -> "Not authenticated"
                    else -> "Failed to load profile: ${e.message}"
                }
                _error.value = errorMsg
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(profile: UserProfile) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                // 1. First log the profile data being sent
                println("Attempting to update profile with data: $profile")

                // 2. Convert to request DTO
                val requestDto = UpdateProfileRequestDto(
                    name = profile.name,


                    birthDate = profile.birthDate ?: "", // Handle nulls
                    phone = profile.phone ?: "", // Handle nulls
                    address = profile.address ?: "", // Handle nulls

                )
                println("Request DTO: ${Gson().toJson(requestDto)}")

                // 3. Make the API call
//                val updatedProfile = updateUserProfileUseCase(profile)

                // 4. Verify the response
//                println("Update successful: $updatedProfile")


                val updatedProfile = updateUserProfileUseCase(profile)
                _uiState.value = ProfileUiState.Success(updatedProfile)
//                _uiState.value = ProfileUiState.Success(updatedProfile)

                // Send navigation event
                _navigationEvent.send(true)
//                _updateSuccess.value = true // Set success flag

            } catch (e: Exception) {
                // Enhanced error handling
                val errorMsg = when {
                    e is HttpException -> {
                        val errorBody = e.response()?.errorBody()?.string()
                        "Server error (${e.code()}): $errorBody"
                    }
                    e is SocketTimeoutException -> "Request timeout - check your connection"
                    e is IOException -> "Network error - please check internet"
                    else -> "Update failed: ${e.message}"
                }

                println("Update error: $errorMsg")
                _error.value = errorMsg
            } finally {
                _isLoading.value = false
            }
        }
    }
//    fun resetUpdateState() {
//        _updateSuccess.value = false
//    }
    fun deleteProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                when (val currentState = _uiState.value) {
                    is ProfileUiState.Success -> {
                        val userId = currentState.profile.id
                        if (userId != 0) {  // Ensure we have a valid ID
                            deleteUserProfileUseCase(userId)
                            preferencesManager.clearToken() // Clear token after deletion
                            _uiState.value = ProfileUiState.Deleted
                        } else {
                            _error.value = "Invalid user ID"
                        }
                    }

                    else -> {
                        _error.value = "Profile not loaded yet"
                    }
                }
            } catch (e: Exception) {
                _error.value = "Failed to delete profile: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }

    }
}

