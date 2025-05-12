package com.site7x24learn.internshipfrontend.domain.usecases.application

import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import javax.inject.Inject

class UpdateApplicationUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(
        applicationId: Int,
        updates: Map<String, Any>
    ): Resource<Boolean> {
        return repository.updateApplication(applicationId, updates)
    }
}