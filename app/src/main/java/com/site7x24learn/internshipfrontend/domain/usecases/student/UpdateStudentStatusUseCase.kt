package com.site7x24learn.internshipfrontend.domain.usecases.student

import com.site7x24learn.internshipfrontend.domain.repositories.StudentStatusRepository


class UpdateStudentStatusUseCase(
    private val repository: StudentStatusRepository
) {
    suspend operator fun invoke(id: Int, status: String) {
        repository.updateStatus(id, status)
    }
}
