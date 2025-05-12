package com.site7x24learn.internshipfrontend.presentation.screens.student

import com.site7x24learn.internshipfrontend.domain.models.application.StudentStatus

data class StudentStatusUiState(
    val students: List<StudentStatus> = emptyList()
)
