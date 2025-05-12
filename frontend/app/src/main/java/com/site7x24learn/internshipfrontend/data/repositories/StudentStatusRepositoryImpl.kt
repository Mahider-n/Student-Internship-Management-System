package com.site7x24learn.internshipfrontend.data.repositories




import com.site7x24learn.internshipfrontend.data.datasources.models.request.UpdateStatusRequestDto
import kotlinx.coroutines.flow.flow
import com.site7x24learn.internshipfrontend.data.datasources.remote.ApiService
import com.site7x24learn.internshipfrontend.domain.models.application.StudentStatus
import com.site7x24learn.internshipfrontend.domain.repositories.StudentStatusRepository
import kotlinx.coroutines.flow.Flow
import com.site7x24learn.internshipfrontend.utils.Constants.BASE_URL

class StudentStatusRepositoryImpl(
    private val api: ApiService
) : StudentStatusRepository {

    override fun getStudents(): Flow<List<StudentStatus>> = flow {
        try {
            val response = api.getApplications() // This returns StudentsResponse object
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.success == true) {
                    // Map from ApplicationDto to StudentStatus
                    val students = body.data.map { application ->
                        StudentStatus(
                            id = application.id,
                            name = application.studentName,
                            email = application.companyName,
                            resume = application.resumePath?.let {
                                "${BASE_URL}${it.replace("\\", "/")}"
                            } ?: "No resume",
                            status = application.status
//                            id = application.id,
//                            name = "N/A", // ⚠️ Temporary - see note below
//                            email = "user${application.userId}@temp.com", // ⚠️ Temporary
//                            resume = application.resumePath ?: "No resume",
//                            status = application.status
                        )
                    }
                    emit(students)
                }
            }
            // Extract students list from response and emit
        } catch (e: Exception) {
            // Handle error (e.g., log or re-throw)
        }
    }

    override suspend fun updateStatus(id: Int, status: String) {
        // Create an instance of UpdateStatusRequestDto with the status string
        val request = UpdateStatusRequestDto(status)

        // Now call the API with the correct type
        api.updateApplicationStatus(id, request)
    }
}