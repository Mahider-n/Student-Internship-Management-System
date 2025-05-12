package com.site7x24learn.internshipfrontend.domain.usecases.application

import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationStatus
import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import javax.inject.Inject

class UpdateApplicationStatusUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(
        applicationId: Int,
        status: ApplicationStatus
    ): Resource<Boolean> {
        return repository.updateApplicationStatus(applicationId, status)
    }
}