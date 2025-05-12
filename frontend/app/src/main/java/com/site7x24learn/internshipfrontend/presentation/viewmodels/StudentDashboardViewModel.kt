
package com.site7x24learn.internshipfrontend.presentation.viewmodels



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.site7x24learn.internshipfrontend.domain.models.application.Application
import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentDashboardViewModel @Inject constructor(
    private val repository: ApplicationRepository
) : ViewModel() {
    private val _state = MutableStateFlow(StudentDashboardState())
    val state = _state.asStateFlow()

    // Track refresh state separately from initial load
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        loadApplications()
    }

    fun loadApplications() {
        viewModelScope.launch {
            // Only show full loading indicator on initial load
            if (!_isRefreshing.value) {
                _state.update { it.copy(isLoading = true, error = null) }
            }

            when (val result = repository.getUserApplications()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            applications = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Failed to load applications"
                        )
                    }
                }
                Resource.Loading -> Unit // Handled by state update
            }
            _isRefreshing.value = false
        }
    }

    fun refreshApplications() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadApplications()
        }
    }

    fun deleteApplication(applicationId: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = repository.deleteApplication(applicationId)) {
                is Resource.Success -> {
                    if (result.data == true) {
                        _state.update {
                            it.copy(
                                applications = state.value.applications.filter { it.id != applicationId },
                                isLoading = false
                            )
                        }
                        onSuccess()
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Failed to delete application"
                        )
                    }
                }
                Resource.Loading -> Unit // Handled by state update
            }
        }
    }

    fun updateApplication(
        applicationId: Int,
        updates: Map<String, Any>,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }


            when (val result = repository.updateApplication(applicationId, updates)) {
                is Resource.Success -> {
                    if (result.data == true) {
                        refreshApplications() // Force reload to get fresh data
                        onSuccess()
                    } else {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = "Update completed but no changes detected"
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Update failed. Please try again."
                        )
                    }
                }
                Resource.Loading -> Unit // Handled by state update
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}

data class StudentDashboardState(
    val applications: List<Application> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)