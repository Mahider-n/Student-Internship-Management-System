package com.site7x24learn.internshipfrontend.domain.usecases.internships

import com.site7x24learn.internshipfrontend.domain.repositories.InternshipRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import javax.inject.Inject

class UpdateInternshipUseCase @Inject constructor(
    private val repository: InternshipRepository
) {
    suspend operator fun invoke(
        id: Int,
        title: String?,
        description: String?,
        deadline: String?,
        companyName: String?,
        categoryId: Int?
    ): Resource<Boolean> {
        return repository.updateInternship(
            id = id,
            title = title,
            description = description,
            deadline = deadline,
            companyName = companyName,
            categoryId = categoryId
        )
    }
}