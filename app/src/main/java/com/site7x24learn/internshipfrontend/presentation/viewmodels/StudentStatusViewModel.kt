package com.site7x24learn.internshipfrontend.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.site7x24learn.internshipfrontend.data.repositories.PreviewStudentStatusRepository
//import com.site7x24learn.internshipfrontend.domain.models.application.StudentStatus
import com.site7x24learn.internshipfrontend.domain.repositories.StudentStatusRepository
import com.site7x24learn.internshipfrontend.domain.usecases.student.GetStudentsUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.student.UpdateStudentStatusUseCase
import com.site7x24learn.internshipfrontend.presentation.screens.student.StudentStatusUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentStatusViewModel @Inject constructor(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val updateStatusUseCase: UpdateStudentStatusUseCase
) : ViewModel() {
    // Add error state
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    private val _uiState = MutableStateFlow(StudentStatusUiState())
    val uiState: StateFlow<StudentStatusUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getStudentsUseCase().collect { list ->
                _uiState.value = StudentStatusUiState(students = list)
            }
        }
    }

    fun updateStatus(id: Int, newStatus: String) {
        viewModelScope.launch {
            try {
                updateStatusUseCase(id, newStatus)
                _uiState.update { currentState ->
                    val updatedList = currentState.students.map { student ->
                        if (student.id == id) student.copy(status = newStatus) else student
                    }
                    currentState.copy(students = updatedList)
                }
            } catch (e: Exception) {
                _errorState.value="Update failed: ${e.message}"
            }
        }
    }
}