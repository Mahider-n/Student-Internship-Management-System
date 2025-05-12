package com.site7x24learn.internshipfrontend.domain.usecases.application

import com.site7x24learn.internshipfrontend.domain.models.application.Application
import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import javax.inject.Inject

class GetApplicationByIdUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(applicationId: Int): Resource<Application> {
        return repository.getApplicationById(applicationId)
    }
}