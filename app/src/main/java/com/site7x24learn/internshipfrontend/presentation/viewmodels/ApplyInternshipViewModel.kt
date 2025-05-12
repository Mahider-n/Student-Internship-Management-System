
package com.site7x24learn.internshipfrontend.presentation.viewmodels



import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationRequest
import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.domain.usecases.application.CheckExistingApplicationUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.application.CreateApplicationUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.application.UpdateApplicationUseCase
import com.site7x24learn.internshipfrontend.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApplyInternshipViewModel @Inject constructor(
    private val repository: ApplicationRepository,
    private val updateApplicationUseCase: UpdateApplicationUseCase,
    private val createApplicationUseCase: CreateApplicationUseCase,
    private val checkExistingApplicationUseCase: CheckExistingApplicationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ApplyInternshipState())
    val state = _state.asStateFlow()

    private val _uiState = MutableStateFlow(ApplyInternshipUiState())
    val uiState = _uiState.asStateFlow()

    private var applicationContext: Context? = null
    var isEditMode by mutableStateOf(false)

    fun resetStateForEdit() {
        _state.update { ApplyInternshipState() }
    }

    fun onEvent(event: ApplyInternshipEvent) {
        when (event) {
            is ApplyInternshipEvent.OnUniversityChange ->
                _state.update { it.copy(university = event.university) }
            is ApplyInternshipEvent.OnDegreeChange ->
                _state.update { it.copy(degree = event.degree) }
            is ApplyInternshipEvent.OnGraduationYearChange ->
                _state.update { it.copy(graduationYear = event.graduationYear) }
            is ApplyInternshipEvent.OnLinkedInChange ->
                _state.update { it.copy(linkedIn = event.linkedIn) }
            is ApplyInternshipEvent.OnResumeSelected ->
                _state.update { it.copy(resumeUri = event.uri, resumeFileName = event.fileName) }
            is ApplyInternshipEvent.SubmitApplication -> {
                applicationContext = event.context
                submitApplication(event.internshipId)
            }
            is ApplyInternshipEvent.UpdateApplication -> {
                applicationContext = event.context
                viewModelScope.launch {
                    updateApplication(event.applicationId)
                }

            }
        }
    }

    fun loadExistingApplication(applicationId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = repository.getApplicationById(applicationId)) {
                is Resource.Success -> result.data?.let { app ->
                    _state.update {
                        ApplyInternshipState(
                            university = app.university,
                            degree = app.degree,
                            graduationYear = app.graduationYear.toString(),
                            linkedIn = app.linkdIn ?: ""
                        )
                    }
                    _uiState.update { it.copy(isLoading = false) }
                }
                is Resource.Error -> _uiState.update {
                    it.copy(isLoading = false, error = result.message)
                }
                Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
            }
        }
    }


    private fun submitApplication(internshipId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val validationErrors = validateFields()
            if (validationErrors.isNotEmpty()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        fieldErrors = validationErrors,
                        isApplicationSubmitted = false
                    )
                }
                return@launch
            }

            val resumeBytes = applicationContext?.let { context ->
                state.value.resumeUri?.let { uri ->
                    context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                }
            } ?: run {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Resume is required",
                        isApplicationSubmitted = false
                    )
                }
                return@launch
            }

            val applicationRequest = ApplicationRequest(
                internshipId = internshipId,
                university = state.value.university,
                degree = state.value.degree,
                graduationYear = state.value.graduationYear.toIntOrNull() ?: 0,
                linkdIn = state.value.linkedIn
            )

            when (val result = createApplicationUseCase(
                applicationRequest,
                resumeBytes,
                state.value.resumeFileName ?: "resume.pdf"
            )) {
                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isApplicationSubmitted = true,
                        error = null,
                        fieldErrors = emptyMap()
                    )
                }
                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = result.message,
                        isApplicationSubmitted = false
                    )
                }
                Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
            }
        }
    }

    private suspend fun handleCreateApplication(internshipId: Int) {
        val resumeBytes = applicationContext?.let { context ->
            state.value.resumeUri?.let { uri ->
                context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            }
        } ?: run {
            _uiState.update { it.copy(error = "Resume is required") }
            return
        }

        val applicationRequest = ApplicationRequest(
            internshipId = internshipId,
            university = state.value.university,
            degree = state.value.degree,
            graduationYear = state.value.graduationYear.toIntOrNull() ?: 0,
            linkdIn = state.value.linkedIn
        )

        when (val result = createApplicationUseCase(
            applicationRequest,
            resumeBytes,
            state.value.resumeFileName ?: "resume.pdf"
        )) {
            is Resource.Success -> _uiState.update {
                it.copy(isApplicationSubmitted = true, error = null)
            }
            is Resource.Error -> _uiState.update {
                it.copy(error = result.message)
            }
            Resource.Loading -> Unit
        }
    }

    private suspend fun updateApplication(applicationId: Int) {
        val updates = mapOf(
            "university" to state.value.university,
            "degree" to state.value.degree,
            "graduationYear" to state.value.graduationYear.toInt(),
            "linkdIn" to state.value.linkedIn
        )


        when (val result = updateApplicationUseCase(applicationId, updates)) {
            is Resource.Success -> _uiState.update {
                it.copy(isApplicationSubmitted = true, error = null)
            }
            is Resource.Error -> _uiState.update {
                it.copy(error = result.message)
            }
            Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
        }
    }

    private fun validateFields(): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        if (state.value.university.isBlank()) errors["university"] = "University required"
        if (state.value.degree.isBlank()) errors["degree"] = "Degree required"
        state.value.graduationYear.toIntOrNull()?.let {
            if (it !in 2000..2030) errors["graduationYear"] = "Invalid year"
        } ?: run { errors["graduationYear"] = "Year required" }
        if (state.value.linkedIn.isBlank()) errors["linkedIn"] = "LinkedIn required"
        if (state.value.resumeUri == null) errors["resume"] = "Resume required"
        return errors
    }

    // State classes
    data class ApplyInternshipState(
        val university: String = "",
        val degree: String = "",
        val graduationYear: String = "",
        val linkedIn: String = "",
        val resumeUri: Uri? = null,
        val resumeFileName: String? = null
    )

    data class ApplyInternshipUiState(
        val isLoading: Boolean = false,
        val isApplicationSubmitted: Boolean = false,
        val error: String? = null,
        val fieldErrors: Map<String, String> = emptyMap()
    )

    sealed class ApplyInternshipEvent {
        data class OnUniversityChange(val university: String) : ApplyInternshipEvent()
        data class OnDegreeChange(val degree: String) : ApplyInternshipEvent()
        data class OnGraduationYearChange(val graduationYear: String) : ApplyInternshipEvent()
        data class OnLinkedInChange(val linkedIn: String) : ApplyInternshipEvent()
        data class OnResumeSelected(val uri: Uri, val fileName: String) : ApplyInternshipEvent()
        data class SubmitApplication(val internshipId: Int, val context: Context) : ApplyInternshipEvent()
        data class UpdateApplication(val applicationId: Int, val context: Context) : ApplyInternshipEvent()
    }
}
