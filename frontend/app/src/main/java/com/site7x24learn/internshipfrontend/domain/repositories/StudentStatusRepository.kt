package com.site7x24learn.internshipfrontend.domain.repositories


import com.site7x24learn.internshipfrontend.domain.models.application.StudentStatus
import kotlinx.coroutines.flow.Flow

interface StudentStatusRepository {
    fun getStudents(): Flow<List<StudentStatus>>
    suspend fun updateStatus(id: Int, status: String)
}