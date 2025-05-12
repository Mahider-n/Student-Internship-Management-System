package com.site7x24learn.internshipfrontend.domain.usecases.internships

import com.site7x24learn.internshipfrontend.domain.models.internships.Category
import com.site7x24learn.internshipfrontend.domain.repositories.InternshipRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: InternshipRepository
) {
    suspend operator fun invoke(): Resource<List<Category>> {
        return repository.getCategories()
    }
}