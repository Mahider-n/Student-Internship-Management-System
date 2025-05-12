package com.site7x24learn.internshipfrontend.domain.usecases.application

import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import javax.inject.Inject

class DeleteApplicationUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(applicationId: Int): Resource<Boolean> {
        return repository.deleteApplication(applicationId)
    }
}