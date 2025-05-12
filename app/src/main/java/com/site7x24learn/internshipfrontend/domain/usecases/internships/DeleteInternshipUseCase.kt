package com.site7x24learn.internshipfrontend.domain.usecases.internships

import com.site7x24learn.internshipfrontend.domain.repositories.InternshipRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import javax.inject.Inject

class DeleteInternshipUseCase @Inject constructor(
    private val repository: InternshipRepository
) {
    suspend operator fun invoke(id: Int): Resource<Unit> {
        return try {
            repository.deleteInternship(id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}
