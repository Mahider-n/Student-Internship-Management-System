package com.site7x24learn.internshipfrontend.domain.repositories

import com.site7x24learn.internshipfrontend.domain.models.internships.Category
import com.site7x24learn.internshipfrontend.domain.models.internships.Internship
import com.site7x24learn.internshipfrontend.utils.Resource

interface InternshipRepository {
    suspend fun getCategories(): Resource<List<Category>>
    suspend fun createInternship(
        title: String,
        description: String?,
        deadline: String,
        companyName: String,
        categoryId: Int
    ): Resource<Internship>
    suspend fun getInternships(): Resource<List<Internship>>
    suspend fun getInternshipById(id: Int): Resource<Internship>
    suspend fun updateInternship(
        id: Int,
        title: String?,
        description: String?,
        deadline: String?,
        companyName: String?,
        categoryId: Int?
    ): Resource<Boolean>

    suspend fun deleteInternship(id: Int): Resource<Boolean>
//    suspend fun reviewApplications(internshipId: Int): Resource<Boolean>


}

