package com.site7x24learn.internshipfrontend.presentation.viewmodels



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.site7x24learn.internshipfrontend.domain.models.application.Application

import com.site7x24learn.internshipfrontend.domain.usecases.application.DeleteApplicationUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.application.GetUserApplicationsUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.application.UpdateApplicationUseCase
import com.site7x24learn.internshipfrontend.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class StudentApplicationsViewModel @Inject constructor(
    private val getUserApplicationsUseCase: GetUserApplicationsUseCase,
    private val updateApplicationUseCase: UpdateApplicationUseCase,
    private val deleteApplicationUseCase: DeleteApplicationUseCase
) : ViewModel() {

    var state by mutableStateOf(StudentApplicationsState())
        private set
    // Add these properties to your ViewModel
    private val _isRefreshing = MutableStateFlow(false)


//    init {
//        loadApplications()
//    }

    fun loadApplications() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = getUserApplicationsUseCase()) {
                is Resource.Success -> state = state.copy(
                    applications = result.data ?: emptyList(),
                    isLoading = false,
                    error = null
                )
                is Resource.Error -> state = state.copy(
                    error = result.message,
                    isLoading = false
                )
                Resource.Loading -> state = state.copy(isLoading = true)
            }
        }
    }
    fun refreshApplications() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadApplications() // Reuse your existing load function
        }
    }



    fun updateApplication(
        applicationId: Int,
        university: String,
        degree: String,
        graduationYear: Int, // Now strictly Int
        linkdIn: String? = null, // Only nullable field
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            try {
                // 1. Validate required fields (non-blank)
                require(university.isNotBlank()) { "University cannot be empty" }
                require(degree.isNotBlank()) { "Degree cannot be empty" }



                // 2. Create strictly typed updates map
                val updates = buildMap<String, Any> {
                    put("university", university)
                    put("degree", degree)
                    put("graduationYear", graduationYear) // Guaranteed to be Int
                    linkdIn?.takeIf { it.isNotBlank() }?.let { put("linkdIn", it) }
                }

                // 3. Call use case
                when (val result = updateApplicationUseCase(applicationId, updates)) {
                    is Resource.Success -> {
                        refreshApplications()
                        onSuccess()
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = result.message ?: "Update failed"
                        )
                    }
                    Resource.Loading -> Unit // Handled by isLoading
                }
            } catch (e: IllegalArgumentException) {
                state = state.copy(
                    isLoading = false,
                    error = e.message ?: "Invalid input"
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = "Failed to update application: ${e.message}"
                )
            }
        }
    }

    fun deleteApplication(applicationId: Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = deleteApplicationUseCase(applicationId)) {
                is Resource.Success -> {
                    if (result.data == true) {
                        loadApplications() // Refresh the list
                    }
                }
                is Resource.Error -> {
                    state = state.copy(error = result.message)
                }
                Resource.Loading -> Unit // Already set loading
            }
            state = state.copy(isLoading = false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun canUpdate(deadline: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val deadlineDate = LocalDate.parse(deadline, formatter)
            LocalDate.now().isBefore(deadlineDate)
        } catch (e: Exception) {
            false
        }
    }
}

data class StudentApplicationsState(
    val applications: List<Application> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)