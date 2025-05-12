package com.site7x24learn.internshipfrontend.data.repositories

import com.site7x24learn.internshipfrontend.data.datasources.models.request.CreateInternshipRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.request.UpdateInternshipRequestDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.CategoryDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.InternshipDataDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.InternshipListResponseDto
import com.site7x24learn.internshipfrontend.data.datasources.models.response.InternshipResponseDto
import com.site7x24learn.internshipfrontend.data.datasources.remote.ApiService
import com.site7x24learn.internshipfrontend.data.datasources.remote.BaseResponseDto
import com.site7x24learn.internshipfrontend.domain.models.internships.Category
import com.site7x24learn.internshipfrontend.domain.models.internships.Internship
import com.site7x24learn.internshipfrontend.domain.repositories.InternshipRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Singleton




@Singleton
class InternshipRepositoryImpl(
    private val apiService: ApiService
) : InternshipRepository {

    override suspend fun getCategories(): Resource<List<Category>> {
        return try {
            val response = apiService.getCategories()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Resource.Success(
                        body.data.categories.map { dto ->
                            Category(
                                id = dto.category_id,
                                name = dto.category_name
                            )
                        }
                    )
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("API error: ${response.code()}")
            }
        } catch (e: HttpException) {
            Resource.Error("HTTP error: ${e.message}")
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        }
    }

    override suspend fun createInternship(
        title: String,
        description: String?,
        deadline: String,
        companyName: String,
        categoryId: Int
    ): Resource<Internship> {
        return try {
            val response = apiService.createInternship(
                CreateInternshipRequestDto(
                    title = title,
                    description = description,
                    deadline = deadline,
                    company_name = companyName,
                    category_id = categoryId
                )
            )

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Resource.Success(
                        Internship(
                            id = body.data.internship_id,
                            title = body.data.title,
                            description = body.data.description ?: "",
                            deadline = body.data.deadline,
                            companyName = body.data.company_name,
                            categoryName = body.data.category_name,
                            createdByName = body.data.created_by_name,
                            createdAt = body.data.created_at,
                            status = body.data.status,
                            isActive = body.data.is_active
                        )
                    )
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Failed with status: ${response.code()}")
            }
        } catch (e: HttpException) {
            Resource.Error("HTTP error: ${e.message}")
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        }
    }

    override suspend fun getInternships(): Resource<List<Internship>> {
        return try {
            val response = apiService.getInternships()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Resource.Success(
                        body.data.map { dto ->
                            Internship(
                                id = dto.internship_id,  // Make sure this matches DTO
                                title = dto.title,
                                description = dto.description ?: "",
                                deadline = dto.deadline,
                                companyName = dto.company_name,  // Note underscore
                                categoryName = dto.category_name,  // Note underscore
                                createdByName = dto.created_by_name,  // Note underscore
                                createdAt = dto.created_at,  // Note underscore
                                status = dto.status,
                                isActive = dto.is_active  // Note underscore
                            )
                        }
                    )
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Failed with status: ${response.code()}")
            }
        } catch (e: HttpException) {
            Resource.Error("HTTP error: ${e.message}")
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        }
    }
    override suspend fun getInternshipById(id: Int): Resource<Internship> {
        return try {
            val response = apiService.getInternshipById(id)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Resource.Success(
                        Internship(
                            id = body.data.internship_id,
                            title = body.data.title,
                            description = body.data.description ?: "",
                            deadline = body.data.deadline,
                            companyName = body.data.company_name,
                            categoryName = body.data.category_name,
                            createdByName = body.data.created_by_name,
                            createdAt = body.data.created_at,
                            status = body.data.status,
                            isActive = body.data.is_active
                        )
                    )
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Failed with status: ${response.code()}")
            }
        } catch (e: HttpException) {
            Resource.Error("HTTP error: ${e.message}")
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        }
    }

    override suspend fun updateInternship(
        id: Int,
        title: String?,
        description: String?,
        deadline: String?,
        companyName: String?,
        categoryId: Int?
    ): Resource<Boolean> {
        return try {
            val response = apiService.updateInternship(
                id,
                UpdateInternshipRequestDto(
                    title = title,
                    description = description,
                    deadline = deadline,
                    company_name = companyName,
                    category_id = categoryId
                )
            )

            if (response.isSuccessful) {
                Resource.Success(true)
            } else {
                Resource.Error("Failed with status: ${response.code()}")
            }
        } catch (e: HttpException) {
            Resource.Error("HTTP error: ${e.message}")
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        }
    }

    override suspend fun deleteInternship(id: Int): Resource<Boolean> {
        return try {
            val response = apiService.deleteInternship(id)
            if (response.isSuccessful) {
                Resource.Success(true)
            } else {
                Resource.Error("Failed with status: ${response.code()}")
            }
        } catch (e: HttpException) {
            Resource.Error("HTTP error: ${e.message}")
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        }
    }
}