package com.site7x24learn.internshipfrontend.domain.repositories

import com.site7x24learn.internshipfrontend.domain.models.application.Application
import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationRequest
import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationStatus
import com.site7x24learn.internshipfrontend.utils.Resource

interface ApplicationRepository {
    suspend fun createApplication(
        applicationRequest: ApplicationRequest,
        resumeFile: ByteArray,
        fileName: String
    ): Resource<Int>

    suspend fun getUserApplications(): Resource<List<Application>>
    suspend fun getApplicationById(applicationId: Int): Resource<Application>
    suspend fun updateApplication(
        applicationId: Int,
        updates: Map<String, Any>
    ): Resource<Boolean>

    suspend fun deleteApplication(applicationId: Int): Resource<Boolean>
    suspend fun checkExistingApplication(internshipId: Int): Resource<Boolean>

    // For admin
    suspend fun updateApplicationStatus(
        applicationId: Int,
        status: ApplicationStatus
    ): Resource<Boolean>
}