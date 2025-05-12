package com.site7x24learn.internshipfrontend.domain.usecases.student


import com.site7x24learn.internshipfrontend.domain.models.application.StudentStatus
import com.site7x24learn.internshipfrontend.domain.repositories.StudentStatusRepository
import kotlinx.coroutines.flow.Flow


class GetStudentsUseCase(
    private val repository: StudentStatusRepository
) {
    operator fun invoke(): Flow<List<StudentStatus>> {
        return repository.getStudents()
    }
}
