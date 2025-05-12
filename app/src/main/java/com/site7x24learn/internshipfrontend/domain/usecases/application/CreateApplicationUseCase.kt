package com.site7x24learn.internshipfrontend.domain.usecases.application

import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationRequest
import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import javax.inject.Inject

class CreateApplicationUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(
        applicationRequest: ApplicationRequest,
        resumeFile: ByteArray,
        fileName: String
    ): Resource<Int> {
        return repository.createApplication(applicationRequest, resumeFile, fileName)
    }
}