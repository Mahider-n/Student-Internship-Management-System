package com.site7x24learn.internshipfrontend.data.repositories


import com.site7x24learn.internshipfrontend.data.datasources.models.request.ApplicationUpdateRequest
import com.site7x24learn.internshipfrontend.data.datasources.models.request.UpdateStatusRequestDto

import com.site7x24learn.internshipfrontend.data.datasources.remote.ApiService
import com.site7x24learn.internshipfrontend.domain.models.application.Application
import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationRequest
import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationStatus
import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import okhttp3.MediaType.Companion.toMediaType

import okhttp3.MultipartBody

import okhttp3.RequestBody.Companion.toRequestBody

import javax.inject.Inject
import retrofit2.HttpException

import java.io.IOException

class ApplicationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ApplicationRepository {

    override suspend fun createApplication(
        applicationRequest: ApplicationRequest,
        resumeFile: ByteArray,
        fileName: String
    ): Resource<Int> {
        return try {
            // Convert each field to RequestBody
            val internshipId = applicationRequest.internshipId.toString()
                .toRequestBody("text/plain".toMediaType())
            val university = applicationRequest.university
                .toRequestBody("text/plain".toMediaType())
            val degree = applicationRequest.degree
                .toRequestBody("text/plain".toMediaType())
            val graduationYear = applicationRequest.graduationYear.toString()
                .toRequestBody("text/plain".toMediaType())
            val linkdIn = applicationRequest.linkdIn
                .toRequestBody("text/plain".toMediaType())

            // Create file part
            val resumePart = MultipartBody.Part.createFormData(
                "resume",
                fileName,
                resumeFile.toRequestBody("application/pdf".toMediaType())
            )

            // Make API call
            val response = apiService.createApplication(
                internshipId,
                university,
                degree,
                graduationYear,
                linkdIn,
                resumePart
            )

            if (response.isSuccessful) {
                Resource.Success(response.body()?.applicationId ?: 0)
            } else {
                val errorBody = response.errorBody()?.string()
                Resource.Error(errorBody ?: "Error ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getUserApplications(): Resource<List<Application>> {
        return try {
            val response = apiService.getUserApplications()
            if (response.isSuccessful) {
                response.body()?.let { wrapper ->
                    if (wrapper.success) {
                        Resource.Success(wrapper.data.map { it.toDomain() })
                    } else {
                        Resource.Error(wrapper.message ?: "Request failed")
                    }
                } ?: Resource.Error("Empty response")
            } else {
                Resource.Error("HTTP ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun getApplicationById(applicationId: Int): Resource<Application> {
        return try {
            val response = apiService.getApplicationById(applicationId)
            if (response.isSuccessful) {
                response.body()?.let { wrapper ->
                    if (wrapper.success) {
                        Resource.Success(wrapper.data.toDomain())
                    } else {
                        Resource.Error(wrapper.message ?: "Request failed")
                    }
                } ?: Resource.Error("Empty response")
            } else {
                Resource.Error("HTTP ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error")
        }
    }

    override suspend fun updateApplication(
        applicationId: Int,
        updates: Map<String, Any>
    ): Resource<Boolean> {
        return try {
            // 1. Validate and extract required fields
            val university = updates["university"] as? String
                ?: return Resource.Error("University is required")

            val degree = updates["degree"] as? String
                ?: return Resource.Error("Degree is required")

            val graduationYear = updates["graduationYear"] as? Int
                ?: return Resource.Error("Graduation year must be provided as Int")

            // 2. Create request DTO
            val request = ApplicationUpdateRequest(
                university = university,
                degree = degree,
                graduation_year = graduationYear,
                linkdIn = updates["linkdIn"] as? String // Optional field
            )

            // 3. Make API call
            val response = apiService.updateApplication(applicationId, request)

            // 4. Handle response
            when {
                response.isSuccessful -> Resource.Success(true)
                else -> {
                    val errorBody = try {
                        response.errorBody()?.string() ?: "HTTP ${response.code()}"
                    } catch (e: Exception) {
                        "Failed to parse error response"
                    }
                    Resource.Error(errorBody)
                }
            }

        } catch (e: ClassCastException) {
            Resource.Error("Invalid field types in request")
        } catch (e: Exception) {
            Resource.Error("Update failed: ${e.message ?: "Unknown error"}")
        }
    }

    override suspend fun deleteApplication(applicationId: Int): Resource<Boolean> {
        return try {
            apiService.deleteApplication(applicationId)
            Resource.Success(true)
        } catch (e: HttpException) {
            Resource.Error(e.message ?: "An error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun checkExistingApplication(internshipId: Int): Resource<Boolean> {
        return try {
            val exists = apiService.checkExistingApplication(internshipId)
            Resource.Success(exists)
        } catch (e: HttpException) {
            Resource.Error(e.message ?: "An error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }

    override suspend fun updateApplicationStatus(
        applicationId: Int,
        status: ApplicationStatus
    ): Resource<Boolean> {
        return try {
            apiService.updateApplicationStatus(applicationId, UpdateStatusRequestDto(status=status.name))
            Resource.Success(true)
        } catch (e: HttpException) {
            Resource.Error(e.message ?: "An error occurred")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection")
        }
    }
}