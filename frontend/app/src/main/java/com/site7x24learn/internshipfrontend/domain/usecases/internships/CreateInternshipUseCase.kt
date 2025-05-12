package com.site7x24learn.internshipfrontend.domain.usecases.internships

import com.site7x24learn.internshipfrontend.domain.models.internships.Internship
import com.site7x24learn.internshipfrontend.domain.repositories.InternshipRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import javax.inject.Inject

class CreateInternshipUseCase @Inject constructor(
    private val repository: InternshipRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String?,
        deadline: String,
        companyName: String,
        categoryId: Int
    ): Resource<Internship> {
        return repository.createInternship(
            title = title,
            description = description,
            deadline = deadline,
            companyName = companyName,
            categoryId = categoryId
        )
    }
}