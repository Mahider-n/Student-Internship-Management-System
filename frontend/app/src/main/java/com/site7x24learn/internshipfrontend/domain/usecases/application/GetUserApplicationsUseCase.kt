package com.site7x24learn.internshipfrontend.domain.usecases.application

import com.site7x24learn.internshipfrontend.domain.models.application.Application
import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import javax.inject.Inject

class GetUserApplicationsUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(): Resource<List<Application>> {
        return repository.getUserApplications()
    }
}